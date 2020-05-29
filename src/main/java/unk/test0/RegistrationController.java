package unk.test0;

import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import unk.test0.generated.tables.Item;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

import static org.springframework.util.MimeTypeUtils.*;


@RestController
@RequestMapping("t1")
public class RegistrationController {

    private boolean usernameAlreadyExists;
    private DataSource ds = new DBConnector();

    @RequestMapping(method = RequestMethod.GET,
            value = "/", produces = TEXT_HTML_VALUE)
    public String index() {
        return "<HTML><BODY><button onClick=\"\">Get Data</button><br><button>PUT DATA</button></BODY></HTML>";
    }


    @RequestMapping(method = RequestMethod.GET,
            value = "/query",
            produces = TEXT_HTML_VALUE /*APPLICATION_JSON_VALUE*/)
    public String query(/*@RequestBody User user*/) throws SQLException {
        Item item = new Item();
        return DSL.using(ds, SQLDialect.POSTGRES)
                .fetch(item).formatHTML();
    }

    @ExceptionHandler
    void handleIllegalArgumentException(
            IllegalArgumentException e,
            HttpServletResponse response) throws IOException {

        response.sendError(HttpStatus.BAD_REQUEST.value());

    }
}