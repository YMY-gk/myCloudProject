package com.gk.activity.config;

/**
 * @author yumuyi
 * @version 1.0
 * @date 2021/6/1 23:26
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//通过configuration注解自动注入配置文件
@Configuration
//开启swagger功能
@EnableSwagger2
//如果有多个配置文件，以这个为准
@Primary
//实现SwaggerResourcesProvider，配置swagger接口文档的数据源
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true)         //是否启用swagger
                .apiInfo(apiInfo()) //
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.gk.company.LoginControler")) //指定扫描的报
                .paths(PathSelectors.any())  //过滤扫描路径下的包含“**”的路径
                .build() ;
    }

    @SuppressWarnings("deprecation")  //作者信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("郭奎API测试")     //swagger 名
                .description("个人测试用api")  // 描述
                .version("1.0")   //版本
                .termsOfServiceUrl("http://blog.csdn.net/penyoudi1")  //服务地址地址
                .contact("郭奎")   //作者信息
                .build();
    }

}
