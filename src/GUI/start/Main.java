package GUI.start;

import GUI.Card;
import GUI.Deck;
import GUI.RangePane;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        RangePane rangePane = new RangePane();
        StackPane pane = new StackPane();
        pane.getChildren().add(rangePane);
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 912, 635));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
