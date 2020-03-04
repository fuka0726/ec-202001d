package com.example.ecommerce_d.service;

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
	public User login(String email , String password) {
		User user=userRepository.findByMailAndPassword(email, password);
		return user;
		
	}

}
