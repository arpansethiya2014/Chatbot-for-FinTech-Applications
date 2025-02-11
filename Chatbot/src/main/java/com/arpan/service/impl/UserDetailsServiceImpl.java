package com.arpan.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arpan.entity.UserDetails;
import com.arpan.entity.UserWalletTransactions;
import com.arpan.repo.UserDetailsRepository;
import com.arpan.repo.UserWalletTransactionsRepository;
import com.arpan.request.UserDetailsRequest;
import com.arpan.service.UserDetailsService;
import com.arpan.utils.DateAndTime;
import com.arpan.wallet.WalletService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDetailsRepository userDetailsRepository;

	@Autowired
	private WalletService walletService;

	@Autowired
	private UserWalletTransactionsRepository walletTransactionRepository;

	@Override
	public String saveUsers(UserDetailsRequest detailsRequest) throws Exception {

		if (userDetailsRepository.existsByMobile(detailsRequest.getMobile())) {
			return "User mobile alredy register";
		}

		if (userDetailsRepository.existsByEmail(detailsRequest.getEmail())) {
			return "User email alredy register";
		}

		UserDetails userDetails = new UserDetails();
		userDetails.setBalance(detailsRequest.getBalance());
		userDetails.setEmail(detailsRequest.getEmail());
		userDetails.setMobile(detailsRequest.getMobile());
		userDetails.setName(detailsRequest.getName());
		userDetails.setDate(Timestamp.valueOf(DateAndTime.getCurrentTimeInIST()));
		userDetails.setPassword("PASS"+detailsRequest.getMobile());
		userDetails.setLoginKey("NA");
		userDetails = userDetailsRepository.save(userDetails);

		String trxnId = "TRXN" + DateAndTime.getAlphaNumericString(10);
		String walletTrxnId = "WAL" + DateAndTime.getAlphaNumericString(10);

		UserWalletTransactions transaction = new UserWalletTransactions();
		transaction.setUser(userDetails);
		transaction.setMercWalletPreviousBalance(0.0);
		transaction.setWalletTrxnType("Credit");
		transaction.setMercWalletNewBalance(detailsRequest.getBalance());
		transaction.setDescription("User Add");
		transaction.setWalletTxnRefNo(walletTrxnId);
		transaction.setWalletTxnDate(Timestamp.valueOf(DateAndTime.getCurrentTimeInIST()));
		transaction.setTrxnRefId(trxnId);

		walletTransactionRepository.save(transaction);

		return "User Data Save";
	}

	@Override
	public List<UserDetails> findAll() {
		return userDetailsRepository.findAll();
	}

	@Override
	public String findWalletBalnace(String mobile) {

		if (!userDetailsRepository.existsByMobile(mobile)) {
			return "User mobile not register";
		}

		Optional<UserDetails> optional = userDetailsRepository.findByMobile(mobile);

		return "Avilable Wallet Balance : " + optional.get().getBalance();
	}

}
