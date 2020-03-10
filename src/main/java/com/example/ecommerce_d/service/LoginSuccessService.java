package com.example.ecommerce_d.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_d.domain.Order;
import com.example.ecommerce_d.repository.JoinOrderRepository;
import com.example.ecommerce_d.repository.OrderItemRepository;
import com.example.ecommerce_d.repository.OrderRepository;

/**
 * ログイン成功時のサービスクラス.
 * 
 * @author shumpei
 *
 */
@Service
public class LoginSuccessService {
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private JoinOrderRepository joinOrderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;
	
	/**
	 * ユーザーIDから注文情報を取得します.
	 * 
	 * @param userId ユーザーID
	 * @return 注文情報
	 */
	public Order findCartList(Integer userId) {
		List<Order> orderList = joinOrderRepository.findByUserIdAndStatus(userId, 0);
		if (orderList.size() == 0) {
			return null;
		}
		Order order = orderList.get(0);
		return order;
	}
	
	
	/**
	 * 注文情報のユーザーIDを更新します.
	 * 
	 * @param loginUserId ログインしたユーザーのID
	 * @param dummyUserId 仮ユーザーのID
	 */
	public void updateUserIdOfOrders(int loginUserId, int dummyUserId) {
		orderRepository.updateUserId(loginUserId, dummyUserId);
	}
	

	
	/**
	 * 注文アイテムのユーザーIDを更新します.
	 * 
	 * @param loginOrderId ログインしたユーザーの注文情報のID
	 * @param dummyOrderId 仮ユーザーの注文情報のID
	 */
	public void updateUserIdOfOrderItems(int loginOrderId, int dummyOrderId) {
		orderItemRepository.updateUserId(loginOrderId, dummyOrderId);
	}

	/**
	 * ユーザーIDから注文情報を削除します.
	 * 
	 * @param userId
	 */
	public void deleteByUserId(Integer userId) {
		orderRepository.deleteByUserId(userId);
	}

}
