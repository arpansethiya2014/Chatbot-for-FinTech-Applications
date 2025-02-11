package com.arpan.service;

import com.arpan.request.UserWalletRequest;

public interface UserWalletTransactionsService {

	String storeTransactions(UserWalletRequest userWalletRequest);

	Object findUserId(long userId);

}
