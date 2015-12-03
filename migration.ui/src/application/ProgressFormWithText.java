package application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
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
public class ProgressFormWithText
{
    private final Stage dialogStage;
    private final ProgressIndicator pin = new ProgressIndicator();
    private final Label text;
    
    private DoubleProperty property;
    
    public ProgressFormWithText(Stage parentStage, double startValue) {
        dialogStage = new Stage();
        dialogStage.initOwner(parentStage);
        dialogStage.initStyle(StageStyle.TRANSPARENT);
        dialogStage.setResizable(false);
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        property = new SimpleDoubleProperty(){
            @Override
            public void set(double newValue)
            {
                super.set(newValue);
                text.setText(Integer.toString((int)(newValue * 100)) +"%");
            }
        };
        
        property.addListener(new ChangeListener<Number>()
        {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                text.setText(Integer.toString((int)(newValue.doubleValue() * 100)) +"%");
            }
        });
        // PROGRESS BAR
        text = new Label();
        text.setText("0%");
        text.setStyle("-fx-background-color:transparent;  -fx-alignment: CENTER;  -fx-font-size: 24px;  -fx-text-fill: white");
        
        pin.setProgress(-1F);
        pin.setPrefWidth(100);
        pin.setPrefHeight(100);
        final Pane hb = new Pane();
        hb.getChildren().addAll(pin);
        hb.getChildren().add(text);
        hb.setStyle("-fx-background-color:transparent;");

        text.setMinWidth(100);
        text.layout();
        text.relocate(0, 34);
        Scene scene = new Scene(hb, 100, 100);
        scene.setFill(Color.TRANSPARENT);
        dialogStage.setScene(scene);
    }

    public void activateProgressBar(final Task<?> task)  {
        property.bind(task.progressProperty());
        dialogStage.show();
    }

    public Stage getDialogStage() {
        return dialogStage;
    }
    
    public static void showProgress(Task<Void> task, Stage parentStage, double startValue){
        final ProgressFormWithText pForm = new ProgressFormWithText(parentStage,startValue);
        pForm.activateProgressBar(task);
        task.setOnSucceeded(event -> {
            pForm.getDialogStage().close();
        });
        
        
        pForm.getDialogStage().show();

        Thread thread = new Thread(task);
        thread.start();
    }
}

