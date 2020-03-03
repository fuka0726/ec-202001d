package com.example.ecommerce_d.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/LoginLogout")
public class LoginLogoutController {

	@Autowired
	private LoginLogoutUserService loginLogoutUserService;

	@Autowired
	private HttpSession session;

	/**
	 * @param form ログイン画面から送られるユーザ情報.
	 * @return ログイン画面もしくは検索リストに画面遷移
	 */
	@RequestMapping("/login")
	public String Login(UserForm form) {

		User user = loginLogoutUserService.login(form.getMail(), form.getPassword());
		if (user == null) {
			return "login";
		} // else if(session.getAttribute("name")!=null) (ログインされていないことの条件)

		session.setAttribute("name", user.getName());

		return "item_list_toy";
	}

}
