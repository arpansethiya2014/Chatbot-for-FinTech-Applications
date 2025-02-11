package com.arpan.entity;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWalletTransactions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long merchantWalletTrxnId;

	@ManyToOne
	private UserDetails user;
	private Double mercWalletPreviousBalance;
	private String walletTrxnType;
	private Double mercWalletNewBalance;
	private String description;
	private String walletTxnRefNo;
	private Timestamp walletTxnDate;
	private String trxnRefId;

}