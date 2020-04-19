package cl.geraud.demoApi.rs;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import Util.Log4jListener;
import Util.Tool;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class DemoApiRsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApiRsApplication.class, args);
	}
	
	@Bean
	public Docket swaggerSpringMvcPlugin() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("cl.geraud.demoApi.rs"))
				.paths(PathSelectors.any()).build().apiInfo(apiInfo());
	}
	
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Demo Api Rest").description("description")
				.contact(new Contact("name", "url", "email")).license("Apache License Version 2.0")
				.licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE").version("2.0").build();
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return mapper;
	}
	
	
	
	
	@Bean
	public ResponseErrorHandler responseErrorHandler() {
		return new ResponseErrorHandler() {
			
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				HttpStatus.Series serie = response.getStatusCode().series();
				return HttpStatus.Series.CLIENT_ERROR.equals(serie) || HttpStatus.Series.SERVER_ERROR.equals(serie);
			}
			
			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				//Do nothing
				
			}
		};
	}
	
	@Bean
	public RestTemplate restTemplate(ResponseErrorHandler responseErrorHandler) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(responseErrorHandler);
		
		return restTemplate;
	}
	
	@Bean
	public Log4jListener executeLog4jListener() {
		return new Log4jListener();
	}
	
	@Bean
	public Tool tool() {
		return new Tool();
	}

	
}
