package com.ever.POS.best.view;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

import com.ever.POS.best.MainPOSApp;
import com.ever.POS.best.controller.DatabaseController;
import com.ever.POS.best.controller.Inventory;
import com.ever.POS.best.model.Product;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

public class SalesScreenController {

	private MainPOSApp posApp;
	private static Calendar cal = Calendar.getInstance();
	static DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	DateFormat hoursFormat = new SimpleDateFormat("hh:mm");
	DateFormat hours = new SimpleDateFormat("HH");
	private static double superTotal;
	private static int selectedProductId;
	private String meridiem;
	private static String dateToday = dateFormat.format(cal.getTime());
	private String hourToday = hoursFormat.format(cal.getTime());
	private static ObservableList<Transaction> salesList = FXCollections.observableArrayList();
	private ObservableList<Product> productGetter = FXCollections.observableArrayList();
	private ObservableList<Product> productsForTable = FXCollections.observableArrayList();
	private ObservableList<Product> products = FXCollections.observableArrayList();

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
	private TableColumn<Product, Number> productID;

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

	public SalesScreenController() {

	}

	@FXML
	private void initialize() throws FileNotFoundException {
		if (Integer.parseInt(hours.format(cal.getTime())) > 12)
			meridiem = "PM";
		else {
			meridiem = "PM";
		}
		dateLabel.textProperty().setValue(dateToday + " " + hourToday + meridiem);
		salesList = FXCollections.observableArrayList(DatabaseController.openSalesDatabase());
		if (salesList == null)
			transactionNumber.setText("1");
		else
			transactionNumber.setText(Integer.toString(salesList.size() + 1));

		quantityTextbox.textProperty().setValue("1");
		customerLabel.setText("Cash");
		searchTextbox.requestFocus();
	}

	@FXML
	private void searchBoxClicked() {
		quantityTextbox.textProperty().setValue("1");
	}

	@FXML
	private void checkPaymentAmount() {
		try {
			if (Double.parseDouble(paymentText.getText()) >= superTotal) {
				paymentOKButton.setDisable(false);
				changeLabel.setText("P " + Double.toString(Double.parseDouble(paymentText.getText()) - superTotal));
			} else {
				paymentOKButton.setDisable(true);
				changeLabel.setText("P 0.0");
			}
		} catch (NumberFormatException e) {
			if (paymentText.getText() != "" || paymentText.getText() != " ") {
				paymentText.setText(paymentText.getText().substring(0, paymentText.getText().length() - 1));
			}
		}
		paymentText.positionCaret(paymentText.getText().length());
	}

	@FXML
	private void positionToLast() {
		paymentText.positionCaret(paymentText.getText().length());
	}

	@FXML
	private void productSearchEvent() throws FileNotFoundException {
		if (searchTextbox.getText().trim().equals("")) {
			searchTextbox.setText("");
			products = Inventory.searchProduct(posApp.getAllProducts(), "1NVALID");
			searchPopUp.setItems(products);
			searchPopUp.setVisible(false);
			addButton.setDisable(true);
		} else {

			if (searchTextbox.getText() != "") {
				products = Inventory.searchProduct(posApp.getAllProducts(), searchTextbox.getText());
				searchPopUp.setItems(products);
				searchPopUp.setVisible(true);
			} else
				// searchPopUp.getItems().removeAll(products);
				products = null;
		}
	}

	@FXML
	private void productSelect() {
		searchTextbox.setText(searchPopUp.getSelectionModel().getSelectedItem().toString());
		searchPopUp.setVisible(false);
		searchTextbox.requestFocus();
		String splitter[] = searchTextbox.getText().split(" ");
		selectedProductId = Integer.parseInt(splitter[0].trim());
		addButton.setDisable(false);
	}

	public void setMainApp(MainPOSApp mainApp) {
		this.posApp = mainApp;
	}

	@FXML
	private void addItem() {
		productGetter = Inventory.searchProduct(posApp.getAllProducts(), Integer.toString(selectedProductId));

		try {
			Integer.parseInt(quantityTextbox.getText());
		} catch (Exception ex) {
			quantityTextbox.setText("1");
		}

		productsForTable.add(new Product(productGetter.get(0).getProductId(), productGetter.get(0).getProductName(),
				productGetter.get(0).getProductUnit(), productGetter.get(0).getProductDescription(),
				productGetter.get(0).getPriceForPurchase(), productGetter.get(0).getPriceForSales(),
				productGetter.get(0).getStockQuantity()));

		productsForTable.get(productsForTable.size() - 1).setSubQuantity(Integer.parseInt(quantityTextbox.getText()));
		productsForTable.get(productsForTable.size() - 1)
				.setSubTotal(productsForTable.get(productsForTable.size() - 1).getPriceForSales()
						* Double.parseDouble(quantityTextbox.getText()));

		searchTextbox.setText("");
		quantityTextbox.setText("1");
		searchTextbox.requestFocus();
		addButton.setDisable(true);
		deleteButton.setDisable(false);
		proceedButton.setDisable(false);
		refreshList();
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
		posApp.showMainMenu();
	}

	@FXML
	private void paymentClicked() throws FileNotFoundException {
		paymentDialog.setVisible(false);
		mainPane.setDisable(false);
		salesList.add(new Transaction(Integer.parseInt(transactionNumber.getText()), dateLabel.getText(),
				customerLabel.getText(), Double.parseDouble(paymentText.getText()), superTotal, productsForTable.size(),
				productsForTable));

		products = posApp.getAllProducts();
		for (int j = 0; j < productsForTable.size(); j++) {
			for (int i = 0; i < products.size(); i++) {
				if (products.get(i).getProductId() == productsForTable.get(j).getProductId()) {
					products.get(i).setStockQuantity(
							products.get(i).getStockQuantity() - productsForTable.get(j).getSubQuantity());
				}
			}
		}
		DatabaseController.saveProductInventory(products);
		productsForTable = null;
		superTotalLabel.setText("P 0.00");
		transactionNumber.setText(Integer.toString(salesList.size() + 1));
		DatabaseController.saveSalesTransactions(salesList);
		refreshList();
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
		changeLabel.setText("P " + Double.toString(Double.parseDouble(paymentText.getText()) - superTotal));
		paymentOKButton.setDisable(false);
	}

	@FXML
	public void refreshList() {
		if (Integer.parseInt(hours.format(cal.getTime())) > 12)
			meridiem = "PM";
		else {
			meridiem = "PM";
		}
		dateLabel.textProperty().setValue(dateToday + " " + hourToday + meridiem);
		superTotal = 0;
		productsTable.setItems(productsForTable);
		productID.setCellValueFactory(cellData -> cellData.getValue().productIDProperty());
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
			superTotalLabel.setText("P " + superTotal);
		} else {
			superTotalLabel.setText("P 0.00");
		}
	}
}