package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import editors.database.MVEditor;
import editors.database.RDBEditor;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import migration.core.db.relational.ProviderException;
import migration.core.db.relational.impl.mysql.MySqlDatabaseClient;
import migration.core.model.mv.MVFile;
import migration.core.model.rdb.RDBRelation;
import migration.core.model.rdb.RDBStructure;
import migration.core.model.rdb.RDBTable;
import migration.core.model.transfer.Transfer;

public class ApplicationController {
    
    private RDBEditor m_rdbEditor;
    private List<MVEditor> m_mvEditors = new ArrayList<MVEditor>();
    
    private ChangeListener<Number> resizeListenerDB;
    
    private ChangeListener<Number> resizeListenerMV;
    
    private static int MAX_NUMBER_OF_VARIANTS = 4;
    
    private Stage m_stage;
    
    private DatabasesSettings dbSettings = new DatabasesSettings();
    
    @FXML
    private TabPane rdbTabPane;
    
    @FXML
    private TabPane mvTabPane;
    
    @FXML
	private Button btn_Settings;
	
	@FXML
	private Button btn_Info;
	
	@FXML
	private Button btn_Export;
    
    @FXML
    private void initialize() {
        resizeListenerDB = new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                if (m_rdbEditor != null){
                    m_rdbEditor.relayout(newValue.doubleValue());
                }
            }
        };
        rdbTabPane.widthProperty().addListener(resizeListenerDB);
        rdbTabPane.heightProperty().addListener(resizeListenerDB);
        
        resizeListenerMV = new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                for(MVEditor editor : m_mvEditors){
                    editor.relayout(newValue.doubleValue());
                }
            }
        };
        
        mvTabPane.widthProperty().addListener(resizeListenerMV);
        mvTabPane.heightProperty().addListener(resizeListenerMV);
    }
    
    public void setStage(Stage stage){
        this.m_stage = stage;
    }
    
    public void showTables() throws ProviderException{
        String dbName = dbSettings.getRDBName();
        final MySqlDatabaseClient client = new MySqlDatabaseClient(dbSettings.getRHost(), dbSettings.getRPort(), 
        		dbName, dbSettings.getRUser(), dbSettings.getRPsw());
        
        final List<RDBTable> tabels = client.getTables();
        final List<RDBRelation> relations = client.getRelations();
        
        Platform.runLater(new Runnable()
        {   
            @Override
            public void run()
            {
                Pane pane = addNewTab(dbName, rdbTabPane);
                m_rdbEditor = new RDBEditor(pane);
                m_rdbEditor.setStructure(new RDBStructure(tabels, relations));
                m_rdbEditor.layout();
                m_rdbEditor.relayout(rdbTabPane.getWidth());
            }
        });
    }
    
    private Pane addNewTab(String name, TabPane tab){
        if (tab != null){
            Tab newTab = new Tab(name);
            AnchorPane aPane = new AnchorPane();
            newTab.setContent(aPane);
            
            ScrollPane scrollPane = new ScrollPane();
            aPane.getChildren().add(scrollPane);
            tab.getTabs().add(newTab);
            AnchorPane.setBottomAnchor(scrollPane, 0.0);
            AnchorPane.setLeftAnchor(scrollPane, 0.0);
            AnchorPane.setRightAnchor(scrollPane, 0.0);
            AnchorPane.setTopAnchor(scrollPane, 0.0);
            
            AnchorPane aPane1 = new AnchorPane();
            scrollPane.setContent(aPane1);
            
            scrollPane.setStyle("-fx-background:#0A2447; -fx-border-color:#0A2447");
            
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
			stage.getIcons().add(new Image("file:icon/Rocket25_black.png"));
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
    
    @FXML
    private void onActionMigrate(ActionEvent e){
        Task<Void> task = new Task<Void>()
        {
            @Override
            protected Void call()
                throws Exception
            {
                RDBStructure structure = m_rdbEditor.getStructure();
                List<Set<Transfer>> transformations = Transfer.proposeTransformations(structure);
                
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        m_mvEditors.clear();
                        mvTabPane.getTabs().clear();
                        for(int i = 0; i <  MAX_NUMBER_OF_VARIANTS && i <  transformations.size(); i++){
                            Set<Transfer> variant = transformations.get(i);
                            List<MVFile> files = new ArrayList<MVFile>();
                            for (Transfer transfer : variant){
                                files.add(transfer.constructMVFile());
                            }
                            Pane pane = addNewTab("Variant" + Integer.toString(i+1), mvTabPane);
                            MVEditor editor = new MVEditor(pane);
                            editor.setData(files);
                            m_mvEditors.add(editor);
                            editor.layout();
                        }
                        for (MVEditor editor : m_mvEditors){
                            editor.relayout(mvTabPane.getWidth());
                        }
                    }
                });
                return null;
            }
        };
        ProgressForm.showProgress(task, m_stage);
    }
}
