package com.yue.ysdk;

import com.yue.ysdk.client.YApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * YuApi 客户端配置
 *
 */
@Configuration
@ConfigurationProperties("yue.client")
@Data
@ComponentScan
public class YuApiClientConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public YApiClient yuApiClient() {
        return new YApiClient(accessKey, secretKey);
    }

}
