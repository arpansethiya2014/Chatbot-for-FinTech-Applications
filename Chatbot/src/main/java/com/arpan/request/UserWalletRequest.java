package com.arpan.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWalletRequest {

	private long userId;
	private double amount;
	private String transactionType; // debit or credit
	private String description;

}
