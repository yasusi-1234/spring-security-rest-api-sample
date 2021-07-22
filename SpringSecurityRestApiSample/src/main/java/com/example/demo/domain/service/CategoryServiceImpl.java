package com.example.demo.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.model.Category;
import com.example.demo.domain.repository.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	private final ObjectMapper objectMapper;

	@Autowired
	public CategoryServiceImpl(CategoryRepository categoryRepository, ObjectMapper objectMapper) {
		super();
		this.categoryRepository = categoryRepository;
		this.objectMapper = objectMapper;
	}

	@Override
	public List<Category> findAllCategory() {
		return categoryRepository.findAll();
	}

	@Override
	public Category findById(Integer id) {
		return categoryRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Category save(String categoryName) {
		Category saveCategory = new Category();
		saveCategory.setCategoryName(categoryName);
		return categoryRepository.save(saveCategory);
	}
}
