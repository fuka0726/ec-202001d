package com.example.ecommerce_d.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_d.domain.Item;
import com.example.ecommerce_d.domain.Order;
import com.example.ecommerce_d.domain.OrderItem;
import com.example.ecommerce_d.domain.OrderTopping;
import com.example.ecommerce_d.domain.Topping;
import com.example.ecommerce_d.repository.ItemRepository;
import com.example.ecommerce_d.repository.OrderItemRepository;
import com.example.ecommerce_d.repository.OrderRepository;
import com.example.ecommerce_d.repository.OrderToppingRepository;
import com.example.ecommerce_d.repository.ToppingRepository;
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

	@Autowired
	private HttpSession session;
	
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderToppingRepository orderToppingRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ToppingRepository toppingRepository;

	@RequestMapping("/orderConfirm")
	public String index(Integer userId,Model model) {
//
//		List<Order> orderList = orderConfirmService.showOrderedList(userId);
//
//		for (Order order : orderList) {
//			List<OrderItem> orderItemList = orderConfirmService.showOrderedItemList(order.getId());
//			order.setOrderItemList(orderItemList);
//			for (OrderItem orderItem : order.getOrderItemList()) {
//				Item item = orderConfirmService.showItemList(orderItem.getItemId());
//				orderItem.setItem(item);
//				System.out.println("sss");
//				List<OrderTopping> orderToppingList = orderConfirmService.showOrderedToppingList(orderItem.getId());
//				orderItem.setOrderToppingList(orderToppingList);
//				for (OrderTopping orderTopping : orderItem.getOrderToppingList()) {
//					Topping topping = orderConfirmService.showToppingList(orderTopping.getToppingId());
//					orderTopping.setTopping(topping);
//				}
//			}
//
//		}
		
		List<Order> orderList = new ArrayList<>();
		// status = 4(配送完了)のリストを表示 ← 仕様に応じて要検討
		orderList = orderRepository.findByUserIdAndStatus(userId, 0);
		// それぞれの注文情報について処理
		for (Order order : orderList) {
			// 注文情報のIDを持つ注文アイテムリストをセット
			order.setOrderItemList(orderItemRepository.findByOrderId(order.getId()));
			// それぞれの注文アイテムについて処理
			for (OrderItem orderItem : order.getOrderItemList()) {
				// 注文アイテムのアイテムIDに一致するアイテムをセット
				orderItem.setItem(itemRepository.load(orderItem.getItemId()));
				// 注文アイテムのIDを持つトッピングリストをセット
				orderItem.setOrderToppingList(orderToppingRepository.findByOrderId(orderItem.getId()));
				// それぞれのトッピングリストについて処理
				for (OrderTopping orderTopping : orderItem.getOrderToppingList()) {
					// 注文トッピングのトッピングIDに一致するトッピングをセット
					orderTopping.setTopping(toppingRepository.load(orderTopping.getToppingId()));
				}
			}
		}
		model.addAttribute("orderList", orderList);
		
		return "order_confirm";
	}

}
