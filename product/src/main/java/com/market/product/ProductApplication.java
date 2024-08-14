package com.market.product;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

	@Autowired
	ProductRepository productRepository;

	@PostConstruct
	public void init() {
		Product product = Product.builder().productName("product").productQuantity(100000).build();
		productRepository.save(product);
	}

}
