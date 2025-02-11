package com.arpan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arpan.request.UserWalletRequest;
import com.arpan.service.UserWalletTransactionsService;

@RestController
@RequestMapping("/wallet")
public class UserWalletController {

	@Autowired
	private UserWalletTransactionsService userWalletTransactionsService;

	@PostMapping("/transaction")
	public String walletTransaction(@RequestBody UserWalletRequest userWalletRequest) {
		return userWalletTransactionsService.storeTransactions(userWalletRequest);
	}

	@GetMapping("/user-transactions/{userId}")
	public Object findUserId(@PathVariable long userId) {
		return userWalletTransactionsService.findUserId(userId);
	}

}
