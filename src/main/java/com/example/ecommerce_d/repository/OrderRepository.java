package com.example.ecommerce_d.repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_d.domain.Order;

/**
 * 注文テーブルを操作するためのリポジトリクラス.
 * 
 * @author shumpei
 *
 */
@Repository
public class OrderRepository {

	private final static RowMapper<Order> ORDER_ROW_MAPPER = (rs, i) -> {
		Order order = new Order();
		order.setId(rs.getInt("id"));
		order.setUserId(rs.getInt("user_id"));
		order.setStatus(rs.getInt("status"));
		order.setTotalPrice(rs.getInt("total_price"));
		order.setOrderDate(rs.getDate("order_date"));
		order.setDestinationName(rs.getString("destination_name"));
		order.setDestinationEmail(rs.getString("destination_email"));
		order.setDestinationZipcode(rs.getString("destination_zipcode"));
		order.setDestinationAddress(rs.getString("destination_address"));
		order.setDestinationTel(rs.getString("destination_tel"));
		order.setDeliveryTime(rs.getTimestamp("delivery_time"));
		order.setPaymentMethod(rs.getInt("payment_method"));
		return order;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private SimpleJdbcInsert insert;
	
	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert =
				new SimpleJdbcInsert((JdbcTemplate)template.getJdbcOperations());
		SimpleJdbcInsert withTableName =
				simpleJdbcInsert.withTableName("orders");
		insert = withTableName.usingGeneratedKeyColumns("id");
	}

	/**
	 * IDから注文状況を取得します.
	 * 
	 * @param id ID
	 * @return 注文状況
	 */
	public Order load(Integer id) {
		String sql = "SELECT id, user_id, status, total_price, order_date,destination_name, destination_email,"
				+ " destination_zipcode, destination_address, destination_tel, delivery_time, payment_method"
				+ " FROM orders WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Order order = template.queryForObject(sql, param, ORDER_ROW_MAPPER);
		return order;
	}

	/**
	 * 注文確認用の注文情報をリストで取得します.
	 * 
	 * @param userId ユーザーID
	 * @param status 注文状態
	 * @return 注文リスト
	 */
	public List<Order> findListByUserIdAndStatus(Integer userId, Integer status) {
		List<Order> orderList = new ArrayList<>();
		String sql = "SELECT id, user_id, status, total_price, order_date,destination_name, destination_email,"
				+ " destination_zipcode, destination_address, destination_tel, delivery_time, payment_method"
				+ " FROM orders WHERE user_id = :userId AND status = :status ORDER BY id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);
		orderList = template.query(sql, param, ORDER_ROW_MAPPER);
		return orderList;
	}
	
	/**
	 * ショッピングカート用の注文情報を単体で取得します.
	 * 
	 * @param userId ユーザーID
	 * @param status 注文状態
	 * @return　注文リスト
	 */
	public Order findByUserIdAndStatus(Integer userId, Integer status) {
		String sql = "SELECT id, user_id, status, total_price, order_date,destination_name, destination_email,"
				+ " destination_zipcode, destination_address, destination_tel, delivery_time, payment_method"
				+ " FROM orders WHERE user_id = :userId AND status = :status ORDER BY id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);
		List<Order> orderList = template.query(sql, param,ORDER_ROW_MAPPER);
		if(orderList.size() == 0) {
			return null;
		}
		return orderList.get(0);
	}
	
	/**
	 * order情報を受け取ってそれをDB上に挿入します.
	 * 
	 * @param order オーダー情報
	 * @return　idを入れたオーダー情報
	 */
	public Order insert(Order order) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		Number key = insert.executeAndReturnKey(param);
		order.setId(key.intValue());
		return order;
		
	}

	/**
	 * 注文時に注文状態を更新します.
	 * 
	 * @param order 注文情報
	 */
	public void updateStatus(Order order) {
		String sql = "UPDATE orders SET status = :status,payment_method =:status,destination_name =:destinationName,destination_email =:destinationEmail,destination_zipcode =:destinationZipcode,destination_address =:destinationAddress,destination_tel =:destinationTel,delivery_time=:deliveryTime,order_date =:orderDate WHERE user_id = :userId and status=0";
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
//		SqlParameterSource param = new MapSqlParameterSource().addValue("status", order.getPaymentMethod()).addValue("payment_method", order.getPaymentMethod());//.addValue("user_id", order.getUserId());
		template.update(sql, param);
	}
	
	public void updateTotalPrice(Order order) {
		String sql = "UPDATE orders SET total_price = :totalPrice WHERE id = :id ;";
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		template.update(sql, param);
	}

}
