package com.eztrip.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() { // Docket -> Swagger 설정의 핵심
        return new Docket(DocumentationType.OAS_30)
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select() // Api selectorBuilder 생성
                .apis(RequestHandlerSelectors.basePackage("eztrip.api")) // API 패키지 경로 설정
//                .paths(PathSelectors.ant("/wemmy/**")) // 특정 경로에 대해 문서화
              .paths(PathSelectors.any()) // 모든 API 경로에 대해 문서화
                .build()
                .apiInfo(apiInfo()); // API 문서에 대한 정보 추가
    }

    private ApiInfo apiInfo(){

        return new ApiInfoBuilder()
                .title("EzTrip API 문서")
                .description("")
                .version("1.0")
                .build();
    }

    private SecurityContext securityContext(){

        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth(){

        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;

        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
    }

    private ApiKey apiKey(){

        return new ApiKey("Authorization", "Authorization", "Header");
    }

}
