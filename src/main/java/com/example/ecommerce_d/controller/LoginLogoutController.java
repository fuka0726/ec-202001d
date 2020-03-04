package com.example.ecommerce_d.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_d.domain.User;
import com.example.ecommerce_d.form.UserForm;
import com.example.ecommerce_d.service.LoginLogoutUserService;

/**
 * 
 * ログイン、ログアウト機能を持つコントローラ.
 * 
 * @author namikitsubasa
 *
 */
@Controller
@RequestMapping("")
public class LoginLogoutController {

	@Autowired
	private LoginLogoutUserService loginLogoutUserService;

	@Autowired
	private HttpSession session;

	/**
	 * 
	 * ログイン画面へ画面遷移する.
	 * 
	 * @return ログイン画面へ画面遷移
	 */
	@RequestMapping("/tologin")
	public String toLogin() {
		return "login";
	}

	//カートリスト 注文へ進む→(ログインしておらず)ログイン画面に遷移した場合、ログイン後注文確認画面に遷移する。
	//カートリストから画面遷移したかどうかは、仮で注文へ進むボタンを押した時、バリューが"1"送信される体で作成
	/**
	 * @param form ログイン画面から送られるユーザ情報.
	 * @return ログイン画面もしくは検索リストに画面遷移
	 */
	@RequestMapping("/login")
	public String Login(UserForm form,String num, Model model) {
		
		User user = loginLogoutUserService.login(form.getEmail(), form.getPassword());
		if (user == null) {
			model.addAttribute("error", "メールアドレス、またはパスワードが間違っています");
			return toLogin();
		} else if (session.getAttribute("name") == null) {
			session.setAttribute("name", user.getName());
		}
		
		if(num.equals("1")){
			return "order_confirm";
		}
		return "item_list_toy";
	}

	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/tologin";
	}

}
