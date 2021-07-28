package com.example.demo.domain.service;

import java.util.List;

import com.example.demo.controller.form.ProductForm;
import com.example.demo.domain.model.Product;

public interface ProductService {

	List<Product> findAllProduct();

	Product findById(String productId);

	Product save(ProductForm productForm);

	int updateStock(String productId, int subtractedValue);

}
