package com.example.ecommerce_d.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_d.domain.Order;
import com.example.ecommerce_d.service.ShowOrderHistoryService;

/**
 * 注文履歴を表示するコントローラ.
 * 
 * @author shumpei
 *
 */
@Controller
@RequestMapping("")
public class ShowOrderHistoryController {

	@Autowired
	private ShowOrderHistoryService service;

	/**
	 * 注文履歴を表示します.
	 * 
	 * @param model  リクエストスコープ
	 * @param userId ユーザーID
	 * @return 注文一覧画面
	 */
	@RequestMapping("/show-order-history")
	public String showOrderHistory(Model model, String userId) {
		List<Order> orderList = service.showOrderHistory(Integer.parseInt(userId));
		if (orderList.size() == 0) {
			model.addAttribute("message", "注文履歴がありません");
		}
		model.addAttribute("orderList", orderList);
		return "order_history";
	}

}
