package com.example.demo.domain.service;

import static com.example.demo.domain.service.helper.SalesSpecificationHelper.betweenDay;
import static com.example.demo.domain.service.helper.SalesSpecificationHelper.equalCategoryName;
import static com.example.demo.domain.service.helper.SalesSpecificationHelper.equalProductName;
import static com.example.demo.domain.service.helper.SalesSpecificationHelper.fetch;
import static com.example.demo.domain.service.helper.SalesSpecificationHelper.likeSoldDay;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.form.SalesForm;
import com.example.demo.controller.form.SalesSearchForm;
import com.example.demo.domain.model.Employee;
import com.example.demo.domain.model.Product;
import com.example.demo.domain.model.Sales;
import com.example.demo.domain.repository.SalesRepository;
import com.example.demo.domain.service.exception.SalesRequestFailureException;
import com.example.demo.domain.service.helper.SalesView;

@Service
@Transactional(readOnly = false)
public class SalesServiceImpl implements SalesService {

	private final SalesRepository salesRepository;

	private final ProductService productService;

	private final EmployeeService employeeService;

	private final ModelMapper modelMapper;

	@Autowired
	public SalesServiceImpl(SalesRepository salesRepository, ProductService productService,
			EmployeeService employeeService, ModelMapper modelMapper) {
		super();
		this.salesRepository = salesRepository;
		this.productService = productService;
		this.employeeService = employeeService;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<SalesView> test() {
		return salesRepository.findAllBy(Specification.where(null), SalesView.class);
	}

	@Override
	public List<SalesView> test2() {
		return salesRepository.findAllSalesView();
	}

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
		productService.updateStock(form.getProductId(), form.getSoldCount());
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
	 * 売上検索用メソッド、form情報を元にDBから指定されたデータを抽出する
	 */
	@Override
	public List<Sales> findBySalesSearchForm(SalesSearchForm form) {
		return salesRepository.findAll(Specification.where(fetch()).and(equalCategoryName(form.getCategoryName()))
				.and(equalProductName(form.getProductName())).and(likeSoldDay(form.getFixedDate()))
				.and(betweenDay(form.getFromDate(), form.getToDate())));
	}
}
