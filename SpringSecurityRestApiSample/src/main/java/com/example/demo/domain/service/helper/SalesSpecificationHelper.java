package com.example.demo.domain.service.helper;

import static com.example.demo.common.EntityValues.CATEGORY;
import static com.example.demo.common.EntityValues.CATEGORY_NAME;
import static com.example.demo.common.EntityValues.EMPLOYEE;
import static com.example.demo.common.EntityValues.PRODUCT;
import static com.example.demo.common.EntityValues.PRODUCT_NAME;
import static com.example.demo.common.EntityValues.ROLE;
import static com.example.demo.common.EntityValues.SALES_TIME;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.demo.domain.model.Sales;

/**
 * SalesServiceクラスでのDBアクセス用Specificationサポートクラス
 *
 */
public class SalesSpecificationHelper {

	/**
	 * Join・Fetch用、N＋１問題対策として用意している
	 * 
	 * @return
	 */
	public static Specification<Sales> fetch() {
		return (root, query, cb) -> {
			if (query.getResultType() == Long.class) {
				root.join(EMPLOYEE).join(ROLE);
				root.join(PRODUCT).join(CATEGORY);
			} else {
				root.fetch(EMPLOYEE).fetch(ROLE);
				root.fetch(PRODUCT).fetch(CATEGORY);
			}
			return query.getRestriction();
		};
	}

	public static Specification<Sales> fetchProduct() {
		return (root, query, cb) -> {
			if (query.getResultType() == Long.class) {
				root.join(PRODUCT);
			} else {
				root.fetch(PRODUCT);
			}
			return query.getRestriction();
		};
	}

	/**
	 * 製品IDリストと一致する要素を抽出する
	 * 
	 * @param salesIds
	 * @return
	 */
	public static Specification<Sales> inSalesIds(List<String> salesIds) {
		return CollectionUtils.isEmpty(salesIds) ? null : (root, query, cb) -> {
			return root.get("salesId").in(salesIds);
		};
	}

	/**
	 * 製品のカテゴリー名に一致するデータを抽出する
	 * 
	 * @param categoryName
	 * @return
	 */
	public static Specification<Sales> equalCategoryName(String categoryName) {
		return (root, query, cb) -> {
			return StringUtils.hasText(categoryName)
					? cb.equal(root.get(PRODUCT).get(CATEGORY).get(CATEGORY_NAME), categoryName)
					: null;
		};
	}

	/**
	 * 製品名に一致するデータを抽出する
	 * 
	 * @param productName
	 * @return
	 */
	public static Specification<Sales> equalProductName(String productName) {
		return StringUtils.hasText(productName)
				? (root, query, cb) -> cb.equal(root.get(PRODUCT).get(PRODUCT_NAME), productName)
				: null;
	}

	/**
	 * 指定されたLocalDateTimeの年月日と一致するデータを抽出する
	 * 
	 * @param fixedDate
	 * @return
	 */
	public static Specification<Sales> likeSoldDay(LocalDateTime fixedDate) {
		return fixedDate == null ? null : (root, query, cb) -> {
			return cb.like(root.get(SALES_TIME).as(String.class),
					"%" + DateTimeFormatter.ofPattern("yyyy-MM-dd").format(fixedDate) + "%");
		};
	}

	/**
	 * 指定されたfromからtoの間のデータを抽出する
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public static Specification<Sales> betweenDay(LocalDateTime from, LocalDateTime to) {
		return from == null || to == null ? null : (root, query, cb) -> {
			return cb.between(root.get(SALES_TIME), from, to);
		};
	}
}
