package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class SettingsController implements Initializable  {
	
	private final Integer DEFAULT_U2_PORT = 31438;
	private final String value_style = "-fx-text-fill: white;-fx-font-style: normal;-fx-prompt-text-fill: white";
	private final String DEFAULT_HOST_TEXT = "Type database host ...";
	private final String DEFAULT_PORT_TEXT = "Type port ...";
	private final String DEFAULT_MV_ACCOUNT_TEXT = "Specify account name ...";
	private final String DEFAULT_R_DBNAME_TEXT = "Type database name ...";
	
	private final String DEFAULT_USERNAME_TEXT = "Username ...";
	private final String DEFAULT_PSWD_TEXT = "Password ...";
	
	@FXML
	private Button btn_OK;
	
	@FXML
	private TextField txt_mv_port;
	@FXML
	private TextField txt_r_port;
	
	@FXML
	private TextField txt_mv_host;
	@FXML
	private TextField txt_r_host;
	
	@FXML
	private TextField txt_mv_account;
	@FXML
	private TextField txt_r_dbname;
	
	@FXML
	private TextField txt_mv_user;
	@FXML
	private TextField txt_r_user;
	
	@FXML
	private PasswordField txt_mv_pswd;
	@FXML
	private PasswordField txt_r_pswd;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btn_OK.disableProperty().set(true);

		txt_mv_port.setText(DEFAULT_U2_PORT.toString());
		txt_mv_port.setStyle(value_style);
		
		setDefaultValuesForTexts();
		
		setFocusListeners();
	}
	
	@FXML
	private void btn_CancelClicked(){		
		Stage stage = (Stage)btn_OK.getScene().getWindow();
		stage.close();
	}	
	
	@FXML
	private void btn_OkClicked(){
		// Verify that fields are filled with correct values
		// Save the settings
		
		
		// TODO: переделать
		Stage stage = (Stage)btn_OK.getScene().getWindow();
		stage.close();
	}
	
	private void setDefaultValuesForTexts(){
		// Relational side
		txt_r_host.setText(DEFAULT_HOST_TEXT);
		txt_r_port.setText(DEFAULT_PORT_TEXT);
		txt_r_dbname.setText(DEFAULT_R_DBNAME_TEXT);
		txt_r_user.setText(DEFAULT_USERNAME_TEXT);
		txt_r_pswd.setPromptText(DEFAULT_PSWD_TEXT);
		
		//MultiValue side
		txt_mv_host.setText(DEFAULT_HOST_TEXT);
		txt_mv_account.setText(DEFAULT_MV_ACCOUNT_TEXT);
		txt_mv_user.setText(DEFAULT_USERNAME_TEXT);
		txt_mv_pswd.setPromptText(DEFAULT_PSWD_TEXT);
	}
	
	private void setFocusListeners(){
		txt_mv_host.focusedProperty().addListener(new TextFieldFocusListener(txt_mv_host, DEFAULT_HOST_TEXT));
		txt_r_host.focusedProperty().addListener(new TextFieldFocusListener(txt_r_host, DEFAULT_HOST_TEXT));
		txt_mv_port.focusedProperty().addListener(new TextFieldFocusListener(txt_mv_port, DEFAULT_PORT_TEXT));
		txt_r_port.focusedProperty().addListener(new TextFieldFocusListener(txt_r_port, DEFAULT_PORT_TEXT));
		txt_mv_account.focusedProperty().addListener(new TextFieldFocusListener(txt_mv_account, DEFAULT_MV_ACCOUNT_TEXT));
		txt_r_dbname.focusedProperty().addListener(new TextFieldFocusListener(txt_r_dbname, DEFAULT_R_DBNAME_TEXT));
		txt_mv_user.focusedProperty().addListener(new TextFieldFocusListener(txt_mv_user, DEFAULT_USERNAME_TEXT));
		txt_r_user.focusedProperty().addListener(new TextFieldFocusListener(txt_r_user, DEFAULT_USERNAME_TEXT));
		txt_mv_pswd.focusedProperty().addListener(new PasswordFieldFocusListener(txt_mv_pswd, DEFAULT_PSWD_TEXT));
		txt_r_pswd.focusedProperty().addListener(new PasswordFieldFocusListener(txt_r_pswd, DEFAULT_PSWD_TEXT));
	}
	
	@FXML
	private void txt_keyTyped(){
		if ( !txt_mv_host.getText().equals(DEFAULT_HOST_TEXT) 
				&& !txt_mv_account.getText().equals(DEFAULT_MV_ACCOUNT_TEXT) 
				&& !txt_mv_port.getText().equals(DEFAULT_PORT_TEXT)
				&& !txt_mv_user.getText().equals(DEFAULT_USERNAME_TEXT) 
				&& !txt_mv_pswd.getText().isEmpty()
				&& !txt_r_host.getText().equals(DEFAULT_HOST_TEXT)
				&& !txt_r_port.getText().equals(DEFAULT_PORT_TEXT)
				&& !txt_r_dbname.getText().equals(DEFAULT_R_DBNAME_TEXT)
				&& !txt_r_user.getText().equals(DEFAULT_USERNAME_TEXT) 
				&& !txt_r_pswd.getText().isEmpty()
				) 
			btn_OK.disableProperty().set(false);
		else
			btn_OK.disableProperty().set(true);
	}
	
	
}
