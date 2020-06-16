package org.spacebank.co.services;

import org.spacebank.co.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
	@Autowired
	private AccountsRepository accountRespository;
	
	
	
}
