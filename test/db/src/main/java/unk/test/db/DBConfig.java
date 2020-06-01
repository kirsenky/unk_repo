package unk.test.db;

import javax.imageio.IIOException;
import java.io.*;
import java.net.URL;
import java.util.Properties;

public class DBConfig extends Properties {
    private String url;
    private String usr;
    private String pword;

    private void setUrl(String url) {
        this.url = url;
    }
    private void setUsr(String usr) {
        this.usr = usr;
    }
    private void setPword(String pword) {
        this.pword = pword;
    }
    private void load() throws IOException {
        URL url = getClass().getClassLoader().getResource("test.db.properties");
        InputStream inp = url.openStream();
        load(inp);
    }

    public  String getUrl(){
        return get("url").toString();
    }
    public  String getUsr(){
        return get("usr").toString();
    }
    public  String getPword(){
        return get("pword").toString();
    }
    public  String getDatabaseName(){
        String urlStr=get("url").toString();
        return urlStr.substring(urlStr.lastIndexOf('/')+1,urlStr.length());
    }

    public static DBConfig getConfig() {
        DBConfig conf=new DBConfig();
        try {
            conf.load();
        }catch (IOException e){
            System.err.println("Cannot open DB properties file: "+e.getMessage());
            return null;
        }
        System.out.println("===" + conf.get("usr") +':'+ conf.getDatabaseName()+"===");
        return conf;
    }
}
