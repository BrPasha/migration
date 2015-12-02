package editors.controls;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

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
    
    @FXML
    private ImageView resizeImage;
    
    private final String resourcePath = "%s.fxml";
    protected Region root;
    
    private Point2D startPoint = null;
    private Point2D size = null;
    
    public UserControl() {
        this.loadFXML();
        init();
    }
 
    private void loadFXML() {
        FXMLLoader loader = new FXMLLoader();
 
        loader.setController(this);
        loader.setLocation(this.getViewURL());
 
        try {
            root = (Region) loader.load();
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
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                if (startPoint == null){
                    addShadow();
                    setSelecting(true);
                }
            }
        });
        
        root.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                if (startPoint == null){
                    removeShadow();
                    setSelecting(false);
                }
            }
        });
        
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
                if (!contains(new Point2D(e.getX(),e.getY()))){
                    removeShadow();
                    setSelecting(false);
                }
                e.consume();
            }
        });
        
        getHeaderNode().setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                Point2D point = new Point2D(e.getSceneX(), e.getSceneY());
                if (startPoint != null){
                    double newX = UserControl.this.getLayoutX() + point.getX() - startPoint.getX();
                    double newY = UserControl.this.getLayoutY() + point.getY() - startPoint.getY();
                    if (newX < 0){
                        newX = 0;
                    }
                    if (newY < 0){
                        newY = 0.0;
                    }
                    UserControl.this.relocate(newX, newY);
                }
                startPoint = point;
                e.consume();
            }
        });
    }
    
    @FXML
    private void onMousePressedResizeImage(MouseEvent e){
        startPoint = new Point2D(e.getSceneX(), e.getSceneY());
        size = new Point2D(UserControl.this.getWidth(), UserControl.this.getHeight());
    }
    
    @FXML
    private void onMouseDraggedResizeImage(MouseEvent e){
        Point2D point = new Point2D(e.getSceneX(), e.getSceneY());
        if (startPoint != null){
            Point2D changes = new Point2D(point.getX() - startPoint.getX(), 
                                          point.getY() - startPoint.getY());
            size = new Point2D(size.getX() + changes.getX(), size.getY() + changes.getY());
            UserControl.this.setMinSize(size.getX(), size.getY());
            root.setMinSize(size.getX(), size.getY());
        }
        startPoint = point;
        startPoint = new Point2D(e.getSceneX(), e.getSceneY());
    }
    
    @FXML
    private void onMouseReleasedResizeImage(MouseEvent e){
        if (!contains(new Point2D(e.getX(),e.getY()))){
            removeShadow();
            setSelecting(false);
        }
        startPoint = null;
    }
    
    private boolean containsPoint(double x, double y){
        Point2D point = this.localToScene(new Point2D(0,0));
        return x >= point.getX() && x <= point.getX() + this.getWidth() && y >= point.getY() && y <= point.getY() + this.getHeight();
    }
    protected void addShadow(){
        UserControl.this.toFront();
        DropShadow shadow = new DropShadow();
        shadow.setWidth(0);
        shadow.setHeight(0);
        shadow.setRadius(30);
        shadow.setColor(Color.web(getSelectedColor()));
        UserControl.this.setEffect(shadow);
    }
    
    protected void removeShadow(){
        UserControl.this.setEffect(null);
    }
    
    protected abstract Node getHeaderNode();
    
    protected abstract void setSelecting(boolean selected);
    
    protected abstract String getSelectedColor();
}

