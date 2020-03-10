package com.example.ecommerce_d.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_d.domain.LoginUser;
import com.example.ecommerce_d.domain.Order;
import com.example.ecommerce_d.service.LoginSuccessService;

/**
 * ログイン成功時の挙動を操作するコントローラ.
 * 
 * @author shumpei
 *
 */
@Controller
@RequestMapping("")
public class LoginSuccessController {
	@Autowired
	private LoginSuccessService service;

	@Autowired
	private HttpSession session;

	/**
	 * ログイン成功時に操作を行います.
	 * 
	 * @return 商品一覧画面
	 */
	@RequestMapping("/login-success")
	public String loginSuccess(@AuthenticationPrincipal LoginUser loginUser) {
		//仮のユーザーIDで作成したカートをログインユーザーで更新する
		if (session.getAttribute("dummyUserId") != null) {
			// セッションの仮ユーザーIDで注文情報を取得する
			int dummyUserId = (int) (session.getAttribute("dummyUserId"));
			int loginUserId = loginUser.getUser().getId();
			Order dummyOrder = service.findCartList(dummyUserId);
			// ログインユーザーのユーザーIDで注文情報を取得する
			Order loginOrder = service.findCartList(loginUserId);
			//既にログインユーザでカートにアイテムを追加していた場合
			if(loginOrder != null) {
				// Order_itemsでオーダーIDのダミーユーザーIDをログインユーザーIDに更新する
				service.updateUserIdOfOrderItems(loginOrder.getId(), dummyOrder.getId());
				// セッションの仮ユーザーIDを持つOrderを削除する
				service.deleteByUserId(dummyUserId);				
			}else {
				//Orderのuser_idをログインユーザーIDに更新する
				service.updateUserIdOfOrders(loginUserId, dummyUserId);
			}
		}
		System.out.println(session.getAttribute("referer"));
		return "redirect:" + (String)session.getAttribute("referer");
	}
}
