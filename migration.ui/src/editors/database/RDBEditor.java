package editors.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import editors.controls.SelectionColumn;
import editors.controls.TableNode;
import editors.controls.VisualLink;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import migration.core.model.rdb.RDBRelation;
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
    private Map<String, TableNode> m_tables = new HashMap<String, TableNode>();
    private Map<String, List<VisualLink>> m_links = new HashMap<String, List<VisualLink>>();
    private SelectionColumn m_selectedColumn = null;
    
    public RDBEditor(Pane pane)
    {
        this.m_pane = pane;
    }
    
    public void setStructure(RDBStructure structure)
    {
        m_structure = structure;
        for (RDBTable table : structure.getTables()){
            TableNode node = createTable(table);
            m_tables.put(table.getName(), node);
            m_pane.getChildren().add(node);
        }
        for (RDBRelation relation : structure.getRelations()){
            TableNode table1 = m_tables.get(relation.getTable1());
            TableNode table2 = m_tables.get(relation.getTable2());
            VisualLink link = new VisualLink(relation, m_pane, table1, table2);
            addLink(link,relation.getTable1());
            addLink(link,relation.getTable2());
            m_pane.getChildren().add(link.construct());
        }
    }
    
    private void addLink(VisualLink link, String tableName){
        List<VisualLink> links = m_links.get(tableName);
        if (links == null){
            links = new ArrayList<VisualLink>();
            m_links.put(tableName, links);
        }
        links.add(link);
    }
    
    public RDBStructure getStructure(){
        return m_structure;
    }
    
    public void relayout(double width){
        double x = SPACE;
        double y = SPACE;
        double maxHeight = 0;
        for (Node node: m_tables.values()){
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
    
    public TableNode createTable(final RDBTable model){
        TableNode control = new TableNode(model, new ISelectedListener()
        {
            
            @Override
            public void select(boolean selected, Object source, Object data)
            {
                SelectionColumn column = new SelectionColumn((String)data, (TableNode)source);
                if (selected){
                    if(m_selectedColumn == null){
                        m_selectedColumn= column;
                        m_selectedColumn.getTable().selectColumn(true, m_selectedColumn.getColumn());
                    }
                    else{
                        if (!(m_selectedColumn.getTable().getName().equals(column.getTable().getName()))){
                            
                        }
                        else{
                            m_selectedColumn.getTable().selectColumn(false, m_selectedColumn.getColumn());
                            column.getTable().selectColumn(true, column.getColumn());
                            m_selectedColumn = column;
                        }
                    }
                }
            }

            @Override
            public void highlight(boolean highlighted, Object data)
            {
                List<VisualLink> links = m_links.get(model.getName());
                if (links != null){
                    for(VisualLink link:links){
                        link.setSelected(highlighted);
                    }
                }
            }
        });
        control.setStyle("-fx-background-color: #2D7EBE; -fx-text-fill: white;");
        return control;
    }
    
    public void highlightTables(List<String> tables, boolean highlight){
        for (String table:tables){
            TableNode node = m_tables.get(table);
            node.highlight(highlight);
        }
    }
    
    public void layout(){
        m_pane.layout();
    }
}

