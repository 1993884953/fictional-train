package com.example.java.com.example.demo;

import com.example.java.jiemianbao.Denglu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
//		SpringApplication.run(DemoApplication.class, args);

		Denglu denglu =new Denglu();
		denglu.makedenglu();
	}

}
