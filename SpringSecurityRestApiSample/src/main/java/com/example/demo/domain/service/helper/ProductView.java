package com.example.demo.domain.service.helper;

import java.time.LocalDate;

public interface ProductView {

	String getProductId();

	String getProductName();

	int getPrice();

	int getStock();

	String getImagePath();

	LocalDate getRegistationDate();

	CategoryView getCategory();

}
