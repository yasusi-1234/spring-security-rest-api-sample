package com.example.demo.domain.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@JsonIgnore
	private String password;
	private String firstName;
	private String lastName;
	private int age;
	private LocalDate joiningDate;
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "role_id")
//	@Fetch(FetchMode.JOIN)
	private Role role;
}
