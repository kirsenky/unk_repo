package unk.test.grpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unk.test.db.JSONWrapper;
import unk.test.grpc.shared.QueryType;

import java.util.Date;

public class TestRunner {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private GrpcClient client;

    TestRunner(GrpcClient client) {
        this.client = client;
    }

    public JSONWrapper parseResult(final String input) {
        JSONWrapper res = new JSONWrapper();
        if ("".equalsIgnoreCase(input))
            res = null;
        else {
            int idPos;
            if (input.length() > 43) {
                idPos = input.indexOf("\"id\":");
                if (idPos > 0) {
                    String id = input.substring(idPos + 6, 43);
                    res.setId(id);
                }
            }
        }
        return res;
    }

    public void iterate(int iter) {
        String result;
        JSONWrapper wrp=new JSONWrapper();
        wrp.setPath("grpc_path_"+iter);
        wrp.setPath("grpc_dsc_"+iter);
        result = client.askServer(QueryType.PUT, wrp);
        LOGGER.debug("Put: {}",result);
        result = client.askServer(QueryType.GET, wrp);
        LOGGER.debug("Inserted: {}",result);
        wrp=parseResult(result);
        wrp.setPath("grpc_path_"+iter+"_amended");
        result = client.askServer(QueryType.PUT, wrp);
        LOGGER.debug("Amended: {}",result);
        wrp.setId("");
        result = client.askServer(QueryType.GET, wrp);
        wrp=parseResult(result);
        String oldId=wrp.getId();
        client.askServer(QueryType.DEL, wrp);
        result = client.askServer(QueryType.GET, wrp);
        wrp=parseResult(result);
        if (wrp==null) LOGGER.debug("Deleted {}",oldId);
    }

    public void runTests() {
        JSONWrapper wrp;
        do {
            LOGGER.debug("Starting iteration");
            wrp = new JSONWrapper();
            String result = client.askServer(QueryType.GET, wrp);
            wrp=parseResult(result);
            LOGGER.debug("qerying: {}", wrp);
            if (wrp!=null) {
                result = client.askServer(QueryType.DEL, wrp);
                LOGGER.debug("cleaning: {} {}", wrp, result);
            }
            result = client.askServer(QueryType.GET, wrp);
            wrp=parseResult(result);
            LOGGER.debug("Server response: {}", result);
        } while (wrp != null && wrp.getId() != null);

        int maxI=100;
        int maxj=10;
        LOGGER.info("=== Starting performance test for gRPC client/server ==");
        for (int j = 0; j < maxj; j++) {
            Date start=new Date();
            for (int i = 0; i < maxI; i++) {
                iterate(j*maxI+i);
            }
            Date stop=new Date();
            long tDiff=stop.getTime()-start.getTime();
            LOGGER.info("Time spent for iteration {} (100 records inserted/updated/deleted) is {} Seconds", j, (stop.getTime()-start.getTime())/1000 );
        }
        LOGGER.info("=== Performance test for gRPC client/server finished ==");
    }
}
