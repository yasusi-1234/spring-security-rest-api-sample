package com.example.demo.controller.form.seles;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

/**
 * 売上データ抽出用のフォーム
 *
 */
@Data
@ToString
public class SalesSearchForm {
	/** カテゴリー名 */
	private String categoryName;
	/** 製品名 */
	private String productName;
	/** 期間抽出用、抽出したい開始時間 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime fromDate;
	/** 期間抽出用、抽出したい終了時間 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime toDate;
	/** 期間抽出用、決まった日付の抽出用 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime fixedDate;

}
