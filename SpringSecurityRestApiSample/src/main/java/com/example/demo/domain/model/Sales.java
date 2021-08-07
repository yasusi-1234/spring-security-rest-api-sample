package com.example.demo.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
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
public class Sales implements Serializable {

	private static final long serialVersionUID = -4212092163197970941L;
	@Id
	private String salesId;
	@Column(nullable = false)
	private LocalDateTime salesTime;
	@Column(nullable = false)
	private int soldCount;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "product_id")
	private Product product;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "employee_id")
	private Employee employee;

	public static Sales of(int soldCount, Product product, Employee employee) {
		Sales sales = new Sales();
		sales.setSalesId(UUID.randomUUID().toString());
		sales.setSalesTime(LocalDateTime.now().withNano(0));
		sales.setSoldCount(soldCount);
		sales.setProduct(product);
		sales.setEmployee(employee);
		return sales;
	}
}
