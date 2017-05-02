package com.ever.POS.best.view;

import java.io.FileNotFoundException;
import java.util.Optional;

import com.ever.POS.best.MainPOSApp;
import com.ever.POS.best.controller.DatabaseController;
import com.ever.POS.best.controller.Inventory;
import com.ever.POS.best.model.Product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class InventoryScreenController {

	public static InventoryUpdate addOrEdit;
	private ObservableList<Product> allProducts;

	private MainPOSApp posApp;

	public InventoryScreenController() {

	}

	public enum InventoryUpdate {
		ADD, EDIT;
	}

	@FXML
	private void initialize() {
		updateProductList();
		updateTable(allProducts);
	}

	private void updateProductList() {
		allProducts = FXCollections.observableArrayList(DatabaseController.openInventoryDatabase());
	}

	public void updateTable(ObservableList<Product> products) {
		productsTable.setItems(products);
		productCode.setCellValueFactory(cellData -> cellData.getValue().productCodeProperty());
		productName.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
		purchasePrice.setCellValueFactory(cellData -> cellData.getValue().priceForPurchaseProperty());
		productUnit.setCellValueFactory(cellData -> cellData.getValue().productUnitProperty());
		productDescription.setCellValueFactory(cellData -> cellData.getValue().productDescriptionProperty());
		retailPrice.setCellValueFactory(cellData -> cellData.getValue().priceForSalesProperty());
		inStock.setCellValueFactory(cellData -> cellData.getValue().stockQuantityProperty());
	}

	public void setMainApp(MainPOSApp mainApp) {
		this.posApp = mainApp;
	}

	@FXML
	private void handleSearch(KeyEvent event) {
		ObservableList<Product> productsFound;
		if (productSearch.getText().trim() != "") {
			productsFound = Inventory.searchProduct(allProducts, productSearch.getText());
			updateTable(productsFound);
		} else
			updateTable(allProducts);
		;
	}

	@FXML
	private void handleAddProduct() {
		addOrEdit = InventoryUpdate.ADD;
		posApp.showProductAddDialog();
		updateProductList();
		updateTable(allProducts);
	}

	@FXML
	private void handleBack() throws FileNotFoundException {
		posApp.showMainMenu();
	}

	// Change to db method
	@FXML
	private void handleDeleteProduct() throws FileNotFoundException {
		Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
		if (selectedProduct != null) {

			Alert alert = new Alert(AlertType.WARNING, "", ButtonType.YES, ButtonType.NO);
			alert.initOwner(posApp.getPrimaryStage());
			alert.setTitle("Delete Product");
			alert.setHeaderText("You are permanently deleting a product from the inventory");
			alert.setContentText("Are you sure?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.YES) {
				if (DatabaseController.deleteProduct(selectedProduct)) {
					updateProductList();
					updateTable(allProducts);
					dbSuccessDialog();
				} else
					dbFailedDialog();
			}
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(posApp.getPrimaryStage());
			alert.setTitle("No Product Selected!");
			alert.setHeaderText(null);
			alert.setContentText("Please select a product in the table.");

			alert.showAndWait();
		}
	}

	private void dbSuccessDialog() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(posApp.getPrimaryStage());
		alert.setTitle("Inventory");
		alert.setHeaderText(null);
		alert.setContentText("Inventory Updated!");
		alert.showAndWait();
	}

	private void dbFailedDialog() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.initOwner(posApp.getPrimaryStage());
		alert.setTitle("Inventory");
		alert.setHeaderText("Update Failed!");
		alert.setContentText("Error on database.");
		alert.showAndWait();
	}

	@FXML
	private void handleEditProduct() {
		Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
		if (selectedProduct != null) {
			addOrEdit = InventoryUpdate.EDIT;
			posApp.showProductEditDialog(selectedProduct);
			updateProductList();
			updateTable(allProducts);
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(posApp.getPrimaryStage());
			alert.setTitle("No Product Selected!");
			alert.setHeaderText(null);
			alert.setContentText("Please select a product in the table.");

			alert.showAndWait();
		}
	}

	@FXML
	public TextField productSearch;

	@FXML
	public TableView<Product> productsTable;

	@FXML
	private TableColumn<Product, Number> productCode;

	@FXML
	private TableColumn<Product, String> productName;

	@FXML
	private TableColumn<Product, String> productUnit;

	@FXML
	private TableColumn<Product, String> productDescription;

	@FXML
	private TableColumn<Product, Number> purchasePrice;

	@FXML
	private TableColumn<Product, Number> retailPrice;

	@FXML
	private TableColumn<Product, Number> inStock;
}
