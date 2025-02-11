package com.arpan.wallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletRequest {
	
	private long userId;
	private double amount;
	private String transactionType; // debit or credit
	private String description;
	private String transactionRefId;
	private String walletTxnRefNo;

}
