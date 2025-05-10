package com.arpan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.arpan.entity.UserDetails;
import com.arpan.entity.UserWalletTransactions;
import com.arpan.repo.UserDetailsRepository;
import com.arpan.repo.UserWalletTransactionsRepository;
import com.arpan.service.UserDetailsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserDetailsRepository userDetailsRepository;

	@Autowired
	private UserWalletTransactionsRepository transactionsRepository;

	@PostMapping("/message")
	public Map<String, String> chat(@RequestBody Map<String, String> request) {
		String userMessage = request.get("message");
		System.out.println("Received user message: " + userMessage);

		Map<String, String> response = new HashMap<>();

		if (userMessage == null || userMessage.isBlank()) {
			response.put("response", "Please enter a valid message.");
			return response;
		}

		// Handle "My Details" requests
		if (userMessage.startsWith("My Details")) {
			if (userMessage.length() < 30) {
				response.put("response", "Invalid input. Please provide a valid mobile number.");
				return response;
			}
			String mobile = userMessage.substring(29).trim();
			System.out.println("Fetching details for mobile: " + mobile);

			Optional<UserDetails> optional = userDetailsRepository.findByMobile(mobile);
			if (optional.isEmpty()) {
				response.put("response", "User mobile not registered.");
				return response;
			}
			String value = "Name: " + optional.get().getName() + ", Email: " + optional.get().getEmail();
			response.put("response", value);
			return response;
		}

		// Handle "Last Five Transactions" requests
		if (userMessage.startsWith("Last five transactions for")) {
			if (userMessage.length() < 30) {
				response.put("response", "Invalid input. Please provide a valid mobile number.");
				return response;
			}
			String mobile = userMessage.substring(41).trim();
			System.out.println("Fetching details for mobile: " + mobile);

			Optional<UserDetails> optional = userDetailsRepository.findByMobile(mobile);
			if (optional.isEmpty()) {
				response.put("response", "User mobile not registered.");
				return response;
			}

			List<UserWalletTransactions> list = transactionsRepository.findByUserId(optional.get().getUserId());

			if (list.isEmpty()) {
				response.put("response", "Data not found");
				return response;
			}

			List<UserWalletTransactions> lastFive = list.stream().limit(5).collect(Collectors.toList());
			StringBuilder responseStr = new StringBuilder();
			for (UserWalletTransactions userWalletTransactions : lastFive) {
				responseStr.append(userWalletTransactions.getWalletTxnDate()).append(" - ")
						.append(userWalletTransactions.getWalletTrxnType()).append(" - ")
						.append(userWalletTransactions.getWalletTxnRefNo()).append(" - ")
						.append(userWalletTransactions.getMercWalletNewBalance()).append("\n");
			}
			String result = responseStr.toString().trim();
			response.put("response", result);
			return response;
		}

		// Handle "My Details" requests
		if (userMessage.startsWith("Transaction Status")) {
			if (userMessage.length() < 30) {
				response.put("response", "Invalid input. Please provide a valid Transactionn id.");
				return response;
			}
			String id = userMessage.substring(37).trim();
			System.out.println("Fetching details for id: " + id);

			if (id.equals("TRXN123456")) {
				response.put("response", "Transaction Success: " + id);
				return response;
			}
			response.put("response", "Transaction Failed: " + id);
			return response;
		}

		// Handle "Others" requests
		if (userMessage.startsWith("Other for")) {
			response.put("response", "Kindly contact abc@abc.com or 7860000000.");
			return response;
		}

		// Handle "Wallet Balance" requests
		if (userMessage.startsWith("Wallet Balance for")) {
			if (userMessage.length() < 34) {
				response.put("response", "Invalid input. Please provide a valid mobile number.");
				return response;
			}
			String mobile = userMessage.substring(33).trim();
			System.out.println("Fetching wallet balance for mobile: " + mobile);
			response.put("response", userDetailsService.findWalletBalnace(mobile));
			return response;
		}

		// Handle generic chat responses
		String botResponse = getBotResponse(userMessage);
		response.put("response", botResponse);
		return response;
	}

	private String getBotResponse(String message) {
		if (message == null || message.isBlank()) {
			return "I didn't understand that. Can you please rephrase?";
		}

		switch (message.toLowerCase().trim()) {
		case "hello":
			return "Hello! How can I assist you today?";
		case "how are you?":
			return "I'm just a bot, but I'm doing great! How about you?";
		case "what services do you provide?":
			return "I can help you check your wallet balance, transaction status, and recent transactions.";
		case "how can i check my wallet balance?":
			return "Click on 'Wallet Balance' or enter your mobile number to proceed";
		case "how do i check my last five transactions?":
			return "Click on 'Last Five Transactions' or enter your mobile number to proceed.";
		case "bye":
			return "Goodbye! Have a great day!";
		default:
			return "I'm not sure how to respond to that. Can you ask something else?";
		}
	}
}
