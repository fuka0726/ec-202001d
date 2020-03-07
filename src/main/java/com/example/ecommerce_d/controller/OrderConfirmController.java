package com.example.ecommerce_d.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_d.domain.LoginUser;
import com.example.ecommerce_d.domain.Order;
import com.example.ecommerce_d.form.OrderForm;
import com.example.ecommerce_d.service.OrderConfirmService;

/**
 * 注文前の商品を表示するコントローラ.
 * 
 * @author namikitsubasa
 *
 */
@Controller
@RequestMapping("/")
public class OrderConfirmController {

	@Autowired
	private OrderConfirmService orderConfirmService;

	/**
	 * 
	 * 注文前の商品を取得し、注文確認ページに画面遷移
	 * 
	 * @param userId ユーザーid
	 * @param model  リクエストスコープ
	 * @return 注文確認ページに画面遷移
	 */
	@RequestMapping("/orderConfirm")
	// ログインしたユーザー情報を受け渡します
	public String orderConfirm(Integer userId, Model model, @AuthenticationPrincipal LoginUser loginUser) {
		List<Order> orderList = orderConfirmService.showOrderedList(loginUser.getUser().getId());
		model.addAttribute("orderList", orderList);
		return "order_confirm";
	}

	/**
	 * 
	 * お届け先情報を取得し、注文を確定する.
	 * 
	 * @param form      お届け先情報
	 * @param resultset エラーメッセージを格納するオブジェクト
	 * @param userId    ユーザーid
	 * @param model		リクエストスコープ
	 * @param loginUser 利用者ユーザー
	 * @return 注文完了画面（リダイレクト）
	 */
	@RequestMapping("/completeOrder")
	public String completeOrder(@Validated OrderForm form, BindingResult resultset, Integer userId, Model model,
			@AuthenticationPrincipal LoginUser loginUser) {
		
		if ("".equals(form.getDeliveryDate())) {
			FieldError deliveryDateError = new FieldError(resultset.getObjectName(), "deliveryDate", "配達日を入力してください");
			resultset.addError(deliveryDateError);
		}
		//エラーがある場合は注文確認画面に遷移
		if (resultset.hasErrors()) {
			System.out.println(resultset);
			return orderConfirm(userId, model, loginUser);
		}
		//配達日をSQL用のTimestamp型に変更
		Date date = Date.valueOf(form.getDeliveryDate());
		LocalDate localdate = date.toLocalDate();
		LocalTime localTime = LocalTime.of(Integer.parseInt(form.getDeliveryTime()), 00);
		LocalDateTime localDateTime = LocalDateTime.of(localdate, localTime);
		System.out.println(localDateTime);
		Timestamp timestamp = Timestamp.valueOf(localDateTime);
		//Orderインスタンスを作成(テーブルからとってきた方が良いのかしら？）
		Order order = new Order();
		BeanUtils.copyProperties(form, order);
		order.setDeliveryTime(timestamp);
		//支払方法によって入金情報を変更
		if (order.getPaymentMethod() == 1) {
			order.setStatus(1);
		} else {
			order.setStatus(2);
		}
		System.out.println(order.getPaymentMethod());
		orderConfirmService.updateStatus(order); // ユーザーidを最終的には変数に変更(orderconfirmservie)
//		userRepository.update(order);
		return "redirect:/tocomplete";
	}

	@RequestMapping("/tocomplete")
	public String tocomplete() {
		return "order_finished";
	}

}
