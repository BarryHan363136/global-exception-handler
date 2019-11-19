package com.barry.global.exception;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * GlobalExceptionStarter [spring boot] 主方法
 *
 * 启动方式，右键->run/debug->Spring Boot App
 *  https://blog.csdn.net/canfengli/article/details/88786339
 *
 * @author
 *
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableScheduling
public class GlobalExceptionStarter {

    public static void main(String[] args) {
        SpringApplication.run(GlobalExceptionStarter.class, args);
    }

}
