package com.example.demo.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.form.product.CategoryForm;
import com.example.demo.domain.model.Category;
import com.example.demo.domain.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	@Override
	public List<Category> findAllCategory() {
		return categoryRepository.findAll();
	}

	/**
	 * カテゴリーIDを元に1件のカテゴリー情報を検索する
	 */
	@Override
	public Category findById(Integer categoryId) {
		return categoryRepository.findById(categoryId).orElse(null);
	}

	/**
	 * カテゴリーの新規登録用メソッド
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Category save(CategoryForm categoryForm) {
		Category saveCategory = new Category();
		saveCategory.setCategoryName(categoryForm.getCategoryName());
		return categoryRepository.save(saveCategory);
	}
}
