package org.spacebank.co.controller;

import org.spacebank.co.payload.request.AccountHttpRequest;
import org.spacebank.co.payload.response.AccountHttpResponse;
import org.spacebank.co.payload.response.AccountUpdateHttpResponse;
import org.spacebank.co.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/api/accounts")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public AccountHttpResponse addAccount(AccountHttpRequest request) {
    	return null;
    }
    
    @RequestMapping(value = "/account/{id}",method = RequestMethod.GET)
    public AccountUpdateHttpResponse update(@PathVariable long id, AccountHttpRequest request) {
    	return null;
    }
}
