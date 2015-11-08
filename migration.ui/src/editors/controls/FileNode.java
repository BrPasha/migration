package editors.controls;

import editors.models.FileModel;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

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
    private TableView tableView;
    
    public FileNode(FileModel model){
        super();
        
//        for (ColumnModel column : model.getColumns()){
//            Label label = new Label();
//            label.setText(column.getName());
//            label.setTextFill(Color.WHITE);
//            vboxColumns.getChildren().add(label);
//        }
        
    }

    @Override
    protected Node getHeaderNode()
    {
        return hboxHeader;
    }
}

