package unk.test.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

@Configuration
@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
public class DBConfig {
    public static String url;
    public static String usr;
    public static String pword;

    @Value("${spring.datasource.username}")
    public void setUrl(String url) {
        this.url = url;
    }

    @Value("${spring.datasource.username}")
    public void setUsr(String usr) {
        this.usr = usr;
    }

    @Value("${spring.datasource.password}")
    public void setPword(String pword) {
        this.pword = pword;
    }

    public static String getUrl(){
        return url;
    }
    public static String getUsr(){
        return usr;
    }
    public static String getPword(){
        return pword;
    }
    public static String getDatabaseName(){
        return url.substring(url.lastIndexOf('/')+1,url.length());
    }

    @PostConstruct
    public void init() {
        System.out.println("===" + usr +':'+ getDatabaseName()+"===");
    }
}
