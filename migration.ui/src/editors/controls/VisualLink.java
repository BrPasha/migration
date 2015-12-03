package editors.controls;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import migration.core.model.rdb.RDBRelation;

/**
 * <pre>
 * Title: VisualRelation
 * Copyright: Copyright (c) 2015
 * Company: Rocket Software, Inc
 * </pre>
 * @author pgizatullin
 */
public class VisualLink
{
    private static int DISTANCE = 10;
    
    private RDBRelation m_relation = null;
    private TableNode m_table1;
    private TableNode m_table2;
    private Pane m_parent;
    private Group m_lines;
    private boolean m_selected = false;
    private boolean m_highlighted = false;
    private String m_baseStyle = "-fx-stroke: #0099FF; -fx-stroke-width: 1;";
    private String m_selectedStyle = "-fx-stroke: #9BD5FF; -fx-stroke-width: 2;";
    private String m_highlightedStyle = "-fx-stroke: #99FFA1; -fx-stroke-width: 2;";
    
    public VisualLink(RDBRelation relation, Pane parent, TableNode table1, TableNode table2){
        this.m_relation = relation;
        this.m_table1 = table1;
        this.m_table2 = table2;
        this.m_parent = parent;
        
        ChangeListener<Number> changeListener = new ChangeListener<Number>()
        {
            
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                update();
            }
        };
        
        m_table1.widthProperty().addListener(changeListener);
        m_table1.heightProperty().addListener(changeListener);
        m_table1.layoutXProperty().addListener(changeListener);
        m_table1.layoutYProperty().addListener(changeListener);
        m_table1.addScrollListener(changeListener);
        
        m_table2.widthProperty().addListener(changeListener);
        m_table2.heightProperty().addListener(changeListener);
        m_table2.layoutXProperty().addListener(changeListener);
        m_table2.layoutYProperty().addListener(changeListener);
        m_table2.addScrollListener(changeListener);
        
    }
    
    public RDBRelation getRelation(){
        return m_relation;
    }
    
    public void update(){
        if (m_lines != null){
            m_parent.getChildren().remove(m_lines);
        }
        m_lines = construct();
        m_parent.getChildren().add(m_lines);
        m_lines.setStyle(m_selected ? m_selectedStyle : m_baseStyle);
        if (m_selected || m_highlighted){
            m_lines.toFront();
            DropShadow shadow = new DropShadow();
            shadow.setRadius(10);
            shadow.setColor(Color.WHITE);
            m_lines.setEffect(shadow);
        }
        else{
            m_lines.toBack();
            m_lines.setEffect(null);
        }
    }
    
    public Group construct(){
        Group lines = new Group();
        Point2D startPoint = getLinkPoint(m_table1, m_relation.getColumn1());
        Point2D endPoint = getLinkPoint(m_table2, m_relation.getColumn2());
        
        LinePoints points = new LinePoints(startPoint, endPoint, m_table1.getWidth(), m_table2.getWidth());
        
        Point2D point1 = points.getStart();
        Point2D point2 = new Point2D(point1.getX() + (points.getStartLeft() ? -DISTANCE : DISTANCE), point1.getY());
        Point2D point4 = points.getEnd();
        Point2D point3 = new Point2D(point4.getX() + (points.getEndLeft() ? -DISTANCE : DISTANCE), point4.getY());
        
        lines.getChildren().add(getLine(point1, point2));
        lines.getChildren().add(getLine(point2, point3));
        lines.getChildren().add(getLine(point3, point4));
        return lines;
    }
    
    private Line getLine(Point2D point1, Point2D point2){
        Line line = new Line(point1.getX(), point1.getY(), point2.getX(), point2.getY());
        if (m_highlighted){
            line.setStyle(m_highlightedStyle);
        }
        else{
            line.setStyle(m_selected ? m_selectedStyle : m_baseStyle);
        }
        return line;
    }
    
    private Point2D getLinkPoint(TableNode table, String column){
        Point2D globalPoint = table.getConnectionPoint(column);
        Point2D localPoint = m_parent.sceneToLocal(globalPoint);
        return new Point2D(table.getLayoutX(), localPoint.getY());
    }
    
    public boolean getSelected(){
        return m_selected;
    }
    
    public void setSelected(boolean selected){
        if (m_selected != selected){
            m_selected = selected;
            update();
        }
    }
    
    public void setHighlighted(boolean highlighted){
        if (m_highlighted != highlighted){
            m_highlighted = highlighted;
            update();
        }
    }
}

