package GUI.start;

import GUI.Card;
import GUI.Deck;
import tools.Actions;
import tools.Calculator;
import tools.Range;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Driver {
    private static Deck deck;
    private Range range;
    private List<Card> flopCards = new ArrayList<>();
    private List<Card> holeCards = new ArrayList<>();
    private String scenarioInfo = "";

    public Driver(Deck deck) {
        Driver.deck = deck;
        generateScenario();
    }

    private void generateScenario() {
        Random random = new Random();

        // generate positions of players
        int ourPos = random.nextInt(9);
        int oppPos = random.nextInt(9);

        // get random opponentAction
        int oppAction = random.nextInt(4);

        // get info on scenario
        this.scenarioInfo = getScenarioText(ourPos, oppPos, oppAction);

        // generate our hand based on ourPos
        Range ourRange = new Range(ourPos, oppAction);
        List<String []> ourHands = Calculator.getAllHands(ourRange.range.get(random.nextInt(ourRange.range.size())));
        List<String> ourFinalHand = Arrays.asList(ourHands.get(random.nextInt(ourHands.size())));
        for (String card : ourFinalHand) {
            holeCards.add(deck.getCardHashMap().get(card));
        }

        // generate range based on oppAction
        this.range = new Range(oppPos, oppAction);


        // generate flop
        while (flopCards.size() != 3) {
            Card c = deck.getAllCards().get(random.nextInt(52));
            if (!ourFinalHand.contains(c.getCardVal())) {
                flopCards.add(c);
            }
        }
    }


    private String getScenarioText(int ourPos, int oppPos, int oppAction) {
        boolean weActFirst = ourPos < oppPos;

        if (oppAction == 0) {
            if (weActFirst) {
                return " we limp from " + Actions.positions[ourPos] +
                        " and opponent calls from " + Actions.positions[oppPos];
            } else {
                return " opponent limp opens from " + Actions.positions[oppPos] +
                        " and we call from " + Actions.positions[ourPos];
            }
        } else if (oppAction == 1) {
            if (weActFirst) {
                return "we open raise from " + Actions.positions[ourPos] +
                        " and opponent calls from " + Actions.positions[oppPos];
            } else {
                return "opponent open raises from " + Actions.positions[oppPos] +
                        " and we call from " + Actions.positions[ourPos];
            }
        } else if (oppAction == 2) {
            if (weActFirst) {
                return " we open raise from " + Actions.positions[ourPos] +
                        " and opponent 3-bets from " + Actions.positions[oppPos] + ". We call";
            } else {
                return " opponent open raises from " + Actions.positions[oppPos] +
                        " and we 3-bet from " + Actions.positions[ourPos] + ". opponent calls";
            }
        } else {
            if (weActFirst) {
                return "we 3-bet an open raise from " + Actions.positions[ourPos] +
                        "and opponent 4-bets from " + Actions.positions[oppPos] + ". We call";
            } else {
                return " opponent 3-bets from " + Actions.positions[ourPos] +
                        " and we 4-bet from " + Actions.positions[oppPos] + ". opponent calls";
            }
        }
    }

    public Range getRange() {
        return range;
    }

    public List<Card> getFlopCards() {
        return flopCards;
    }

    public List<Card> getHoleCards() {
        return holeCards;
    }

    public String getScenarioInfo() {
        return scenarioInfo;
    }
}
