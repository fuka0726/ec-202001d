package com.example.ecommerce_d.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_d.domain.OrderItem;

/**
 * 注文商品テーブルを操作するためのリポジトリクラス.
 * 
 * @author shumpei
 *
 */
@Repository
public class OrderItemRepository {

	private final static RowMapper<OrderItem> ORDER_ITEM_ROWMAPPER = (rs, i) -> {
		OrderItem orderItem = new OrderItem();
		orderItem.setId(rs.getInt("id"));
		orderItem.setItemId(rs.getInt("item_id"));
		orderItem.setOrderId(rs.getInt("order_id"));
		orderItem.setQuantity(rs.getInt("quantity"));
		orderItem.setSize(rs.getString("size").toCharArray()[0]);
		return orderItem;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 注文IDから該当する注文アイテム（商品の概要と注文内容）を取得します.
	 * 
	 * @param orderId
	 * @return 注文アイテムリスト
	 */
	public List<OrderItem> findByOrderId(Integer orderId) {
		String sql = "SELECT id, item_id, order_id, quantity, size FROM order_items WHERE order_id = :orderId ORDER BY id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderId);
		List<OrderItem> orderItemList = template.query(sql, param, ORDER_ITEM_ROWMAPPER);
		return orderItemList;
	}
	
	/**
	 * IDに紐づく注文アイテムと注文トッピングを削除します.
	 * 
	 * @param id
	 */
	public void deleteByID(Integer id) {
		String sql = "WITH deleted AS (DELETE FROM order_items WHERE id = :id RETURNING id) " 
				+ "DELETE FROM order_toppings WHERE order_item_id IN (SELECT id FROM deleted)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}

}
