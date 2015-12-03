package editors.controls;

import editors.database.ISelectedListener;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import migration.core.model.rdb.RDBColumn;

/**
 * <pre>
 * Title: ColumnControl
 * Date: 2 дек. 2015 г.
 * Description: TODO
 * Copyright: Copyright (c) 2015
 * Company: Rocket Software, Inc
 * </pre>
 * @author pgizatullin
 */
public class ColumnControl
{
    private RDBColumn m_column;
    private Label m_label;
    private boolean m_selected;
    private Pane m_parent;
    private ISelectedListener m_listener;
    
    private final static String HOVER = "-fx-border-width: 1; -fx-border-color: #418CC5;";
    private final static String SELECTED = "-fx-border-width: 1; -fx-border-color: #99FFA1;";
    private final static String NORMAL = "-fx-border-width: 1; -fx-border-color: #2D7EBE;";
    
    public ColumnControl(Pane parent, RDBColumn column, ISelectedListener listener){
        m_column = column;
        m_parent = parent;
        m_label = new Label(column.getName());
        m_listener = listener;
        m_label.setTextAlignment(TextAlignment.CENTER);
        m_label.setTextFill(Color.WHITE);
        m_label.setMaxWidth(Double.POSITIVE_INFINITY);
        m_label.setStyle(NORMAL);
        m_label.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                if (!m_selected){
                    m_label.setStyle(HOVER);
                }  
            }
        });
        
        m_label.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                if (!m_selected)
                    m_label.setStyle(NORMAL);
            }
        });
        
        m_label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                listener.select(!m_selected, ColumnControl.this,null);
            }
        });
        
        parent.getChildren().add(m_label);
    }
    
    public Label getLabel(){
        return m_label;
    }
    
    public String getName(){
        return m_column.getName();
    }
    
    public void setSelected(boolean selected){
        m_selected = selected;
        m_label.setStyle(m_selected ? SELECTED : HOVER);
    }
}

