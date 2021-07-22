package com.example.demo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
