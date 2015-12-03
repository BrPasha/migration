package application;

import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * <pre>
 * Title: ProgressForm
 * Description: TODO
 * Copyright: Copyright (c) 2015
 * Company: Rocket Software, Inc
 * </pre>
 * @author pgizatullin
 */
public class ProgressForm
{
    private final Stage dialogStage;
    private final ProgressIndicator pin = new ProgressIndicator();

    public ProgressForm(Stage parentStage, double startValue) {
        dialogStage = new Stage();
        dialogStage.initOwner(parentStage);
        dialogStage.initStyle(StageStyle.TRANSPARENT);
        dialogStage.setResizable(false);
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        // PROGRESS BAR
        
        pin.setProgress(startValue);
        pin.setPrefWidth(100);
        pin.setPrefHeight(100);
        final HBox hb = new HBox();
        hb.setSpacing(5);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(pin);
        hb.setStyle("-fx-background-color: rgba(255, 255, 255, 0);");

        Scene scene = new Scene(hb);
        scene.setFill(Color.TRANSPARENT);
        dialogStage.setScene(scene);
    }

    public void activateProgressBar(final Task<?> task)  {
        pin.progressProperty().bind(task.progressProperty());
        dialogStage.show();
    }

    public Stage getDialogStage() {
        return dialogStage;
    }
    
    public static void showProgress(Task<Void> task, Stage parentStage, double startValue){
        final ProgressForm pForm = new ProgressForm(parentStage,startValue);
        pForm.activateProgressBar(task);
        task.setOnSucceeded(event -> {
            pForm.getDialogStage().close();
        });
        
        
        pForm.getDialogStage().show();

        Thread thread = new Thread(task);
        thread.start();
    }
}

