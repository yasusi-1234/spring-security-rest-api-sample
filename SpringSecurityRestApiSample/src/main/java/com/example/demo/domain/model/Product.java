package com.example.demo.domain.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product implements Serializable {

	private static final long serialVersionUID = 2016432484042520894L;
	@Id
	private String productId;
	@Column(nullable = false)
	private String productName;

	@Column(nullable = false)
	private int price;

	@Column(nullable = false)
	private int stock;

	private String imagePath;

	@Column(nullable = false)
	private LocalDate registationDate;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "category_id")
	private Category category;

}
