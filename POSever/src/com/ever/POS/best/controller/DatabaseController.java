package com.ever.POS.best.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ever.POS.best.model.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseController {

	public static List<Purchase> openPurchaseDatabase() throws FileNotFoundException {
		String fileName = "PurchaseReport.csv";
		List<Purchase> transactionList = new ArrayList<Purchase>();
		ObservableList<Product> productList = FXCollections.observableArrayList();
		String line = null;
		FileReader content = new FileReader(fileName);
		Scanner entryReader = new Scanner(content);
		int column;
		while (entryReader.hasNext()) {
			line = entryReader.nextLine();
			String token[] = line.split(",");
			column = 6;

			for (int i = 0; i < (token.length - 6) / 9; i++) {
				productList.add(new Product(Integer.parseInt(token[column].trim()), token[column + 1].trim(),
						token[column + 2].trim(), token[column + 3].trim(),
						Double.parseDouble(token[column + 4].trim()), Double.parseDouble(token[column + 5].trim()),
						Integer.parseInt(token[column + 6].trim())));
						productList.get(i).setSubQuantity(Integer.parseInt(token[column + 7].trim()));
						productList.get(i).setSubTotal(Double.parseDouble(token[column + 8].trim()));
				column +=9;
			}

			transactionList.add(new Purchase(Integer.parseInt(token[0].trim()), token[1].trim(), token[2].trim(),
					Double.parseDouble(token[3].trim()), Double.parseDouble(token[4].trim()),
					Integer.parseInt(token[5].trim()), productList));
			productList = FXCollections.observableArrayList();
		}
		entryReader.close();
		return transactionList;
	}

	public static void savePurchaseTransactions(ObservableList<Purchase> transactionList) throws FileNotFoundException {
		String fileName = "PurchaseReport.csv";
		FileWriter file = null;
		try {
			file = new FileWriter(fileName);
		} catch (IOException e) {
		}
		PrintWriter fileSaver = new PrintWriter(file);
		for (Purchase transaction : transactionList) {
			fileSaver.print(transaction.getTransactionNumber() + ", " + transaction.getTransactionDate() + ", "
					+ transaction.getNameOfClient() + ", " + transaction.getCashGiven() + ", "
					+ transaction.getTotalAmount() + ", " + transaction.getTotalItems());
			for (Product product : transaction.getProductList()) {
				fileSaver.print(", " + product.getProductId() + ", " + product.getProductName() + ", "
						+ product.getProductUnit() + ", " + product.getProductDescription() + ", "
						+ product.getPriceForPurchase() + ", " + product.getPriceForSales() + ", "
						+ product.getStockQuantity() + ", " + product.getSubQuantity() + ", " + product.getSubTotal());
			}
			fileSaver.println();
		}
		fileSaver.flush();
	}

	public static List<Transaction> openSalesDatabase() throws FileNotFoundException {
		String fileName = "SalesReport.csv";
		List<Transaction> transactionList = new ArrayList<Transaction>();
		ObservableList<Product> productList = FXCollections.observableArrayList();
		String line = null;
		FileReader content = new FileReader(fileName);
		Scanner entryReader = new Scanner(content);
		int column;
		while (entryReader.hasNext()) {
			line = entryReader.nextLine();
			String token[] = line.split(",");
			column = 6;

			for (int i = 0; i < (token.length - 6) / 9; i++) {
				productList.add(new Product(Integer.parseInt(token[column].trim()), token[column + 1].trim(),
						token[column + 2].trim(), token[column + 3].trim(),
						Double.parseDouble(token[column + 4].trim()), Double.parseDouble(token[column + 5].trim()),
						Integer.parseInt(token[column + 6].trim())));
						productList.get(i).setSubQuantity(Integer.parseInt(token[column + 7].trim()));
						productList.get(i).setSubTotal(Double.parseDouble(token[column + 8].trim()));
				column +=9;
			}

			transactionList.add(new Transaction(Integer.parseInt(token[0].trim()), token[1].trim(), token[2].trim(),
					Double.parseDouble(token[3].trim()), Double.parseDouble(token[4].trim()),
					Integer.parseInt(token[5].trim()), productList));
			productList = FXCollections.observableArrayList();
		}
		entryReader.close();
		return transactionList;
	}

	public static void saveSalesTransactions(ObservableList<Transaction> transactionList) throws FileNotFoundException {
		String fileName = "SalesReport.csv";
		FileWriter file = null;
		try {
			file = new FileWriter(fileName);
		} catch (IOException e) {
		}
		PrintWriter fileSaver = new PrintWriter(file);
		for (Transaction transaction : transactionList) {
			fileSaver.print(transaction.getTransactionNumber() + ", " + transaction.getTransactionDate() + ", "
					+ transaction.getNameOfClient() + ", " + transaction.getCashGiven() + ", "
					+ transaction.gettotalAmount() + ", " + transaction.getTotalItems());
			for (Product product : transaction.getProductList()) {
				fileSaver.print(", " + product.getProductId() + ", " + product.getProductName() + ", "
						+ product.getProductUnit() + ", " + product.getProductDescription() + ", "
						+ product.getPriceForPurchase() + ", " + product.getPriceForSales() + ", "
						+ product.getStockQuantity() + ", " + product.getSubQuantity() + ", " + product.getSubTotal());
			}
			fileSaver.println();
		}
		fileSaver.flush();
	}

	public static List<Product> openInventoryDatabase() throws FileNotFoundException {
		String fileName = "Inventory.csv";
		List<Product> inventoryList = new ArrayList<Product>();
		String line = null;
		FileReader content = new FileReader(fileName);
		Scanner entryReader = new Scanner(content);
		while (entryReader.hasNext()) {
			line = entryReader.nextLine();
			String token[] = line.split(",");
			inventoryList.add(new Product(Integer.parseInt(token[0].trim()), token[1].trim(), token[2].trim(),
					token[3].trim(), Double.parseDouble(token[4].trim()), Double.parseDouble(token[5].trim()),
					Integer.parseInt(token[6].trim())));
		}
		entryReader.close();
		return inventoryList;
	}

	public static void saveProductInventory(ObservableList<Product> inventoryList) throws FileNotFoundException {
		String fileName = "Inventory.csv";
		FileWriter file = null;
		try {
			file = new FileWriter(fileName);
		} catch (IOException e) {
		}
		PrintWriter fileSaver = new PrintWriter(file);
		for (Product product : inventoryList) {
			fileSaver.println(product.getProductId() + ", " + product.getProductName() + ", " + product.getProductUnit()
					+ ", " + product.getProductDescription() + ", " + product.getPriceForPurchase() + ", "
					+ product.getPriceForSales() + ", " + product.getStockQuantity());
		}
		fileSaver.flush();
	}

	/*
	 * public static List<Transaction> openPurchaseTransactionDatabase() throws
	 * FileNotFoundException { String fileName = "PurchaseTransaction.csv";
	 * List<Transaction> transactionPurchase = new ArrayList<Transaction>();
	 * String line = null; FileReader content = null; try { content = new
	 * FileReader(fileName); } catch (IOException e) { } Scanner entryReader =
	 * new Scanner(content); while (entryReader.hasNext()) { line =
	 * entryReader.nextLine(); String token[] = line.split(","); Purchase
	 * purchase = new
	 * Purchase(token[2],Integer.parseInt(token[3]),token[4],token[5],Double.
	 * parseDouble(token[6]),Integer.parseInt(token[7]));
	 * transactionPurchase.add(new
	 * Transaction(Integer.parseInt(token[0].trim()),token[1],purchase)); }
	 * entryReader.close(); return transactionPurchase; }
	 */

	/*
	 * public static void
	 * savePurchaseTransactionDatabase(ObservableList<Transaction>
	 * transactionPurchase) throws FileNotFoundException { FileWriter file =
	 * null; String fileName = "PurchaseTransaction.csv"; try { file = new
	 * FileWriter(fileName); } catch (IOException e) {
	 * System.out.println("Error"); } PrintWriter fileSaver = new
	 * PrintWriter(file); for (Transaction transact : transactionPurchase) {
	 * Purchase purchase = transact.getPurchaseItem();
	 * fileSaver.println(transact.getTransactionNumber().intValue() + "," +
	 * transact.getTransactionDate().get() + "," + purchase.getSupplier().get()
	 * + "," + purchase.getProductId().intValue() + "," +
	 * purchase.getProductName().get() + "," +
	 * purchase.getProductDescription().get() + "," +
	 * purchase.getPriceForPurchase().doubleValue() + "," +
	 * purchase.getPurchaseQuantity().intValue()); } fileSaver.flush(); }
	 */
}