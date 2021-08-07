package com.example.demo.domain.service;

import java.util.List;

import com.example.demo.controller.form.product.ProductForm;
import com.example.demo.controller.form.product.UpdateProductsForm;
import com.example.demo.domain.model.Product;
import com.example.demo.domain.service.exception.ProductStockUpdateFailureException;

public interface ProductService {

	List<Product> findAllProduct();

	Product findById(String productId);

	Product save(ProductForm productForm);

	Product updateSubscribeStock(String productId, int subtractedValue);

	Product updateAddStock(String productId, int subtractedValue);

	/**
	 * 製品情報更新用メソッド(供給用) 製品IDと在庫増加数の情報を元に製品の数量を複数更新する
	 * 
	 * @throws ProductStockUpdateFailureException リクエストされた商品IDがDBに存在しない場合に発生する
	 * 
	 */
	List<Product> multiplAddStock(UpdateProductsForm form);

}
