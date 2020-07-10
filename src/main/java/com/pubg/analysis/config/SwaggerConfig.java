package com.pubg.analysis.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author sunpeikai
 * @version MongoConfigProperties, v0.1 2020/7/10 12:42
 * @description
 */
@Configuration
@EnableSwagger2
@Profile({"local","remote"})
@ConditionalOnProperty(prefix = "swagger",value = {"enable"},havingValue = "true")
public class SwaggerConfig {
	@Bean
	public Docket buildDocket() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(buildApiInf()).select()
				.apis(RequestHandlerSelectors.basePackage("com.pubg.analysis.controller"))
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo buildApiInf() {
		return new ApiInfoBuilder().title("PUBG analysis").version("1.0").build();
	}
}
