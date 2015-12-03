package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SuccessDialogController implements Initializable{

	private Stage m_stage;
	
	public void setStage(Stage stage){
        this.m_stage = stage;
    }
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	private void btn_OkClicked(){
		m_stage.close();
	}
}
