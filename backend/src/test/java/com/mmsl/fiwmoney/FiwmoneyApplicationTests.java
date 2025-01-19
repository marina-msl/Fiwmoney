package com.mmsl.fiwmoney;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mmsl.fiwmoney.model.Stock;
import com.mmsl.fiwmoney.service.StockService;

class FiwmoneyApplicationTests {

	@Test
	void contextLoads() {
	}


	@Autowired
	private StockService stockService;

	// @Test
	// public void whenApplicationStarts_thenHibernateCreatesInitialRecors() {
	// 	List<Stock> stocks = stockService.findAll();
 	// }

}
