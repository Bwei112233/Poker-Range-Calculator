package GUI.start;

import GUI.Alert;
import GUI.Card;
import GUI.Deck;
import GUI.RangePane;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
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

    @FXML
    private StackPane flopStack;

    private static boolean handGenerated = false;
    private static Deck deck = new Deck();
    private static Driver driver = new Driver(deck);
    private static RangePane rangePane = new RangePane();
    private static HashMap<String, double[]> equityMap = new HashMap<>();
    static {
        equityMap.put("67% to 100%", new double[]{.67, 1});
        equityMap.put("34% to 66%", new double[]{.34, .66});
        equityMap.put("0% to 33% ", new double[]{.0, .33});
    }

    /**
     * Calculates equity of player's hand in current scenario and displays result Alert to the player.
     * @param mouseEvent executed on mouse click of an equity button
     */
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


    /**
     * resets all current graphics and generates a new scenario.
     */
    public void startAnotherHand() {
        driver.generateScenario();
        resetCards(driver.getFlopCards(), driver.getHoleCards());
        resetRange(driver.getRange(), rangePane);
        handInfo.setText(driver.getScenarioInfo());
        handGenerated = true;
    }




    private void resetCards(List<Card> flopCards, List<Card> holeCards) {
        hole1.setImage(holeCards.get(0).getImage());
        hole2.setImage(holeCards.get(1).getImage());

        flopStack.setPadding(new Insets(0, 0, 0, hole1.getX() - 240));

        // reset pos of flop cards
        flop2.setX(flop1.getX());
        flop3.setX(flop1.getX());
        flop1.setImage(flopCards.get(0).getImage());
        flop2.setImage(flopCards.get(1).getImage());
        flop3.setImage(flopCards.get(2).getImage());

        preformTranslate(flop2, 110);
        preformTranslate(flop3, 220);
    }

    private void preformTranslate(ImageView cardImage, int distance) {
        // Set up a Translate Transition for the Text object
        TranslateTransition trans = new TranslateTransition(Duration.seconds(1.2), cardImage);
        trans.setFromX(cardImage.getX());
        trans.setToX(cardImage.getX() + distance);
        trans.setCycleCount(1);
        // Play the Animation
        trans.play();
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
