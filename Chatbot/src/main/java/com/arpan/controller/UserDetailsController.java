package com.arpan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

import com.arpan.entity.UserDetails;
import com.arpan.request.UserDetailsRequest;
import com.arpan.service.UserDetailsService;

@RestController
@RequestMapping("/user")
public class UserDetailsController {

	@Autowired
	private UserDetailsService userDetailsService;

	@PostMapping("/save")
	public String save(@RequestBody UserDetailsRequest detailsRequest) throws Exception {
		return userDetailsService.saveUsers(detailsRequest);
	}

	@GetMapping("/find-all")
	public List<UserDetails> findAll() throws Exception {
		return userDetailsService.findAll();
	}

	@GetMapping("/findWalletBalance/{mobile}")
	public String findWalletBalnace(@PathVariable String mobile) throws Exception {
		return userDetailsService.findWalletBalnace(mobile);
	}

}
