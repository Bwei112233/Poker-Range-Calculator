package GUI.start;

import GUI.Alert;
import GUI.Card;
import GUI.Deck;
import GUI.RangePane;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import tools.Actions;
import tools.Range;

import java.util.*;

/**
 * Handles events from the GUI
 */
public class MainController {

    @FXML
    private Button rangeBut;
    @FXML
    private ImageView flop1;
    @FXML
    private ImageView flop2;
    @FXML
    private ImageView flop3;
    @FXML
    private ImageView hole1;
    @FXML
    private ImageView hole2;

    // equity buttons
    @FXML
    private Button equity_0_33;
    @FXML
    private Button equity_34_66;
    @FXML
    private Button equity_67_100;

    @FXML
    private Button nextHand;

    @FXML
    private Text handInfo;

    static boolean handGenerated = false;
    static Deck deck = new Deck();
    static Driver driver = new Driver(deck);
    static RangePane rangePane = new RangePane();

    private static HashMap<String, double[]> equityMap = new HashMap<>();
    static {
        equityMap.put("67% to 100%", new double[]{.67, 1});
        equityMap.put("34% to 66%", new double[]{.34, .66});
        equityMap.put("0% to 33% ", new double[]{.0, .33});
    }

    public void equityClicked(MouseEvent mouseEvent) {
        if (!handGenerated) {
            Alert.displayInitError();
            return;
        }
        Button b  = (Button) mouseEvent.getSource();
        double [] equityRange = equityMap.get(b.getText());

        List<String> flopCards = new ArrayList<>();
        List<String> holeCards = new ArrayList<>();

        for (Card c : driver.getFlopCards()) flopCards.add(c.getCardVal());
        for (Card c : driver.getHoleCards()) holeCards.add(c.getCardVal());

        double currEquity = tools.Calculator.getEquity(flopCards, holeCards, driver.getRange().range);
        if (equityRange[0] < currEquity && currEquity < equityRange[1]) {
            // make correct popup
            Alert.displayAlert("correct!", currEquity);
        } else {
            // make bad popup
            Alert.displayAlert("incorrect!", currEquity);
        }
    }


    public void startAnotherHand() {
        driver.generateScenario();
        resetCards(driver.getFlopCards(), driver.getHoleCards());
        resetRange(driver.getRange(), rangePane);
        handInfo.setText(driver.getScenarioInfo());
    }




    private void resetCards(List<Card> flopCards, List<Card> holeCards) {
        handGenerated = true;
        flop1.setImage(flopCards.get(0).getImage());
        flop2.setImage(flopCards.get(1).getImage());
        flop3.setImage(flopCards.get(2).getImage());
        hole1.setImage(holeCards.get(0).getImage());
        hole2.setImage(holeCards.get(1).getImage());
    }

    @FXML
    private void showRange() {
        Alert.displayRange(rangePane);
    }

    private void resetRange(Range newRange, RangePane rangePane) {
        List<String> rangeCards = newRange.range;
        rangePane.reset();
        for (String cards : rangeCards) {
            rangePane.getButtonMap().get(cards).setStyle("-fx-background-color: deepskyblue");
        }
    }
}
