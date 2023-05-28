package org.example;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class Main {

    public static void main(String[] args) {
        //System.out.println(BCrypt.checkpw("$2a$10$v14c3zTBrAtRGktZB.4BROng4P8./CQ1iWxJl5ba.leKC2ZinDVBa"));
        SpringApplication.run(Main.class, args);
    }

}
