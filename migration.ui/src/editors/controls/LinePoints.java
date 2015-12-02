package editors.controls;

import javafx.geometry.Point2D;

/**
 * <pre>
 * Title: LinePoints
 * Date: 1 дек. 2015 г.
 * Description: TODO
 * Copyright: Copyright (c) 2015
 * Company: Rocket Software, Inc
 * </pre>
 * @author pgizatullin
 */
public class LinePoints
{
    private Point2D m_startPoint;
    
    private Point2D m_endPoint;
    
    private boolean m_startPointDirectionLeft = true;
    
    private boolean m_endPointDirectionLeft = true;
    
    public Point2D getStart(){
        return m_startPoint;
    }
    
    public Point2D getEnd(){
        return m_endPoint;
    }
    
    public boolean getStartLeft(){
        return m_startPointDirectionLeft;
    }
    
    public boolean getEndLeft(){
        return m_endPointDirectionLeft;
    }
    public LinePoints(Point2D start, Point2D end, double widthStart, double widthEnd){
        Point2D start1 = new Point2D(start.getX() + widthStart, start.getY());
        Point2D end1 = new Point2D(end.getX() + widthEnd, end.getY());
        
        Point2D[][] points = new Point2D[4][2];
        points[0][0] = start;
        points[0][1] = end;
        
        points[1][0] = start;
        points[1][1] = end1;
        
        points[2][0] = start1;
        points[2][1] = end;
        
        points[3][0] = start1;
        points[3][1] = end1;
           
        int index = 0;
        double distance = distance(points[0][0], points[0][1]);
        for (int i =1; i<4; i++){
            double newDistance = distance(points[i][0], points[i][1]);
            if (newDistance < distance){
                distance = newDistance;
                index = i;
            }
        }
        m_startPoint = points[index][0];
        m_endPoint = points[index][1];
        
        m_startPointDirectionLeft = index == 0 || index == 1;
        m_endPointDirectionLeft = index == 0 || index == 2;
    }
    
    double distance(Point2D point1, Point2D point2){
        return Math.sqrt((Math.pow(point1.getX() - point2.getX(), 2) + Math.pow(point1.getY() - point2.getY(), 2)));
    }
}

