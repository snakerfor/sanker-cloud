package com.snaker;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.snaker.system.annotation.EnableRyFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.snaker.*.mapper")
@EnableRyFeignClients
public class RddsGenApp
{
    public static void main(String[] args)
    {
        SpringApplication.run(RddsGenApp.class, args);
    }
}
