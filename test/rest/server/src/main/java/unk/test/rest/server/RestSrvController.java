package unk.test.rest.server;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import unk.test.db.JSONWrapper;
import unk.test.db.Processor;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.logging.Logger;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static org.springframework.util.MimeTypeUtils.TEXT_HTML_VALUE;


@RestController
@RequestMapping("t1")
public class RestSrvController {
    private static final Logger logger = Logger.getLogger(RestSrvController.class.getName());
    private Processor db = new Processor();

    @RequestMapping(method = RequestMethod.GET,
            value = "/", produces = TEXT_HTML_VALUE)
    public String index() {
        return "<HTML><BODY>REST test</BODY></HTML>";
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "/item",
            produces = APPLICATION_JSON_VALUE)
    public String query(@RequestBody(required = false) JSONWrapper wrapper) {
        String res = db.get(wrapper);
        logger.info("Returning:"+res);
        return res;
    }

    @RequestMapping(method = RequestMethod.PUT,
            value = "/item",
            //consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public String upsert(@PathParam("id") String id,
                         @PathParam("path") String path,
                         @PathParam("dsc") String dsc,
                         @RequestBody(required = false)JSONWrapper wrapper) {
        if (wrapper==null) wrapper=new JSONWrapper();
        if (id!=null) wrapper.setId(id);
        wrapper.setDsc(dsc);
        wrapper.setPath(path);
        String res = db.upsert(wrapper);
        logger.info("Returning:"+res);
        return res;
    }

    @RequestMapping(method = RequestMethod.DELETE,
            value = "/item",
            //consumes = MediaType.APPLICATION_JSON_VALUE
            produces = APPLICATION_JSON_VALUE)
    public String drop(@PathParam("id") String id, @RequestBody(required = false) JSONWrapper wrapper) {
        if (wrapper==null) wrapper=new JSONWrapper();
        if(id!=null) wrapper.setId(id);
        String res = db.drop(wrapper);
        logger.info("Returning:"+res);
        return res;
    }

    @ExceptionHandler
    void handleIllegalArgumentException(
            IllegalArgumentException e,
            HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}