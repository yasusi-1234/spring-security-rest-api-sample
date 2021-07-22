package com.example.demo.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.model.Sales;
import com.example.demo.domain.repository.SalesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional(readOnly = false)
public class SalesServiceImpl implements SalesService {

	private final SalesRepository proceedRepository;

	private final ObjectMapper objectMapper;

	@Autowired
	public SalesServiceImpl(SalesRepository proceedRepository, ObjectMapper objectMapper) {
		super();
		this.proceedRepository = proceedRepository;
		this.objectMapper = objectMapper;
	}

	@Override
	public List<Sales> findAllProceed() {
		return proceedRepository.findAll();
	}

	@Override
	public Sales findById(String proceedId) {
		return proceedRepository.findById(proceedId).orElse(null);
	}
}
