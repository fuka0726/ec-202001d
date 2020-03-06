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
		userId = 1;
		Order order = service.showCartList(userId);
		model.addAttribute("order", order);
		return "cart_list";
	}

	public String addItem(AddItemForm form, Integer userId) {
		service.addItem(form, userId);
		return "cart-list";
	}

	/**
	 * 注文アイテムIDに紐づく注文アイテムと注文トッピングをカートから除きます.
	 * 
	 * @param orderItemId 注文アイテムID
	 * @return ショッピングカートリスト
	 */
	@RequestMapping("/remove-order-item")
	public String removeOrderItem(Integer orderItemId) {
		service.removeOrderItemAndOrderTopping(orderItemId);
		return "cart-list";
	}
}
