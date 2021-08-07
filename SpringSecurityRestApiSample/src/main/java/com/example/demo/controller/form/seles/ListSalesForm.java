package com.example.demo.controller.form.seles;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ListSalesForm {

	@NotNull
	@Valid
	private List<SalesForm> salesForms;
}
