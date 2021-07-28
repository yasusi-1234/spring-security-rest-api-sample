package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.form.CategoryForm;
import com.example.demo.controller.form.ProductForm;
import com.example.demo.controller.form.ProductUpdateForm;
import com.example.demo.controller.form.error.BindErrorHelper;
import com.example.demo.domain.model.Category;
import com.example.demo.domain.model.Product;
import com.example.demo.domain.service.CategoryService;
import com.example.demo.domain.service.ProductService;
import com.example.demo.domain.service.exception.ProductStockUpdateFailureException;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

@RestController
@RequestMapping("api/product")
@CrossOrigin // test用
public class ProductController {

	private final ProductService productService;

	private final CategoryService categoryService;

	@Autowired
	public ProductController(ProductService productService, CategoryService categoryService) {
		super();
		this.productService = productService;
		this.categoryService = categoryService;
	}

	@GetMapping
	public ResponseEntity<List<Product>> findAll() {
		return ResponseEntity.ok(productService.findAllProduct());
	}

	@GetMapping("/category")
	public ResponseEntity<List<Category>> findAllCategory() {
		return ResponseEntity.ok(categoryService.findAllCategory());
	}

	@GetMapping(path = "/{product}")
	public ResponseEntity<Product> findById(@PathVariable("productId") String productId) {
		return ResponseEntity.ok(productService.findById(productId));
	}

	@PostMapping(path = "/register")
	public ResponseEntity<Object> postProduct(@Validated @RequestBody ProductForm productForm,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = BindErrorHelper.getErrorDetailsMap(bindingResult);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
		}
		return ResponseEntity.ok(productService.save(productForm));
	}

	@PatchMapping(path = "/update")
	public ResponseEntity<Object> updateProduct(@Validated @RequestBody ProductUpdateForm form,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = BindErrorHelper.getErrorDetailsMap(bindingResult);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
		}
		return ResponseEntity.ok(productService.updateStock(form.getProductId(), form.getSubtractedValue()));
	}

	@PostMapping(path = "/category/register")
	public ResponseEntity<Object> postCategory(@Validated @RequestBody CategoryForm form, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = BindErrorHelper.getErrorDetailsMap(bindingResult);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
		}
		return ResponseEntity.ok(categoryService.save(form));
	}

	@ExceptionHandler
	public ResponseEntity<Object> mysqlDataTruncationHandler(MysqlDataTruncation ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

	@ExceptionHandler
	public ResponseEntity<Object> productStockUpdateFailureExceptionHandler(ProductStockUpdateFailureException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

}
