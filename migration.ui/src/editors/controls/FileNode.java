package editors.controls;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import migration.core.model.mv.MVField;
import migration.core.model.mv.MVFile;

/**
 * <pre>
 * Title: TableControl
 * Description: TODO
 * Copyright: Copyright (c) 2015
 * Company: Rocket Software, Inc
 * </pre>
 * @author pgizatullin
 */
public class FileNode extends UserControl
{
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_IS_MULTIVALUE = "type";
    private static final String COLUMN_TABLE = "location";
    
    @FXML
    private Label labelTitle;
    
    @FXML
    private HBox hboxHeader;
    
    @FXML
    private TableView<MVField> tableFields;
 
    @FXML
    private TableColumn<MVField, String> columnName;
 
    @FXML
    private TableColumn<MVField, String> columnMultivalue;
 
    @FXML
    private TableColumn<MVField, String> columnTable;

    @FXML
    private void initialize() {

        columnName.setCellValueFactory(new PropertyValueFactory<MVField, String>(COLUMN_NAME));
        columnName.setEditable(true);
        columnName.setCellFactory(TextFieldTableCell.<MVField>forTableColumn());
        columnName.setOnEditCommit(
            new EventHandler<CellEditEvent<MVField, String>>() {
                @Override
                public void handle(CellEditEvent<MVField, String> t) {
                    ((MVField) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setName(t.getNewValue());
                }
            }
        );
        columnMultivalue.setCellValueFactory(new PropertyValueFactory<MVField, String>(COLUMN_IS_MULTIVALUE));
        columnTable.setCellValueFactory(new PropertyValueFactory<MVField, String>(COLUMN_TABLE));
    }
    
    private ObservableList<MVField> fields = FXCollections.observableArrayList();
    
    public FileNode(MVFile model){
        super();
        
        labelTitle.setText(model.getName());
        fields.addAll(model.getFields());
        tableFields.setItems(fields);
        
        //tableFields.setSelectionModel(null);
        tableFields.widthProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> source, Number oldWidth, Number newWidth)
            {
//                Pane header = (Pane) tableFields.lookup("TableHeaderRow");
//                header.setStyle("-fx-background-color: #669933;");
//                if (header.isVisible()){
//                    header.setMaxHeight(0);
//                    header.setMinHeight(0);
//                    header.setPrefHeight(0);
//                    header.setVisible(false);
//                }
            }
        });

    }

    @Override
    protected Node getHeaderNode()
    {
        return hboxHeader;
    }
}

