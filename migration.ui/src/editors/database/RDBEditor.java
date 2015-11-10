package editors.database;

import java.util.List;

import editors.controls.TableNode;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import migration.core.model.rdb.RDBTable;

/**
 * <pre>
 * Title: RDBEditor
 * Description: TODO
 * Copyright: Copyright (c) 2015
 * Company: Rocket Software, Inc
 * </pre>
 * @author pgizatullin
 */
public class RDBEditor
{
    private Pane m_pane;
    private static final double SPACE = 10;
    
    public RDBEditor(Pane pane)
    {
        this.m_pane = pane;
    }
    
    public void addTables(List<RDBTable> tables)
    {
        for (RDBTable table : tables){
            TableNode node = createTable(table);
            m_pane.getChildren().add(node);
        }
    }
    
    public void relayout(){
        double x = SPACE;
        double y = SPACE;
        double maxHeight = 0;
        
        for (Node node: m_pane.getChildren()){
            if (node instanceof Region){
                if (x > m_pane.getWidth()){
                    x = SPACE;
                    y = y + maxHeight + SPACE;
                    maxHeight = 0;
                }
                node.setLayoutX(x);
                node.setLayoutY(y);
                if (maxHeight < ((Region)node).getHeight()){
                    maxHeight = ((Region)node).getHeight();
                }
                x = x + ((Region)node).getWidth() + SPACE;
            }
        }
    }
    
    public TableNode createTable(RDBTable model){
        TableNode control = new TableNode(model);
        control.setStyle("-fx-background-color: #2D7EBE; -fx-text-fill: white;");
        return control;
    }
}

