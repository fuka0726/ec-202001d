package com.example.ecommerce_d.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.ecommerce_d.domain.CreditCardApi;
import com.example.ecommerce_d.form.CreditCardForm;

/**
 * 
 * クレジット決済のwebapiを呼び出すサービスクラス.
 * 
 * @author namikitsubasa
 *
 */
@Service
public class CreditCardApiService {
	
    @Autowired
    RestTemplate restTemplate;

    private static final String URL = "http://192.168.17.19:8080/sample-credit-card-web-api/credit-card/payment";
    
    public CreditCardApi service(CreditCardForm creditCardForm) {
        return restTemplate.postForObject(URL, creditCardForm, CreditCardApi.class);
    }
    
}
