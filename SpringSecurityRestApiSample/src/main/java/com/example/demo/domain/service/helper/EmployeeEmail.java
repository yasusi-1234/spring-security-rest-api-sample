package com.example.demo.domain.service.helper;

/**
 * 従業員リポジトリからEmaiの情報のみを取得する場合に、JPA側で使う為のインターフェース
 * 
 * @author yasuyasu
 *
 */
public interface EmployeeEmail {

	String getMailAddress();
}
