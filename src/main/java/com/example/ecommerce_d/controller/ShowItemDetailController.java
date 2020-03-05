package com.example.ecommerce_d.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_d.domain.Item;
import com.example.ecommerce_d.service.ShowItemDetailService;

@Controller
@RequestMapping("")
public class ShowItemDetailController {
	
	@Autowired
	private ShowItemDetailService showItemDetailService;
	
	@RequestMapping("/showItemDetail")
	public String showItemDetail(String id, Model model) {
		Item item = showItemDetailService.showItemDetail(Integer.parseInt(id));
		model.addAttribute("item",item);
		return "item_detail";
	}
	
	
	
}
