package editors.controls;

import editors.models.FieldModel;
import editors.models.FileModel;
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
import javafx.scene.layout.Pane;

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
    @FXML
    private Label labelTitle;
    
    @FXML
    private HBox hboxHeader;
    
    @FXML
    private TableView<FieldModel> tableFields;
 
    @FXML
    private TableColumn<FieldModel, String> columnName;
 
    @FXML
    private TableColumn<FieldModel, String> columnMultivalue;
 
    @FXML
    private TableColumn<FieldModel, String> columnTable;

    @FXML
    private void initialize() {

        columnName.setCellValueFactory(new PropertyValueFactory<FieldModel, String>("name"));
        columnName.setEditable(true);
        columnName.setCellFactory(TextFieldTableCell.<FieldModel>forTableColumn());
        columnName.setOnEditCommit(
            new EventHandler<CellEditEvent<FieldModel, String>>() {
                @Override
                public void handle(CellEditEvent<FieldModel, String> t) {
                    ((FieldModel) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setName(t.getNewValue());
                }
            }
        );
        columnMultivalue.setCellValueFactory(new PropertyValueFactory<FieldModel, String>("isSingleValue"));
        columnTable.setCellValueFactory(new PropertyValueFactory<FieldModel, String>("table"));
    }
    
    private ObservableList<FieldModel> fields = FXCollections.observableArrayList();
    
    public FileNode(FileModel model){
        super();
        
        for(FieldModel field : model.getFields()){
            fields.add(field);
        }
        labelTitle.setText(model.getName());
        tableFields.setItems(fields);
        
        //tableFields.setSelectionModel(null);
        tableFields.widthProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> source, Number oldWidth, Number newWidth)
            {
                Pane header = (Pane) tableFields.lookup("TableHeaderRow");
                header.setStyle("-fx-background-color: #669933;");
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

