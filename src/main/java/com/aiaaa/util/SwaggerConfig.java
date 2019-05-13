package com.aiaaa.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
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
    public Docket createRestApi() {
        System.out.println("config init");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.aiaaa"))// 指定扫描包下面的注解
                .paths(PathSelectors.any())
                .build();
    }
    // 创建api的基本信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("集成Swagger2构建RESTful APIs")
                .description("集成Swagger2构建RESTful APIs")
                .termsOfServiceUrl("http://www.aiaaa.com/")
                .contact(new Contact("web","com.aiaaa","aisns@aiaaa.com"))
                .version("1.0.0")
                .build();
    }
}
