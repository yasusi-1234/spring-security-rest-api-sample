package com.example.demo.controller.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ProductUpdateForm {

	@NotBlank
	private String productId;
	@Min(1)
	private int subtractedValue;
}
