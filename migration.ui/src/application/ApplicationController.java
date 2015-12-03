package application;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import editors.database.ISelectedListener;
import editors.database.JoiningController;
import editors.database.MVEditor;
import editors.database.RDBEditor;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import migration.core.db.multivalue.impl.uv.UniVerseDatabaseClient;
import migration.core.db.relational.ProviderException;
import migration.core.db.relational.impl.mysql.MySqlDatabaseClient;
import migration.core.model.mv.MVFile;
import migration.core.model.rdb.RDBRelation;
import migration.core.model.rdb.RDBStructure;
import migration.core.model.rdb.RDBTable;
import migration.core.model.transfer.Data;
import migration.core.model.transfer.Plan;
import migration.core.model.transfer.Transfer;
import migration.core.util.TransferSet;

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
	private Label lbl_Factor;
	
	private List<Double> m_weights;
	private List<Integer> m_filesCount;
	private List<TransferSet> m_transfers;
    
    public void setStage(Stage stage){
        this.m_stage = stage;
    }
    
    public TabPane getRDBPane(){
    	return rdbTabPane;
    }
    
    public DatabasesSettings getDBSettings(){
    	return this.dbSettings;
    }
    
    public void showTables() throws ProviderException{
        String dbName = dbSettings.getRDBName();
        final MySqlDatabaseClient client = new MySqlDatabaseClient(dbSettings.getRHost(), dbSettings.getRPort(), 
        		dbName, dbSettings.getRUser(), dbSettings.getRPsw());
        /**/
        final List<RDBTable> tabels = client.getTables();
        final List<RDBRelation> relations = client.getRelations();
        
        Platform.runLater(new Runnable()
        {   
            @Override
            public void run()
            {
                ChangeListener<Number> resizeListenerDB = new ChangeListener<Number>()
                {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
                    {
                        if (m_rdbEditor != null){
                            m_rdbEditor.layout();
                            m_rdbEditor.relayout(newValue.doubleValue());
                        }
                    }
                };
                Pane pane = addNewTab("DATABASE NAME: " + dbName, rdbTabPane, resizeListenerDB);
                m_rdbEditor = new RDBEditor(pane);
                m_rdbEditor.setStructure(new RDBStructure(tabels, relations));
                m_rdbEditor.layout();
                m_rdbEditor.relayout(rdbTabPane.getWidth());
            }
        });
        
    }
    
    private Pane addNewTab(String name, TabPane tab, ChangeListener<Number> listener){
        if (tab != null){
        	//System.out.println("!!!! " + tab.getTabs().size());
        	//if (tab.getTabs().size() == 0) {
            Tab newTab = new Tab(name);
            AnchorPane aPane = new AnchorPane();
            newTab.setContent(aPane);
            
            aPane.widthProperty().addListener(listener);
            aPane.heightProperty().addListener(listener);
            
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
            /*}
        	
        	else {
        		tab.getTabs().clear();
        	}
        	*/
        }
        return null;
    }
    
    @FXML
    private void btn_Info_clicked(){
    	try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Joining.fxml"));
			BorderPane root = (BorderPane)fxmlLoader.load();
    		Scene scene = new Scene(root, 500, 350);
			scene.getStylesheets().add(getClass().getResource("joining.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Join types");
			stage.getIcons().add(new Image("file:icon/Rocket25_black.png"));
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(m_stage);
			((JoiningController)fxmlLoader.getController()).setStage(stage);
			stage.show();
    	}
    	catch(Exception e) {
    	       e.printStackTrace();
        }
    }
    
    @FXML
	public void btn_Settings_clicked(){
    	try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Settings.fxml"));
			//((SettingsController)fxmlLoader.getController()).setDBSettings(dbSettings);
			BorderPane root = (BorderPane)fxmlLoader.load();
    		Scene scene = new Scene(root, 800, 600);
			scene.getStylesheets().add(getClass().getResource("settings.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Settings");
			stage.getIcons().add(new Image("file:icon/Rocket25_black.png"));
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(m_stage);
			SettingsController settingsController = (SettingsController)fxmlLoader.getController();
			settingsController.setStage(stage);
			settingsController.setParentController(this);
			settingsController.initializeDefaultValues(dbSettings);
			settingsController.addSettingsListener(new SettingsListener() {
				@Override
				public void onApply(DatabasesSettings settings) {
					dbSettings = settings;
					submitShowTables();
				}
			});
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
    
    protected void submitShowTables() {
    	Task<Void> task = new Task<Void>()
        {
            @Override
            protected Void call()
                throws Exception
            {
                showTables();
                return null;
            }
        };
        ProgressForm.showProgress(task, m_stage);
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
                List<TransferSet> transformations = Transfer.proposeTransformations(structure);
                
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        m_mvEditors.clear();
                        mvTabPane.getTabs().clear();
                        
                        ISelectedListener listener = new ISelectedListener()
                        {
                            @Override
                            public void select(boolean selected, Object source, Object data)
                            {
                                
                            }

                            @Override
                            public void highlight(boolean highlighted, Object data)
                            {
                                m_rdbEditor.highlightTables((List<String>)data, highlighted);
                            }
                        };
                        m_weights = new ArrayList<>();
                        m_filesCount = new ArrayList<>();
                        m_transfers = new ArrayList<>();
                        for(int i = 0; i <  MAX_NUMBER_OF_VARIANTS && i <  transformations.size(); i++){
                            TransferSet variant = transformations.get(i);
                            m_transfers.add(variant);
                            m_weights.add(variant.getWeight(structure));
                            List<MVFile> files = new ArrayList<MVFile>();
                            for (Transfer transfer : variant){
                                files.add(transfer.constructMVFile());
                            }
                            m_filesCount.add(files.size());
                            if (i == 0) {
                            	lbl_Factor.setText(getInformation(i));
                            }
                            ChangeListener<Number> resizeListenerMV = new ChangeListener<Number>()
                            {
                                @Override
                                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
                                {
                                    for(MVEditor editor:m_mvEditors){
                                        editor.layout();
                                        editor.relayout(newValue.doubleValue());
                                    }
                                }
                            };
                            Pane pane = addNewTab("OPTION " + Integer.toString(i+1), mvTabPane, resizeListenerMV);
                            MVEditor editor = new MVEditor(pane, listener);
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
	
	private String getInformation(int i) {
		if (m_weights != null) {
			return MessageFormat.format("{0} tables were packed into {1} files.\nEfficiency factor is {2}",
					m_rdbEditor.getStructure().getTables().size(), m_filesCount.get(i), m_weights.get(i));
		}
		return "";
	}
    
	@FXML
	private void onTabSelected() {
		ObservableList<Tab> tabs = mvTabPane.getTabs();
		for (int i = 0; i < tabs.size(); i++) {
			if (tabs.get(i).isSelected())
				lbl_Factor.setText(getInformation(i));
		}
	}
	
	private int getSelectedMVTab(){
	    ObservableList<Tab> tabs = mvTabPane.getTabs();
        for (int i = 0; i < tabs.size(); i++) {
            if (tabs.get(i).isSelected()){
                return i;
            }
        }
        return 0;
	}
	
	@FXML
    private void onActionExport(ActionEvent e){
        Task<Void> task = new Task<Void>()
        {

            @Override
            protected Void call()
                throws Exception
            {
                UniVerseDatabaseClient u2Client = new UniVerseDatabaseClient(dbSettings.getMVHost(), dbSettings.getMVPort(), dbSettings.getMVUser(), dbSettings.getMVPsw());
                final MySqlDatabaseClient client = new MySqlDatabaseClient(dbSettings.getRHost(), dbSettings.getRPort(), 
                    dbSettings.getRDBName(), dbSettings.getRUser(), dbSettings.getRPsw());
//                if (!u2Client.getAccounts().contains(dbSettings.getMVAccount())) {
//                	u2Client.createAccount(dbSettings.getMVAccount());
//                }
                TransferSet transformation = m_transfers.get(getSelectedMVTab());
                transformation.stream().forEach(tr -> System.out.println(tr.getBaseTable() + ": " + tr.getEmbeddedTables()));
                Plan plan = new Plan(new ArrayList<>(transformation), client);
                while (plan.next()) {
                    MVFile mvFile = plan.getStructure();
                    u2Client.createFile(dbSettings.getMVAccount(), mvFile);
                    try (Data data = plan.getData()) {
                        u2Client.exportData(dbSettings.getMVAccount(), mvFile.getName(), mvFile, data);
                    }
                }
                return null;
            }
            
        };
        ProgressForm.showProgress(task, m_stage);
    }
}
