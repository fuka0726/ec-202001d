package com.example.ecommerce_d.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_d.domain.Item;

/**
 * itemsテーブルを表すリポジトリ.
 * 
 * @author fuka
 *
 */
@Repository
public class ItemRepository {

	/**
	 * Itemオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setDescription(rs.getString("description"));
		item.setPriceM(rs.getInt("price_m"));
		item.setPriceL(rs.getInt("price_l"));
		item.setImagePath(rs.getString("image_path"));
		item.setDeleted(rs.getBoolean("deleted"));
		return item;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 商品を全件検索します.
	 * 
	 * @return 商品一覧
	 */
	public List<Item> findAll() {
		String sql = "SELECT id, name, description, price_m, price_l, image_path, deleted FROM items ORDER BY id";
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * IDから商品を検索します.
	 * 
	 * @param id ID
	 * @return アイテム情報
	 */
	public Item load(Integer id) {
		String sql = "SELECT id, name, description, price_m, price_l, image_path, deleted"
				+ " FROM items WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER);
		return item;
	}

	/**
	 * 商品名を曖昧検索します.
	 * 
	 * @param name
	 * @return 検索された商品の名前.該当商品が存在しない場合は
	 */
	public List<Item> findByLikeName(String name) {
		String sql = "SELECT id, name, description, price_m,price_l,image_path,deleted "
				+ " FROM items WHERE name ilike :name";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%");
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}
	
	
	/**
	 * 
	 * 商品名を曖昧検索します.ページング用
	 * 
	 * @param name　商品名
	 * @param offset 検索開始位置
	 * @return 商品リスト
	 */
	public List<Item> findByLikeName(String name,Integer offset) {
		String sql = "SELECT id, name, description, price_m,price_l,image_path,deleted "
				+ " FROM items WHERE name ILIKE :name limit 6 offset :offset";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%").addValue("offset", offset);
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}
	
	
	/**
	 * 
	 *  商品名を全件検索します.ページング用
	 * 
	 * @param offset　　検索開始位置
	 * @return　商品リスト
	 */
	public List<Item> findAll(Integer offset) {
		String sql = "SELECT id, name, description, price_m, price_l, image_path, deleted FROM items ORDER BY id limit 6 offset :offset";
		SqlParameterSource param = new MapSqlParameterSource().addValue("offset", offset);
		List<Item> itemList = template.query(sql,param, ITEM_ROW_MAPPER);
		return itemList;
	}
	
	/**
	 * 商品を全件検索する。その際に並び順を指定した商品情報を返す.
	 * 
	 * @param culum カラム名（DESCを入れることで降順に)
	 * @param offset 検索開始位置
	 * @return　商品情報のリスト
	 */
	public List<Item> findAllOrderByCulum(String culum,Integer offset){
//		"ORDER BY :culum" の:culumに "price_l DESC"などを入れることによって昇順、降順も選択可能？
		String sql = "SELECT id, name, description, price_m, price_l, image_path, deleted FROM items ORDER BY " + culum + ",id LIMIT 6 OFFSET :offset";
		SqlParameterSource param = new MapSqlParameterSource().addValue("offset", offset);
		return template.query(sql, param,ITEM_ROW_MAPPER);
	}
	
	public List<Item>findByLikeNameOrderByCulum(String name,String culum,Integer offset){
		String sql = "SELECT id, name, description, price_m,price_l,image_path,deleted FROM items WHERE name ILIKE :name ORDER BY " + culum + ",id LIMIT 6 OFFSET :offset";
		SqlParameterSource param = new MapSqlParameterSource().addValue("offset", offset).addValue("name", "%" + name + "%");
		return template.query(sql, param,ITEM_ROW_MAPPER);
	}

}
