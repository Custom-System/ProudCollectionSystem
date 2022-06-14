package com.proud.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.baomidou.mybatisplus.samples.crud.mapper")
public class MyBatisPlusConfig {

}
