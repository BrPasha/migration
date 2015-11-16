package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class PasswordFieldFocusListener implements ChangeListener<Boolean>{

	private final TextField textField ;
	private final String default_txt;
	private final String value_style = "-fx-text-fill: white;-fx-font-style: normal;-fx-prompt-text-fill: white";
	private final String comment_style = "-fx-font-style: italic; -fx-prompt-text-fill: #c8c8c8;-fx-text-fill: #c8c8c8;";
	
	
	PasswordFieldFocusListener(TextField textField, String defaultText){
		this.textField = textField ;
        this.default_txt = defaultText;
	}
	
	@Override
	public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		if(!newValue)    // check if focus gained or lost
        {
			if (textField.getText().isEmpty()) {
				textField.setPromptText(default_txt);
				textField.setStyle(comment_style);
			}
        }
		else
			if (!textField.getPromptText().isEmpty()){
				textField.setPromptText("");
				textField.setStyle(value_style);
			}
		
	}

}
