package application;

import javafx.application.Application;
import javafx.concurrent.Task;
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
			controller.setStage(primaryStage);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Migration Tool");
			primaryStage.getIcons().add(new Image("file:icon/Rocket25_black.png"));
			primaryStage.show();
			
			Task<Void> task = new Task<Void>()
            {
                @Override
                protected Void call()
                    throws Exception
                {
                    controller.showTables();
                    return null;
                }
            };
            ProgressForm.showProgress(task, primaryStage);
            

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	   
	public static void main(String[] args) {
		launch(args);
	}
}
