package com.publicis.sapient.credit.card.api.configuration;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author poonamgupta
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.select().apis(RequestHandlerSelectors.basePackage("com.publicis.sapient.credit.card.api.controller"))
				.paths(regex("/api/v1/*"))
				.build().apiInfo(getApiInfo());
	}

	private ApiInfo getApiInfo() {
		return new ApiInfo(
				"Credit Card Systeme",
				"The API facilitate adding new credit card and getting the list of all credit cards in the system",
				"1.0.0",
				"TERMS OF SERVICE URL",
				new Contact("NAME", "URL", "EMAIL"),
				"LICENSE",
				"LICENSE URL",
				Collections.emptyList()
		);
	}
}
