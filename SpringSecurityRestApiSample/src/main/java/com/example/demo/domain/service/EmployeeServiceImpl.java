package com.example.demo.domain.service;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.form.employee.EmployeeForm;
import com.example.demo.domain.model.Employee;
import com.example.demo.domain.model.Role;
import com.example.demo.domain.repository.EmployeeRepository;
import com.example.demo.domain.repository.RoleRepository;
import com.example.demo.domain.service.exception.MailAddressAlreadyRegisteredException;
import com.example.demo.domain.service.helper.EmployeeEmail;
import com.example.demo.domain.service.helper.EmployeeSpecificationHelper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;

	private final RoleRepository roleRepository;

	private final ModelMapper modelMapper;

	@Override
	public List<Employee> findAllEmployee() {
		return employeeRepository.findAll(EmployeeSpecificationHelper.joinFetch());
	}

	/**
	 * 社員IDを元に1件の従業員情報を返却するメソッド、一致するデータが見つからなかった場合は Nullを返却
	 */
	@Override
	public Employee findById(String employeeId) {
		return employeeRepository.findById(employeeId).orElse(null);
	}

	/**
	 * 従業員新規登録用メソッド
	 * 
	 * @throws MailAddressAlreadyRegisteredException 登録用フォームに指定されたメールアドレスが
	 *                                               既にDB(他のテーブル)に存在していた場合に送出される
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@Override
	public Employee save(EmployeeForm form) {
		List<EmployeeEmail> checkList = employeeRepository.findByMailAddress(form.getMailAddress());
		if (checkList.size() > 0) {
			throw new MailAddressAlreadyRegisteredException("request mailAddress is already registered.");
		}

		Employee employee = modelMapper.map(form, Employee.class);
		employee.setEmployeeId(UUID.randomUUID().toString());

		Role role = roleRepository.findByRoleName(form.getRoleName());
		employee.setRole(role);

		return employeeRepository.save(employee);
	}
}
