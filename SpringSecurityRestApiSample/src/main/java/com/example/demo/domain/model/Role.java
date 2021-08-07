package com.example.demo.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {

	private static final long serialVersionUID = 7798250402937866729L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roleId;
	@Column(nullable = false, unique = true)
	private String roleName;
}
