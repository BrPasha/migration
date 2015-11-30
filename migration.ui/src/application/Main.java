package application;

import editors.controls.FileNode;
import editors.controls.TableNode;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {
    
    @Override
	public void start(Stage primaryStage) {
		try { 
		    
	        FXMLLoader loader = new FXMLLoader();
	        final ApplicationController controller = new ApplicationController();
	        loader.setController(controller);
	        loader.setLocation(getClass().getResource("Application.fxml"));
			BorderPane root = (BorderPane)loader.load();
			Scene scene = new Scene(root, 1000, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.setTitle("Migration Tool");
			primaryStage.getIcons().add(new Image("file:icon/Rocket25_black.png"));
			primaryStage.show();
//			final ScrollPane tab = (ScrollPane)scene.lookup("#tab");
//			tab.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
//              @Override public void changed(ObservableValue<? extends Bounds> bounds, Bounds oldBounds, Bounds newBounds) {
//                  editor.setPrefWidth(newBounds.getWidth());
//                  editor.setPrefHeight(newBounds.getHeight());
//                }
//              });
//			tab.setFitToHeight(true);
//			tab.setFitToWidth(true);
			
//			Path path = new Path();
//		    path.getElements().add(new MoveTo(50.0f, 50.0f));
//		    path.getElements().add(new HLineTo(80.0f));
//		    path.setStroke(Color.RED);
//		    editor.getChildren().add(path);
//		    
//	        Path path = new Path();
//
//	        QuadCurve quad =QuadCurveBuilder.create()
//	                .startX(50)
//	                .startY(50)
//	                .endX(150)
//	                .endY(50)
//	                .controlX(125)
//	                .controlY(150)
//	                .translateY(path.getBoundsInParent().getMaxY())
//	                .strokeWidth(3)
//	                .stroke(Color.WHITE)
//	                .fill(Color.RED)
//	                .build();
//
//	        root.getChildren().add(quad);
	        
//			EventHandler handler = new EventHandler<MouseEvent>() {
//			      public void handle(MouseEvent event) {
//			          editor.getChildren().add(createTable(event.getX(), event.getY()));
//			      }
//			    };
//			editor.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
//			
//			final Pane editorFile = (Pane)scene.lookup("#u2schema");
//			editorFile.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//                          public void handle(MouseEvent event) {
//                              editorFile.getChildren().add(createFile(event.getX(), event.getY()));
//                          }
//            });
			
			
			controller.showTables();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

    
	public TableNode createTable(double x, double y){
//        TableModel model = new TableModel("Table1", new ColumnModel[] {new ColumnModel("column1"),new ColumnModel("column2"),new ColumnModel("column2"),new ColumnModel("column2"),new ColumnModel("column2"),new ColumnModel("column2"),new ColumnModel("column2"),new ColumnModel("column2"),new ColumnModel("column2"),new ColumnModel("column2"),new ColumnModel("column2"),new ColumnModel("column2")});
//        TableNode control = new TableNode(model);
//        control.setLayoutX(x);
//        control.setLayoutY(y);
//        control.setStyle("-fx-background-color: #2D7EBE; -fx-text-fill: white;");
        return null;
	}

	public FileNode createFile(double x, double y){
//        FileModel model = new FileModel("File1", new FieldModel[] {new FieldModel("field1",true,"Table1"),
//                                                                     new FieldModel("field2",false,"Table2"),
//                                                                     new FieldModel("field3",true,"Table1"),
//                                                                     new FieldModel("field4",true,"Table2"),
//                                                                     new FieldModel("field5",false,"Table2"),
//                                                                     new FieldModel("field6",true,"Table1"),
//                                                                     new FieldModel("field7",false,"Table2"),
//                                                                     new FieldModel("field8",true,"Table1"),
//                                                                     new FieldModel("field9",false,"Table1"),
//                                                                     new FieldModel("field10",true,"Table1"),
//                                                                     new FieldModel("field11",true,"Table1")});
//        FileNode control = new FileNode(model);
//        control.setLayoutX(x);
//        control.setLayoutY(y);
        return null;
    }
	   
	public static void main(String[] args) {
		launch(args);
	}
}
