package com.example.demo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.domain.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

	@Modifying
	@Query(nativeQuery = true, value = "UPDATE product SET stock = stock - :subtractedValue WHERE product_id = :productId")
	public int updateProductStock(@Param("productId") String productId, @Param("subtractedValue") int subtractedValue);
}
