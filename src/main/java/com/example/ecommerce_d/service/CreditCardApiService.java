package com.example.ecommerce_d.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.ecommerce_d.domain.CreditCardApi;
import com.example.ecommerce_d.domain.Order;

@Service
public class CreditCardApiService {
	
    @Autowired
    @Qualifier("CreditCardRestTemplate")
    RestTemplate restTemplate;

    private static final String URL = "http://153.126.174.131:8080/sample-credit-card-web-api/credit-card/payment";
    
    public CreditCardApi service(Order order) {
        return restTemplate.getForObject(URL, CreditCardApi.class,order);
    }
    
}
