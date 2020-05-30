package unk.test1;

import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import unk.test0.generated.tables.Item;
import unk.test0.generated.tables.records.ItemRecord;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.util.MimeTypeUtils.*;


@RestController
@RequestMapping("t2")
public class RestController {

    private boolean usernameAlreadyExists;
    private DataSource ds = new DBConnector();

    @RequestMapping(method = RequestMethod.GET,
            value = "/", produces = TEXT_HTML_VALUE)
    public String index() {
        return "<HTML><BODY><button onClick=\"\">Get Data</button><br><button>PUT DATA</button></BODY></HTML>";
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "/item",
            produces = TEXT_HTML_VALUE /*APPLICATION_JSON_VALUE*/)
    public String query() throws SQLException {
        Item item = new Item();
        return "Test1 GET";
    }

    @RequestMapping(method = RequestMethod.PUT,
            value = "/item",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = TEXT_HTML_VALUE /*APPLICATION_JSON_VALUE*/)
    public String upsert(@RequestBody JSONWrapper wrapper) throws SQLException {        
        return "TEST1 PUT";
    }

    @RequestMapping(method = RequestMethod.DELETE,
            value = "/item",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = TEXT_HTML_VALUE /*APPLICATION_JSON_VALUE*/)
    public String drop(@RequestBody JSONWrapper wrapper) throws SQLException {
        return "TEST1 DELETE";
    }

        @ExceptionHandler
    void handleIllegalArgumentException(
            IllegalArgumentException e,
            HttpServletResponse response) throws IOException {

        response.sendError(HttpStatus.BAD_REQUEST.value());

    }
}