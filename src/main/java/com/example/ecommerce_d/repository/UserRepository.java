package com.example.ecommerce_d.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
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
	 * @param mail     メールアドレス
	 * @param password パスワード
	 * @return ユーザー情報(検索がヒットしなければnull)
	 */
	public User findByMailAndPassword(String email, String password) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email).addValue("password", password);
		String sql = "select id, name , email , password , zipcode, address, telephone from users where email= :email and password= :password";

		try {
			User user = template.queryForObject(sql, param, USER_ROW_MAPPER);
			return user;
		} catch (Exception e) {
			return null;
		}
	}

}
