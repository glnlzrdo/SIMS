package com.ever.POS.best.view;

import java.io.FileNotFoundException;
import java.util.Optional;

import com.ever.POS.best.MainPOSApp;
import com.ever.POS.best.controller.DatabaseController;
import com.ever.POS.best.controller.Inventory;
import com.ever.POS.best.model.Product;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class InventoryScreenController {

	static String addOrEdit;
	private ObservableList<Product> allProducts;

	@FXML
	public TextField productSearch;

	@FXML
	public TableView<Product> productsTable;

	@FXML
	private TableColumn<Product, Number> productID;

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

	private MainPOSApp posApp;

	public InventoryScreenController() {

	}

	@FXML
	private void initialize() throws FileNotFoundException {
		// allProducts =
		// FXCollections.observableArrayList(DatabaseController.openInventoryDatabase());
		updateTable();
	}

	public void updateTable() throws FileNotFoundException {
		// productsTable.setItems(FXCollections.observableArrayList(DatabaseController.openInventoryDatabase()));
		productID.setCellValueFactory(cellData -> cellData.getValue().productIDProperty());
		productName.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
		purchasePrice.setCellValueFactory(cellData -> cellData.getValue().priceForPurchaseProperty());
		productUnit.setCellValueFactory(cellData -> cellData.getValue().productUnitProperty());
		productDescription.setCellValueFactory(cellData -> cellData.getValue().productDescriptionProperty());
		retailPrice.setCellValueFactory(cellData -> cellData.getValue().priceForSalesProperty());
		inStock.setCellValueFactory(cellData -> cellData.getValue().stockQuantityProperty());
	}

	public void setMainApp(MainPOSApp mainApp) throws FileNotFoundException {
		this.posApp = mainApp;
		productsTable.setItems(posApp.getAllProducts());
		// allProducts =
		// FXCollections.observableArrayList(DatabaseController.openInventoryDatabase());
		updateTable();
	}

	@FXML
	private void handleSearch() throws FileNotFoundException {
		ObservableList<Product> productsFound;
		if (productSearch.getText().trim() != "") {
			productsFound = Inventory.searchProduct(posApp.getAllProducts(), productSearch.getText());
			productsTable.setItems(productsFound);
		} else
			productsTable.setItems(posApp.getAllProducts());
	}

	@FXML
	private void handleAddProduct() {
		addOrEdit = "ADD";
		posApp.showProductAddDialog(this.posApp.getAllProducts());
	}

	@FXML
	private void handleBack() throws FileNotFoundException {
		if (productSearch.getText() == "")
			DatabaseController.saveProductInventory(posApp.getAllProducts());
		posApp.showMainMenu();
	}

	@FXML
	private void handleDeleteProduct() throws FileNotFoundException {
		Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
		if (selectedProduct != null) {

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete Product");
			alert.setHeaderText("You are permanently deleting a product from the inventory");
			alert.setContentText("Are you ok with this?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {

				allProducts = posApp.getAllProducts();
				allProducts.remove(productsTable.getSelectionModel().getSelectedIndex());
				DatabaseController.saveProductInventory(posApp.getAllProducts());
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(posApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Product Selected");
			alert.setContentText("Please select a product in the table.");

			alert.showAndWait();
		}
	}

	@FXML
	private void handleEditProduct() {
		Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
		if (selectedProduct != null) {
			addOrEdit = "EDIT";
			posApp.showProductEditDialog(selectedProduct);
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(posApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Product Selected");
			alert.setContentText("Please select a product in the table.");

			alert.showAndWait();
		}
	}

}
