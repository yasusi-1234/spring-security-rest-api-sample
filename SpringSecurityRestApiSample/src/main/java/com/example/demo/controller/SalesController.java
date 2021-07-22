package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.model.Sales;
import com.example.demo.domain.service.SalesService;

@RestController
@RequestMapping("api/sales")
public class SalesController {

	private final SalesService salesService;

	@Autowired
	public SalesController(SalesService salesService) {
		super();
		this.salesService = salesService;
	}

	@GetMapping
	public ResponseEntity<List<Sales>> findAllProceed() {
		return ResponseEntity.ok(salesService.findAllProceed());
	}

	@GetMapping(path = "/{proceedId}")
	public ResponseEntity<Sales> findById(@PathVariable("proceedId") String proceedId) {
		return ResponseEntity.ok(salesService.findById(proceedId));
	}
}
