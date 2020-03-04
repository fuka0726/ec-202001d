package com.example.ecommerce_d.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_d.domain.Item;
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
	public String showItemList(String searchName,Model model) {
		List<Item> itemList = showItemListService.showItemList();
		if (searchName != null) {
			itemList = showItemListService.searchByName(searchName);
			model.addAttribute("itemList", itemList);
		}
		if(itemList.size() == 0) {
				itemList = showItemListService.showItemList();
				model.addAttribute("errormessage", "該当する商品がありません");
			}				
		model.addAttribute("itemList", itemList);
		return "item_list_toy";
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
