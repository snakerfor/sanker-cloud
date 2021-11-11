package com.snaker;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.snaker.*.mapper")
@EnableFeignClients(basePackages = "com.snaker")
public class RddsDfsApp
{
    public static void main(String[] args)
    {
        SpringApplication.run(RddsDfsApp.class, args);
    }
}
