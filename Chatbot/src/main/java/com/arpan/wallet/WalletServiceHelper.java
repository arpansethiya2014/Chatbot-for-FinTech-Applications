package com.arpan.wallet;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arpan.entity.UserDetails;
import com.arpan.entity.UserWalletTransactions;
import com.arpan.repo.UserDetailsRepository;
import com.arpan.repo.UserWalletTransactionsRepository;
import com.arpan.utils.DateAndTime;

@Service
public class WalletServiceHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(WalletServiceHelper.class);
	
	@Autowired
	private UserDetailsRepository userDetailsRepository;

	@Autowired
	private UserWalletTransactionsRepository walletTransactionRepository;
	
	@Transactional
	public void processSingleTransaction(WalletRequest request)
			throws ParseException, IOException, InterruptedException {

		LOGGER.info("--------------Start processSingleTransaction----------------");

		LOGGER.info("Merchant Id : " + request.getUserId());
		UserDetails user = userDetailsRepository.findById(request.getUserId())
				.orElseThrow(() -> new RuntimeException("User not found"));

		double previousBalance = user.getBalance();
		double newBalance;

		if ("Debit".equalsIgnoreCase(request.getTransactionType())) {
			if (previousBalance < request.getAmount()) {
				throw new RuntimeException("Insufficient balance");
			}
			newBalance = previousBalance - request.getAmount();
		} else if ("Credit".equalsIgnoreCase(request.getTransactionType())) {
			newBalance = previousBalance + request.getAmount();
		} else {
			throw new RuntimeException("Invalid transaction type");
		}

		UserWalletTransactions transaction = new UserWalletTransactions();
		transaction.setUser(user);
		transaction.setMercWalletPreviousBalance(previousBalance);
		transaction.setWalletTrxnType(request.getTransactionType());
		transaction.setMercWalletNewBalance(newBalance);
		transaction.setDescription(request.getDescription());
		transaction.setWalletTxnRefNo(request.getWalletTxnRefNo());
		transaction.setWalletTxnDate(Timestamp.valueOf(DateAndTime.getCurrentTimeInIST()));
		transaction.setTrxnRefId(request.getTransactionRefId());

		walletTransactionRepository.save(transaction);

		user.setBalance(newBalance);
		userDetailsRepository.save(user);

		LOGGER.info("--------------End processSingleTransaction----------------");
	}
	
}
