package com.example.demo.domain.service.helper;

import java.time.LocalDate;

public interface EmployeeView {

	String getEmployeeId();

	String getMailAddress();

//	String getPassword(); //このフィールドは含めない
	String getFirstName();

	String getLastName();

	int getAge();

	LocalDate getJoiningDate();

	RoleView getRole();
}
