package com.example.demo.domain.service;

import static com.example.demo.domain.service.helper.SalesSpecificationHelper.betweenDay;
import static com.example.demo.domain.service.helper.SalesSpecificationHelper.equalCategoryName;
import static com.example.demo.domain.service.helper.SalesSpecificationHelper.equalProductName;
import static com.example.demo.domain.service.helper.SalesSpecificationHelper.fetch;
import static com.example.demo.domain.service.helper.SalesSpecificationHelper.likeSoldDay;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.form.seles.ListSalesForm;
import com.example.demo.controller.form.seles.SalesForm;
import com.example.demo.controller.form.seles.SalesSearchForm;
import com.example.demo.domain.model.Employee;
import com.example.demo.domain.model.Product;
import com.example.demo.domain.model.Sales;
import com.example.demo.domain.repository.SalesRepository;
import com.example.demo.domain.service.exception.SalesIdNotFoundException;
import com.example.demo.domain.service.exception.SalesRequestFailureException;
import com.example.demo.domain.service.helper.SalesSpecificationHelper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = false)
public class SalesServiceImpl implements SalesService {

	private final SalesRepository salesRepository;

	private final ProductService productService;

	private final EmployeeService employeeService;

	/**
	 * 登録されている全ての売上情報を返却する
	 */
	@Override
	public List<Sales> findAllSales() {
		List<Sales> returnList = salesRepository.findAll(fetch());
		return returnList;
	}

	/**
	 * 売上ID情報を元に1件の売上情報を返却するメソッド、存在しない場合はNull返却
	 */
	@Override
	public Sales findById(String salesId) {
		return salesRepository.findById(salesId).orElse(null);
	}

	/**
	 * フォーム情報を元に商品の在庫数を更新し、かつ売り上げの登録を行う。
	 * 
	 * @throws SalesRequestFailureException 指定された注文数より現在DBに登録されている商品在庫数が少ない場合に例外が発生
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@Override
	public Sales save(SalesForm form) {
		Product product = productService.findById(form.getProductId());
		Employee employee = employeeService.findById(form.getEmployeeId());
		// リクエストのプロダクトIDまたは従業員IDに不正な値が入っていた場合はエラー
		if (product == null || employee == null) {
			String productError = product == null
					? "request productId is not found. requested id : " + form.getProductId() + System.lineSeparator()
					: "";
			String employeeError = employee == null
					? "request employeeId is not found. requested id : " + form.getEmployeeId() + System.lineSeparator()
					: "";
			throw new SalesRequestFailureException(productError + employeeError);
		}
		// 現在のDBに登録されている数よりも、リクエストされた注文数が多い場合に送出するエラー
		if (form.getSoldCount() > product.getStock()) {
			throw new SalesRequestFailureException("request failed. now repository stock is " + product.getStock()
					+ ". but request" + " request soldcount is " + form.getSoldCount()
					+ ". need to fix it to the correct number.", product);
		}
		// 商品の更新処理
		productService.updateSubscribeStock(form.getProductId(), form.getSoldCount());

		Sales sales = new Sales();
		sales.setSalesId(UUID.randomUUID().toString());
//		String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		sales.setSalesTime(LocalDateTime.now().withNano(0));
		sales.setSoldCount(form.getSoldCount());
		sales.setEmployee(employee);
		// 返却用のカウント数の更新
		product.setStock(product.getStock() - form.getSoldCount());
		sales.setProduct(product);

		return salesRepository.save(sales);
	}

	/**
	 * 複数の商品情報のアップデートと販売情報のInsertを実行するメソッド
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@Override
	public List<Sales> multipleSave(ListSalesForm form) {
		Employee employee = employeeService.findById(form.getSalesForms().get(0).getEmployeeId());
		List<Sales> salesList = form.getSalesForms().stream().map(sale -> {
			// 製品情報を一つ一つ更新し結果の戻り値を取得
			Product product = productService.updateSubscribeStock(sale.getProductId(), sale.getSoldCount());
			// 返ってきた製品情報を使って新たなSales情報を生成
			Sales sales = Sales.of(sale.getSoldCount(), product, employee);
			return sales;
		}).collect(Collectors.toList());
		// リポジトリに保存
		List<Sales> saveSalesList = salesRepository.saveAll(salesList);

		return saveSalesList;
	}

	/**
	 * 売上検索用メソッド、form情報を元にDBから指定されたデータを抽出する
	 */
	@Override
	public List<Sales> findBySalesSearchForm(SalesSearchForm form) {
		return salesRepository.findAll(Specification.where(fetch()).and(equalCategoryName(form.getCategoryName()))
				.and(equalProductName(form.getProductName())).and(likeSoldDay(form.getFixedDate()))
				.and(betweenDay(form.getFromDate(), form.getToDate())));
	}

	/**
	 * 販売履歴を一件削除し、削除された販売履歴の製品情報の在庫数を元に戻すメソッド
	 * @param 削除する販売履歴ID
	 * @throws SalesIdNotFoundException 削除する販売履歴IDが見つからなかった場合
	 * @return 削除した販売履歴に対応する製品情報の、更新後のデータ
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@Override
	public Product deleteBySalesId(String salesId) {
		Sales sales = findById(salesId);
		if (sales == null) {
			throw new SalesIdNotFoundException("request salesId is not Found. request id: " + salesId);
		}

		salesRepository.deleteBySalesId(salesId);
		// ここで削除した要素の数値を、製品情報のstockに加える
		Product product = productService.updateAddStock(sales.getProduct().getProductId(), sales.getSoldCount());
		return product;
	}

	/**
	 * 販売履歴を複数件削除し、削除された複数の販売履歴の製品情報の在庫数を元に戻すメソッド
	 * @param 削除する販売履歴IDリスト
	 * @throws SalesIdNotFoundException 削除する販売履歴IDリストに一致する履歴情報が
	 * 1つ以上ある場合にこの例外が発生する
	 * @return 削除した販売履歴リストに対応する製品情報の、更新後の製品情報データリスト
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@Override
	public List<Product> deleteBySalesIds(List<String> salesIds) {
		List<Sales> sales = salesRepository.findAll(Specification.where(SalesSpecificationHelper.fetch())
				.and(SalesSpecificationHelper.inSalesIds(salesIds)));
		if (salesIds.size() != sales.size()) {
			// リクエストのIDと実際にDBに格納されているデータ数が一致しない場合
			List<String> dbIds = sales.stream().map(sale -> sale.getSalesId()).collect(Collectors.toList());
			List<String> emptyIds = salesIds.stream().filter(id -> !dbIds.contains(id)).collect(Collectors.toList());
			throw new SalesIdNotFoundException("no mathing id found. no matching ids: " + emptyIds);
		}
		// 削除が成功した数量が返却される
		int result = salesRepository.deleteBySalesIds(salesIds);
		System.out.println("deleteResult: " + result);

		List<Product> products = sales.stream().map(sale -> {
			Product product = productService.updateAddStock(sale.getProduct().getProductId(), sale.getSoldCount());
			return product;
		}).collect(Collectors.toList());
		return products;
	}

}
