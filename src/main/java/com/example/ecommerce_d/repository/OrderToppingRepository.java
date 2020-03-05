package com.example.ecommerce_d.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_d.domain.OrderTopping;

/**
 * 注文したトッピングを管理するリポジトリクラス.
 * 
 * @author shumpei
 *
 */
@Repository
public class OrderToppingRepository {

	private final static RowMapper<OrderTopping> ORDER_TOPPING_ROWMAPPER = (rs, i) -> {
		OrderTopping orderTopping = new OrderTopping();
		orderTopping.setId(rs.getInt("id"));
		orderTopping.setToppingId(rs.getInt("topping_id"));
		orderTopping.setOrderItemId(rs.getInt("order_item_id"));
		return orderTopping;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * IDからトッピング情報を取得します.
	 * 
	 * @param id ID
	 * @return トッピングリスト
	 */
	public List<OrderTopping> findByOrderId(Integer id) {
		String sql = "SELECT id, topping_id, order_item_id FROM order_toppings WHERE id = :id ORDER BY id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		List<OrderTopping> orderToppingList = template.query(sql, param, ORDER_TOPPING_ROWMAPPER);
		return orderToppingList;
	}
	
//	public void deleteById(Integer orderItemId) {
//		String sql = "DELETE FROM order_toppings WHERE order_item_id = :orderItemId;";
//		SqlParameterSource param = new MapSqlParameterSource().addValue("orderItemId", orderItemId);
//		template.update(sql, param);
//	}
	
	/**
	 * オーダートッピング情報を挿入します.
	 * 
	 * @param orderTopping オーダートッピング情報
	 */
	public void insert(OrderTopping orderTopping) {
		String sql = "INSERT INTO order_toppings (topping_id,order_item_id) VALUES (:toppingId,:orderItemId);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderTopping);
		template.update(sql, param);
	}

}
