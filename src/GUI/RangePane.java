package GUI;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import tools.Range;

import java.util.HashMap;
import java.util.List;

public class RangePane extends GridPane {
    private Range range;
    HashMap<String, Button> buttonMap;
    public RangePane() {
        super();
        String [] hands = Range.rangeChart.split(" ");
        int pointer = 0;
        buttonMap = new HashMap<>();
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                Button b = new Button(hands[i]);
                b.setStyle("-fx-background-radius: 0");
                this.add(b, i, j);
                buttonMap.put(hands[i], b);
            }
        }
    }
}
