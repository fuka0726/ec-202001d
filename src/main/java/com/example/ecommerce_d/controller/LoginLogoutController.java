package com.example.ecommerce_d.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

//import javax.servlet.http.HttpSession;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import com.example.ecommerce_d.domain.User;
//import com.example.ecommerce_d.form.UserForm;
//import com.example.ecommerce_d.service.LoginLogoutUserService;

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

//	SpringSecurityが実装するためコメントアウトしました
//	@Autowired
//	private LoginLogoutUserService loginLogoutUserService;
//	
	@Autowired
	private HttpSession session;

	@Autowired
	private HttpServletRequest request;

	/**
	 * 
	 * ログイン画面へ画面遷移する.
	 * 
	 * @return ログイン画面へ画面遷移
	 */
	@RequestMapping("/tologin")
	public String toLogin(Model model, @RequestParam(required = false) String error) {
		// ログイン前のパスをセッションに格納する
		System.out.println("referer:" + request.getHeader("REFERER"));
		if (request.getHeader("REFERER") == "http://localhost:8080/resister-user"
				|| request.getHeader("REFERER") == "http://localhost:8080/show-resister"
				|| request.getHeader("REFERER") == "http://localhost:8080/tologin"
				|| request.getHeader("REFERER") == "http://localhost:8080/tologin?error=true") {
			session.setAttribute("referer", "http://localhost:8080/");
		}
		session.setAttribute("referer", request.getHeader("REFERER"));

		if (error != null) {
			model.addAttribute("errorMessage", "メールアドレスまたはパスワードが不正です");
		}
		return "login";
	}

	/**
	 * @param form ログイン画面から送られるユーザ情報.
	 * @return ログイン画面もしくは検索リストに画面遷移
	 */
//	SpringSecurityが実装するためコメントアウトしました
//	@RequestMapping("/login")
//	public String Login(UserForm form,String num, Model model) {
//		
//		User user = loginLogoutUserService.login(form.getEmail(), form.getPassword());
//		if (user == null) {
//			model.addAttribute("error", "メールアドレス、またはパスワードが間違っています");
//			return toLogin();
//		} else if (session.getAttribute("user") == null) { //userのidをパラメータで使用するためUserオブジェクトをセットしました
//			session.setAttribute("user", user); 
//		}
//		
//		return "redirect:/showItemList";
//	}

//	SpringSecurityが実装するためコメントアウトしました
//	@RequestMapping("/logout")
//	public String logout() {
//		session.invalidate();
//		return "login"; //login画面に遷移するように修正しました
//	}

}
