package com.arpan.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "balance", nullable = false)
	private double balance;

	@Column(name = "date", nullable = false, unique = true)
	private Timestamp date;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "mobile", nullable = false)
	private String mobile;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "loginKey", nullable = false)
	private String loginKey;
}
