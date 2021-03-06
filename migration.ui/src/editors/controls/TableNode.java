package editors.controls;

import java.util.HashMap;
import java.util.Map;

import editors.database.ISelectedListener;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private final static String SELECTED_COLOR = "#9BD5FF";
    //private final static String HIGLIGHTED_COLOR = "#02CD02";
    private final static String HIGLIGHTED_COLOR = "#99FFA1";
    
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
   
    private Map<String, ColumnControl> m_rows = new HashMap<String, ColumnControl>(); 
    
    private ISelectedListener m_selectedListener;
        
    private String color = SELECTED_COLOR;
    
    public final boolean isSelected() {
        return selected == null ? false : selected.get();
    }
    
    public TableNode(RDBTable model, ISelectedListener listener){
        super();
        m_model = model;
        m_selectedListener = listener;
        scrollPane.setFitToWidth(true);
        labelTitle.setText(model.getName());
        vboxColumns.getChildren().clear();
        for (RDBColumn column : model.getColumns()){
            ColumnControl columnNode = new ColumnControl(vboxColumns, column, new ISelectedListener()
            {
                
                @Override
                public void select(boolean selected, Object source, Object data)
                {
                    m_selectedListener.select(selected, TableNode.this, ((ColumnControl)source).getName());
                }
                
                @Override
                public void highlight(boolean highlighted, Object data)
                {
                    
                }
            });
            m_rows.put(column.getName(), columnNode);
        }
    }

    public void addScrollListener(ChangeListener<Number> listener){
        scrollPane.vvalueProperty().addListener(listener);
    }
    
    public Point2D getConnectionPoint(String column)
    {
        ColumnControl row = m_rows.get(column);
        if (row == null){
            return this.localToScene(0, 0);
        }
        Point2D columnPoint = row.getLabel().localToScene(new Point2D(0,0));
        columnPoint = new Point2D(columnPoint.getX(), columnPoint.getY() + row.getLabel().getHeight()/2);
        Point2D rootPoint = scrollPane.localToScene(new Point2D(0,0));
        
        double yPosition = columnPoint.getY();
        if (yPosition > rootPoint.getY() + scrollPane.getHeight()){
            yPosition = rootPoint.getY() + scrollPane.getHeight();
        }
        if (yPosition < rootPoint.getY()){
            yPosition = rootPoint.getY();
        }
        return new Point2D(0,yPosition);
    }
    
    public String getName(){
        return m_model.getName();
    }
    
    @Override
    protected Node getHeaderNode()
    {
        return hboxHeader;
    }
    
    @FXML
    private void onMouseClicked(MouseEvent event){
        
    }
    
    protected void setSelecting(boolean selected){
        m_selectedListener.highlight(selected, null);
    }

    @Override
    protected String getSelectedColor()
    {
        return color;
    }
    
    public void highlight(boolean highlighted){
        color = highlighted ? HIGLIGHTED_COLOR : SELECTED_COLOR;
        if(highlighted){
            addShadow();
        }
        else{
            removeShadow();
        }
    }
    
    public void selectColumn(boolean select, String name){
        m_rows.get(name).setSelected(select);
    }
}

