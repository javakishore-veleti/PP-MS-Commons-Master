package jk.pp.ms.commons.configs.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jk.pp.engg.foundations.common.core.util.AppGlobalProperties;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@ConditionalOnProperty(name = "pp.ms.common.swagger.enabled", havingValue = "true")
@Configuration
public class SpringFoxConfig {

	@Autowired
	private AppGlobalProperties appGlobalProps;

	public static final String AUTHORIZATION_HEADER = "Authorization";

	@Bean
	public Docket api() {

		if (appGlobalProps.isSecurityEnabled()) {

			return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
					.securityContexts(Arrays.asList(securityContext())).securitySchemes(Arrays.asList(apiKey()))
					.select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
		}

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("Tech Summit App REST API", "", "1.0", "Terms of service",
				new Contact("TechSummitApp", "techsummitapp-admin@somedomain.com",
						"techsummitapp-admin@somedomain.com"),
				"License of API", "API license URL", Collections.emptyList());
	}

	private ApiKey apiKey() {
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
	}

}
