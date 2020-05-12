package GUI;

import java.util.List;

public class Deck {
    private static String [] suits = {"clubs", "hearts", "diamonds", "spades"};
    private static String [] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10",
            "jack", "queen", "king", "ace"};

    private List<Card> allCards;

    public Deck() {
        for (String rank : ranks) {
            for (String suit : suits) {
                String imageName = rank + "_of_" + suit;
                allCards.add(new Card(imageName));
            }
        }
    }
}
