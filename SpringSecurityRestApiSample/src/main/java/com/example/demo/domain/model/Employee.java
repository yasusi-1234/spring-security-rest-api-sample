package com.example.demo.domain.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable {

	private static final long serialVersionUID = -5952602149226260392L;

	@Id
	private String employeeId;
	@Column(unique = true, nullable = false)
	private String mailAddress;
	@JsonIgnore
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	@Column(nullable = false)
	private int age;
	@Column(nullable = false)
	private LocalDate joiningDate;
	@ManyToOne(optional = false)
	@JoinColumn(name = "role_id")
	private Role role;

	public String getFullName() {
		return getLastName() + " " + getFirstName();
	}
}
