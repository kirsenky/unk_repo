package unk.test.rest.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestServer<context> {
    public static void main(String[] args) {
        SpringApplication.run(RestServer.class, args);
    }
}
