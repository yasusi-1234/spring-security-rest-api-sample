package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.form.helper.BindErrorHelper;
import com.example.demo.controller.form.seles.ListSalesForm;
import com.example.demo.controller.form.seles.SalesForm;
import com.example.demo.controller.form.seles.SalesSearchForm;
import com.example.demo.domain.model.Sales;
import com.example.demo.domain.service.SalesService;
import com.example.demo.domain.service.exception.ProductStockUpdateFailureException;
import com.example.demo.domain.service.exception.SalesIdNotFoundException;
import com.example.demo.domain.service.exception.SalesRequestFailureException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/sales")
@CrossOrigin // test用
@Slf4j
public class SalesController {

	private final SalesService salesService;

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
		return ResponseEntity.status(HttpStatus.CREATED).body(salesService.save(form));
	}

	@PostMapping(path = "/multiple/register")
	public ResponseEntity<Object> saveMultipleSales(@Validated @RequestBody ListSalesForm form,
			BindingResult bindingResult) {
		System.out.println(bindingResult.hasErrors());
		System.out.println(form);
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = BindErrorHelper.getErrorDetailsMap(bindingResult);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(salesService.multipleSave(form));
	}

	@PostMapping(path = "/search")
	public ResponseEntity<List<Sales>> postSearch(@RequestBody SalesSearchForm form) {
		return ResponseEntity.status(HttpStatus.CREATED).body(salesService.findBySalesSearchForm(form));
	}

	@DeleteMapping(path = "/delete")
	public ResponseEntity<Object> deleteSalesAndUpdateProduct(@RequestBody(required = false) String salesId) {
		if (!StringUtils.hasText(salesId)) {
			// 指定のIdが不正な値の場合
			return ResponseEntity.badRequest().body("request salesId is null. please fill in the value.");
		}
		return ResponseEntity.ok(salesService.deleteBySalesId(salesId));
	}

	@DeleteMapping(path = "/multipleDelete")
	public ResponseEntity<Object> deleteSalesAndUpdateProduct(@RequestBody(required = false) List<String> salesIds) {
		System.out.println(salesIds);

		if (CollectionUtils.isEmpty(salesIds)) {
			// 指定のIdが不正な値の場合
			return ResponseEntity.badRequest().body("request salesId is null or empty. please fill in the value.");
		}

		return ResponseEntity.ok(salesService.deleteBySalesIds(salesIds));
	}

	@ExceptionHandler
	public ResponseEntity<Object> productStockUpdateFailureExceptionHandler(ProductStockUpdateFailureException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMap());
	}

	@ExceptionHandler
	public ResponseEntity<Object> salesRequestFailureExceptionExceptionHandler(SalesRequestFailureException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getResponseMap());
	}

	@ExceptionHandler
	public ResponseEntity<Object> salesIdNotFoundExceptionHandler(SalesIdNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

	@ExceptionHandler
	public ResponseEntity<Object> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
		log.warn(ex.getMessage(), ex);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bad request");
	}
}
