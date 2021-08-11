package com.example.demo.config.security.service;

import java.util.Objects;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.example.demo.domain.model.Employee;
import com.example.demo.domain.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmployeeDetailsService implements UserDetailsService {

	private final EmployeeService employeeService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (!StringUtils.hasText(username)) {
			throw new UsernameNotFoundException("request username is null.");
		}
		Employee employee = employeeService.findByMailAddress(username);
		if (Objects.isNull(employee)) {
			throw new UsernameNotFoundException("No employee information matching email address found.");
		}
		return new EmployeeDetails(employee);
	}

}
