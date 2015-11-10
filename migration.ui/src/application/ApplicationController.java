package application;

import editors.database.RDBEditor;
import javafx.fxml.FXML;
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
        String dbName = "sakila";
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
}
