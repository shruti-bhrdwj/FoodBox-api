package com.foodboxapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket productAPI() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.foodboxapi.controllers"))
				.build().apiInfo(metaDeta());
	}
	
	private ApiInfo metaDeta() {
		ApiInfo apiInfo = new ApiInfo("FOODBOX REST API ",
				"Ecommerce Products REST API for online store FOODBOX", "1.0.0", "Term of service as per user guide lines.", 
				new Contact("Shruti Sharma", "", ""), 
				"Apache Lience Version 2.0", "https://www.apache.org/licenses/LICENSE-2.0.html");
		return apiInfo;
	}
}

