package com.example.demo.domain.service;

import java.util.List;

import com.example.demo.domain.model.Employee;

public interface EmployeeService {

	List<Employee> findAllEmployee();

	Employee findById(String employeeId);

}