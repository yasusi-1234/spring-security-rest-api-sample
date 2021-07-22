package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.model.Employee;
import com.example.demo.domain.service.EmployeeService;
import com.example.demo.domain.service.RoleService;

@RestController
@RequestMapping("api/employee")
public class EmployeeController {

	private final EmployeeService employeeService;
	private final RoleService roleService;

	public EmployeeController(EmployeeService employeeService, RoleService roleService) {
		super();
		this.employeeService = employeeService;
		this.roleService = roleService;
	}

	@GetMapping
	public ResponseEntity<List<Employee>> findAllEmployee() {
		return ResponseEntity.ok(employeeService.findAllEmployee());
	}

	@GetMapping(path = "/{employeeId}")
	public ResponseEntity<Employee> findById(@PathVariable("employeeId") String employeeId) {
		return ResponseEntity.ok(employeeService.findById(employeeId));
	}
}
