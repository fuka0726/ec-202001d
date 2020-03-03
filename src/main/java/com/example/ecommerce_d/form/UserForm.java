package com.example.ecommerce_d.form;

/**
 * 
 * ログイン情報をリクエストパラメータで受け取るformクラス.
 * 
 * @author namikitsubasa
 *
 */
public class UserForm {

	/** メールアドレス */
	String mail;
	/** パスワード */
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
