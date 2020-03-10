package com.example.ecommerce_d.service;

import java.util.ArrayList;
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
	
	public List<Item> showItemListOrderByCulum(String culum,Integer offset){
		List<Item> itemList = new ArrayList<Item>();
		if(culum.equals("1")) {
			culum = "price_m";
			itemList = itemRepository.findAllOrderByCulum(culum, offset);
		}else if(culum.equals("2")) {
			culum = "price_m DESC";
			itemList = itemRepository.findAllOrderByCulum(culum, offset);
		} else if(culum.equals("0")) {
			itemList = itemRepository.findAll(offset);
		}
		return itemList;
	}
	
	public List<Item> searchByNameOrderByCulum(String name,String culum,Integer offset){
		List<Item> itemList = new ArrayList<Item>();
		if(culum.equals("1")) {
			culum = "price_m";
			itemList = itemRepository.findByLikeNameOrderByCulum(name, culum, offset);
		}else if(culum.equals("2")) {
			culum = "price_m DESC";
			itemList = itemRepository.findByLikeNameOrderByCulum(name, culum, offset);
		} else if(culum.equals("0")) {
			itemList = itemRepository.findByLikeName(name, offset);
		}
		return itemList;
	}
	
}
