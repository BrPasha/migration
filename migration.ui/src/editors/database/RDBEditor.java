package editors.database;

import editors.controls.TableNode;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import migration.core.model.rdb.RDBStructure;
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
    private static final double SPACE = 10;
    
    private Pane m_pane; 
    private RDBStructure m_structure;
    
    public RDBEditor(Pane pane)
    {
        this.m_pane = pane;
    }
    
    public void setStructure(RDBStructure structure)
    {
        m_structure = structure;
        for (RDBTable table : structure.getTables()){
            TableNode node = createTable(table);
            m_pane.getChildren().add(node);
        }
    }
    
    public RDBStructure getStructure(){
        return m_structure;
    }
    
    public void relayout(double width){
        System.out.println("layout");
        double x = SPACE;
        double y = SPACE;
        double maxHeight = 0;
        for (Node node: m_pane.getChildren()){
            if (node instanceof Region){
                if (x > width){
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
    
    public void layout(){
        m_pane.layout();
    }
}

