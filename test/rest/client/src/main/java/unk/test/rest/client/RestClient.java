package unk.test.rest.client;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RestClient {

	public static void main(String[] args) {
		SpringApplication.run(RestClient.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) {
				new TestRunner().execute(restTemplate);
				Runtime.getRuntime().halt(0);
			}
		};
	}
}