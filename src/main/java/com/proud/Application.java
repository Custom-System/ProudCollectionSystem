package com.proud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@MapperScan(basePackages = {
        "com.proud.dao",
        "com.gitee.sunchenbin.mybatis.actable.dao.controller"
})
@ComponentScan(basePackages = {"com.gitee.sunchenbin.mybatis.actable.manager.*", "com.proud.*"})
public class Application {
    public static void main(String [] args){
        SpringApplication.run(Application.class,args);
    }

}
