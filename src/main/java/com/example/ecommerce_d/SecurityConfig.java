package com.example.ecommerce_d;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 設定用クラスであることを明示する
@EnableWebSecurity // SpringSecurityのWeb用の機能を利用すことを明示する
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * アプリケーション全体のセキュリティーフィルターを設定する.
	 *
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		// staticディレクトリ配下のファイルをセキュリティフィルターから無効化する
		web.ignoring().antMatchers("/css/**/", "/img/**", "/js/**", "/fonts/**");
	}

	/**
	 * アプリケーションの認証設定やログイン・ログアウトの設定をする.
	 *
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests() // 認証に関する設定
				.antMatchers("/tologin","/", "showItemDetail", "/showItemList", "/show-resister", "/resister-user", "/show-cart",
						"/add-item", "/remove-order-item", "/login").permitAll() // ログインしなくても使用できるパスを指定
				.anyRequest().authenticated(); //上記で指定したパス以外は認証が必要

		http.formLogin() // ログインに関する設定
				.loginPage("/tologin") //ログインする時のパス
				.loginProcessingUrl("/login") //ログイン時のパス
				.failureUrl("/tologin?error=true") //ログイン失敗時のパス
				.defaultSuccessUrl("/showItemList", false) // 操作途中のログイン時は、ログイン後に続きのページに遷移
				.usernameParameter("email")
				.passwordParameter("password");

		http.logout() // ログアウトに関する設定
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout**")) //ログアウトする時のパス
				.logoutSuccessUrl("/") // 商品一覧に遷移
				.deleteCookies("JSESSIONID") // CookieのセッションIDを削除
				.invalidateHttpSession(true); // セッションを無効
	}

	/**
	 * パスワードハッシュ化のためのクラスをDIコンテナで使用できるようにする.
	 * 
	 * @return パスパードハッシュ化のためのオブジェクト
	 */
	@Bean //これをつけると下記のクラスがDIコンテナで注入できる
	public PasswordEncoder passwordEncorder() {
		return new BCryptPasswordEncoder();
	}
}
