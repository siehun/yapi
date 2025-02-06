package com.yue.ygateway.utils;

import com.yue.ygateway.filter.YBloomFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.NoSuchAlgorithmException;

@Configuration
public class FilterClient {
    @Bean
    public YBloomFilter yBloomFilter() throws NoSuchAlgorithmException {
        return new YBloomFilter();
    }
}
