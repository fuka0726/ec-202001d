package com.example.ecommerce_d.repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_d.domain.Item;
import com.example.ecommerce_d.domain.Order;
import com.example.ecommerce_d.domain.OrderItem;
import com.example.ecommerce_d.domain.OrderTopping;
import com.example.ecommerce_d.domain.Topping;

/**
 * 注文リストをくっつける時のリポジトリ.
 * 
 * @author shumpei
 *
 */
@Repository
public class JoinOrderRepository {

	/**
	 * 5テーブルを結合したものからorderリストを作成する.
	 */
	private static final ResultSetExtractor<List<Order>> ORDER_RESULT_SET_EXTRACTOR = (rs) -> {
		List<Order> orderList = new LinkedList<Order>();
		List<OrderItem> orderItemList = null;
		List<OrderTopping> orderToppingList = null;
		long orderIdToCheck = 0;
		long orderItemIdToCheck = 0;
		while (rs.next()) {
			// データベースの検索値からOrderのIDを取得する
			int orderIdFromTable = rs.getInt("A_id");
			// 取得したOrderのIDが1つ前のループと重複しなければ記事をインスタンス化する
			if (orderIdToCheck != orderIdFromTable) {
				Order order = new Order();
				order.setId(orderIdFromTable);
				order.setUserId(rs.getInt("A_user_id"));
				order.setStatus(rs.getInt("A_status"));
				order.setTotalPrice(rs.getInt("A_total_price"));
				order.setOrderDate(rs.getDate("A_order_date"));
				order.setDestinationName(rs.getString("A_destination_name"));
				order.setDestinationEmail(rs.getString("A_destination_email"));
				order.setDestinationZipcode(rs.getString("A_destination_zipcode"));
				order.setDestinationAddress(rs.getString("A_destination_address"));
				order.setDestinationTel(rs.getString("A_destination_tel"));
				order.setDeliveryTime(rs.getTimestamp("A_delivery_time"));
				order.setPaymentMethod(rs.getInt("A_payment_method"));
				// OrderItemListをセット
				orderItemList = new ArrayList<OrderItem>();
				order.setOrderItemList(orderItemList);
				orderList.add(order);
			}
			// データベースの検索値からOrderItemのIDを取得する
			int orderItemIdFromTable = rs.getInt("B_id");
			// 取得したOrderItemのIDが1つ前のループと重複しなければOrderItemをインスタンス化
			if (orderItemIdToCheck != orderItemIdFromTable) {
				OrderItem orderItem = new OrderItem();
				orderItem.setId(orderItemIdFromTable);
				orderItem.setItemId(rs.getInt("B_item_id"));
				orderItem.setOrderId(rs.getInt("B_order_id"));
				orderItem.setQuantity(rs.getInt("B_quantity"));
				orderItem.setSize(rs.getString("B_size").toCharArray()[0]);
				orderItemList.add(orderItem);
				// OrderItemに連動するItemをインスタンス化
				Item item = new Item();
				item.setId(rs.getInt("C_id"));
				item.setName(rs.getString("C_name"));
				item.setDescription(rs.getString("C_description"));
				item.setPriceM(rs.getInt("C_price_m"));
				item.setPriceL(rs.getInt("C_price_l"));
				item.setImagePath(rs.getString("C_image_path"));
				item.setDeleted(rs.getBoolean("C_deleted"));
				orderItem.setItem(item);
				// ToppingListをセット
				orderToppingList = new ArrayList<OrderTopping>();
				orderItem.setOrderToppingList(orderToppingList);
			}
			// データベースからOrderToppingを取得できればインスタンス化する
			if (rs.getInt("D_id") != 0) {
				OrderTopping orderTopping = new OrderTopping();
				orderTopping.setId(rs.getInt("D_id"));
				orderTopping.setToppingId(rs.getInt("D_topping_id"));
				orderTopping.setOrderItemId(rs.getInt("D_order_item_id"));
				Topping topping = new Topping();
				topping.setId(rs.getInt("E_id"));
				topping.setName(rs.getString("E_name"));
				topping.setPriceM(rs.getInt("E_price_m"));
				topping.setPriceL(rs.getInt("E_price_l"));
				orderTopping.setTopping(topping);
				orderToppingList.add(orderTopping);
			}
			// チェック用のIDを更新する
			orderIdToCheck = orderIdFromTable;
			orderItemIdToCheck = orderItemIdFromTable;
		}
		return orderList;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * ユーザーと注文状況から注文情報リストを取得します.
	 * 
	 * @param userId
	 * @param status
	 * @return
	 */
	public List<Order> findByUserIdAndStatus(Integer userId, Integer status) {
		String sql = "SELECT A.id A_id, A.user_id A_user_id, A.status A_status, A.total_price A_total_price, A.order_date A_order_date, "
				+ "A.destination_name A_destination_name, A.destination_email A_destination_email, A.destination_zipcode "
				+ "A_destination_zipcode, A.destination_address A_destination_address, A.destination_tel A_destination_tel, "
				+ "A.delivery_time A_delivery_time, A.payment_method A_payment_method, B.id B_id, B.item_id B_item_id, B.order_id B_order_id, "
				+ "B.quantity B_quantity, B.size B_size, C.id C_id, C.name C_name, C.description C_description, C.price_m C_price_m, C.price_l "
				+ "C_price_l, C.image_path C_image_path, C.deleted C_deleted, D.id D_id, D.topping_id D_topping_id, D.order_item_id D_order_item_id, "
				+ "E.id E_id, E.name E_name, E.price_m E_price_m, E.price_l E_price_l "
				+ "FROM orders A LEFT OUTER JOIN order_items B ON A.id = B.order_id INNER JOIN items C  ON B.item_id = C.id  "
				+ "LEFT OUTER JOIN order_toppings D ON B.id = D.order_item_id INNER JOIN toppings E ON D.topping_id = E.id "
				+ "WHERE A.user_id = :userId AND A.status = :status ORDER BY A_id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);
		List<Order> orderList = template.query(sql, param, ORDER_RESULT_SET_EXTRACTOR);
		return orderList;
	}

}
