package editors.database;

import java.util.List;

import editors.controls.FileNode;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import migration.core.model.mv.MVFile;

/**
 * <pre>
 * Title: RDBEditor
 * Description: TODO
 * Copyright: Copyright (c) 2015
 * Company: Rocket Software, Inc
 * </pre>
 * @author pgizatullin
 */
public class MVEditor
{
    private static final double SPACE = 10;
    
    private Pane m_pane;
    private List<MVFile> m_files;
    
    public MVEditor(Pane pane)
    {
        this.m_pane = pane;
    }
    
    public void setData(List<MVFile> files)
    {
        m_files = files;
        for (MVFile file : files){
            FileNode node = createFile(file);
            node.layout();
            m_pane.getChildren().add(node);
        }
    }
    
    public List<MVFile> getData(){
        return m_files;
    }
    
    public void relayout(double width){
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
    
    public FileNode createFile(MVFile model){
        FileNode control = new FileNode(model);
        control.setStyle("-fx-background-color: #2D7EBE; -fx-text-fill: white;");
        return control;
    }
    
    public void layout(){
        m_pane.layout();
    }
}

