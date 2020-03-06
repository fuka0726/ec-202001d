package com.example.ecommerce_d.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_d.domain.Order;
import com.example.ecommerce_d.repository.JoinOrderRepository;

@Service
public class OrderConfirmService {
	
	@Autowired
	private JoinOrderRepository joinOrderRepository;
	
	
	/**
	 * 
	 * ユーザーから注文前の注文情報リストを取得します.
	 * 
	 * @param userId　ユーザーid
	 * @return 注文リスト
	 */
	public List<Order> showOrderedList(Integer userId){
		List<Order> orderList=joinOrderRepository.findByUserIdAndStatus(userId,0);
		return orderList;
	}

}
