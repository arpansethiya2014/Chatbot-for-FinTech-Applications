package com.arpan.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.arpan.entity.UserWalletTransactions;

@Repository
public interface UserWalletTransactionsRepository extends JpaRepository<UserWalletTransactions, Long> {

	@Query(value = "SELECT * FROM user_wallet_transactions where user_user_id=?1 ORDER BY merchant_wallet_trxn_id desc", nativeQuery = true)
	List<UserWalletTransactions> findByUserId(long userId);

}
