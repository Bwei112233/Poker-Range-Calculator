package GUI;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
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
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                String card = hands[pointer];
                if (card.length() == 2) {
                    card += " ";
                }
                Button b = new Button(card);
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
