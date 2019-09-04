package com.ming;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class TrendTradingBacktestApplication {
    public static void main(String[] args){
        int port = RandomUtils.nextInt(1000,4000);
        new SpringApplicationBuilder(TrendTradingBacktestApplication.class).properties("server.port=" + port).run(args);
    }
}
