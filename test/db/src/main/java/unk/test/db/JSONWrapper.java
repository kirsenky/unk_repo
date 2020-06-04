package unk.test.db;

import org.jooq.Record;
import unk.test.db.generated.tables.records.ItemRecord;

import java.util.UUID;

public class JSONWrapper {
    String id; //d9e842b2-e564-4b00-9607-1f522c24d1f7
    String path;
    String dsc;
    String comments;
    public JSONWrapper() {

    }

    public JSONWrapper(Record rec){
        this.id=rec.get("id").toString();
        this.path=rec.get("path").toString();
        this.dsc=rec.get("dsc").toString();
    }

    public ItemRecord getRec() {
        ItemRecord rec=new ItemRecord();
        if( id!=null && id.length() == 36 )
            rec.setId(UUID.fromString(id));
        rec.setPath(path);
        rec.setDsc(dsc);
        return rec;
    }

    public boolean isIdValid(){
        if ( id!=null && id.length() == 36 ) {
            UUID uid=UUID.fromString(id);
            return uid.toString().equalsIgnoreCase(id);
        }
        return false;
    }

    public String getId() {
        if (isIdValid())
            return id;
        else
            return null;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString(){
        return "{\"id\":\"" + (this.id != null ? id:"")
                + "\",\"path\":\""+(this.path != null ? path:"")
                + "\",\"dsc\":\""+(this.dsc != null ? dsc:"")
                + "\",\"comments\":\""+(this.comments != null ? comments:"")
                + "\"}";
    }
}
