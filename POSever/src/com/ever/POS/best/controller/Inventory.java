package com.ever.POS.best.controller;

import com.ever.POS.best.enums.TransactionType;
import com.ever.POS.best.model.Product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

	public static ObservableList<Product> searchProduct(ObservableList<Product> products, String productCriteria) {
		ObservableList<Product> productsFound = FXCollections.observableArrayList();

		for (Product product : products) {
			String productFullName = product.toString();
			if (productFullName.toUpperCase().trim().contains(productCriteria.trim().toUpperCase())) {
				productsFound.add(product);
			}
		}
		return productsFound;
	}

	public static ObservableList<String> searchProductToStringFormat(ObservableList<Product> products,
			String productCriteria, TransactionType transactionType) {
		ObservableList<String> productsFound = FXCollections.observableArrayList();

		if (transactionType == TransactionType.PURCHASE)
			for (Product product : products) {
				String productFullName = product.toString();
				if (productFullName.toUpperCase().trim().contains(productCriteria.trim().toUpperCase())) {
					productsFound.add(product.toStringPurchase());
				}
			}
		else
			for (Product product : products) {
				String productFullName = product.toString();
				if (productFullName.toUpperCase().trim().contains(productCriteria.trim().toUpperCase())) {
					productsFound.add(product.toStringSales());
				}
			}
		return productsFound;
	}
}
