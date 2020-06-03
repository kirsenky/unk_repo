package unk.test.db;

import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import unk.test.db.generated.tables.Item;
import unk.test.db.generated.tables.records.ItemRecord;

import javax.sql.DataSource;
import java.sql.SQLException;

public class Processor {
    private DataSource ds = new DBConnector();

    public String get(JSONWrapper wrapper) throws SQLException {
        if (wrapper==null || wrapper.getId()==null){
            Item item = new Item();
            return DSL.using(ds, SQLDialect.POSTGRES)
                    .fetch(item).formatHTML();
        } else {
            return DSL.using(ds, SQLDialect.POSTGRES)
                    .fetch("select * from item where id='"+wrapper.getId()+"'").formatJSON();
        }
    }

    public String upsert(JSONWrapper wrapper) throws SQLException {
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

    public String drop(JSONWrapper wrapper) throws SQLException {
        ItemRecord record = wrapper.getRec();
        if (record.getId() != null){
            DSL.using(ds, SQLDialect.POSTGRES)
                    .query("delete from item where id='"+wrapper.getId()+"'")
                    .execute();
            return "Record "+wrapper.getId()+" deleted";
        }else
            return "Record ID not specified. Nothing to delete";
    }

}
