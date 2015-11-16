package application;

import editors.database.RDBEditor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import migration.core.db.relational.ProviderException;
import migration.core.db.relational.impl.mysql.MySqlDatabaseClient;

public class ApplicationController {
    
    private RDBEditor m_rdbEditor;
    
    @FXML
    private TabPane rdbTabPane;
    
    @FXML
	private Button btn_Settings;
	
	@FXML
	private Button btn_Info;
	
	@FXML
	private Button btn_Export;
    
    @FXML
    private void initialize() {
        String dbName = "sakila".toUpperCase();
        
        MySqlDatabaseClient client = new MySqlDatabaseClient("che-l-im01", 3306, dbName, "mysql", "mysql");
        Pane pane = addNewDBTab(dbName);
        m_rdbEditor = new RDBEditor(pane);
        try
        {
            m_rdbEditor.addTables(client.getTables());
        }
        catch (ProviderException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void showTables(){
        m_rdbEditor.relayout();
    }
    
    private Pane addNewDBTab(String name){
        if (rdbTabPane != null){
            Tab newTab = new Tab(name);
            AnchorPane aPane = new AnchorPane();
            newTab.setContent(aPane);
            Pane pane = new Pane();
            aPane.getChildren().add(pane);
            
            rdbTabPane.getTabs().add(newTab);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);
            AnchorPane.setTopAnchor(pane, 0.0);
            return pane;
        }
        return null;
    }
    
    @FXML
	private void btn_Settings_clicked(){
    	try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Settings.fxml"));
			BorderPane root = (BorderPane)fxmlLoader.load();
    		Scene scene = new Scene(root, 800, 600);
			scene.getStylesheets().add(getClass().getResource("settings.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Settings");
			stage.show();
			
			/*
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Settings.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));  
            stage.show();
            */
            
    } catch(Exception e) {
       e.printStackTrace();
      }
    }
}
