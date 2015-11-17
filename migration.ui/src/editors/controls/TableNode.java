package editors.controls;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import migration.core.model.rdb.RDBColumn;
import migration.core.model.rdb.RDBTable;

/**
 * <pre>
 * Title: TableControl
 * Description: TODO
 * Copyright: Copyright (c) 2015
 * Company: Rocket Software, Inc
 * </pre>
 * @author pgizatullin
 */
public class TableNode extends UserControl
{
    private RDBTable m_model;
    
    @FXML
    private ScrollPane scrollPane;
    
    @FXML
    private VBox vboxColumns;
    
    @FXML
    private Label labelTitle;
    
    @FXML
    private HBox hboxHeader;
    
    private ReadOnlyBooleanWrapper selected;
   
    protected final void setSelected(boolean value) {
        selectedPropertyImpl().set(value);
    }
        
    public final boolean isSelected() {
        return selected == null ? false : selected.get();
    }
        
    public final ReadOnlyBooleanProperty selectedProperty() {
        return selectedPropertyImpl().getReadOnlyProperty();
    }
    
    private ReadOnlyBooleanWrapper selectedPropertyImpl() {
        if (selected == null) {
            selected = new ReadOnlyBooleanWrapper(){
                 @Override
                public String getName()
                {
                    return "selected";
                }   
                
                 @Override
                public Object getBean()
                {
                    return TableNode.this;
                }
            };
        }
        return selected;
    }
    
    public TableNode(RDBTable model){
        super();
        m_model = model;
        scrollPane.setFitToWidth(true);
        labelTitle.setText(model.getName());
        vboxColumns.getChildren().clear();
        for (RDBColumn column : model.getColumns()){
            Label label = new Label();
            label.setText(column.getName());
            label.setTextFill(Color.WHITE);
            vboxColumns.getChildren().add(label);
        }
    }

    @Override
    protected Node getHeaderNode()
    {
        return hboxHeader;
    }
    
    @FXML
    private void onMouseClicked(MouseEvent event){
        setSelected(!isSelected());
    }
}

