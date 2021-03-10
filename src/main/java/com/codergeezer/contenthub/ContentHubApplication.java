package com.codergeezer.contenthub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author haidv
 * @version 1.0
 */
@EnableSwagger2
@EnableCaching
@ComponentScan(basePackages = "com.codergeezer")
@EnableJpaRepositories(basePackages = "com.codergeezer")
@SpringBootApplication
public class ContentHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentHubApplication.class, args);
    }

}
