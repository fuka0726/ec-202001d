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
	 * 商品情報を全件取得します.
	 * 
	 * @return 商品情報一覧
	 */
	public List<Item> showItemList() {
		List<Item> itemList = itemRepository.findAll();
		return itemList;
	}

	/**
	 *
	 * 名前から商品を曖昧検索します.
	 * 
	 * @param name 商品名
	 * @return 検索された商品一覧
	 */
	public List<Item> searchByName(String name) {
		List<Item> itemList = itemRepository.findByLikeName(name);
		return itemList;
	}
	
	/**
	 *
	 * 名前から商品を曖昧検索します.(ページング用)
	 * 
	 * @param name 商品名
	 * @return 検索された商品一覧
	 */
	public List<Item> searchByName(String name,Integer offset) {
		List<Item> itemList = itemRepository.findByLikeName(name,offset);
		return itemList;
	}

	/**
	 * 
	 * 名前から商品を全件検索します.(ページング用)
	 * 
	 * @param offset sql検索対象の始まりのid
	 * @return 商品情報一覧
	 */
	public List<Item> showItemList(Integer offset) {
		List<Item> itemList = itemRepository.findAll(offset);
		return itemList;
	}
	
}
