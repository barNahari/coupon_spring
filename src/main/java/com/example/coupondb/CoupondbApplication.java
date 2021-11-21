package com.example.coupondb;

import com.example.coupondb.utils.Art;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoupondbApplication {

	public static void main(String[] args) {

		SpringApplication.run(CoupondbApplication.class, args);
		System.out.println(Art.localhost);
	}

}
