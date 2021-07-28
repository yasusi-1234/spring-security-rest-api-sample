package com.example.demo.domain.service;

import java.util.List;

import com.example.demo.controller.form.SalesForm;
import com.example.demo.controller.form.SalesSearchForm;
import com.example.demo.domain.model.Sales;
import com.example.demo.domain.service.exception.SalesRequestFailureException;
import com.example.demo.domain.service.helper.SalesView;

public interface SalesService {

	Sales findById(String proceedId);

	List<Sales> findBySalesSearchForm(SalesSearchForm form);

	List<Sales> findAllSales();

	List<SalesView> test();

	List<SalesView> test2();

	/**
	 * フォーム情報を元に商品の在庫数を更新し、かつ売り上げの登録を行う。
	 * 
	 * @throws SalesRequestFailureException 指定された注文数より現在DBに登録されている商品在庫数が少ない場合に例外が発生
	 */
	Sales save(SalesForm form);

}
