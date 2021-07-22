package com.example.demo.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.model.Employee;
import com.example.demo.domain.repository.EmployeeRepository;
import com.example.demo.domain.service.helper.EmployeeSpecificationHelper;
import com.fasterxml.jackson.databind.ObjectMapper;

@Transactional(readOnly = true)
@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;

	private final ObjectMapper objectMapper;

	@Autowired
	public EmployeeServiceImpl(EmployeeRepository employeeRepository, ObjectMapper objectMapper) {
		super();
		this.employeeRepository = employeeRepository;
		this.objectMapper = objectMapper;
	}

	@Override
	public List<Employee> findAllEmployee() {
		return employeeRepository.findAll(EmployeeSpecificationHelper.joinFetch());
	}

	@Override
	public Employee findById(String employeeId) {
		return employeeRepository.findById(employeeId).orElse(null);
	}
}
