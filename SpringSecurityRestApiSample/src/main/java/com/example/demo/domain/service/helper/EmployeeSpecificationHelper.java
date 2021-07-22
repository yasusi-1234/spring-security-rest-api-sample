package com.example.demo.domain.service.helper;

import static com.example.demo.common.EntityValues.ROLE;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.domain.model.Employee;

public class EmployeeSpecificationHelper {

	public static Specification<Employee> joinFetch() {
		return (root, query, builder) -> {
			root.fetch(ROLE);
			return query.getRestriction();
		};
	}
}
