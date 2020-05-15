package GUI;

import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import tools.Range;

import java.util.HashMap;
import java.util.List;

public class RangePane extends GridPane {
    private HashMap<String, Button> buttonMap = new HashMap<>();
    private int w = 465;
    private int h = 330;
    public RangePane() {
        super();
        String [] hands = Range.rangeChart.split(" ");
        int pointer = 0;

        // make buttons fit pane
        for (int i = 0; i < 13; i++) {
            RowConstraints rc = new RowConstraints();
            rc.setFillHeight(true);
            rc.setVgrow(Priority.ALWAYS);
            this.getRowConstraints().add(rc);
        }

        for (int i = 0; i < 13; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setFillWidth(true);
            cc.setHgrow(Priority.ALWAYS);
            this.getColumnConstraints().add(cc);
        }


        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                String card = hands[pointer];
                if (card.length() == 2) {
                    card += " ";
                }
                Button b = new Button(card);
                b.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                b.setStyle("-fx-background-radius: 0");
                this.add(b, j, i);
                if (hands[pointer].charAt(hands[pointer].length() - 1) == 'o') {
                    buttonMap.put(hands[pointer].substring(0, 2), b);
                } else{
                    buttonMap.put(hands[pointer], b);
                }
                pointer++;
            }
        }

    }

    public HashMap<String, Button> getButtonMap() {
        return buttonMap;
    }


    public void reset() {
        for (Button b : buttonMap.values()) {
            b.getStyleClass().removeAll();
        }

    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }
}
