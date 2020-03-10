package com.example.ecommerce_d.form;

/**
 * 入力したクレジットカード情報を受け取るform.
 * 
 * @author namikitsubasa
 *
 */
public class CreditCardForm {
	
	/**カード番号*/
	private String card_number;
	/**有効期限(年)*/
	private String card_exp_year;
	/**有効期限(月)*/
	private String card_exp_month;
	/**セキュリティーコード*/
	private String card_cvv;
	/**名義人*/
	private String card_name;
	/**ユーザーid*/
	private Integer user_id;
	/**注文id*/
	private Integer order_number;
	/**総額*/
	private Integer amount;

	public String getCard_exp_year() {
		return card_exp_year;
	}

	public void setCard_exp_year(String card_exp_year) {
		this.card_exp_year = card_exp_year;
	}

	public String getCard_exp_month() {
		return card_exp_month;
	}

	public void setCard_exp_month(String card_exp_month) {
		this.card_exp_month = card_exp_month;
	}

	public String getCard_cvv() {
		return card_cvv;
	}

	public void setCard_cvv(String card_cvv) {
		this.card_cvv = card_cvv;
	}

	public String getCard_number() {
		return card_number;
	}

	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}

	public String getCard_name() {
		return card_name;
	}

	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getOrder_number() {
		return order_number;
	}

	public void setOrder_number(Integer order_number) {
		this.order_number = order_number;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}
