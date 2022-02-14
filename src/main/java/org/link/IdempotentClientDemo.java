package org.link;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @Author: Gol
 */
@SpringBootApplication
public class IdempotentClientDemo extends ThreadPoolTaskExecutor {

    public static void main(String[] args){
        
        SpringApplication.run(IdempotentClientDemo.class,args);
    }

}
