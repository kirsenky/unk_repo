package unk.test.t1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestApplication<context> {
    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }
}
