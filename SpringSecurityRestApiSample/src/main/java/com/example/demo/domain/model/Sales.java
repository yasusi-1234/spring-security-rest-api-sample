package com.example.demo.domain.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sales {
	@Id
	private String salesId;
	private LocalDateTime salesTime;
	private int soldCount;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "product_id")
	private Product product;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "employee_id")
	private Employee employee;
}
