package editors.database;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import migration.core.model.rdb.RDBRelation;
import migration.core.model.rdb.RDBRelationType;

public class JoiningController implements Initializable{

	private String[] joins = new String[]{"One-to-One", "One-to-Many", "Many-to-One", "Many-to-Many"};
	
	@FXML
	private Label lbl_TitleSourceTable;
	
	@FXML
	private Label lbl_TitleSourceColumn;
	
	@FXML
	private Label lbl_TitleTargetTable;
	
	@FXML
	private Label lbl_TargetTable;
	
	@FXML
	private Label lbl_ColumnSource;
	
	@FXML
	private Label lbl_TitleTargetColumn;
	@FXML
	private Label lbl_ColumnTarget;
	
	@FXML
	private Label lbl_SourceTable;
	
	@FXML
	private ComboBox combo_Joins;
	
	private Stage m_stage;
	
	private RDBRelation m_relation;
	
	private JoinListener m_listener;
	
	public void setStage(Stage stage){
        this.m_stage = stage;
    }
	
    public void setListener(JoinListener listener){
        this.m_listener = listener;
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		lbl_TitleSourceTable.setText("Source table:");
		lbl_TitleTargetTable.setText("Target table:");
		
		lbl_TitleSourceColumn.setText("Source column:");
		lbl_TitleTargetColumn.setText("Target column:");
		lbl_ColumnSource.setText("Column 1");
		lbl_ColumnTarget.setText("Column 2");
		
		lbl_SourceTable.setText("Table 1");
		lbl_TargetTable.setText("Table 2");
		
		combo_Joins.getItems().addAll(joins);
		combo_Joins.setValue(joins[0]);
	}
	
	public void initRelation(RDBRelation relation){
	    m_relation = relation;
        lbl_ColumnSource.setText(relation.getColumn1());
        lbl_ColumnTarget.setText(relation.getColumn2());    
        lbl_SourceTable.setText(relation.getTable1());
        lbl_TargetTable.setText(relation.getTable2());
	}
	
	@FXML
	private void btn_OkClicked(){
	    RDBRelationType type = RDBRelationType.values()[combo_Joins.getSelectionModel().getSelectedIndex()];
	    m_listener.onApply(new RDBRelation(m_relation.getTable1(), m_relation.getColumn1(), m_relation.getTable2(), m_relation.getColumn2(), type));
		m_stage.close();
	}

	@FXML
	private void btn_CancelClicked(){
		m_stage.close();
	}
}
