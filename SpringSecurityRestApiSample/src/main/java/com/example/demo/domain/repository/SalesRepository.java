package com.example.demo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.model.Sales;

public interface SalesRepository extends JpaRepository<Sales, String> {

}
