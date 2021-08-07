package com.example.demo.controller.form.employee;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeForm {
	@NotBlank
	@Email
	private String mailAddress;
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@Min(value = 16)
	@Max(value = 100)
	private int age;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate joiningDate;
	@NotBlank
	private String roleName;
}
