package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class TextFieldFocusListener implements ChangeListener<Boolean>{

	private final TextField textField ;
	private final String default_txt;
	private final String comment_style = "-fx-text-fill: #c8c8c8;-fx-font-style: italic;";
	private final String value_style = "-fx-text-fill: white;-fx-font-style: normal;";
	
	TextFieldFocusListener(TextField textField, String defaultText) {
        this.textField = textField ;
        this.default_txt = defaultText;
        this.textField.setStyle(value_style);
      }
	
	@Override
	public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		if(!newValue)    // check if focus gained or lost
        {
			if (textField.getText().isEmpty()) {
				textField.setText(default_txt);
				textField.setStyle(comment_style);
			}
        }
		else
			if (!textField.getText().isEmpty())
				if (textField.getText().equals(default_txt)){
					textField.setText("");
					textField.setStyle(value_style);
				}
	}

}
