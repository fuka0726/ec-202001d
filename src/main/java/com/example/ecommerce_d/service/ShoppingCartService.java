package com.example.ecommerce_d.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_d.domain.Order;
import com.example.ecommerce_d.domain.OrderItem;
import com.example.ecommerce_d.domain.OrderTopping;
import com.example.ecommerce_d.form.AddItemForm;
import com.example.ecommerce_d.repository.JoinOrderRepository;
import com.example.ecommerce_d.repository.OrderItemRepository;
import com.example.ecommerce_d.repository.OrderRepository;
import com.example.ecommerce_d.repository.OrderToppingRepository;

@Service
@Transactional
public class ShoppingCartService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderToppingRepository orderToppingRepository;

	@Autowired
	private JoinOrderRepository joinOrderRepository;

	/**
	 * ユーザーIDに紐づく注文履歴を表示します.
	 * 
	 * @param userId ユーザーID
	 * @return 注文情報リスト
	 */
	public Order showCartList(Integer userId) {
		List<Order> orderList = new ArrayList<>();
		// status = 4(配送完了)のリストを表示 ← 仕様に応じて要検討
		orderList = joinOrderRepository.findByUserIdAndStatus(userId, 0);
		return orderList.get(0);
		
	}

	public void addItem(AddItemForm form, Integer userId) {
		Order order = new Order();
		order.setUserId(userId);
		order.setStatus(0);

		if (orderRepository.findByUserIdAndStatus(userId, 0) == null) {
			order = orderRepository.insert(order);
		} else {
			order = orderRepository.findByUserIdAndStatus(userId, 0);
		}

		OrderItem orderItem = new OrderItem();
		Integer intItemId = Integer.parseInt(form.getItemId());
		orderItem.setItemId(intItemId);
		orderItem.setOrderId(order.getId());
		orderItem.setSize(form.getSize().toCharArray()[0]);
		orderItem.setQuantity(Integer.parseInt(form.getQuantity()));

		orderItem = orderItemRepository.insert(orderItem);

		List<Integer> tooppingIntegerList = form.getTopping();
		for (Integer toppingId : tooppingIntegerList) {
			OrderTopping orderTopping = new OrderTopping();
			orderTopping.setOrderItemId(orderItem.getId());
			orderTopping.setToppingId(toppingId);
			orderToppingRepository.insert(orderTopping);
		}

	}

//	public void removeOrderItem(Integer orderItemId) {
//		orderItemRepository.deleteById(orderItemId);
//		orderToppingRepository.deleteById(orderItemId);
//	}
}
