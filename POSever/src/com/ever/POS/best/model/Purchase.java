package com.ever.POS.best.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Purchase {
	protected IntegerProperty transactionNumber;
	protected StringProperty transactionDate;
	protected DoubleProperty cashGiven;
	protected DoubleProperty totalAmount;
	protected IntegerProperty totalItems;
	protected StringProperty nameOfClient;
	protected ObservableList<Product> productList;

	public Purchase() {
	}

	public Purchase(int transactionNumber, String transactionDate, String nameOfClient, Double cashGiven, Double totalAmount,
			int totalItems, ObservableList<Product> productList){
		this.transactionNumber = new SimpleIntegerProperty(transactionNumber);
		this.transactionDate = new SimpleStringProperty(transactionDate);
		this.nameOfClient = new SimpleStringProperty(nameOfClient);
		this.cashGiven = new SimpleDoubleProperty(cashGiven);
		this.totalAmount = new SimpleDoubleProperty(totalAmount);
		this.totalItems = new SimpleIntegerProperty(totalItems);
		this.productList = productList;
	}

	///transNumber
	public int getTransactionNumber() {
		return transactionNumber.get();
	}

	public void setTransactionNumber(int productId) {
		this.transactionNumber.set(productId);
	}

	public IntegerProperty tansactionNumberProperty() {
		return transactionNumber;
	}

	///transDate
	public String getTransactionDate() {
		return transactionDate.get();
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate.set(transactionDate);
	}

	public StringProperty transactionDateProperty() {
		return transactionDate;
	}

	///clientName
	public String getNameOfClient() {
		return nameOfClient.get();
	}

	public void setNameOfClient(String nameOfClient) {
		this.nameOfClient.set(nameOfClient);
	}

	public StringProperty nameOfClientProperty() {
		return nameOfClient;
	}


	///cashGiven
	public Double getCashGiven() {
		return cashGiven.get();
	}

	public void setCashGiven(Double cashGiven) {
		this.cashGiven.set(cashGiven);
	}

	public DoubleProperty cashGivenProperty() {
		return cashGiven;
	}

	///totalAmount
	public Double getTotalAmount() {
		return totalAmount.get();
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount.set(totalAmount);
	}

	public DoubleProperty totalAmountProperty() {
		return totalAmount;
	}

	///totalItems

	public int getTotalItems() {
		return totalItems.get();
	}

	public void setTotalItems(int totalItems) {
		this.totalItems.set(totalItems);
	}

	public IntegerProperty totalItemsProperty() {
		return totalItems;
	}


	///productList
	public ObservableList<Product> getProductList() {
		return productList;
	}

	public void setProductList(ObservableList<Product> productList) {
		this.productList = productList;
	}

	@Override
	public String toString(){
		return "Transaction No. " + Integer.toString(getTransactionNumber());
	}

}
