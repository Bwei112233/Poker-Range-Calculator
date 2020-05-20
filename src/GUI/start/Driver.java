package GUI.start;
import GUI.Card;
import GUI.Deck;
import tools.Actions;
import tools.Calculator;
import tools.Range;
import java.util.*;

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

    public void generateScenario() {
        flopCards.clear();
        holeCards.clear();
        Random random = new Random();

        // generate positions of players
        int ourPos = random.nextInt(9);
        int oppPos = random.nextInt(9);
        while (oppPos == ourPos) {
            oppPos = random.nextInt(9);
        }
        // get random opponentAction
        int oppAction = random.nextInt(3);

        // get info on scenario
        this.scenarioInfo = getScenarioText(ourPos, oppPos, oppAction);

        // generate our hand based on ourPos
        Range ourRange = new Range(ourPos, oppAction);
        List<String[]> ourHands = Calculator.getAllHands(ourRange.range.get(random.nextInt(ourRange.range.size())));
        List<String> ourFinalHand = Arrays.asList(ourHands.get(random.nextInt(ourHands.size())));
        for (String card : ourFinalHand) {
            holeCards.add(deck.getCardHashMap().get(card));
        }

        // generate range based on oppAction
        this.range = new Range(oppPos, oppAction);


        // generate flop
        HashSet<String> inFlop = new HashSet<>();
        while (flopCards.size() != 3) {
            Card c = deck.getAllCards().get(random.nextInt(52));
            if (!ourFinalHand.contains(c.getCardVal()) && !inFlop.contains(c.getCardVal())) {
                flopCards.add(c);
                inFlop.add(c.getCardVal());
            }
        }
    }


    private String getScenarioText(int ourPos, int oppPos, int oppAction) {
        boolean weActFirst = ourPos < oppPos;

        if (oppAction == 1) {
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
