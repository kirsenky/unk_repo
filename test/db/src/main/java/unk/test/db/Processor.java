package unk.test.db;

import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import unk.test.db.generated.tables.Item;
import unk.test.db.generated.tables.records.ItemRecord;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;


public class Processor {
    private DataSource ds = new DBConnector();

    public String get(JSONWrapper wrapper) {
        List<JSONWrapper> list;
        StringBuilder result = new StringBuilder();

        if (wrapper == null || wrapper.getId() == null) {
            Item item = new Item();
            list = DSL.using(ds, SQLDialect.POSTGRES)
                    .fetch(item)
                    .stream()
                    .map(JSONWrapper::new)
                    .collect(Collectors.toList());

            boolean firstRec = true;
            for (JSONWrapper ent : list) {
                if (!firstRec)
                    result.append(',');
                result.append(ent);
                firstRec = false;
            }

        } else {
            result.append(new JSONWrapper(DSL.using(ds, SQLDialect.POSTGRES)
                    .fetch("select * from item where id='" + wrapper.getId() + "'")
                    .get(0)));
        }
        return result.toString();
    }

    public String upsert(JSONWrapper wrapper) {
        ItemRecord record = wrapper.getRec();
        int found = 0;
        if (record.getId() != null)
            found = Integer.parseInt(
                    DSL.using(ds, SQLDialect.POSTGRES)
                            .fetch("select count(id) from item where id='"
                                    + record.getId() + "'")
                            .get(0).get(0).toString()
            );
        if (found == 1) {
            DSL.using(ds, SQLDialect.POSTGRES)
                    .query("update item set path='" + record.getPath()
                            + "', dsc='" + record.getDsc() + "' where id='" + wrapper.getId() + "'")
                    .execute();
            wrapper.setComments("Record updated");
            return wrapper.toString();
        } else if (found == 0) {
            DSL.using(ds, SQLDialect.POSTGRES)
                    .query("insert into item (path,dsc) values('" + record.getPath()
                            + "', '" + record.getDsc() + "')")
                    .execute();
            wrapper.setComments("New record inserted");
            return wrapper.toString();

        }
        wrapper.setComments("Wrong database state on merge ITEM.");
        return wrapper.toString();
    }

    public String drop(JSONWrapper wrapper) {
        ItemRecord record = wrapper.getRec();
        if (record.getId() != null) {
            DSL.using(ds, SQLDialect.POSTGRES)
                    .query("delete from item where id='" + wrapper.getId() + "'")
                    .execute();
            wrapper.setComments("Record deleted.");
            return wrapper.toString();
        }
        wrapper.setComments("Record ID not specified. Nothing to delete");
        return wrapper.toString();
    }

}
