package com.ever.POS.best.view;

import java.io.FileNotFoundException;

import com.ever.POS.best.model.Product;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddEditProductScreenController {

	@FXML
	private TextField productID;

	@FXML
	private TextField productName;

	@FXML
	private TextField productUnit;

	@FXML
	private TextField productDescription;

	@FXML
	private TextField purchasePrice;

	@FXML
	private TextField retailPrice;

	@FXML
	private TextField inStock;

	private Stage dialogStage;
	private ObservableList<Product> allProducts;
	private Product product;
	private boolean okClicked = false;

	@FXML
	private void initialize() {
		if (InventoryScreenController.addOrEdit == "ADD") {
			productID.setEditable(true);
		}
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void passProducts(ObservableList<Product> products) {
		allProducts = products;
	}

	public void setProduct(Product product) {
		this.product = product;

		productID.setText(Integer.toString(product.getProductId()));
		productName.setText(product.getProductName());
		productUnit.setText(product.getProductUnit());
		productDescription.setText(product.getProductDescription());
		purchasePrice.setText(Double.toString(product.getPriceForPurchase()));
		retailPrice.setText(Double.toString(product.getPriceForSales()));
		inStock.setText(Integer.toString(product.getStockQuantity()));
	}

	public boolean isOkClicked() {
		return okClicked;
	}


	@FXML
	private void handleSave() throws FileNotFoundException {
		if (isInputValid()) {
			if (InventoryScreenController.addOrEdit == "ADD") {
				allProducts.add(
						new Product(Integer.parseInt(productID.getText()), productName.getText(), productUnit.getText(),
								productDescription.getText(), Double.parseDouble(purchasePrice.getText()),
								Double.parseDouble(retailPrice.getText()), Integer.parseInt(inStock.getText())));
			} else {
				product.setProductId(Integer.parseInt(productID.getText()));
				product.setProductName(productName.getText());
				product.setProductUnit(productUnit.getText());
				product.setProductDescription(productDescription.getText());
				product.setPriceForPurchase(Double.parseDouble(purchasePrice.getText()));
				product.setPriceForSales(Double.parseDouble(retailPrice.getText()));
				product.setStockQuantity(Integer.parseInt(inStock.getText()));
			}

			okClicked = true;

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(dialogStage);
			alert.setTitle("Product Inventory");
			alert.setHeaderText("Inventory has been updated!");
			//alert.setContentText("Inventory has been updated!");

			alert.showAndWait();

			dialogStage.close();
		}
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (productID.getText() == null || productID.getText().length() == 0) {
			errorMessage += "Invalid Product ID!\n";
		}
		if (productName.getText() == null || productName.getText().length() == 0) {
			errorMessage += "Invalid Product Name!\n";
		}
		if (productUnit.getText() == null || productUnit.getText().length() == 0) {
			errorMessage += "Invalid Product Unit!\n";
		}
		if (productDescription.getText() == null || productDescription.getText().length() == 0) {
			errorMessage += "Invalid Product Description!\n";
		}
		if (purchasePrice.getText() == null || purchasePrice.getText().length() == 0) {
			errorMessage += "Invalid Price!\n";
		} else {
			// try to parse the postal code into an int.
			try {
				Double.parseDouble(purchasePrice.getText());
			} catch (NumberFormatException e) {
				errorMessage += "Please enter a valid number!\n";
			}
		}
		if (retailPrice.getText() == null || retailPrice.getText().length() == 0) {
			errorMessage += "Invalid Price!\n";
		} else {
			// try to parse the postal code into an int.
			try {
				Double.parseDouble(retailPrice.getText());
			} catch (NumberFormatException e) {
				errorMessage += "Please enter a valid number!\n";
			}
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Details!");
			alert.setHeaderText("Please input a valid details for each field!");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}

}
