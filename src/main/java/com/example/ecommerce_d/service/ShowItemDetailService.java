package com.example.ecommerce_d.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_d.domain.Item;
import com.example.ecommerce_d.repository.ItemRepository;

/**
 * 商品情報を操作するサービス.
 * 
 * @author fuka
 *
 */
@Service
public class ShowItemDetailService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	
	/**
	 * 
	 * 商品情報を取得します.
	 * @param id ID
	 * @return 商品情報
	 */
	public Item showItemDetail(Integer id) {
		Item item = itemRepository .load(id);
		return item;
	}
	
	
	
	
}
