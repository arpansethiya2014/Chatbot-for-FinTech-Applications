package com.arpan.wallet;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class WalletService {

	private final BlockingQueue<WalletRequest> requestQueue = new LinkedBlockingQueue<>();

	private static final Logger LOGGER = LoggerFactory.getLogger(WalletService.class);

	@Autowired
	private WalletServiceHelper walletServiceHelper;


	// Start a background thread to process the queue
	@PostConstruct
	public void init() {
		LOGGER.info("--------------init----------------");
		new Thread(this::processQueue).start();
	}

	public void enqueueTransaction(WalletRequest request) {

		LOGGER.info("--------------enqueueTransaction----------------");
		requestQueue.add(request);
	}

	
	private void processQueue() {
		while (true) {
			try {

				LOGGER.info("--------------processQueue----------------");
				WalletRequest request = requestQueue.take(); // Wait for a request
				walletServiceHelper.processSingleTransaction(request); // Process the request
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


}
