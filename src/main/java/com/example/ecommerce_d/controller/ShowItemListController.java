package com.example.ecommerce_d.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

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
	
	@Autowired
	private HttpSession session;

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
		session.setAttribute("culum", 1);
		session.setAttribute("getOffset", 1);
		//sql実行開始番号を初期化

		int offset = 0;
		// sql実行開始番号を商品検索画面のページングボタンから取得する.
		if (getOffset != null) {
			offset = Integer.parseInt(getOffset) * 6;
		}

		// あいまい検索で検索する.
		if (searchName != null && showItemListService.searchByName(searchName).size() != 0) {
			itemList = showItemListService.searchByName(searchName);
			int listNumber = itemList.size() / 6;
			List<Integer> numList = new ArrayList<>();
			int i = 0;
			do {
				numList.add(i);
				i++;
			} while (i < listNumber);
			model.addAttribute("numList", numList);
			itemList = showItemListService.searchByName(searchName, offset);
			// あいまい検索したが検索条件が0の場合の処理
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
			// あいまい検索していない場合の処理
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

		// 商品一覧を3×3表示するメソッドの呼び出し
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

	/**
	 * 商品一覧の表示、場合によっては検索欄に入力された文字での検索結果を表示する.
	 * 
	 * @param searchName　検索欄に入力された文字
	 * @param model　リクエストスコープ
	 * @param getOffset　商品一覧の下部にあるクリックされたボタンの値
	 * @param culum　カラム名
	 * @return
	 */
	@RequestMapping("/show-ordered")
	public String showOrderedList(String searchName, Model model, String getOffset,String culum) {
		System.out.println(searchName);
		System.out.println(getOffset);
		System.out.println(culum);
		System.out.println("------------------------");
		
		session.setAttribute("culum", culum);
		session.setAttribute("getOffset", getOffset);
		
		List<Item> itemList = null;
		// sql実行開始番号を初期化
		int offset = 0;
		int getOffsetInt = Integer.parseInt(getOffset);
		// sql実行開始番号を商品検索画面のページングボタンから取得する.
		if (getOffsetInt == 1) {
			offset = 0;
		} else if (getOffsetInt >= 2) {
			offset = (getOffsetInt - 1) * 6;
		}

		// あいまい検索で検索する.
		if (searchName != null && showItemListService.searchByName(searchName).size() != 0) {
			System.out.println("きてる");
			itemList = showItemListService.searchByName(searchName);
			int listNumber = itemList.size() / 6;
			List<Integer> numList = new ArrayList<>();
			int i = 0;
			do {
				numList.add(i);
				i++;
			} while (i < listNumber);
			System.out.println(offset);
			itemList = showItemListService.searchByNameOrderByCulum(searchName, culum, offset);
			model.addAttribute("numList", numList);
			// あいまい検索したが検索条件が0の場合の処理
		} else if (searchName != null) {
			itemList = showItemListService.showItemList();
			int listNumber = itemList.size() / 6;
			List<Integer> numList = new ArrayList<>();
			int i = 0;
			do {
				numList.add(i);
				i++;
			} while (i < listNumber);
			itemList = showItemListService.showItemListOrderByCulum(culum, offset);
			model.addAttribute("numList", numList);

			model.addAttribute("errormessage", "該当する商品がありません");
			// あいまい検索していない場合の処理
		} else if (searchName == null) {
			itemList = showItemListService.showItemList();
			int listNumber = itemList.size() / 6;
			List<Integer> numList = new ArrayList<>();
			int i = 0;
			do {
				numList.add(i);
				i++;
			} while (i < listNumber);
			itemList = showItemListService.showItemListOrderByCulum(culum, offset);
			model.addAttribute("numList", numList);
		}

		// 商品一覧を3×3表示するメソッドの呼び出し
		List<List<Item>> itemListList = threeItemList(itemList);

		model.addAttribute("itemListList", itemListList);

		// オートコンプリート用にJavaScriptの配列の中身を文字列で作ってスコープへ格納
				StringBuilder itemListForAutocomplete = showItemListService.getItemListForAutocomplete(itemList);
				model.addAttribute("itemListForAutocomplete", itemListForAutocomplete);
				System.out.println(itemListForAutocomplete);
		
			if(searchName == null) {
				session.setAttribute("searchName", "商品名をいれてください");
			} else {
				session.setAttribute("searchName", searchName);
			}
			return "item_list_toy";	


	}

}
