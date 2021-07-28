package com.example.demo.domain.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.form.ProductForm;
import com.example.demo.domain.model.Category;
import com.example.demo.domain.model.Product;
import com.example.demo.domain.repository.ProductRepository;
import com.example.demo.domain.service.exception.ProductStockUpdateFailureException;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	private final ModelMapper modelMapper;

	@Autowired
	public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
		super();
		this.productRepository = productRepository;
		this.modelMapper = modelMapper;
	}

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
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@Override
	public int updateStock(String productId, int subtractedValue) {
		Product nowProduct = findById(productId);
		if (nowProduct.getStock() < subtractedValue) {
			throw new ProductStockUpdateFailureException(
					"request update failed. now repository stock is " + nowProduct.getStock() + ". but request"
							+ " subtractedValue is " + subtractedValue + ". Need to rewrite the value to be reduced.");
		}
		return productRepository.updateProductStock(productId, subtractedValue);
	}

}
