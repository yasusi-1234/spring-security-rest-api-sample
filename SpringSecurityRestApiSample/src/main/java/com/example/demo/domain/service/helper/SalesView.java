package com.example.demo.domain.service.helper;

import java.time.LocalDateTime;

public interface SalesView {

	String getSalesId();

	LocalDateTime getSalesTime();

	int getSoldCount();

	ProductView getProduct();

	EmployeeView getEmployee();
}
