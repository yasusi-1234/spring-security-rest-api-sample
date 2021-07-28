package com.example.demo.controller;

import java.util.List;

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

import com.example.demo.controller.form.EmployeeForm;
import com.example.demo.controller.form.error.BindErrorHelper;
import com.example.demo.domain.model.Employee;
import com.example.demo.domain.service.EmployeeService;
import com.example.demo.domain.service.RoleService;
import com.example.demo.domain.service.exception.MailAddressAlreadyRegisteredException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/employee")
@CrossOrigin // This is Test
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

	@PostMapping(path = "/register")
	public ResponseEntity<Object> registerEmployee(@Validated @RequestBody EmployeeForm form,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			// Validationチェックに引っかかった場合
			log.info("bind error occurred");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(BindErrorHelper.getErrorDetailsMap(bindingResult));
		}
		Employee employee = employeeService.save(form);
		return ResponseEntity.ok(employee);
	}

	@ExceptionHandler
	public ResponseEntity<Object> MailAddressAlreadyRegisteredExceptionHandler(
			MailAddressAlreadyRegisteredException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}
}
