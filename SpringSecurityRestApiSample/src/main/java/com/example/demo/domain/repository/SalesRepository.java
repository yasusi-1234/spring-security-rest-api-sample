package com.example.demo.domain.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.model.Sales;
import com.example.demo.domain.service.helper.SalesView;

public interface SalesRepository extends JpaRepository<Sales, String>, JpaSpecificationExecutor<Sales> {

	<T> List<T> findAllBy(Class<T> projection); // test用 残念ながらSpecificationとは同居できない模様

	<T> List<T> findAllBy(Specification<Sales> spec, Class<T> projection); // 動かないorz 下記のJPQL地獄に耐えるしかないと言うのか...

	@Query("select s.salesId AS salesId, s.soldCount AS soldCount, s.salesTime AS salesTime, s.product AS product, s.employee AS employee from Sales s "
			+ "INNER JOIN s.product INNER JOIN s.employee INNER JOIN FETCH s.product.category AS c INNER JOIN FETCH  s.employee.role "
			+ "WHERE c.categoryName = '書籍'")
	List<SalesView> findAllSalesView(); // test用
}
