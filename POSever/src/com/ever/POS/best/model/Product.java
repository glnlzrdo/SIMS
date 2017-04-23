package com.ever.POS.best.model;

import java.text.NumberFormat;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Product {

	private int productId;
	private final IntegerProperty productCode;
	private StringProperty productName;
	private StringProperty productUnit;
	private StringProperty productDescription;
	private DoubleProperty priceForPurchase;
	private DoubleProperty priceForSales;
	private DoubleProperty stockQuantity;
	private DoubleProperty subTotal;
	private DoubleProperty subQuantity;

	public Product() {
		this(0, null, null, null, 0, 0, 0);
	}

	public Product(int productCode, String productName, String productUnit, String productDescription,
			double priceForPurchase, double priceForSales, double d) {
		this.productCode = new SimpleIntegerProperty(productCode);
		this.productName = new SimpleStringProperty(productName);
		this.productUnit = new SimpleStringProperty(productUnit);
		this.productDescription = new SimpleStringProperty(productDescription);
		this.priceForPurchase = new SimpleDoubleProperty(priceForPurchase);
		this.priceForSales = new SimpleDoubleProperty(priceForSales);
		this.stockQuantity = new SimpleDoubleProperty(d);
		this.subQuantity = new SimpleDoubleProperty(0);
		this.subTotal = new SimpleDoubleProperty(0);
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getProductCode() {
		return productCode.get();
	}

	public void setProductCode(int productCode) {
		this.productCode.set(productCode);
	}

	public IntegerProperty productCodeProperty() {
		return productCode;
	}

	public String getProductName() {
		return productName.get();
	}

	public void setProductName(String productName) {
		this.productName.set(productName);
	}

	public StringProperty productNameProperty() {
		return productName;
	}

	public StringProperty productUnitProperty() {
		return productUnit;
	}

	public String getProductUnit() {
		return productUnit.get();
	}

	public void setProductUnit(String productUnit) {
		this.productUnit.set(productUnit);
	}

	public StringProperty productDescriptionProperty() {
		return productDescription;
	}

	public String getProductDescription() {
		return productDescription.get();
	}

	public void setProductDescription(String productDescription) {
		this.productDescription.set(productDescription);
	}

	public double getPriceForPurchase() {
		return priceForPurchase.get();
	}

	public void setPriceForPurchase(double priceForPurchase) {
		this.priceForPurchase.set(priceForPurchase);
	}

	public DoubleProperty priceForPurchaseProperty() {
		return priceForPurchase;
	}

	public double getPriceForSales() {
		return priceForSales.get();
	}

	public void setPriceForSales(double priceForSales) {
		this.priceForSales.set(priceForSales);
	}

	public DoubleProperty priceForSalesProperty() {
		return priceForSales;
	}

	public double getStockQuantity() {
		return stockQuantity.get();
	}

	public void setStockQuantity(double d) {
		this.stockQuantity.set(d);
	}

	public DoubleProperty stockQuantityProperty() {
		return stockQuantity;
	}

	//

	public double getSubQuantity() {
		return subQuantity.get();
	}

	public void setSubQuantity(int subQuantity) {
		this.subQuantity.set(subQuantity);
	}

	public DoubleProperty productSubQuantityProperty() {
		return subQuantity;
	}

	public double getSubTotal() {
		return subTotal.get();
	}

	public void setSubTotal(double subTotal) {
		this.subTotal.set(subTotal);
	}

	public DoubleProperty productSubTotalProperty() {
		return subTotal;
	}

	@Override
	public String toString() {
		return getProductCode() + " " + getProductName() + " " + getProductDescription();
	}

	public String toStringPurchase() {
		return getProductCode() + " " + getProductName() + " " + getProductDescription() + "  -  " + NumberFormat.getCurrencyInstance().format(getPriceForPurchase())
				+ " PER " + getProductUnit();
	}

	public String toStringSales() {
		return getProductCode() + " " + getProductName() + " " + getProductDescription() + "  -  " + NumberFormat.getCurrencyInstance().format(getPriceForSales())
				+ " PER " + getProductUnit();
	}

}
