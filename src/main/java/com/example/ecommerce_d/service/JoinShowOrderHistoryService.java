package com.example.ecommerce_d.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_d.domain.Order;
import com.example.ecommerce_d.repository.JoinOrderRepository;

@Service
public class JoinShowOrderHistoryService {

	@Autowired
	private JoinOrderRepository repository;

	/**
	 * ユーザーIDに紐づく注文履歴を表示します.
	 * 
	 * @param userId ユーザーID
	 * @return 注文情報リスト
	 */
	public List<Order> showOrderHistory(Integer userId) {
		List<Order> orderList = new ArrayList<>();
		// status = 4(配送完了)のリストを表示 ← 仕様に応じて要検討
		orderList = repository.findByUserIdAndStatus(userId, 4);
		return orderList;
	}
}
