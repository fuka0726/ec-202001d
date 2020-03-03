package com.example.ecommerce_d.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_d.domain.User;
import com.example.ecommerce_d.form.UserResisterForm;
import com.fasterxml.jackson.databind.BeanProperty;

@Controller
@RequestMapping("")
public class ResisterUserController {
	
	
	@RequestMapping("show-resister")
	public String showResister() {
		return "resister_user";
	}
	
	@RequestMapping("resister-user")
	public String resisterUser(UserResisterForm form) {
		User user = new User();
		BeanUtils.copyProperties(form, user);
//		パスワードと確認用パスワードが一致していなければエラーメッセージを出す記述をする
		if(user.getPassword().equals(form.getConfirmPassword())) {
			return null;
		}
		
		
	}
}
