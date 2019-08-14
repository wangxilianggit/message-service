package com.panshi.hujin2.message.common.utils.ignore_ssl;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * 代理配置，开发环境，测试环境生效
 */
//@Profile({"dev","testina","testvi"})
//@Configuration
//@Getter
public class ProxyConfig {

//    @Value("${proxy.host}")
    private String proxyHost="127.0.0.1";

//    @Value("${proxy.port}")
    private Integer proxyPort=8118;
}

