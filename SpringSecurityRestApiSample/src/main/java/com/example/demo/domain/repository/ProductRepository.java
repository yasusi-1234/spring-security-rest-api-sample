package com.example.demo.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.domain.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

	/* ロックを掛けるかの検討をする */
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE product SET stock = stock - :subtractedValue WHERE product_id = :productId")
	public int updateSubscribeProductStock(@Param("productId") String productId,
			@Param("subtractedValue") int subtractedValue);

	/* ロックを掛けるかの検討をする */
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE product SET stock = stock + :addValue WHERE product_id = :productId")
	public int updateAddProductStock(@Param("productId") String productId, @Param("addValue") int addValue);

	@Query("SELECT pro, ca FROM Product pro INNER JOIN FETCH Category ca ON pro.category = ca.categoryId WHERE pro.productId IN :productIds")
	public List<Product> findByProductIds(@Param("productIds") List<String> productIds);
}
