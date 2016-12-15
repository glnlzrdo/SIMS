package com.ever.POS.best.controller;

import com.ever.POS.best.model.Product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
	// private static int productPointer = 0;
	private static ObservableList<Product> productList;

	public static void addProduct(int productId, String productName, String productUnit, String productDescription,
			double productPriceForPurchase, double productPriceForSales, int stockQuantity) {
		productList.add(new Product(productId, productName, productUnit, productDescription, productPriceForPurchase,
				productPriceForSales, stockQuantity));
	}

	// To be replaced by Product Table List on view

	public static ObservableList<Product> searchProduct(ObservableList<Product> product, String productCriteria) {
		ObservableList<Product> productsFound = FXCollections.observableArrayList();
		for (int i = 0; i < product.size(); i++) {
			if (product.get(i) != null
					&& product.get(i).getProductName().toUpperCase().contains(productCriteria.toUpperCase())) {
				productsFound.add(product.get(i));
			} else if (product.get(i) != null && Integer.toString(product.get(i).getProductId()).toUpperCase()
					.contains(productCriteria.toUpperCase())) {
				productsFound.add(product.get(i));
			} else if (product.get(i) != null
					&& product.get(i).getProductDescription().toUpperCase().contains(productCriteria.toUpperCase())) {
				productsFound.add(product.get(i));
			}
		}
		return productsFound;
	}


	public static ObservableList<Product> getAllProducts() {
		return productList;
	}

}
