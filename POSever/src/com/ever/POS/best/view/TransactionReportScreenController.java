package com.ever.POS.best.view;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.ever.POS.best.MainPOSApp;
import com.ever.POS.best.controller.DatabaseController;
import com.ever.POS.best.controller.Inventory;
import com.ever.POS.best.enums.TransactionType;
import com.ever.POS.best.model.Client;
import com.ever.POS.best.model.Product;
import com.ever.POS.best.model.Transaction;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class TransactionReportScreenController {
	private MainPOSApp posApp;
	private Toggle previousToggle;
	private ObservableList<String> clientNames = FXCollections.observableArrayList();

	@FXML
	private void initialize() {
		transactionType.setItems(FXCollections.observableArrayList("PURCHASE TRANSACTIONS", "SALES TRANSACTIONS"));
		showAllTransactions(TransactionType.PURCHASE);

		// Determine if the different radioButton was clicked
		filterGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (filterGroup.getSelectedToggle() != null) {
					previousToggle = old_toggle;
				}
			}
		});

		formatDatePickers();
	}

	private void showAllTransactions(TransactionType type) {
		ObservableList<Transaction> transactions = FXCollections
				.observableArrayList(DatabaseController.openTransactionDatabase(type));
		transactionTable.setItems(transactions);
		transactionType.setValue("PURCHASE TRANSACTIONS");
		selectTransactionType();
		updateTable(transactions.get(0));
		transactionNumberColumn.setCellValueFactory(cellData -> cellData.getValue().tansactionNumberProperty());
	}

	private void formatDatePickers() {
		String pattern = "yyyy-MM-dd";

		startDate.setPromptText(pattern.toLowerCase());
		endDate.setPromptText(pattern.toLowerCase());

		startDate.setConverter(new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		});

		endDate.setConverter(new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		});
		startDate.setValue(LocalDate.now());
		endDate.setValue(LocalDate.now());
	}

	@FXML
	private void selectTransactionType() {
		filterGroup.selectToggle(showAllRadio);
		clientNames.clear();
		if (transactionType.getValue() == "PURCHASE TRANSACTIONS") {
			ObservableList<Transaction> purchaseList = FXCollections
					.observableArrayList(DatabaseController.openTransactionDatabase(TransactionType.PURCHASE));
			transactionTable.setItems(purchaseList);
			ObservableList<Client> clients = DatabaseController.getAllClients(TransactionType.PURCHASE);
			for (Client client : clients) {
				clientNames.add(client.getName());
			}
			updateTable(purchaseList.get(0));
		} else {
			ObservableList<Transaction> salesList = FXCollections
					.observableArrayList(DatabaseController.openTransactionDatabase(TransactionType.RETAIL));
			transactionTable.setItems(salesList);
			ObservableList<Client> clients = DatabaseController.getAllClients(TransactionType.RETAIL);
			for (Client client : clients) {
				clientNames.add(client.getName());
			}
			updateTable(salesList.get(0));
		}
		nameCombo.setItems(clientNames);
		nameCombo.setValue(clientNames.get(0));
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

		if (transactionType.getValue() == "PURCHASE TRANSACTIONS") {
			productPrice.setCellValueFactory(cellData -> cellData.getValue().priceForPurchaseProperty());
		} else {
			productPrice.setCellValueFactory(cellData -> cellData.getValue().priceForSalesProperty());
		}

		Inventory.formatCellToDecimal(productPrice);
		productQuantity.setCellValueFactory(cellData -> cellData.getValue().productSubQuantityProperty());
		Inventory.formatCellToDecimal(productQuantity);
		// productSubtotalAmount.setCellValueFactory(cellData ->
		// cellData.getValue().productSubTotalProperty());

		productSubtotalAmount.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Product, Number>, ObservableValue<Number>>() {
					@Override
					public ObservableValue<Number> call(CellDataFeatures<Product, Number> param) {
						if (transactionType.getValue() == "PURCHASE TRANSACTIONS") {
							return new SimpleDoubleProperty(
									param.getValue().getSubQuantity() * param.getValue().getPriceForPurchase());
						} else {
							return new SimpleDoubleProperty(
									param.getValue().getSubQuantity() * param.getValue().getPriceForSales());
						}
					}

				});

		Inventory.formatCellToCurrency(productSubtotalAmount);
	}

	@FXML
	private void selectNumber() {
		if (transactionTable.getSelectionModel().getSelectedItem() != null) {
			updateTable(transactionTable.getSelectionModel().getSelectedItem());
		}
	}

	private enum Filter {
		SHOW_ALL, DATE, NAME
	}

	private Filter filter;

	@FXML
	private void radioShowAllClicked() {
		showAllTransactions(transactionType.getValue() == "PURCHASE TRANSACTIONS" ? TransactionType.PURCHASE
				: TransactionType.RETAIL);
		filter = Filter.SHOW_ALL;
	}

	@FXML
	private void radioDateClicked() {
		mainPane.setDisable(true);
		dateFilterDialog.setVisible(true);
	}

	@FXML
	private void dateCancel(ActionEvent event) {
		dateFilterDialog.setVisible(false);
		mainPane.setDisable(false);
		if (filter != Filter.DATE)
			filterGroup.selectToggle(previousToggle);
	}

	@FXML
	private void dateSearch(ActionEvent event) {
		if (transactionType.getValue() == "PURCHASE TRANSACTIONS") {
			ObservableList<Transaction> purchaseList = FXCollections.observableArrayList(
					DatabaseController.getTransactionsFilteredByDate(startDate.getValue().toString(),
							endDate.getValue().toString(), TransactionType.PURCHASE));
			transactionTable.setItems(purchaseList);
			if (purchaseList.size() > 0)
				updateTable(purchaseList.get(0));
			else
				clearTable();
		} else {
			ObservableList<Transaction> salesList = FXCollections.observableArrayList(
					DatabaseController.getTransactionsFilteredByDate(startDate.getValue().toString(),
							endDate.getValue().toString(), TransactionType.RETAIL));
			transactionTable.setItems(salesList);
			if (salesList.size() > 0)
				updateTable(salesList.get(0));
			else
				clearTable();
		}
		transactionNumberColumn.setCellValueFactory(cellData -> cellData.getValue().tansactionNumberProperty());
		filter = Filter.DATE;
		dateFilterDialog.setVisible(false);
		mainPane.setDisable(false);
	}

	@FXML
	private void radioNameClicked() {
		mainPane.setDisable(true);
		nameFilterDialog.setVisible(true);
	}

	@FXML
	private void nameCancel(ActionEvent event) {
		nameFilterDialog.setVisible(false);
		mainPane.setDisable(false);
		if (filter != Filter.NAME)
			filterGroup.selectToggle(previousToggle);
	}

	@FXML
	private void nameSearch(ActionEvent event) {
		if (transactionType.getValue() == "PURCHASE TRANSACTIONS") {
			ObservableList<Transaction> purchaseList = FXCollections.observableArrayList(DatabaseController
					.getTransactionsFilteredByClientName(nameCombo.getValue(), TransactionType.PURCHASE));
			transactionTable.setItems(purchaseList);
			if (purchaseList.size() > 0)
				updateTable(purchaseList.get(0));
			else
				clearTable();
		} else {
			ObservableList<Transaction> salesList = FXCollections.observableArrayList(DatabaseController
					.getTransactionsFilteredByClientName(nameCombo.getValue(), TransactionType.RETAIL));
			transactionTable.setItems(salesList);
			if (salesList.size() > 0)
				updateTable(salesList.get(0));
			else
				clearTable();
		}
		transactionNumberColumn.setCellValueFactory(cellData -> cellData.getValue().tansactionNumberProperty());
		nameFilterDialog.setVisible(false);
		mainPane.setDisable(false);
		filter = Filter.NAME;
	}

	private void clearTable() {
		customerName.setText("");
		dateLabel.setText("");
		totalAmount.setText(NumberFormat.getCurrencyInstance().format(0.00));
		numberOfItems.setText("0");
		productsTable.setItems(null);
	}

	@FXML
	private void backButtonClicked() {
		posApp.showMainMenu();
	}

	public void setMainApp(MainPOSApp mainApp) {
		this.posApp = mainApp;
	}

	@FXML
	private AnchorPane mainPane;

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

	@FXML
	private DatePicker startDate;

	@FXML
	private DatePicker endDate;

	@FXML
	private ComboBox<String> nameCombo;
}
