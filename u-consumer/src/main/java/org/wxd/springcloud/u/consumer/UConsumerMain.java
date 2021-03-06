package org.wxd.springcloud.u.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Created by wangxd on 2017/9/16.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
public class UConsumerMain {
    public static void main(String[] args) {
        SpringApplication.run(UConsumerMain.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
