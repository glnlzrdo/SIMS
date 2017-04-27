package com.ever.POS.best.view;

import com.ever.POS.best.controller.DatabaseController;
import com.ever.POS.best.controller.Helpers;
import com.ever.POS.best.model.Product;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AddEditProductScreenController {

	private Stage dialogStage;

	@FXML
	private void initialize() {
		if (InventoryScreenController.addOrEdit == "ADD") {
			productCode.setEditable(true);
			productCode.setPromptText("Type a new Product Code Here...");
		}
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
		this.dialogStage.getIcons().add(new Image(this.getClass().getResource("EmployeeBuddy.png").toString()));
	}

	public void setProduct(Product product) {
		productCode.setText(Integer.toString(product.getProductCode()));
		productName.setText(product.getProductName());
		productUnit.setText(product.getProductUnit());
		productDescription.setText(product.getProductDescription());
		purchasePrice.setText(Double.toString(product.getPriceForPurchase()));
		retailPrice.setText(Double.toString(product.getPriceForSales()));
		inStock.setText(Double.toString(product.getStockQuantity()));
	}

	@FXML
	private void productCodeKeypressEvent() {
		if (!productCode.getText().trim().equals(""))
			try {
				if (DatabaseController.getProductViaCode(Integer.parseInt(productCode.getText())) != null) {
					productExistingWarning.setVisible(true);
				} else
					productExistingWarning.setVisible(false);
			} catch (NumberFormatException e) {
				Helpers.filterTextfieldToNumbers(productCode);
			}
	}

	@FXML
	private void handleSave() {
		if (isInputValid()) {
			Product product = new Product(Integer.parseInt(productCode.getText()), productName.getText(),
					productUnit.getText(), productDescription.getText(), Double.parseDouble(purchasePrice.getText()),
					Double.parseDouble(retailPrice.getText()), Double.parseDouble(inStock.getText()));

			if (InventoryScreenController.addOrEdit == "ADD") {
				/* Check if product code doesn't exists */
				if (DatabaseController.getProductViaCode(product.getProductCode()) == null) {
					if (DatabaseController.createNewProduct(product))
						dbSuccessDialog();
					else
						dbFailedDialog();
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.initOwner(dialogStage);
					alert.setTitle("Product Inventory");
					alert.setHeaderText("Product code existing!");
					alert.showAndWait();
				}

			} else {
				if (DatabaseController.updateProductDetails(product))
					dbSuccessDialog();
				else
					dbFailedDialog();
			}
			dialogStage.close();
		}
	}

	private void dbSuccessDialog() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(dialogStage);
		alert.setTitle("Product Inventory");
		alert.setHeaderText("Inventory has been updated!");
		alert.showAndWait();
	}

	private void dbFailedDialog() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.initOwner(dialogStage);
		alert.setTitle("Product Inventory");
		alert.setHeaderText("Inventory update failed!");
		alert.showAndWait();
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	private boolean isInputValid() {
		String errorMessage = "";
		if (productCode.getText().trim().equals("")) {
			errorMessage += "Invalid Product ID!\n";
		}
		if (productName.getText().trim().equals("")) {
			errorMessage += "Invalid Product Name!\n";
		}
		if (productUnit.getText().trim().equals("")) {
			errorMessage += "Invalid Product Unit!\n";
		}
		if (productDescription.getText().trim().equals("")) {
			errorMessage += "Invalid Product Description!\n";
		}
		if (purchasePrice.getText().trim().equals("")) {
			errorMessage += "Invalid Purchase Price!\n";
		} else {
			try {
				if (Double.parseDouble(purchasePrice.getText()) <= 0)
					throw new NumberFormatException();
			} catch (NumberFormatException e) {
				errorMessage += "Invalid Purchase Price!\n";
			}
		}
		if (retailPrice.getText().trim().equals("")) {
			errorMessage += "Invalid Retail Price!\n";
		} else {
			try {
				if (Double.parseDouble(retailPrice.getText()) <= 0)
					throw new NumberFormatException();
			} catch (NumberFormatException e) {
				errorMessage += "Invalid Retail Price!\n";
			}
		}
		if (inStock.getText().trim().equals("")) {
			errorMessage += "Invalid Quantity!\n";
		} else {
			try {
				if (Double.parseDouble(inStock.getText()) < 0)
					throw new NumberFormatException();
			} catch (NumberFormatException e) {
				errorMessage += "Invalid Quantity!\n";
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

	@FXML
	private TextField productCode;

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

	@FXML
	private Label productExistingWarning;
}
