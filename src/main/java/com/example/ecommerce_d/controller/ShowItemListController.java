package com.example.ecommerce_d.controller;

import java.util.ArrayList;
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
	@RequestMapping("/")
	public String showItemList(String searchName, Model model, String getOffset) {
		List<Item> itemList = null;
		//sql実行開始番号を初期化
		int offset = 0;
		//sql実行開始番号を商品検索画面のページングボタンから取得する.
		if (getOffset != null) {
			offset = Integer.parseInt(getOffset) * 6;
		}
		
		//あいまい検索で検索する.
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
			//あいまい検索したが検索条件が0の場合の処理
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
			//あいまい検索していない場合の処理
		} else if (searchName == null) {
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

		//商品一覧を3×3表示するメソッドの呼び出し
		List<List<Item>> itemListList = threeItemList(itemList);

		model.addAttribute("itemListList", itemListList);
		
		// オートコンプリート用にJavaScriptの配列の中身を文字列で作ってスコープへ格納
				StringBuilder itemListForAutocomplete = showItemListService.getItemListForAutocomplete(itemList);
				model.addAttribute("itemListForAutocomplete", itemListForAutocomplete);
		
		return "item_list_toy";
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

}
