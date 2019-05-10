package com.panshi.hujin2;


import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author shenjiankang
 * @date 2018/6/19
 */
@SpringBootApplication
//@ImportResource("classpath:dubbo.xml")
@MapperScan(basePackages = "com.panshi.hujin2.message.dao.mapper")
//@EnableTransactionManagement(proxyTargetClass=true)
//@ComponentScan(basePackages={"com.codingapi.tx.*","com.iking.provider.*","com.panshi.hujin2.*"})
public class AppStarter extends SpringBootServletInitializer /*implements TxManagerTxUrlService*/ {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppStarter.class);

//    @Autowired
//    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(AppStarter.class, args);
    }

//    /**
//     * 获取代理连接池
//     * @return
//     */
//    @Bean
//    public DataSource dataSource() {
//        LOGGER.info("------代理连接池------");
//
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setUrl(env.getProperty("spring.datasource.url"));
//        dataSource.setUsername(env.getProperty("spring.datasource.username"));
//        dataSource.setPassword(env.getProperty("spring.datasource.password"));
//        return dataSource;
//    }
//    /**
//     * 注入LCN的代理连接池
//     * @return
//     */
//    @Bean("transactionManager")
//    public PlatformTransactionManager txManager(){
//        return new DataSourceTransactionManager(dataSource());
//    }
//
//    @Override
//    public String getTxUrl() {
//        return env.getProperty("tx.manager.url");
//    }

}
