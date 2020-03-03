package com.example.ecommerce_d.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_d.domain.User;

/**
 * 
 * usersテーブルを操作するリポジトリ.
 * 
 * @author namikitsubasa
 *
 */
@Repository
public class UserRepository {

	private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setEmail("email");
		user.setPassword("password");
		user.setZipcode("zipcode");
		user.setAddress("address");
		user.setTelephone("telephone");
		return user;

	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 
	 * メールアドレスとパスワードからユーザー情報を取得.
	 * 
	 * @param mail メールアドレス
	 * @param      password パスワード
	 * @return　　　ユーザー情報(検索がヒットしなければnull)
	 */
	public List<User> findByMailAndPassword(String mail, String password) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("mail", mail).addValue("password", password);
		String sql = "select id, name , email , password , zipcode, address, telephone from users where id= :id and password= :password";
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList;
	}
	
	/**
	 * 引数でもらったユーザー情報をusersテーブルに挿入します.
	 * 
	 * @param user ユーザー情報
	 */
	public void insert(User user) {
		String sql = "INSERT INTO users (name,email,password,zipcode,address,telephone) VALUES(:name, :email, :password, :zipcode, :address, :telephone) ;";
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		template.update(sql, param);
		
	}

}
