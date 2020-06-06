package unk.test.rest.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import unk.test.db.JSONWrapper;

import java.util.Date;
import java.util.HashMap;

public class TestRunner {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private static final String itemUrl = "http://localhost:8080/t1/item";

    private void iterate(RestTemplate restTemplate, int iter) {
        JSONWrapper wrapObj = new JSONWrapper();
        wrapObj.setPath("path_" + iter);
        wrapObj.setDsc("dsc_" + iter);
        HashMap<String, String> uriVars = new HashMap<>();
        uriVars.put("path", wrapObj.getPath());
        uriVars.put("dsc", wrapObj.getDsc());
        LOGGER.debug("===> Iteration started:{}", new Date());
        restTemplate.put(itemUrl + "?path=" + wrapObj.getPath()+"&dsc="+wrapObj.getDsc(), null, uriVars);
        wrapObj = restTemplate.getForObject(itemUrl, JSONWrapper.class);
        if ((wrapObj!=null) && wrapObj.isIdValid()) {
            LOGGER.debug("{}=> inserted: {}", wrapObj.getId(),wrapObj);
            String newDesc = "DSC_amended";
            restTemplate.put(itemUrl + "?id="+wrapObj.getId()+"&path=" + wrapObj.getPath()+"&dsc="+newDesc,null);
            wrapObj = restTemplate.getForObject(itemUrl, JSONWrapper.class);
            if ((wrapObj!=null) && newDesc.equalsIgnoreCase(wrapObj.getDsc())) {
                LOGGER.debug("{}=> updated: {}", wrapObj.getId(),wrapObj);
            }
            restTemplate.delete(itemUrl + "?id=" + wrapObj.getId());
            JSONWrapper delObj;
            delObj = restTemplate.getForObject(itemUrl, JSONWrapper.class, wrapObj);
            if ((delObj == null) || !delObj.isIdValid()) {
                LOGGER.debug(wrapObj.getId() + "=> deleted");
            }
        }
        LOGGER.debug("===> Iteration finished:" + new Date());
    }

    void execute(RestTemplate restTemplate) {
        JSONWrapper wrapObj = new JSONWrapper();
        wrapObj = (restTemplate.getForObject(itemUrl, JSONWrapper.class, wrapObj));
        do {
            if ((wrapObj != null) && wrapObj.isIdValid()) {
                restTemplate.delete(itemUrl + "?id=" + wrapObj.getId());
                wrapObj = (restTemplate.getForObject(itemUrl, JSONWrapper.class, wrapObj));
            }
        } while ((wrapObj != null) && wrapObj.isIdValid());
        int maxI=100;
        int maxj=10;
        LOGGER.info("=== Starting performance test for REST client/server ==");
        for (int j = 0; j < maxj; j++) {
            Date start=new Date();
            for (int i = 0; i < maxI; i++) {
                iterate(restTemplate, j*maxI+i);
            }
            Date stop=new Date();
            long tDiff=stop.getTime()-start.getTime();
            LOGGER.info("Time spent for iteration {} (100 records inserted/updated/deleted) is {} Seconds", j, (stop.getTime()-start.getTime())/1000 );
        }
        LOGGER.info("=== Performance test for REST client/server finished ==");
    }
}
