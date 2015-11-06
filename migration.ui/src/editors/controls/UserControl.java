package editors.controls;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

/**
 * <pre>
 * Title: UserControl
 * Description: TODO
 * Copyright: Copyright (c) 2015
 * Company: Rocket Software, Inc
 * </pre>
 * @author pgizatullin
 */
public abstract class UserControl extends Region {
    
    private final String resourcePath = "%s.fxml";
    protected Node root;
    
    private Point2D startPoint = null;
    
    public UserControl() {
        this.loadFXML();
        init();
    }
 
    private void loadFXML() {
        FXMLLoader loader = new FXMLLoader();
 
        loader.setController(this);
        loader.setLocation(this.getViewURL());
 
        try {
            root = (Node) loader.load();
            this.getChildren().add(root);
        }
        catch (IOException ex) {
            Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
 
    private String getViewPath() {
        return String.format(resourcePath, this.getClass().getSimpleName());
    }
 
    private URL getViewURL() {
        return this.getClass().getResource(this.getViewPath());
    }
    
    private void init(){
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                e.consume();
            }
        });
        
        getHeaderNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                getHeaderNode().setCursor(Cursor.HAND);
            }
        });
        
        getHeaderNode().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                startPoint = new Point2D(e.getSceneX(), e.getSceneY());
                getHeaderNode().setCursor(Cursor.CLOSED_HAND);
                e.consume();
            }
        });
        
        getHeaderNode().setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                getHeaderNode().setCursor(Cursor.DEFAULT);
                startPoint = null;
                e.consume();
            }
        });
        
        getHeaderNode().setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                Point2D point = new Point2D(e.getSceneX(), e.getSceneY());
                if (startPoint != null){
                    Point2D changes = new Point2D(point.getX() - startPoint.getX(), 
                                                  point.getY() - startPoint.getY());
                    UserControl.this.relocate(UserControl.this.getLayoutX() + changes.getX(), UserControl.this.getLayoutY() + changes.getY());
                }
                startPoint = point;
                e.consume();
            }
        });
    }
    
    protected abstract Node getHeaderNode();
}

