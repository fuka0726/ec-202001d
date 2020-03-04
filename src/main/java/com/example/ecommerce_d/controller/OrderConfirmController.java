package com.example.ecommerce_d.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_d.domain.Item;
import com.example.ecommerce_d.domain.Order;
import com.example.ecommerce_d.domain.OrderItem;
import com.example.ecommerce_d.domain.OrderTopping;
import com.example.ecommerce_d.domain.Topping;
import com.example.ecommerce_d.service.OrderConfirmService;

/**
 * 
 * 
 * @author namikitsubasa
 *
 */
@Controller
@RequestMapping("/")
public class OrderConfirmController {

	@Autowired
	private OrderConfirmService orderConfirmService;

	@Autowired
	private HttpSession session;

	@RequestMapping("/orderConfirm")
	public String index(Integer userId) {

//		List<Order> orderList = orderConfirmService.showOrderedList(userId);
//
//		for (Order order : orderList) {
//			List<OrderItem> orderItemList = orderConfirmService.showOrderedItemList(order.getId());
//			order.setOrderItemList(orderItemList);
//			for (OrderItem orderItem : orderItemList) {
//				Item item = orderConfirmService.showItemList(orderItem.getItemId());
//				orderItem.setItem(item);
//				List<OrderTopping> orderToppingList = orderConfirmService.showOrderedToppingList(orderItem.getId());
//				orderItem.setOrderToppingList(orderToppingList);
//				for (OrderTopping orderTopping : orderToppingList) {
//					Topping topping = orderConfirmService.showToppingList(orderTopping.getToppingId());
//					orderTopping.setTopping(topping);
//				}
//			}
//
//		}
//
//		session.setAttribute("orderList", orderList);
		
		return "order_confirm";
	}

}
