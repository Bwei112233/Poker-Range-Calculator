package GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Deck {
    private static String[] suits = {"clubs", "hearts", "diamonds", "spades"};
    private static String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10",
            "jack", "queen", "king", "ace"};

    private List<Card> allCards = new ArrayList<>();
    private HashMap<String, Card> cardHashMap = new HashMap<>();

    public Deck() {
        for (String rank : ranks) {
            for (String suit : suits) {
                String imageName = rank + "_of_" + suit;
                Card c = new Card(imageName);
                allCards.add(c);
                cardHashMap.put(c.getCardVal(), c);
            }
        }
    }

    public HashMap<String, Card> getCardHashMap() {
        return cardHashMap;
    }


    public List<Card> getAllCards() {
        return allCards;
    }
}
