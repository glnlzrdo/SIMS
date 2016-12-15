package com.ever.POS.best.view;

import java.io.FileNotFoundException;

import com.ever.POS.best.MainPOSApp;
import com.ever.POS.best.controller.DatabaseController;
import com.ever.POS.best.model.Product;
import com.ever.POS.best.model.Purchase;
import com.ever.POS.best.model.Transaction;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class TransactionReportScreenController {
	private MainPOSApp posApp;
	private static ObservableList<Purchase> purchaseList = FXCollections.observableArrayList();
	private static ObservableList<Transaction> salesList = FXCollections.observableArrayList();
	private ObservableList<Product> purchaseProducts = FXCollections.observableArrayList();
	private ObservableList<Product> products = FXCollections.observableArrayList();

	// Purchase Pane

	@FXML
	private AnchorPane purchasePane;

	@FXML
	private ListView<Purchase> purchaseTransactionNumberColumn;

	@FXML
	private TableView<Product> purchaseProductsTable;

	@FXML
	private TableColumn<Product, Number> purchaseProductID;

	@FXML
	private TableColumn<Product, String> purchaseProductName;

	@FXML
	private TableColumn<Product, String> purchaseProductDescription;

	@FXML
	private TableColumn<Product, Number> purchaseProductPrice;

	@FXML
	private TableColumn<Product, Number> purchaseProductQuantity;

	@FXML
	private TableColumn<Product, String> purchaseProductUnit;

	@FXML
	private TableColumn<Product, Number> purchaseProductSubTotalAmount;

	@FXML
	private Label purchaseCustomerName;

	@FXML
	private Label purchaseDateLabel;

	@FXML
	private Label purchaseTotalAmount;

	@FXML
	private Label purchaseNumberOfItems;

	// Sales Pane

	@FXML
	private AnchorPane salesPane;

	@FXML
	private ListView<Transaction> transactionNumberColumn;

	@FXML
	private TableView<Product> productsTable;

	@FXML
	private TableColumn<Product, Number> productID;

	@FXML
	private TableColumn<Product, String> productName;

	@FXML
	private TableColumn<Product, String> productDescription;

	@FXML
	private TableColumn<Product, Number> productPrice;

	@FXML
	private TableColumn<Product, Number> productQuantity;

	@FXML
	private TableColumn<Product, String> productUnit;

	@FXML
	private TableColumn<Product, Number> productSubtotalAmount;

	@FXML
	private Label customerName;

	@FXML
	private Label dateLabel;

	@FXML
	private Label totalAmount;

	@FXML
	private Label numberOfItems;

	@FXML
	private ComboBox<String> transactionType;

	@FXML
	private Button backButton;

	@FXML
	private void initialize() throws FileNotFoundException {
		transactionType.setItems(FXCollections.observableArrayList("Purchase Transactions", "Sales Transactions"));
		purchaseList = FXCollections.observableArrayList(DatabaseController.openPurchaseDatabase());
		purchaseProducts = purchaseList.get(0).getProductList();
		updatePurchaseTable();
		purchaseCustomerName.setText(purchaseList.get(0).getNameOfClient());
		purchaseDateLabel.setText(purchaseList.get(0).getTransactionDate());
		purchaseTotalAmount.setText(Double.toString(purchaseList.get(0).getTotalAmount()));
		purchaseNumberOfItems.setText(Integer.toString(purchaseList.get(0).getTotalItems()));

		salesList = FXCollections.observableArrayList(DatabaseController.openSalesDatabase());
		products = salesList.get(0).getProductList();
		updateTable();
		customerName.setText(salesList.get(0).getNameOfClient());
		dateLabel.setText(salesList.get(0).getTransactionDate());
		totalAmount.setText(Double.toString(salesList.get(0).gettotalAmount()));
		numberOfItems.setText(Integer.toString(salesList.get(0).getTotalItems()));
	}

	public void updatePurchaseTable() {
		purchaseProductsTable.setItems(purchaseProducts);
		purchaseTransactionNumberColumn.setItems(purchaseList);

		purchaseProductID.setCellValueFactory(cellData -> cellData.getValue().productIDProperty());
		purchaseProductName.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
		purchaseProductUnit.setCellValueFactory(cellData -> cellData.getValue().productUnitProperty());
		purchaseProductDescription.setCellValueFactory(cellData -> cellData.getValue().productDescriptionProperty());
		purchaseProductPrice.setCellValueFactory(cellData -> cellData.getValue().priceForPurchaseProperty());
		purchaseProductQuantity.setCellValueFactory(cellData -> cellData.getValue().productSubQuantityProperty());
		purchaseProductSubTotalAmount.setCellValueFactory(cellData -> cellData.getValue().productSubTotalProperty());

	}

	@FXML
	private void selectTransactionType() {
		if (transactionType.getValue() == "Purchase Transactions") {
			purchasePane.setVisible(true);
			salesPane.setVisible(false);
		} else {
			purchasePane.setVisible(false);
			salesPane.setVisible(true);
		}
	}

	@FXML
	private void selectPurchaseNumber() {
		purchaseProducts = purchaseList.get(purchaseTransactionNumberColumn.getSelectionModel().getSelectedIndex())
				.getProductList();
		purchaseCustomerName.setText(purchaseList
				.get(purchaseTransactionNumberColumn.getSelectionModel().getSelectedIndex()).getNameOfClient());
		purchaseDateLabel.setText(purchaseList
				.get(purchaseTransactionNumberColumn.getSelectionModel().getSelectedIndex()).getTransactionDate());
		purchaseTotalAmount.setText(Double.toString(purchaseList
				.get(purchaseTransactionNumberColumn.getSelectionModel().getSelectedIndex()).getTotalAmount()));
		purchaseNumberOfItems.setText(Integer.toString(purchaseList
				.get(purchaseTransactionNumberColumn.getSelectionModel().getSelectedIndex()).getTotalItems()));

		updatePurchaseTable();
	}

	public void updateTable() {
		productsTable.setItems(products);
		transactionNumberColumn.setItems(salesList);

		productID.setCellValueFactory(cellData -> cellData.getValue().productIDProperty());
		productName.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
		productUnit.setCellValueFactory(cellData -> cellData.getValue().productUnitProperty());
		productDescription.setCellValueFactory(cellData -> cellData.getValue().productDescriptionProperty());
		productPrice.setCellValueFactory(cellData -> cellData.getValue().priceForSalesProperty());
		productQuantity.setCellValueFactory(cellData -> cellData.getValue().productSubQuantityProperty());
		productSubtotalAmount.setCellValueFactory(cellData -> cellData.getValue().productSubTotalProperty());

	}

	@FXML
	public void selectNumber() {
		products = salesList.get(transactionNumberColumn.getSelectionModel().getSelectedIndex()).getProductList();
		customerName.setText(
				salesList.get(transactionNumberColumn.getSelectionModel().getSelectedIndex()).getNameOfClient());
		dateLabel.setText(
				salesList.get(transactionNumberColumn.getSelectionModel().getSelectedIndex()).getTransactionDate());
		totalAmount.setText(Double.toString(
				salesList.get(transactionNumberColumn.getSelectionModel().getSelectedIndex()).gettotalAmount()));
		numberOfItems.setText(Integer.toString(
				salesList.get(transactionNumberColumn.getSelectionModel().getSelectedIndex()).getTotalItems()));

		updateTable();
	}

	@FXML
	private void backButtonClicked() {
		posApp.showMainMenu();
	}

	public void setMainApp(MainPOSApp mainApp) {
		this.posApp = mainApp;
	}

}
