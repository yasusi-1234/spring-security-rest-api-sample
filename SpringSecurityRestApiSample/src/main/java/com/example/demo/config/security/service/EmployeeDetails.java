package com.example.demo.config.security.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.domain.model.Employee;

import lombok.Getter;

public class EmployeeDetails implements UserDetails {

	private static final long serialVersionUID = 3180719632170021970L;
	@Getter
	private Employee employee;

	private String password;

	private String mailAddress;

	@Getter
	private String fullName;

	private final Collection<GrantedAuthority> authorites;

	public EmployeeDetails(Employee employee) {
		this.employee = employee;
		this.password = employee.getPassword();
		this.mailAddress = employee.getMailAddress();
		this.fullName = employee.getFullName();
		this.authorites = AuthorityUtils.createAuthorityList(employee.getRole().getRoleName());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorites;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.mailAddress;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
