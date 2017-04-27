package com.ever.POS.best;

import java.io.IOException;
import java.util.Optional;

import com.ever.POS.best.model.Product;
import com.ever.POS.best.controller.*;
import com.ever.POS.best.enums.Screen;
import com.ever.POS.best.view.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainPOSApp extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;

	private ObservableList<Product> product = FXCollections.observableArrayList();

	public MainPOSApp() {
		product = FXCollections.observableArrayList(DatabaseController.openInventoryDatabase());
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Sales & Inventory Management System");
		this.primaryStage.setResizable(false);
		this.primaryStage.getIcons().add(new Image(this.getClass().getResource("EmployeeBuddy.png").toString()));
		this.primaryStage.setOnCloseRequest(event -> {
			Alert alert = new Alert(AlertType.CONFIRMATION, "",
		            ButtonType.YES, ButtonType.NO);
			alert.setGraphic(new ImageView(this.getClass().getResource("EmployeeBuddy.png").toString()));
			alert.setTitle("Exit Program");
			alert.initOwner(this.getPrimaryStage());
			alert.setHeaderText("You are leaving the program...");
			alert.setContentText("Are you sure with this?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.YES) {
				DatabaseController.dbClose();
				Platform.exit();
			} else
				event.consume();
		});
		initRootLayout();
		showMainMenu();
	}

	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainPOSApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			Object controller = (MainMenuController) loader.getController();
			((MainMenuController) controller).setMainApp(this);
			// Screen screen = Screen.getPrimary();
			// Rectangle2D bounds = screen.getVisualBounds();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			// primaryStage.setX(bounds.getMinX());
			// primaryStage.setY(bounds.getMinY());
			// primaryStage.setWidth(bounds.getWidth());
			// primaryStage.setHeight(bounds.getHeight());
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showMainMenu() {
		generateAnchorPaneScreen("view/MainMenu.fxml", Screen.MAIN);
	}

	public void generateAnchorPaneScreen(String resource, Screen screen) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainPOSApp.class.getResource(resource));
			AnchorPane mainScreen = (AnchorPane) loader.load();

			rootLayout.setCenter(mainScreen);
			Object controller;
			switch (screen) {
			case MAIN:
				controller = (MainMenuController) loader.getController();
				((MainMenuController) controller).setMainApp(this);
				break;
			case INVENTORY:
				controller = (InventoryScreenController) loader.getController();
				((InventoryScreenController) controller).setMainApp(this);
				break;
			case PURCHASE:
				controller = (PurchaseScreenController) loader.getController();
				((PurchaseScreenController) controller).setMainApp(this);
				break;
			case SALES:
				controller = (SalesScreenController) loader.getController();
				((SalesScreenController) controller).setMainApp(this);
				break;
			case REPORTS:
				controller = (TransactionReportScreenController) loader.getController();
				((TransactionReportScreenController) controller).setMainApp(this);
				break;
			default:
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ObservableList<Product> getAllProducts() {
		return product;
	}

	public void showProductAddDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainPOSApp.class.getResource("view/AddEditProductScreen.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Add Product");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			AddEditProductScreenController controller = loader.getController();
			controller.setDialogStage(dialogStage);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showProductEditDialog(Product product) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainPOSApp.class.getResource("view/AddEditProductScreen.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Product");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the product into the controller.
			AddEditProductScreenController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setProduct(product);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
