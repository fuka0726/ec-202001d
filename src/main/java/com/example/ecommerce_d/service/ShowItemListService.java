package com.example.ecommerce_d.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_d.domain.Item;
import com.example.ecommerce_d.repository.ItemRepository;

/**
 * 商品一覧を検索するサービスクラス.
 * 
 * @author fuka
 *
 */
@Service
public class ShowItemListService {

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * @param name 商品名
	 * @return 検索された商品一覧
	 */
	public List<Item> searchByName(String name) {
		List<Item> itemList = itemRepository.findByLikeName(name);
		return itemList;
	}

}
