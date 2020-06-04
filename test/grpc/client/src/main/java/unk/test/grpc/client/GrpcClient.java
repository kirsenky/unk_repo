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

package unk.test.grpc.client;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unk.test.db.JSONWrapper;
import unk.test.grpc.shared.QueryType;
import unk.test.grpc.shared.Reply;
import unk.test.grpc.shared.Request;
import unk.test.grpc.shared.TestServiceGrpc;

import java.util.concurrent.TimeUnit;


public class GrpcClient {
    private static final Logger logger = LoggerFactory.getLogger(GrpcClient.class);

    private TestServiceGrpc.TestServiceBlockingStub blockingStub;

    /** Construct client for accessing HelloWorld server using the existing channel. */
    public GrpcClient(Channel channel) {
        // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's responsibility to
        // shut it down.

        // Passing Channels to code makes code easier to test and makes it easier to reuse Channels.
        blockingStub = TestServiceGrpc.newBlockingStub(channel);
    }

    public String askServer(QueryType method, JSONWrapper wrp) {
        logger.debug("Sending to server ");
        Request.Builder builder=Request.newBuilder();
        builder.setType(method);
        if (wrp.getId() != null)
            builder.setId(wrp.getId());
        if (wrp.getPath() != null)
            builder.setPath(wrp.getPath());
        if (wrp.getDsc() != null)
            builder.setDsc(wrp.getDsc());
        Request request = builder.build();
        Reply response;
        try {
            response = blockingStub.askTestServer(request);
            return response.getResult();
        } catch (StatusRuntimeException e) {
            logger.warn("RPC failed: {}", e);
            return e.getMessage();
        }
    }

    /**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting. The second argument is the target server.
     */
    public static void main(String[] args) throws Exception {
        // Access a service running on the local machine on port 50051
        String target = "localhost:50051";


        // Create a communication channel to the server, known as a Channel. Channels are thread-safe
        // and reusable. It is common to create channels at the beginning of your application and reuse
        // them until the application shuts down.
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build();
        try {
            GrpcClient client = new GrpcClient(channel);
            TestRunner runner = new TestRunner(client);
            runner.runTests();

        } finally {
            // ManagedChannels use resources like threads and TCP connections. To prevent leaking these
            // resources the channel should be shut down when it will no longer be used. If it may be used
            // again leave it running.
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }

}
