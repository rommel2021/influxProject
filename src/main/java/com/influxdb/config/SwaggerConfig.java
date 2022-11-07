package com.influxdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    public static final Contact CONTACT = new Contact("qhy", "", "2859295586@qq.com");

    /**
     * 配置swagger的bean实例
     * 指定扫描com.influxdb.controller包下的接口
     * @return docket
     */
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.influxdb.controller"))
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfo(
                "api document of our influxdb project",
                "contact back-end with front-end",
                "1.0",
                "https://github.com/rommel2021/influxProject",
                CONTACT,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList()
        );
    }
}
