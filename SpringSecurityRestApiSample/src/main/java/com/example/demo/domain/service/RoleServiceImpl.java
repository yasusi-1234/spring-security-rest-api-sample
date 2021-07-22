package com.example.demo.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.model.Role;
import com.example.demo.domain.repository.RoleRepository;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {
	private final RoleRepository roleRepository;

	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository) {
		super();
		this.roleRepository = roleRepository;
	}

	@Override
	@Transactional(readOnly = false)
	public Role saveRole(String roleName) {
		Role saveRole = new Role();
		saveRole.setRoleName(roleName);
		return roleRepository.save(saveRole);
	}
}
