/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package unk.test.grpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import unk.test.db.JSONWrapper;
import unk.test.db.Processor;
import unk.test.grpc.shared.QueryType;
import unk.test.grpc.shared.TestReply;
import unk.test.grpc.shared.TestRequest;
import unk.test.grpc.shared.TestServiceGrpc;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class GrpcServer {
    private static final Logger logger = Logger.getLogger(GrpcServer.class.getName());

    private Server server;

    private void start() throws IOException {
        /* The port on which the server should run */
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new TestServiceImpl())
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            try {
                GrpcServer.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            System.err.println("*** server shut down");
        }));
    }

    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final GrpcServer server = new GrpcServer();
        server.start();
        server.blockUntilShutdown();
    }

    static class TestServiceImpl extends TestServiceGrpc.TestServiceImplBase {
        private Processor db = new Processor();

        private JSONWrapper decode(TestRequest req) {
            JSONWrapper wrapper = new JSONWrapper();
            wrapper.setId(req.getId());
            wrapper.setPath(req.getPath());
            wrapper.setDsc(req.getDsc());
            return wrapper;
        }

        private String queryDB(int reqType, JSONWrapper wrapper) {
            try {
                switch (reqType) {
                    case QueryType.GET_VALUE:
                        return this.db.get(wrapper);
                    case QueryType.PUT_VALUE:
                        return this.db.upsert(wrapper);
                    case QueryType.DELETEE_VALUE:
                        return this.db.drop(wrapper);
                    case QueryType.UNSPECIFIED_VALUE:
                    default:
                        return "TestServiceGrpc failed to process unknown query.";
                }
            } catch (SQLException e) {
                return e.getMessage();
            }
        }

        @Override
        public void askTestServer(TestRequest req, StreamObserver<TestReply> responseObserver) {
            String result;
            JSONWrapper wrapper = decode(req);
            int reqType = req.getType().getNumber();
            result = queryDB(reqType, wrapper);
            TestReply.Builder builder = TestReply.newBuilder();
            TestReply reply = builder.setResult(result).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }

}