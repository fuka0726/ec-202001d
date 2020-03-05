package com.example.ecommerce_d.controller;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
		Date date = Date.valueOf(form.getDeliveryDate());
		LocalDate localdate = date.toLocalDate();
		System.out.println(localdate);
		System.out.println(form.getDeliveryTime());
        
      
//		Order order = new Order();
//		BeanUtils.copyProperties(form,order);
//		order.setDeliveryTime(form.getDeliveryTime());
//		orderRepository.updateStatus(order);
//		userRepository.update(order);
		return"order_finished";
	}
	
}
