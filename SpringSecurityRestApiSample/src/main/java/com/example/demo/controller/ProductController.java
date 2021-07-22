package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.model.Product;
import com.example.demo.domain.service.ProductService;

@RestController
@RequestMapping("api/pruduct")
public class ProductController {

	private final ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		super();
		this.productService = productService;
	}

	@GetMapping
	public ResponseEntity<List<Product>> findAll() {
		return ResponseEntity.ok(productService.findAllProduct());
	}

	@GetMapping(path = "/{product}")
	public ResponseEntity<Product> findById(@PathVariable("productId") String productId) {
		return ResponseEntity.ok(productService.findById(productId));
	}
}
