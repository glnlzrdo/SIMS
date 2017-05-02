package com.ever.POS.best.view;

import java.util.Optional;

import com.ever.POS.best.MainPOSApp;
import com.ever.POS.best.controller.DatabaseController;
import com.ever.POS.best.enums.ScreenMenu;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
	private void handleInventory() {
		posApp.generateAnchorPaneScreen("view/InventoryScreen.fxml", ScreenMenu.INVENTORY);
	}

	@FXML
	private void handlePurchase() {
		posApp.generateAnchorPaneScreen("view/PurchaseScreen.fxml", ScreenMenu.PURCHASE);
	}

	@FXML
	private void handleSales() {
		posApp.generateAnchorPaneScreen("view/SalesScreen.fxml", ScreenMenu.SALES);
	}

	@FXML
	private void handleReports() {
		posApp.generateAnchorPaneScreen("view/TransactionReportScreen.fxml", ScreenMenu.REPORTS);
	}

	@FXML
	private void handleExit() {
		Alert alert = new Alert(AlertType.CONFIRMATION, "",
	            ButtonType.YES, ButtonType.NO);
		alert.setGraphic(new ImageView(this.getClass().getResource("EmployeeBuddy.png").toString()));
		alert.initOwner(posApp.getPrimaryStage());
		alert.setTitle("Exit Program");
		alert.setHeaderText("You are leaving the program...");
		alert.setContentText("Are you sure with this?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.YES) {
			DatabaseController.dbClose();
			Platform.exit();
		}
	}


}
