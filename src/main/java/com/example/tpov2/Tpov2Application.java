package com.example.tpov2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories("com.example.tpov2.repository")
public class Tpov2Application {

    public static void main(String[] args) {
        SpringApplication.run(Tpov2Application.class, args);
    }

}
