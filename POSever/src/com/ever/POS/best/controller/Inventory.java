package com.ever.POS.best.controller;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.ever.POS.best.enums.TransactionType;
import com.ever.POS.best.model.Product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

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

	public static void formatCellToDecimal(TableColumn<Product, Number> cell) {
		cell.setCellFactory(col -> new TableCell<Product, Number>() {
			@Override
			public void updateItem(Number price, boolean empty) {
				super.updateItem(price, empty);
				if (empty) {
					setText(null);
				} else {
					DecimalFormat formatter = new DecimalFormat("##,###,###.00");
					setText(formatter.format(price.doubleValue()));
				}
			}
		});
	}

	public static void formatCellToCurrency(TableColumn<Product, Number> cell) {
		cell.setCellFactory(col -> new TableCell<Product, Number>() {
			@Override
			public void updateItem(Number price, boolean empty) {
				super.updateItem(price, empty);
				if (empty) {
					setText(null);
				} else {
					setText(NumberFormat.getCurrencyInstance().format(price.doubleValue()));
				}
			}
		});
	}

	public static String formatDoubleToCurrencyString(double amount) {
		return NumberFormat.getCurrencyInstance().format(amount);
	}
}
