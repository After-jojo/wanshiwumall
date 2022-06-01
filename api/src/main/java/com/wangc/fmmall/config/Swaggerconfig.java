package com.wangc.fmmall.config;

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
public class Swaggerconfig {
    @Bean
    public Docket getDocket(){
        //创建封面信息对象
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        apiInfoBuilder.title("《万事屋商城》后端接⼝说明")
                .description("此⽂档详细说明了万事屋商城项⽬后端接⼝规范....")
                .version("v 2.0.1")
                .contact(new Contact("After拂晓","https://github.com/After-jojo","chen@wang.com"));
        ApiInfo apiInfo = apiInfoBuilder.build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wangc.fmmall.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}
