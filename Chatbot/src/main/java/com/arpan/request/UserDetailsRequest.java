package com.arpan.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsRequest {

	private String name;
	private double balance;
	private String email;
	private String mobile;

}
