package com.ever.POS.best.controller;

import javafx.scene.control.TextField;

public class Helpers {
	public static void filterTextfieldToNumbers (TextField textField){
		int carPos = textField.getCaretPosition();
		textField.setText(textField.getText().replaceAll("[^\\d]", ""));
		textField.positionCaret(carPos - 1);
	}
}
