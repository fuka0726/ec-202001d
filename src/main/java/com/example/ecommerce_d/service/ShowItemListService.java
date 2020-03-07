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
	 * オートコンプリート用にJavaScriptの配列の中身を文字列で作ります.
	 * 
	 * @param employeeList　商品一覧
	 * @return　オートコンプリート用JavaScriptの配列の文字列
	 * 　　　
	 */
	public StringBuilder getItemListForAutocomplete(List<Item> itemList) {
		StringBuilder itemListForAutocomplete = new StringBuilder();
		for (int i = 0; i < itemList.size(); i++) {
			if (i != 0) {
				itemListForAutocomplete.append(",");
			}
			Item item = itemList.get(i);
			itemListForAutocomplete.append("\"");
			itemListForAutocomplete.append(item.getName());
			itemListForAutocomplete.append("\"");
		}
		return itemListForAutocomplete;
	}
	
	
	
	
	
}
