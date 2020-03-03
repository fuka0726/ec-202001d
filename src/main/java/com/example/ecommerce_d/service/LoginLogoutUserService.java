package com.example.ecommerce_d.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_d.domain.User;
import com.example.ecommerce_d.repository.UserRepository;

/**
 * 
 * ログイン処理を行うサービスクラス.
 * 
 * @author namikitsubasa
 *
 */
@Service
public class LoginLogoutUserService {
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * @param mail メールアドレス
	 * @param password　パスワード
	 * @return　ユーザー情報
	 */
	public List<User> login(String mail , String password) {
		List<User> userList=userRepository.findByMailAndPassword(mail, password);
		return userList;
		
	}

}
