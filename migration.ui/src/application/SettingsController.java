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
	
	private final String DEFAULT_HOST_TEXT = "Type database host ...";
	private final String DEFAULT_PORT_TEXT = "Type port ...";
	private final String DEFAULT_MV_ACCOUNT_TEXT = "Specify account name ...";
	private final String DEFAULT_R_DBNAME_TEXT = "Type database name ...";
	
	private final String DEFAULT_USERNAME_TEXT = "Username ...";
	private final String DEFAULT_PSWD_TEXT = "Password ...";
	
	private DatabasesSettings dbSettings = new DatabasesSettings();
	
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
	
	private Stage m_stage;
	
	public void setStage(Stage stage){
        this.m_stage = stage;
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeDefaultValues();
		
		setFocusListeners();
	}
	
	@FXML
	private void btn_CancelClicked(){
		m_stage.close();
	}	
	
	@FXML
	private void btn_OkClicked(){
		// Verify that fields are filled with correct values		
		
		// Save the settings
		dbSettings.setRDBName(txt_r_dbname.getText());
		dbSettings.setRHost(txt_r_host.getText());
		dbSettings.setRPort(Integer.parseInt(txt_r_port.getText()));
		dbSettings.setRUser(txt_r_user.getText());
		dbSettings.setRPsw(txt_r_pswd.getText());
		
		dbSettings.setMVHost(txt_mv_host.getText());
		dbSettings.setMVAccount(txt_mv_account.getText());
		dbSettings.setMVPort(Integer.parseInt(txt_mv_port.getText()));
		dbSettings.setMVUser(txt_mv_user.getText());
		dbSettings.setMVPsw(txt_mv_pswd.getText());
		
		m_stage.close();
	}
	
	private void initializeDefaultValues(){
		txt_r_host.setText(dbSettings.getRHost());
		txt_r_port.setText(dbSettings.getRPort().toString());
		txt_r_dbname.setText(dbSettings.getRDBName());
		txt_r_user.setText(dbSettings.getRUser());
		txt_r_pswd.setText(dbSettings.getRPsw());
		
		txt_mv_host.setText(dbSettings.getMVHost());
		txt_mv_port.setText(dbSettings.getMVPort().toString());
		txt_mv_account.setText(dbSettings.getMVAccount());
		txt_mv_user.setText(dbSettings.getMVUser());
		txt_mv_pswd.setText(dbSettings.getMVPsw());
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
