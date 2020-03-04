package com.example.ecommerce_d.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_d.domain.Order;
import com.example.ecommerce_d.domain.OrderItem;
import com.example.ecommerce_d.domain.OrderTopping;
import com.example.ecommerce_d.repository.ItemRepository;
import com.example.ecommerce_d.repository.OrderItemRepository;
import com.example.ecommerce_d.repository.OrderRepository;
import com.example.ecommerce_d.repository.OrderToppingRepository;
import com.example.ecommerce_d.repository.ToppingRepository;

/**
 * 注文履歴を表示するための業務処理クラス.
 * 
 * @author shumpei
 *
 */
@Service
public class ShowOrderHistoryService {

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
	public List<Order> showOrderHistory(Integer userId) {
		List<Order> orderList = new ArrayList<>();
		// status = 4(配送完了)のリストを表示 ← 仕様に応じて要検討
		orderList = orderRepository.findByUserIdAndStatus(userId, 4);
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
		return orderList;

	}

}
