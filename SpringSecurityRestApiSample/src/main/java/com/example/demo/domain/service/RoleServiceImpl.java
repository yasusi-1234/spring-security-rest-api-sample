package com.example.demo.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.model.Role;
import com.example.demo.domain.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;

	/**
	 * ロール情報新規追加用メソッド
	 */
	@Override
	@Transactional(readOnly = false)
	public Role saveRole(String roleName) {
		Role saveRole = new Role();
		saveRole.setRoleName(roleName);
		return roleRepository.save(saveRole);
	}
}
