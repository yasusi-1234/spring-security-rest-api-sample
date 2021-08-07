package com.example.demo.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.domain.model.Sales;
import com.example.demo.domain.service.helper.SalesView;

public interface SalesRepository extends JpaRepository<Sales, String>, JpaSpecificationExecutor<Sales> {

	@Query("select s.salesId AS salesId, s.soldCount AS soldCount, s.salesTime AS salesTime, s.product AS product, s.employee AS employee from Sales s "
			+ "INNER JOIN s.product INNER JOIN s.employee INNER JOIN FETCH s.product.category AS c INNER JOIN FETCH  s.employee.role "
			+ "WHERE c.categoryName = '書籍'")
	List<SalesView> findAllSalesView(); // test用

	/**
	 * 1件の販売情報を削除する
	 * @param salesId 削除したい販売情報ID
	 * @return 削除成功で1 データ自体が無い場合は0
	 */
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("DELETE FROM Sales WHERE salesId = :salesId")
	int deleteBySalesId(@Param("salesId") String salesId);
	
	/**
	 * 複数件の販売情報を削除する
	 * @param salesIds 削除したい販売情報IDのリスト
	 * @return 削除できた件数
	 */
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("DELETE FROM Sales WHERE salesId IN :salesIds")
	int deleteBySalesIds(@Param("salesIds") List<String> salesIds);
}
