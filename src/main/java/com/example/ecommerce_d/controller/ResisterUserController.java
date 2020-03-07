package com.example.ecommerce_d.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_d.domain.User;
import com.example.ecommerce_d.form.UserResisterForm;
import com.example.ecommerce_d.service.ResisterUserService;

/**
 * ユーザー登録の情報制御をするコントローラークラスです.
 * 
 * @author sanihiro
 *
 */
@Controller
@RequestMapping("")
public class ResisterUserController {
	@ModelAttribute
	public UserResisterForm setUpForm() {
		return new UserResisterForm();
	}

	@Autowired
	private ResisterUserService service;

	/**
	 * ユーザー登録画面へ遷移します.
	 * 
	 * @return ユーザー登録画面
	 */
	@RequestMapping("/show-resister")
	public String showResister() {
		return "register_user";
	}

	/**
	 * 入力した値をフォームで受け取り、そこからユーザードメインを作成してDBに新規作成する.
	 * 
	 * @param form ユーザー登録のフォーム
	 * @return パスワードと確認用パスワードが一致していれば、ログイン画面。 そうでなければ、登録画面。
	 */
	@RequestMapping("/resister-user")
	public String resisterUser(@Validated UserResisterForm form, BindingResult result) {
		System.out.println(form.getName());
		// パスワード確認
		if (!(form.getPassword().equals(form.getConfirmPassword()))) {
			result.rejectValue("password", "", "パスワードが一致していません");
			result.rejectValue("confirmPassword", "", "");
		}

		// メールアドレスが重複している場合の処理
		if (service.findByMail(form.getEmail()) != null) {
			result.rejectValue("email", "", "そのメールアドレスは既に登録されています");
		}

		// エラーがある場合は登録画面に遷移
		if (result.hasErrors()) {
			return showResister();
		}

//		Userオブジェクトを新たに作るかどうかが怪しい。
//		copyPropetiesで上書きされるかどうかで書き足すかどうかが変わる。
		User user = new User();
		user.setName(form.getName());
		user.setEmail(form.getEmail());
		user.setZipcode(form.getZipcode());
		user.setAddress(form.getAddress());
		user.setTelephone(form.getTelephone());
		user.setPassword(form.getPassword());
		service.insert(user);
		return "login";

	}
}
