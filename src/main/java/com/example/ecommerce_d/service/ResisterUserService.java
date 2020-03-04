package com.example.ecommerce_d.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_d.domain.User;
import com.example.ecommerce_d.repository.UserRepository;

/**
 * ユーザー登録の業務処理を行うサービスクラスです.
 * 
 * @author sanihiro
 *
 */
@Service
@Transactional
public class ResisterUserService {

	@Autowired
	private UserRepository repository;
	
	/**
	 * repositoryのinsertメソッド呼び出します.
	 * 
	 * @param user ユーザー情報
	 */
	public void insert(User user) {
		repository.insert(user);
	}
	
	/**
	 *emailでuser情報を検索します.
	 * 
	 * @param email Eメール
	 * @return　User情報、もしくは検索された結果がなければnull
	 */
	public User findByMail(String email) {
		return repository.findByMail(email);
	}
}
