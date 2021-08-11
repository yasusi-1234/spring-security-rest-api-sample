package com.example.demo.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.domain.model.Employee;
import com.example.demo.domain.service.helper.EmployeeEmail;

public interface EmployeeRepository extends JpaRepository<Employee, String>, JpaSpecificationExecutor<Employee> {

	List<EmployeeEmail> findByMailAddress(String email);

	/**
	 * メールアドレス情報を元に1件の従業員情報を返却
	 * 
	 * @param email メールアドレス
	 * @return 同一のメールアドレスを保持する従業員情報、一致する情報がない場合null
	 */
	@Query("SELECT e, r FROM Employee e INNER JOIN Role r ON e.role = r.roleId WHERE e.mailAddress = :email")
	Employee findByMail(@Param("email") String email);
}
