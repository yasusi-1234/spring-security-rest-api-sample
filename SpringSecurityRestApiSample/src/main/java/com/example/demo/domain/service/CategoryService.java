package com.example.demo.domain.service;

import java.util.List;

import com.example.demo.domain.model.Category;

public interface CategoryService {

	List<Category> findAllCategory();

	Category findById(Integer id);

	Category save(String categoryName);

}
