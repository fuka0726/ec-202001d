//package com.example.ecommerce_d.controller;
//
//
//import java.sql.Date;
//import java.sql.Timestamp;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.example.ecommerce_d.domain.Order;
//import com.example.ecommerce_d.form.OrderForm;
//import com.example.ecommerce_d.repository.OrderRepository;
//import com.example.ecommerce_d.repository.UserRepository;
//
///**
// * 
// * 注文確定、クレジット決済、メール送信機能を制御するコントローラ.
// * 
// * @author namikitsubasa
// *
// */
//@Controller
//@RequestMapping
//public class OrderController {
//
//	@Autowired
//	private OrderRepository orderRepository;
//	
//	@Autowired
//	private UserRepository userRepository;
//	
//	@ModelAttribute
//	public OrderForm OrderForm() {
//		return new OrderForm();
//	}
//	
////	if文等はサービスクラスで記述する(並木)
//	/**
//	 * 
//	 * 注文を確定する.
//	 * 
//	 * @param form お届け先情報と決済情報
//	 * @return オーダ完了画面に遷移
//	 */
//	@RequestMapping("/completeOrder")
//	public String completeOrder(@Validated OrderForm form,BindingResult resultset,RedirectAttributes redirectAttributes,Integer userId,Model model) {
//		if(resultset.hasErrors()) {
//			return OrderConfirmController.orderConfirm(userId,model);
//		}
//		Date date = Date.valueOf(form.getDeliveryDate());
//		LocalDate localdate = date.toLocalDate();
//		LocalTime localTime = LocalTime.of(Integer.parseInt(form.getDeliveryTime()),00);
//		LocalDateTime localDateTime = LocalDateTime.of(localdate, localTime);
//        System.out.println(localDateTime);
//        Timestamp timestamp = Timestamp.valueOf(localDateTime);
//        
//		Order order = new Order();
//		BeanUtils.copyProperties(form,order);
//		order.setDeliveryTime(timestamp);
//		if(order.getPaymentMethod()==1) {
//			order.setStatus(1);
//		}else {
//			order.setStatus(2);
//		}
//		System.out.println(order.getPaymentMethod());
//		orderRepository.updateStatus(order);
////		userRepository.update(order);
//		return"order_finished";
//	}
//	
//}
