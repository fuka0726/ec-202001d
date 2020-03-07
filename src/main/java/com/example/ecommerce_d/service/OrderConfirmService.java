package com.example.ecommerce_d.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_d.domain.Order;
import com.example.ecommerce_d.repository.JoinOrderRepository;
import com.example.ecommerce_d.repository.OrderRepository;

@Service
public class OrderConfirmService {
	
	@Autowired
	private JoinOrderRepository joinOrderRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	/**
	 * 
	 * ユーザーから注文前の注文情報リストを取得します.
	 * 
	 * @param userId　ユーザーid
	 * @return 注文リスト
	 */
	public List<Order> showOrderedList(Integer userId){
		List<Order> orderList=joinOrderRepository.findByUserIdAndStatus(2,0);
		return orderList;
	}
	
	/**
	 * お届け情報から注文ステータスを更新する.
	 * 
	 * @param order お届け情報
	 */
	public void updateStatus(Order order){
		order.setUserId(2);
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String format = ldt.format(formatter);// formatはメソッド
		Date date = Date.valueOf(format);
		order.setOrderDate(date);
		orderRepository.updateStatus(order);
		
	}

}
