package com.ever.POS.best.view;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import com.ever.POS.best.MainPOSApp;
import com.ever.POS.best.controller.DatabaseController;
import com.ever.POS.best.controller.Helpers;
import com.ever.POS.best.controller.Inventory;
import com.ever.POS.best.enums.TransactionType;
import com.ever.POS.best.model.Product;
import com.ever.POS.best.model.Sales;
import com.ever.POS.best.model.Transaction;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

public class SalesScreenController {

	private MainPOSApp posApp;
	static DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
	private static double superTotal;
	private static int selectedproductCode;
	private static int transNumber = 0;
	private ObservableList<Product> productsForTable = FXCollections.observableArrayList();
	private static ObservableList<Product> products;

	public SalesScreenController() {

	}

	@FXML
	private void initialize() {
		dateLabel.setText(dateFormat.format(new Date()));
		products = FXCollections.observableArrayList(DatabaseController.openInventoryDatabase());
		ObservableList<Transaction> transactionList = FXCollections
				.observableArrayList(DatabaseController.openTransactionDatabase(TransactionType.RETAIL));
		if (transactionList == null)
			transNumber = 1;
		else
			transNumber = transactionList.size() + 1;
		transactionNumber.setText(String.valueOf(transNumber));
		quantityTextbox.setText("1");
		customerLabel.setText("Cash");
		searchTextbox.requestFocus();
	}

	@FXML
	private void checkPaymentAmount(KeyEvent event) {
		if (event.getCode() == KeyCode.ESCAPE) {
			paymentCancelClicked();
		} else {
			try {
				if (Double.parseDouble(paymentText.getText()) >= superTotal) {
					paymentOKButton.setDisable(false);
					changeLabel.setText(Inventory.formatDoubleToCurrencyString(Double.parseDouble(paymentText.getText()) - superTotal));
				} else {
					paymentOKButton.setDisable(true);
					changeLabel.setText("Php0.00");
				}
			} catch (NumberFormatException e) {
				Helpers.filterTextfieldToNumbers(paymentText);
			}
		}
	}

	@FXML
	private void productSearchEvent(KeyEvent event) {
		if (event.getCode() == KeyCode.ESCAPE) {
			searchPopUp.setVisible(false);
		} else {
			// user keeps entering white space
			if (searchTextbox.getText().trim().equals("")) {

				searchTextbox.setText("");
				searchPopUp.setItems(products);
				searchPopUp.setVisible(true);
				if (event.getCode() == KeyCode.DOWN) {
					searchPopUp.requestFocus();
					searchPopUp.getSelectionModel().select(0);
				}
				// addButton.setDisable(true);
			} else {
				ObservableList<Product> productSearchResults = Inventory.searchProduct(products,
						searchTextbox.getText());
				// if the user press down arrow on results
				if (event.getCode() == KeyCode.DOWN) {
					searchPopUp.requestFocus();
					searchPopUp.getSelectionModel().select(0);
				}
				searchPopUp.setVisible(true);
				searchPopUp.setItems(productSearchResults);
			}
		}
	}

	@FXML
	private void clearSearchBox() {
		searchTextbox.setText("");
		quantityTextbox.setDisable(true);
		addButton.setDisable(true);
		searchPopUp.setVisible(false);
		searchTextbox.requestFocus();
	}

	@FXML
	private void enterQuantity(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			addItem();
		} else {
			if (quantityTextbox.getText().trim().equals("")) {
				quantityTextbox.setText("1");
			}
			try {
				if (Double.parseDouble(quantityTextbox.getText()) <= 0) {
					quantityTextbox.setText("1");
				}
			} catch (NumberFormatException e) {
				Helpers.filterTextfieldToNumbers(quantityTextbox);
			}
		}
	}

	/* Key release event handler for product search popup */
	@FXML
	private void productListView_KeyPress(KeyEvent event) {
		if (event.getCode() == KeyCode.ESCAPE) {
			searchPopUp.setItems(null);
			searchPopUp.setVisible(false);
			searchTextbox.requestFocus();
		} else if (event.getCode() == KeyCode.ENTER) {
			selectFromListView();
		}
	}

	private void selectFromListView() {
		searchTextbox.setText(searchPopUp.getSelectionModel().getSelectedItem().toString());
		searchPopUp.setVisible(false);
		String splitter[] = searchTextbox.getText().split(" ");
		selectedproductCode = Integer.parseInt(splitter[0].trim());
		addButton.setDisable(false);
		quantityTextbox.setDisable(false);
		quantityTextbox.requestFocus();
	}

	/* Click event handler for product search popup */
	@FXML
	private void productSelect() {
		selectFromListView();
	}

	public void setMainApp(MainPOSApp mainApp) {
		this.posApp = mainApp;
	}

	@FXML
	private void addItem() {
		ObservableList<Product> productGetter = Inventory.searchProduct(products,
				Integer.toString(selectedproductCode));
		Product product = productGetter.get(0);

		product.setSubQuantity(Integer.parseInt(quantityTextbox.getText()));
		product.setSubTotal(product.getPriceForSales() * Double.parseDouble(quantityTextbox.getText()));

		productsForTable.add(product);

		searchTextbox.setText("");
		quantityTextbox.setText("1");
		searchTextbox.requestFocus();
		addButton.setDisable(true);
		deleteButton.setDisable(false);
		proceedButton.setDisable(false);
		refreshList();

		searchTextbox.requestFocus();
		quantityTextbox.setDisable(true);
	}

	@FXML
	private void deleteItem() {
		Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
		if (selectedProduct != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.initOwner(posApp.getPrimaryStage());
			alert.setTitle("Confirmation");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to delete this item?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				productsForTable.remove(selectedProduct);
				refreshList();
				if (productsForTable.size() == 0) {
					deleteButton.setDisable(true);
					proceedButton.setDisable(true);
				}

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
	private void handleBack() {
		Alert alert = new Alert(AlertType.WARNING, "", ButtonType.YES, ButtonType.NO);
		alert.initOwner(posApp.getPrimaryStage());
		alert.setTitle("Confirmation");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to go back?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.YES)
			posApp.showMainMenu();
	}

	@FXML
	private void paymentClicked() throws FileNotFoundException {
		paymentDialog.setVisible(false);
		mainPane.setDisable(false);
		Transaction sales = new Sales(transNumber, dateLabel.getText(), customerLabel.getText(),
				Double.parseDouble(paymentText.getText()), superTotal, productsForTable.size(), productsForTable);
		transactionNumber.textProperty().setValue(String.valueOf(++transNumber));

		if (DatabaseController.saveTransaction(sales, TransactionType.RETAIL)) {
			dbSuccessDialog();
		} else {
			dbFailedDialog();
		}
		productsForTable = FXCollections.observableArrayList();
		superTotalLabel.setText("Php0.00");
		refreshList();

	}

	private void dbSuccessDialog() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(posApp.getPrimaryStage());
		alert.setTitle("Sales Transaction");
		alert.setHeaderText("Transaction Completed.");
		alert.setContentText("");
		alert.showAndWait();
	}

	private void dbFailedDialog() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.initOwner(posApp.getPrimaryStage());
		alert.setTitle("Sales Transaction");
		alert.setHeaderText("Transaction Failed!");
		alert.setContentText("Error on database.");
		alert.showAndWait();
	}

	@FXML
	private void paymentCancelClicked() {
		paymentDialog.setVisible(false);
		mainPane.setDisable(false);
	}

	@FXML
	private void processClicked() {
		mainPane.setDisable(true);
		paymentDialog.setVisible(true);
		paymentText.setText(Double.toString(superTotal));
		changeLabel.setText(Inventory.formatDoubleToCurrencyString(Double.parseDouble(paymentText.getText()) - superTotal));
		paymentOKButton.setDisable(false);
	}

	@FXML
	public void refreshList() {
		dateLabel.textProperty().setValue(dateFormat.format(new Date()));
		superTotal = 0;
		productsTable.setItems(productsForTable);

		productCode.setCellValueFactory(cellData -> cellData.getValue().productCodeProperty());
		productName.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
		productDescription.setCellValueFactory(cellData -> cellData.getValue().productDescriptionProperty());
		priceForSales.setCellValueFactory(cellData -> cellData.getValue().priceForSalesProperty());
		productQuantity.setCellValueFactory(cellData -> cellData.getValue().productSubQuantityProperty());
		productUnit.setCellValueFactory(cellData -> cellData.getValue().productUnitProperty());
		subtotalAmount.setCellValueFactory(cellData -> cellData.getValue().productSubTotalProperty());

		if (productsForTable != null && productsForTable.size() > 0) {
			for (int i = 0; i < productsForTable.size(); i++) {
				superTotal += productsForTable.get(i).getSubTotal();
			}
			superTotalLabel.setText(Inventory.formatDoubleToCurrencyString(superTotal));
		} else {
			superTotalLabel.setText("Php0.00");
		}
	}

	@FXML
	private Pane mainPane;

	@FXML
	private DialogPane paymentDialog;

	@FXML
	private TextField paymentText;

	@FXML
	private Label changeLabel;

	@FXML
	private TableView<Product> productsTable;

	@FXML
	private TableColumn<Product, Number> productCode;

	@FXML
	private TableColumn<Product, String> productName;

	@FXML
	private TableColumn<Product, String> productDescription;

	@FXML
	private TableColumn<Product, Number> priceForSales;

	@FXML
	private TableColumn<Product, Number> productQuantity;

	@FXML
	private TableColumn<Product, String> productUnit;

	@FXML
	private TableColumn<Product, Number> subtotalAmount;

	@FXML
	private Label dateLabel;

	@FXML
	private Label transactionNumber;

	@FXML
	private TextField searchTextbox;

	@FXML
	private ListView<Product> searchPopUp;

	@FXML
	private TextField quantityTextbox;

	@FXML
	private Label superTotalLabel;

	@FXML
	private TextField customerLabel;

	@FXML
	private Button addButton;

	@FXML
	private Button deleteButton;

	@FXML
	private Button proceedButton;

	@FXML
	private Button paymentOKButton;
}