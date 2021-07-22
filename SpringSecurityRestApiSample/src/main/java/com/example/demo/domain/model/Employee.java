package com.example.demo.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	@Id
	private String employeeId;
	@Column(unique = true)
	private String mailAddress;

	private String firstName;
	private String lastName;
	private int age;
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "role_id")
//	@Fetch(FetchMode.JOIN)
	private Role role;
}
