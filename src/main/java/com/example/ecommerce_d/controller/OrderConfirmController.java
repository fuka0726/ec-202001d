package com.example.ecommerce_d.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_d.service.OrderConfirmService;

/**
 * 
 * 
 * @author namikitsubasa
 *
 */
@Controller
@RequestMapping("/")
public class OrderConfirmController {
	
	@Autowired
	private OrderConfirmService orderConfirmService;
	
	

}
