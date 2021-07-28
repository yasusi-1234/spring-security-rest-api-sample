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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.form.SalesForm;
import com.example.demo.controller.form.SalesSearchForm;
import com.example.demo.controller.form.error.BindErrorHelper;
import com.example.demo.domain.model.Sales;
import com.example.demo.domain.service.SalesService;
import com.example.demo.domain.service.exception.ProductStockUpdateFailureException;
import com.example.demo.domain.service.exception.SalesRequestFailureException;
import com.example.demo.domain.service.helper.SalesView;

@RestController
@RequestMapping("api/sales")
@CrossOrigin // test用
public class SalesController {

	private final SalesService salesService;

	@Autowired
	public SalesController(SalesService salesService) {
		super();
		this.salesService = salesService;
	}

	@GetMapping("/test")
	public ResponseEntity<List<SalesView>> test() {
		return ResponseEntity.ok(salesService.test());
	}

	@GetMapping("/test2")
	public ResponseEntity<List<SalesView>> test2() {
		return ResponseEntity.ok(salesService.test2());
	}

	@GetMapping
	public ResponseEntity<List<Sales>> findAllSalse() {
		return ResponseEntity.ok(salesService.findAllSales());
	}

	@GetMapping(path = "/{salesId}")
	public ResponseEntity<Sales> findById(@PathVariable("salesId") String salesId) {
		return ResponseEntity.ok(salesService.findById(salesId));
	}

	@PostMapping(path = "/register")
	public ResponseEntity<Object> saveSales(@Validated @RequestBody SalesForm form, BindingResult bindingResult) {
		System.out.println(form);
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = BindErrorHelper.getErrorDetailsMap(bindingResult);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
		}
		return ResponseEntity.ok(salesService.save(form));
	}

	@PostMapping(path = "/search")
	public ResponseEntity<List<Sales>> postSearch(@RequestBody SalesSearchForm form) {
		return ResponseEntity.ok(salesService.findBySalesSearchForm(form));
	}

	@ExceptionHandler
	public ResponseEntity<Object> productStockUpdateFailureExceptionHandler(ProductStockUpdateFailureException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

	@ExceptionHandler
	public ResponseEntity<Object> salesRequestFailureExceptionExceptionHandler(SalesRequestFailureException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getResponseMap());
	}
}
