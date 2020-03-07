package com.example.ecommerce_d.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_d.domain.Item;
import com.example.ecommerce_d.repository.ItemRepository;
import com.example.ecommerce_d.service.ShowItemListService;

/**
 * 商品情報を操作するコントローラー.
 * 
 * @author fuka
 *
 */
@Controller
@RequestMapping("")
public class ShowItemListController {

	@Autowired
	private ShowItemListService showItemListService;


	/**
	 * 商品一覧の表示、商品名を検索します.
	 * 
	 * @param name
	 * @param model
	 * @return (検索された)商品一覧画面
	 */
	@RequestMapping("/showItemList")
	public String showItemList(String searchName, Model model, String getOffset) {
		List<Item> itemList = null;
		int offset = 0;
		if (getOffset != null) {
			offset = Integer.parseInt(getOffset) * 6;
		}

		if (searchName != null && showItemListService.searchByName(searchName).size() != 0) {
			itemList = showItemListService.searchByName(searchName);
			int listNumber = itemList.size() / 6;
			List<Integer> numList = new ArrayList<>();
			int i = 0;
			do {
				numList.add(i);
				i++;
			} while (i < listNumber);
			System.out.println(offset);
			itemList = showItemListService.searchByName(searchName, offset);
			model.addAttribute("numList", numList);
		} else if (searchName != null) {
			itemList = showItemListService.showItemList();
			int listNumber = itemList.size() / 6;
			List<Integer> numList = new ArrayList<>();
			int i = 0;
			do {
				numList.add(i);
				i++;
			} while (i < listNumber);
			itemList = showItemListService.showItemList(offset);
			model.addAttribute("numList", numList);

			model.addAttribute("errormessage", "該当する商品がありません");
		} else if(searchName == null){
		itemList = showItemListService.showItemList();
		int listNumber = itemList.size() / 6;
		List<Integer> numList = new ArrayList<>();
		int i = 0;
		do {
			numList.add(i);
			i++;
		} while (i < listNumber);
		itemList = showItemListService.showItemList(offset);
		model.addAttribute("numList", numList);
	}
	
	List<List<Item>> itemListList = threeItemList(itemList);

	model.addAttribute("itemListList",itemListList);
	return"item_list_toy";
	}

	/**
	 * 商品一覧を3×3表示するメソッド
	 * 
	 * @param itemList
	 * @return itemListList
	 */
	private List<List<Item>> threeItemList(List<Item> itemList) {
		List<List<Item>> itemListList = new ArrayList<>();
		List<Item> threeItemList = new ArrayList<>();

		for (int i = 1; i <= itemList.size(); i++) {
			threeItemList.add(itemList.get(i - 1));

			if (i % 3 == 0 || i == itemList.size()) {
				itemListList.add(threeItemList);
				threeItemList = new ArrayList<>();
			}
		}
		return itemListList;
	}

//	@RequestMapping("/showItemList")
//	public String showItemList(Model model) {
//		List<Item> itemList = showItemListService.showItemList();
//		model.addAttribute("itemList", itemList);
//		return "item_list_toy";
//	}
//	
//	
//	@RequestMapping("/search")
//	public String searchByName(String name,Model model) {
//		List<Item> itemList = showItemListService.searchByName(name);
//		model.addAttribute("itemList", itemList);
//		if(itemList.size() == 0) {
//			model.addAttribute("errorMessage", "該当する商品がありません");
//			showItemList(model);
//		}
//		return "item_list_toy";
//		
//		
//		
//	}

}
