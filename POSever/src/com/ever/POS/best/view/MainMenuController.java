package com.ever.POS.best.view;

import java.io.IOException;
import java.util.Optional;

import com.ever.POS.best.MainPOSApp;
import com.ever.POS.best.controller.DatabaseController;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;

public class MainMenuController {

	public MainMenuController() {

	}

	private MainPOSApp posApp;

	public void setMainApp(MainPOSApp mainApp) {
		this.posApp = mainApp;

	}

	@FXML
	private Label charmsClock;

	@FXML
	private void handleInventory() {
		posApp.showInventoryScreen();
	}

	@FXML
	private void handlePurchase() {
		posApp.showPurchaseScreen();
	}

	@FXML
	private void handleSales() {
		posApp.showSalesScreen();
	}

	@FXML
	private void handleReports() {
		posApp.showTransactionReportScreen();
	}


	@FXML
	private void handleExit() throws IOException {
		DatabaseController.saveProductInventory(posApp.getAllProducts());
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setGraphic(new ImageView(this.getClass().getResource("EmployeeBuddy.png").toString()));
		alert.setTitle("Exit Program");
		alert.setHeaderText("You are exiting the program...");
		alert.setContentText("Are you ok with this?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			Platform.exit();
		}
	}

	// @FXML
	// private void initialize() {
	// charmsClock.setText();
	// }

}
