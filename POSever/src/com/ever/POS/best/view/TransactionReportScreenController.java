package com.ever.POS.best.view;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ever.POS.best.MainPOSApp;
import com.ever.POS.best.controller.DatabaseController;
import com.ever.POS.best.controller.Inventory;
import com.ever.POS.best.enums.TransactionType;
import com.ever.POS.best.model.Product;
import com.ever.POS.best.model.Transaction;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;

public class TransactionReportScreenController {
	private MainPOSApp posApp;

	@FXML
	private void initialize() {
		transactionType.setItems(FXCollections.observableArrayList("Purchase Transactions", "Sales Transactions"));
		ObservableList<Transaction> purchaseList = FXCollections
				.observableArrayList(DatabaseController.openTransactionDatabase(TransactionType.PURCHASE));
		transactionTable.setItems(purchaseList);
		updateTable(purchaseList.get(0));
		transactionNumberColumn.setCellValueFactory(cellData -> cellData.getValue().tansactionNumberProperty());
	}

	@FXML
	private void selectTransactionType() {
		ObservableList<Transaction> purchaseList = FXCollections
				.observableArrayList(DatabaseController.openTransactionDatabase(TransactionType.PURCHASE));

		ObservableList<Transaction> salesList = FXCollections
				.observableArrayList(DatabaseController.openTransactionDatabase(TransactionType.RETAIL));

		if (transactionType.getValue() == "Purchase Transactions") {
			transactionTable.setItems(purchaseList);
			updateTable(purchaseList.get(0));
		} else {
			transactionTable.setItems(salesList);
			updateTable(salesList.get(0));
		}
		transactionNumberColumn.setCellValueFactory(cellData -> cellData.getValue().tansactionNumberProperty());
	}

	public void updateTable(Transaction transaction) {
		customerName.setText(transaction.getNameOfClient());
		Date date = new Date();
		SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
		try {
			date = dateParser.parse(
					transaction.getTransactionDate().substring(0, transaction.getTransactionDate().length() - 2));
			dateLabel.setText(sdf.format(date));
		} catch (ParseException e1) {
			dateLabel.setText(
					transaction.getTransactionDate().substring(0, transaction.getTransactionDate().length() - 2));
		}
		totalAmount.setText(NumberFormat.getCurrencyInstance().format(transaction.getTotalAmount()));
		numberOfItems.setText(Integer.toString(transaction.getTotalItems()));
		productsTable.setItems(transaction.getProductList());

		productCode.setCellValueFactory(cellData -> cellData.getValue().productCodeProperty());
		productName.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
		productUnit.setCellValueFactory(cellData -> cellData.getValue().productUnitProperty());
		productDescription.setCellValueFactory(cellData -> cellData.getValue().productDescriptionProperty());

		if (transactionType.getValue() == "Purchase Transactions") {
			productPrice.setCellValueFactory(cellData -> cellData.getValue().priceForPurchaseProperty());
		} else {
			productPrice.setCellValueFactory(cellData -> cellData.getValue().priceForSalesProperty());
		}

		Inventory.formatCellToDecimal(productPrice);
		productQuantity.setCellValueFactory(cellData -> cellData.getValue().productSubQuantityProperty());
		Inventory.formatCellToDecimal(productQuantity);
		productSubtotalAmount.setCellValueFactory(cellData -> cellData.getValue().productSubTotalProperty());
		Inventory.formatCellToCurrency(productSubtotalAmount);
	}

	@FXML
	public void selectNumber() {
		if (transactionTable.getSelectionModel().getSelectedItem() != null) {
			updateTable(transactionTable.getSelectionModel().getSelectedItem());
		}
	}

	@FXML
	private void backButtonClicked() {
		posApp.showMainMenu();
	}

	public void setMainApp(MainPOSApp mainApp) {
		this.posApp = mainApp;
	}

	@FXML
	private TableView<Transaction> transactionTable;

	@FXML
	private TableColumn<Transaction, Number> transactionNumberColumn;

	@FXML
	private TableView<Product> productsTable;

	@FXML
	private TableColumn<Product, Number> productCode;

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
	private ToggleGroup filterGroup;

	@FXML
	private RadioButton showAllRadio;

	@FXML
	private RadioButton dateRadio;

	@FXML
	private RadioButton nameRadio;

	@FXML
	private DialogPane dateFilterDialog;

	@FXML
	private DialogPane nameFilterDialog;
}
