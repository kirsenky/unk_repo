package unk.test.rest.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import unk.test.db.JSONWrapper;

@SpringBootApplication
public class RestClient {

	private static final Logger log = LoggerFactory.getLogger(RestClient.class);

	public static void main(String[] args) {
		SpringApplication.run(RestClient.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			JSONWrapper item= restTemplate.getForObject(
					"http://localhost:8080/t1/item", JSONWrapper.class);
			log.info(item.toString());
		};
	}
}