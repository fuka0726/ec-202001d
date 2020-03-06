package com.example.ecommerce_d.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String showCartList(Integer userId) {
		service.showCartList(userId);
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
	public String addItem(AddItemForm form, Integer userId) {
		userId = 1;
		System.out.println(form);
		service.addItem(form, userId);
		return "cart_list";
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
