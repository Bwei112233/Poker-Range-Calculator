package GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Creates Alerts based on Actions by GUI.
 */
public class Alert {
    public static void displayAlert(String message, double equity) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Alert");
        window.setMinWidth(300);

        // add correctness label
        Label correctLabel = new Label();
        correctLabel.setText(message);

        // add real equity label
        Label equityLabel = new Label();
        equityLabel.setText(" your actual equity is " + equity);

        Button b = new Button("close");
        b.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(correctLabel, equityLabel, b);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void displayInitError() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Alert");
        window.setMinWidth(300);


        Label errorLabel = new Label();
        errorLabel.setText("Please start a hand first!");

        Button b = new Button("close");
        b.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(errorLabel, b);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void displayRange(RangePane rangePane) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Current Range");
        window.setMaxWidth(rangePane.getW() + 50);
        window.setMaxHeight(rangePane.getH() + 50);
        window.setMinWidth(rangePane.getW() + 50);
        window.setMinHeight(rangePane.getH() + 50);

        StackPane pane = new StackPane();
        pane.getChildren().add(rangePane);

        Scene scene = new Scene(pane);
        window.setScene(scene);
        window.showAndWait();
    }


}
