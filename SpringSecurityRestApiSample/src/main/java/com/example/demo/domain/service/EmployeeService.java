package com.example.demo.domain.service;

import java.util.List;

import com.example.demo.controller.form.employee.EmployeeForm;
import com.example.demo.domain.model.Employee;

public interface EmployeeService {

	List<Employee> findAllEmployee();

	Employee findById(String employeeId);

	Employee save(EmployeeForm form);

	Employee findByMailAddress(String mailAddress);

}
