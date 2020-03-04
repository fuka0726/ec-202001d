package com.example.ecommerce_d.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class OrderConfirmService {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private OrderToppingRepository orderToppingRepository;
	@Autowired
	private ToppingRepository toppingRepository;

	/**
	 * 
	 * 注文情報を取得する.
	 * 
	 * @param userId ユーザーid
	 * @return 注文リスト
	 */
	public List<Order> showOrderedList(Integer userId) {
		List<Order> orderList = orderRepository.findByUserIdAndStatus(userId, 0);
		return orderList;
	}

	/**
	 * 注文商品を取得する.
	 * 
	 * @param orderId 注文商品id
	 * @return 注文商品リスト
	 */
	public List<OrderItem> showOrderedItemList(Integer orderId) {
		List<OrderItem> orderItemList = orderItemRepository.findByOrderId(orderId);
		return orderItemList;
	}

	/**
	 * 
	 * 注文トッピングを取得する.
	 * 
	 * @param orderItemId 注文トッピングid
	 * @return 注文トッピングリスト
	 */
	public List<OrderTopping> showOrderedToppingList(Integer orderItemId) {
		List<OrderTopping> orderToppingList = orderToppingRepository.findByOrderId(orderItemId);
		return orderToppingList;
	}

	/**
	 * 
	 * 注文商品詳細を取得する.
	 * 
	 * @param itemId 注文商品id
	 * @return 注文商品詳細
	 */
	public Item showItemList(Integer itemId) {
		Item item = itemRepository.load(itemId);
		return item;
	}

	/**
	 * 
	 * 注文トッピング詳細を取得する.
	 * 
	 * @param toppingId 注文トッピングid
	 * @return 注文トッピング詳細
	 */
	public Topping showToppingList(Integer toppingId) {
		Topping toppingList = toppingRepository.findByToppingId(toppingId);
		return toppingList;
	}

}
