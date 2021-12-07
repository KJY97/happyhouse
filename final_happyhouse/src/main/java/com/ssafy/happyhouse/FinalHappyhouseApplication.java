package com.ssafy.happyhouse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// http://localhost:9999/happyhouse/swagger-ui.html
@SpringBootApplication
@MapperScan(basePackages = "com.ssafy.happyhouse.model.dao")
public class FinalHappyhouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalHappyhouseApplication.class, args);
	}

}
