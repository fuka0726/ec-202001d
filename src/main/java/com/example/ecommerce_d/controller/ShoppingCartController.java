package com.example.ecommerce_d.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_d.domain.LoginUser;
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
	// ログインユーザーを受け取る
	public String showCartList(Integer userId, Model model, @AuthenticationPrincipal LoginUser loginUser) {
//		TODO直下1行はあとで消す。(ログインユーザーがいない場合はuserId=2で検索する)
		userId = 2;
		// ログインユーザーがいればログインユーザーのIDで検索する
		if (loginUser != null) {
			userId = loginUser.getUser().getId();
			System.out.println(userId);
		}
		//ショッピングカートに注文がなければメッセージを表示する
		Order order = service.showCartList(userId);
		if(order == null) {
			model.addAttribute("message", "カート内に商品はありません");
		}
		model.addAttribute("order", order);
		return "cart_list";
	}

	/**
	 * カートにアイテムを追加します.
	 * 
	 * @param form   フォーム
	 * @param userId ユーザーID
	 * @return カート一覧画面
	 */
	@RequestMapping("/add-item")
	// ログインユーザーを受け取る
	public String addItem(AddItemForm form, Integer userId, Model model, @AuthenticationPrincipal LoginUser loginUser) {
//		直下1行はあとで消す。(ログインユーザーがいない場合はuserId=2で検索する)
		userId = 2;
		// ログインユーザーがいればログインユーザーのIDで検索する
		if (loginUser != null) {
			userId = loginUser.getUser().getId();
		}
		service.addItem(form, userId);
		return showCartList(userId, model, loginUser);
	}

	/**
	 * 注文アイテムIDに紐づく注文アイテムと注文トッピングをカートから除きます.
	 * 
	 * @param orderItemId 注文アイテムID
	 * @return ショッピングカートリスト
	 */
	@RequestMapping("/remove-order-item")
	// ログインユーザーを受け取る
	public String removeOrderItem(Integer orderItemId, Model model, Integer userId,
			@AuthenticationPrincipal LoginUser loginUser) {
		service.removeOrderItemAndOrderTopping(orderItemId);
//		直下1行はあとで消す。(ログインユーザーがいない場合はuserId=2で検索する)
		userId = 2;
		// ログインユーザーがいればログインユーザーのIDで検索する
		if (loginUser != null) {
			userId = loginUser.getUser().getId();
		}
		return showCartList(userId, model, loginUser);
	}
}
