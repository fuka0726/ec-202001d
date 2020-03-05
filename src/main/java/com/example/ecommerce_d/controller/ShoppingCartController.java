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
		
		return "cart_list";
	}
	
	public String addItem(AddItemForm form, Integer userId) {
		service.addItem(form, userId);
		return "cart-list";
	}
	
//	public String removeOrderItem(Integer orderItemId) {
//		service.removeOrderItem(orderItemId);
//		return "cart-list";
//	}
}
