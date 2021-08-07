package com.example.demo.controller.form.product;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ProductUpdateForm {

	@NotBlank
	private String productId;
	@Min(1)
	private int requestValue;
	@NotNull
	private boolean subtraction;
}
