package com.meenie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ZhihuCopyApplication {

    public static void main(String[] args) {
        try{
            SpringApplication.run(ZhihuCopyApplication.class, args);
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

}
