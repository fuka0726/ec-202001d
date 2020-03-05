package com.example.ecommerce_d.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_d.domain.Order;
import com.example.ecommerce_d.form.OrderForm;
import com.example.ecommerce_d.repository.OrderRepository;
import com.example.ecommerce_d.repository.UserRepository;

/**
 * 
 * 注文確定、クレジット決済、メール送信機能を制御するコントローラ.
 * 
 * @author namikitsubasa
 *
 */
@Controller
@RequestMapping
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@ModelAttribute
	public OrderForm OrderForm() {
		return new OrderForm();
	}
	
	/**
	 * 
	 * 注文を確定する.
	 * 
	 * @param form お届け先情報と決済情報
	 * @return オーダ完了画面に遷移
	 */
	@RequestMapping("/completeOrder")
	public String completeOrder(OrderForm form) {
		Order order = new Order();
		BeanUtils.copyProperties(form,order);
		order.setDeliveryTime(form.getDeliveryTime());
		orderRepository.updateStatus(order);
		userRepository.update(order);
		return"order_finished";
	}
	
}
