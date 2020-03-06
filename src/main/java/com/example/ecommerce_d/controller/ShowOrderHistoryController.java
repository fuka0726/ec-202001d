package com.example.ecommerce_d.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_d.domain.LoginUser;
import com.example.ecommerce_d.domain.Order;
import com.example.ecommerce_d.service.JoinShowOrderHistoryService;
//5つテーブルをジョインするためコメントアウトしました
//import com.example.ecommerce_d.service.ShowOrderHistoryService;

/**
 * 注文履歴を表示するコントローラ.
 * 
 * @author shumpei
 *
 */
@Controller
@RequestMapping("")
public class ShowOrderHistoryController {

//	5つテーブルをジョインするためコメントアウトしました
//	@Autowired
//	private ShowOrderHistoryService service;

	@Autowired
	private JoinShowOrderHistoryService service;

	/**
	 * 注文履歴を表示します.
	 * 
	 * @param model  リクエストスコープ
	 * @param userId ユーザーID
	 * @return 注文一覧画面
	 */
	@RequestMapping("/show-order-history")
	
	public String showOrderHistory(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		// 管理者ユーザーでログインする
		List<Order> orderList = service.showOrderHistory(loginUser.getUser().getId());
		// 管理者ユーザーのログインIDを渡す
		if (orderList.size() == 0) {
			model.addAttribute("message", "注文履歴がありません");
		}
		model.addAttribute("orderList", orderList);
		return "order_history";
	}

}
