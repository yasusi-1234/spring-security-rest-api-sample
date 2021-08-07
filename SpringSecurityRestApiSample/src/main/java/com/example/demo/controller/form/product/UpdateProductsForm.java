package com.example.demo.controller.form.product;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UpdateProductsForm {

	@NotNull
	@Valid
	List<ProductUpdateForm> updateProducts;
}
