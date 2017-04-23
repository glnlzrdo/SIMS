package com.ever.POS.best.model;

import javafx.collections.ObservableList;

public class Purchase extends Transaction {

	public Purchase() {
	}

	public Purchase(int transactionNumber, String transactionDate, String nameOfClient, double cashGiven,
			double totalAmount, int totalItems, ObservableList<Product> products) {
		super(transactionNumber, transactionDate, nameOfClient, cashGiven, totalAmount, totalItems, products);
	}
}
