package com.ever.POS.best.controller;

import com.ever.POS.best.model.Transaction;

import javafx.collections.ObservableList;

public class Transact {

	private static ObservableList<Transaction> transactionList;

	public static ObservableList<Transaction> getAllTransactions() {
		return transactionList;
	}
}

//1, 09/17/2016 08:36PM, nameOfClient, 100.0, 100.0, 1001, 1, PLYWOOD RICHMONDE, PC, 1/8" X 4' X 8', 240.0, 390.0, 27, 1002, PLYWOOD RICHMONDE, PC, "1/4"" X 4' X 8'", 330.0, 480.0, 25,1003, PLYWOOD RICHMONDE, PC, "1/4"" X  4' X 8'", 380.0, 530.0, 30
//2, 09/17/2016 11:07PM, Cash, 1179.0, 1179.0, 3, 2001, BOYSEN, GALON, ACRYLIC EMULSION, 535.0, 565.0, 46, 2033, BOYSEN QUICK-DRY ENAMEL, GALON, INTERNATIONAL RED, 512.0, 542.0, 4, 2044, BOYSEN QUICK-DRY ENAMEL, 1/4 LTR, EMERALD GREEN, 42.0, 72.0, 12
