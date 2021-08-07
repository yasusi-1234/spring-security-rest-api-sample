package com.example.demo.domain.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.form.product.ProductForm;
import com.example.demo.controller.form.product.UpdateProductsForm;
import com.example.demo.domain.model.Category;
import com.example.demo.domain.model.Product;
import com.example.demo.domain.repository.ProductRepository;
import com.example.demo.domain.service.exception.ProductStockUpdateFailureException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	private final ModelMapper modelMapper;

	/**
	 * DBに登録されている全件分の製品情報を検索し、返却するメソッド
	 */
	@Override
	public List<Product> findAllProduct() {
		return productRepository.findAll();
	}

	/**
	 * DBに登録されている製品情報のIDから1件の製品情報を返却するメソッド。一致するデータが存在しない 場合はNullを返却する
	 */
	@Override
	public Product findById(String productId) {
		return productRepository.findById(productId).orElse(null);
	}

	/**
	 * 製品登録用メソッド 製品IDはRandomUUIDで新たに生成し、登録日情報はLocalDateで作成される
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@Override
	public Product save(ProductForm productForm) {
		// 後に画像関連の処理も加える
		Product product = modelMapper.map(productForm, Product.class);
//		System.out.println("before product: " + product);
		product.setProductId(UUID.randomUUID().toString());
		product.setRegistationDate(LocalDate.now());
		Category category = new Category();
		category.setCategoryId(productForm.getCategoryId());
		product.setCategory(category);
//		System.out.println("after product: " + product);
		return productRepository.save(product);
	}

	/**
	 * 製品情報更新用メソッド(削減用) 製品IDと在庫削減数の情報を元に製品の数量を更新する
	 * 
	 * @throws ProductStockUpdateFailureException 現在のDBの在庫数より多い数値が指定された場合に発生する
	 *                                            またリクエストされた商品IDがDBに存在しない場合も発生する
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@Override
	public Product updateSubscribeStock(String productId, int subtractedValue) {
		Product nowProduct = findById(productId);
		if (nowProduct == null) {
			throw new ProductStockUpdateFailureException(
					"request update failed." + "request productId not found. requestId: " + productId);
		}
		if (nowProduct.getStock() < subtractedValue) {
			throw new ProductStockUpdateFailureException(
					"request update failed. now repository stock is " + nowProduct.getStock() + ". but request"
							+ " subtractedValue is " + subtractedValue + ". Need to rewrite the value to be reduced.",
					nowProduct);
		}
		// 失敗するとMysqlDataTruncation
		productRepository.updateSubscribeProductStock(productId, subtractedValue);
		return findById(productId);
	}

	/**
	 * 製品情報更新用メソッド(供給用) 製品IDと在庫増加数の情報を元に製品の数量を更新する
	 * 
	 * @throws ProductStockUpdateFailureException リクエストされた商品IDがDBに存在しない場合に発生する
	 * 
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@Override
	public Product updateAddStock(String productId, int addValue) {
		Product nowProduct = findById(productId);
		if (nowProduct == null) {
			throw new ProductStockUpdateFailureException(
					"request update failed." + "request productId not found. requestId: " + productId);
		}
		productRepository.updateAddProductStock(productId, addValue);
		return findById(productId);
	}

	/**
	 * 製品情報更新用メソッド(供給用) 製品IDと在庫増加数の情報を元に製品の数量を複数更新する
	 * 
	 * @throws ProductStockUpdateFailureException リクエストされた商品IDがDBに存在しない場合に発生する
	 * 
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@Override
	public List<Product> multiplAddStock(UpdateProductsForm form) {
		List<Product> responseProduct = form.getUpdateProducts().stream()
				.map(requestData -> updateAddStock(requestData.getProductId(), requestData.getRequestValue()))
				.collect(Collectors.toList());
		return responseProduct;
	}

}
