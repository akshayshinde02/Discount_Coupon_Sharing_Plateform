package com.coupons;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DiscountCouponBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscountCouponBackendApplication.class, args);
	}

	// @Bean
	// public CommandLineRunner commandLineRunner(
	// 	AuthController aunAuthController
	// ){

	// 	return args ->{

	// 		var admin = User.builder()
	// 		.firstName("Admin")
	// 		.lastName("Admin")
	// 		.email("admin@gmail.com")
	// 		.password("password")
	// 		.role(com.coupons.enums.UserRole.ADMIN)
	// 		.build();

	// 		System.out.println("Admin Token: "+aunAuthController.createUserHandler(admin));

	// 	};
	// }

}
