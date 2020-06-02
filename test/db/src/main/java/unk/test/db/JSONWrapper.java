package unk.test.db;

import unk.test.db.generated.tables.records.ItemRecord;

import java.util.UUID;


public class JSONWrapper {
    String id; //d9e842b2-e564-4b00-9607-1f522c24d1f7
    String path;
    String dsc;

    public ItemRecord getRec() {
        ItemRecord rec=new ItemRecord();
        if( id!=null && id.length() == 36 )
            rec.setId(UUID.fromString(id));
        rec.setPath(path);
        rec.setDsc(dsc);
        return rec;
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

    public boolean isIdValid(){
        if( id!=null && id.length() == 36 ){
            UUID uid=UUID.fromString(id);
            return uid.toString().equalsIgnoreCase(id);
        }
        return false;
    }
}