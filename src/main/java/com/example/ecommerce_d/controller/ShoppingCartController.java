package com.example.ecommerce_d.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_d.domain.Order;
import com.example.ecommerce_d.form.AddItemForm;
import com.example.ecommerce_d.service.ShoppingCartService;

@Controller
@RequestMapping("")
public class ShoppingCartController {

	@Autowired
	private ShoppingCartService service;

	/**
	 * 
	 * 
	 * @param userId hiddenで送られきたユーザーID
	 * @return カート画面
	 */
	@RequestMapping("/show-cart")
	public String showCartList(Integer userId,Model model) {
//		直下1行はあとで消す。
		userId = 2;
		Order order = service.showCartList(userId);
		System.out.println(order);
		model.addAttribute("order", order);
		return "cart_list";
	}
	
	/**
	 * カートにアイテムを追加します.
	 * 
	 * @param form フォーム
	 * @param userId ユーザーID
	 * @return カート一覧画面
	 */
	@RequestMapping("/add-item")
	public String addItem(AddItemForm form, Integer userId,Model model) {
//		直下1行はあとで消す。
		userId = 2;
		System.out.println(form);
		service.addItem(form, userId);
		return showCartList(userId, model);
	}


	/**
	 * 注文アイテムIDに紐づく注文アイテムと注文トッピングをカートから除きます.
	 * 
	 * @param orderItemId 注文アイテムID
	 * @return ショッピングカートリスト
	 */
	@RequestMapping("/remove-order-item")
	public String removeOrderItem(Integer orderItemId,Model model,Integer userId) {
		service.removeOrderItemAndOrderTopping(orderItemId);
//		直下1行はあとで消す。
		userId = 2;
		return showCartList(userId,model);
	}
}
