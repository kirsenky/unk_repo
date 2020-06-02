package unk.test.rest.server;

import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import unk.test.db.*;
import java.io.IOException;
import java.sql.SQLException;

import static org.springframework.util.MimeTypeUtils.*;


@RestController
@RequestMapping("t1")
public class RestSrvController {
    private Processor db=new Processor();
    @RequestMapping(method = RequestMethod.GET,
            value = "/", produces = TEXT_HTML_VALUE)
    public String index() {
        return "<HTML><BODY><button onClick=\"\">Get Data</button><br><button>PUT DATA</button></BODY></HTML>";
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "/item",
            produces = TEXT_HTML_VALUE)
    public String query(@RequestBody(required = false) JSONWrapper wrapper) throws SQLException {
            return db.get(wrapper);
    }

    @RequestMapping(method = RequestMethod.PUT,
            value = "/item",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = TEXT_HTML_VALUE)
    public String upsert(@RequestBody JSONWrapper wrapper) throws SQLException {
        return db.upsert(wrapper);
    }

    @RequestMapping(method = RequestMethod.DELETE,
            value = "/item",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = TEXT_HTML_VALUE)
    public String drop(@RequestBody JSONWrapper wrapper) throws SQLException {
            return db.drop(wrapper);
    }

        @ExceptionHandler
    void handleIllegalArgumentException(
            IllegalArgumentException e,
            HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}