package unk.test0;

import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import unk.test0.generated.tables.Item;
import unk.test0.generated.tables.records.ItemRecord;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

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
            value = "/item",
            produces = TEXT_HTML_VALUE /*APPLICATION_JSON_VALUE*/)
    public String query() throws SQLException {
        Item item = new Item();
        return DSL.using(ds, SQLDialect.POSTGRES)
                .fetch(item).formatHTML();
    }

    @RequestMapping(method = RequestMethod.PUT,
            value = "/item",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = TEXT_HTML_VALUE /*APPLICATION_JSON_VALUE*/)
    public String upsert(@RequestBody JSONWrapper wrapper) throws SQLException {
        ItemRecord record = wrapper.getRec();
        Item item = new Item();
        int found = 0;
        if (record.getId() != null)
            found =  DSL.using(ds, SQLDialect.POSTGRES)
                    .fetch("select count(id) from item where id='"+record.getId()+"'")
                    .size();
        if (found == 1){
            DSL.using(ds, SQLDialect.POSTGRES)
                    .query("update item set path='"+record.getPath()
                            +"', dsc='"+record.getDsc()+"' where id='"+wrapper.getId()+"'")
                    .execute();
            return "Record "+wrapper.getId()+" updated: path="+wrapper.getPath()
                    +", dsc="+wrapper.getDsc();
        }else if (found==0){
            DSL.using(ds, SQLDialect.POSTGRES)
                    .query("insert into item (path,dsc) values('"+record.getPath()
                            +"', '"+record.getDsc()+"')")
                    .execute();
            return "New record inserted: path="+wrapper.getPath()
                    +", dsc="+wrapper.getDsc();

        }
        return "Wrong database state on merge ITEM";
    }

    @RequestMapping(method = RequestMethod.DELETE,
            value = "/item",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = TEXT_HTML_VALUE /*APPLICATION_JSON_VALUE*/)
    public String drop(@RequestBody JSONWrapper wrapper) throws SQLException {
        ItemRecord record = wrapper.getRec();
        if (record.getId() != null){
            DSL.using(ds, SQLDialect.POSTGRES)
                .query("delete from item where id='"+wrapper.getId()+"'")
                .execute();
            return "Record "+wrapper.getId()+" deleted";
        }else
            return "Record ID not specified. Nothing to delete";
    }

        @ExceptionHandler
    void handleIllegalArgumentException(
            IllegalArgumentException e,
            HttpServletResponse response) throws IOException {

        response.sendError(HttpStatus.BAD_REQUEST.value());

    }
}