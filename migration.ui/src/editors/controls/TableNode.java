package editors.controls;

import editors.models.ColumnModel;
import editors.models.TableModel;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

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
    @FXML
    private ScrollPane scrollPane;
    
    @FXML
    private VBox vboxColumns;
    
    @FXML
    private Label labelTitle;
    
    @FXML
    private HBox hboxHeader;
    
    public TableNode(TableModel model){
        super();
        scrollPane.setFitToWidth(true);
        labelTitle.setText(model.getName());
        vboxColumns.getChildren().clear();
        for (ColumnModel column : model.getColumns()){
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
}

