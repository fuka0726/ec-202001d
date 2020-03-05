package com.example.ecommerce_d.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_d.domain.Order;
import com.example.ecommerce_d.domain.OrderItem;
import com.example.ecommerce_d.domain.OrderTopping;
import com.example.ecommerce_d.form.AddItemForm;
import com.example.ecommerce_d.repository.ItemRepository;
import com.example.ecommerce_d.repository.OrderItemRepository;
import com.example.ecommerce_d.repository.OrderRepository;
import com.example.ecommerce_d.repository.OrderToppingRepository;
import com.example.ecommerce_d.repository.ToppingRepository;

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
	private ItemRepository itemRepository;

	@Autowired
	private ToppingRepository toppingRepository;

	/**
	 * ユーザーIDに紐づく注文履歴を表示します.
	 * 
	 * @param userId ユーザーID
	 * @return 注文情報リスト
	 */
	public Order showCartList(Integer userId) {

		// status = 4(配送完了)のリストを表示 ← 仕様に応じて要検討
		Order order = orderRepository.findByUserIdAndStatus(userId, 0);
		// それぞれの注文情報について処理

		// 注文情報のIDを持つ注文アイテムリストをセット
		order.setOrderItemList(orderItemRepository.findListByOrderId(order.getId()));
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

		return order;
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

	/**
	 * IDに紐づく注文アイテムと注文トッピングを注文から外します.
	 * 
	 * @param id ID
	 */
	public void removeOrderItemAndOrderTopping(Integer id) {
		orderItemRepository.deleteByID(id);
	}

//	public void removeOrderItem(Integer orderItemId) {
//		orderItemRepository.deleteById(orderItemId);
//		orderToppingRepository.deleteById(orderItemId);
//	}
}
