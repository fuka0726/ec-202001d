package com.example.ecommerce_d.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.ecommerce_d.domain.LoginUser;
import com.example.ecommerce_d.domain.Order;
import com.example.ecommerce_d.domain.OrderItem;
import com.example.ecommerce_d.domain.OrderTopping;
import com.example.ecommerce_d.domain.User;
import com.example.ecommerce_d.form.OrderForm;
import com.example.ecommerce_d.service.OrderConfirmService;
import com.example.ecommerce_d.service.ShoppingCartService;
import com.example.ecommerce_d.service.UserDetailsServiceImpl;

/**
 * 注文前の商品を表示するコントローラ.
 * 
 * @author namikitsubasa
 *
 */
@Controller
@RequestMapping("")
public class OrderConfirmController {

	@Autowired
	private OrderConfirmService orderConfirmService;

	@Autowired
    private MailSender sender;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImplrepository;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@ModelAttribute
	public OrderForm setUpOrderForm() {
		return new OrderForm();
	}
	

	/**
	 * 注文前の商品を取得し、注文確認ページに画面遷移
	 * 
	 * @param userId ユーザーid
	 * @param model  リクエストスコープ
	 * @return 注文確認ページに画面遷移
	 */
	@RequestMapping("/orderConfirm")
	// ログインしたユーザー情報を受け渡します
	public String orderConfirm(Integer userId, Model model, @AuthenticationPrincipal LoginUser loginUser) {
		List<Order> orderList = orderConfirmService.showOrderedList(loginUser.getUser().getId());
		User user=userDetailsServiceImplrepository.findByUserId(loginUser.getUser().getId());
		model.addAttribute("orderList", orderList);
		model.addAttribute("user", user);
		return "order_confirm";
	}

	/**
	 * 
	 * お届け先情報を取得し、注文を確定する.
	 * 
	 * @param form      お届け先情報
	 * @param resultset エラーメッセージを格納するオブジェクト
	 * @param userId    ユーザーid
	 * @param model     リクエストスコープ
	 * @param loginUser 利用者ユーザー
	 * @return 注文完了画面（リダイレクト）
	 */
	@RequestMapping(value="/completeOrder", method=RequestMethod.POST)
	public String completeOrder(@Validated OrderForm form, BindingResult resultset, Integer userId, Model model,
			@AuthenticationPrincipal LoginUser loginUser) {
		
		Date date = Date.valueOf(form.getDeliveryDate());
		LocalDate localdate = date.toLocalDate();
		LocalTime localTime = LocalTime.of(Integer.parseInt(form.getDeliveryTime()), 00);
		LocalDateTime localDateTime = LocalDateTime.of(localdate, localTime);
		
		LocalDateTime timeLimit = LocalDateTime.now();
		timeLimit = timeLimit.plusHours(12);
		
		if(timeLimit.isAfter(localDateTime)) {
			FieldError deliveryDateError = new FieldError(resultset.getObjectName(), "deliveryTime", "配達時間は注文日時より12時間後以降のお時間帯に設定してください。");
			resultset.addError(deliveryDateError);
		}

		if ("".equals(form.getDeliveryDate())) {
			FieldError deliveryDateError = new FieldError(resultset.getObjectName(), "deliveryDate", "配達日を入力してください");
			resultset.addError(deliveryDateError);
		}
		// エラーがある場合は注文確認画面に遷移
		if (resultset.hasErrors()) {
			return orderConfirm(userId, model, loginUser);
		}
		// 配達日をSQL用のTimestamp型に変更
		// Orderインスタンスを作成(テーブルからとってきた方が良いのかしら？）
		//データベースの総額をアップデートするために、カートリストの商品一覧を呼ぶsql実行
		Order ordered=shoppingCartService.showCartList(loginUser.getUser().getId());
		Order order = new Order();
		BeanUtils.copyProperties(form, order);
		//注文日セット
		Timestamp timestamp = Timestamp.valueOf(localDateTime);
		order.setDeliveryTime(timestamp);
		//ユーザーidセット
		order.setUserId(loginUser.getUser().getId());
		//総額セット
		order.setTotalPrice(ordered.getCalcTotalPrice()+ordered.getTax());
		// 支払方法によって入金情報を変更
		if (order.getPaymentMethod() == 1) {
			order.setStatus(1);
		} else {
			order.setStatus(2);
		}
		//注文確認メールに注文詳細を記載するためにsql発行
		List<Order> orderList = orderConfirmService.showOrderedList(loginUser.getUser().getId());
		orderConfirmService.updateStatus(order); 
		
		
		//メール送信するメソッドを呼ぶ.
		sendEmail(order,orderList);
		
		return "redirect:/tocomplete";
	}

	@RequestMapping("/tocomplete")
	public String tocomplete() {
		return "order_finished";
	}
	
	
//以下メール送付のコード
	
	/**
	 * 
	 * 注文内容をメールで送る
	 * 
	 * @param order　お届け情報
	 * @param orderList　注文リスト
	 */
	public void sendEmail(Order order,List<Order> orderList) {
		//改行のためのメソッド
		String br = System.getProperty("line.separator");
		String itemName=null;
		Character itemSize=null;
		Integer itemCount=null;
		
		//注文内容を変数に代入
		List<OrderTopping> toppingList=null;
		for(Order orderItem:orderList) {
			for(OrderItem item:orderItem.getOrderItemList()) {
		     itemName=item.getItem().getName();
		     itemSize=item.getSize();
		     itemCount=item.getQuantity();
		     toppingList=item.getOrderToppingList();
			}
		}

		//トッピングを文字連結する
		String toppingName="";
	    for(OrderTopping topping:toppingList) {
		toppingName=toppingName+topping.getTopping().getName()+",";
	    }
	    
	    String paymentMethod=null;
	    if(order.getPaymentMethod()==1) {
	    	paymentMethod="代引決済";
	    }else {
	    	paymentMethod="クレジットカード";
	    }
	    
	    //メールの本文
		String message=order.getDestinationName()+"様"+br+br+"この度は発注いただきありがとうございました。"
				+ br+"ご注文内容を以下のとおりお知らせいたします。"+br+
				"内容にお間違いがないかご確認の程よろしくお願いいたします。"+ br+br+
				     "【注文日】："+order.getOrderDate()+br+
				     "【商品詳細】："+br+
				     "・商品名："+itemName+br+
				     "・サイズ："+itemSize+br+
				     "・個数："+itemCount+br+
				     "・トッピング："+toppingName+br+br+

				     "【商品代金】："+order.getTotalPrice()+"円"+br+
				     "【お届け先】："+order.getDestinationAddress()+br+
					 "【配達日時】："+order.getDeliveryTime()+br+
					 "【決済方法】："+paymentMethod+br+br+
					 "株式会社ラクラクトイザラス";
		
		//メールを送る準備
		SimpleMailMessage msg = new SimpleMailMessage();
	
		//送り主
		msg.setFrom("namiki.ba13@gmail.com");
		//宛先
		msg.setTo("namiki.ba13@gmail.com");//order.getDestinationEmail() を本来入れる
		msg.setSubject("ご注文完了のお知らせ");
		//本文
		msg.setText(message);
		
		this.sender.send(msg);
		
	}

}
