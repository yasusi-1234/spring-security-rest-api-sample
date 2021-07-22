package com.example.demo.domain.service;

import java.util.List;

import com.example.demo.domain.model.Sales;

public interface SalesService {

	Sales findById(String proceedId);

	List<Sales> findAllProceed();

}
