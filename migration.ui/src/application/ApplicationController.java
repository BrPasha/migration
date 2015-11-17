package application;

import editors.database.RDBEditor;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import migration.core.db.relational.ProviderException;
import migration.core.db.relational.impl.mysql.MySqlDatabaseClient;

public class ApplicationController {
    
    private RDBEditor m_rdbEditor;
    
    @FXML
    private TabPane rdbTabPane;
    
    @FXML
    private void initialize() {
        String dbName = "cm_core_trunk";
        //MySqlDatabaseClient client = new MySqlDatabaseClient("che-l-im01", 3306, dbName, "mysql", "mysql");
        MySqlDatabaseClient client = new MySqlDatabaseClient("localhost", 3306, dbName, "root", "password");
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
            
            ScrollPane scrollPane = new ScrollPane();
            aPane.getChildren().add(scrollPane);
            rdbTabPane.getTabs().add(newTab);
            AnchorPane.setBottomAnchor(scrollPane, 0.0);
            AnchorPane.setLeftAnchor(scrollPane, 0.0);
            AnchorPane.setRightAnchor(scrollPane, 0.0);
            AnchorPane.setTopAnchor(scrollPane, 0.0);
            
            AnchorPane aPane1 = new AnchorPane();
            scrollPane.setContent(aPane1);
            
            Pane pane = new Pane();
            aPane1.getChildren().add(pane);
            
            AnchorPane.setBottomAnchor(scrollPane, 0.0);
            AnchorPane.setLeftAnchor(scrollPane, 0.0);
            AnchorPane.setRightAnchor(scrollPane, 0.0);
            AnchorPane.setTopAnchor(scrollPane, 0.0);
            
            pane.setMinSize(200, 200);
            return pane;
        }
        return null;
    }
}
