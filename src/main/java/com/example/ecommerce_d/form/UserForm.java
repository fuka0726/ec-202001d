package com.example.ecommerce_d.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 
 * ログイン情報をリクエストパラメータで受け取るformクラス.
 * 
 * @author namikitsubasa
 *
 */
public class UserForm {

	/** メールアドレス */
	@Email(message="メールアドレス形式で入力してください")
	@NotBlank(message="メールアドレスを入力してください")
	String mail;
	/** パスワード */
	@NotBlank(message="パスワードを入力してください")
	String password;

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserForm [mail=" + mail + ", password=" + password + "]";
	}

}
