package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@RequestMapping("/test")
@RequiredArgsConstructor
@RestController
public class TestController {

	private final EmployeeRepository repository;

	@GetMapping("/{mailAddress}")
	public ResponseEntity<Object> test1(@PathVariable("mailAddress") String mailAddress) {
		return ResponseEntity.ok(repository.findByMail(mailAddress));
	}
}
