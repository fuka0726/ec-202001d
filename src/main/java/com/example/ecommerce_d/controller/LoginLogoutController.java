package com.example.ecommerce_d.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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

	/**
	 * @param form ログイン画面から送られるユーザ情報.
	 * @return ログイン画面もしくは検索リストに画面遷移
	 */
	@RequestMapping("/login")
	public String Login(UserForm form) {

		User user = loginLogoutUserService.login(form.getEmail(), form.getPassword());
		if (user == null) {
			return toLogin();
		} // else if(session.getAttribute("name")!=null) (ログインされていないことの条件)
// ショッピングカートから注文に進むときログインされてなったらログイン画面に移動し、ログイン後注文かくにん画面に移動
		session.setAttribute("name", user.getName());

		return "item_list_toy";
	}

	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/tologin";
	}

}
