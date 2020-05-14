package GUI;

import javafx.scene.image.Image;

/**
 * Represents a GUI.Card in GUI
 */
public class Card {
    private String cardVal;
    public Image image;


    public Card(String fileName) {
        this.image = new Image("./GUI/images/" + fileName + ".png");
        cardVal = findRank(fileName) + findSuit(fileName);
    }



    private String findSuit(String fileName) {
        String suit = fileName.split("_")[2];
        switch (suit) {
            case "hearts": return "h";
            case "spades": return "s";
            case "diamonds": return "d";
            case "clubs": return "c";
        }
        throw new IllegalArgumentException("bad filename");
    }


    private String findRank(String fileName) {
        String rank = fileName.split("_")[0];
        switch (rank) {
            case "ace": return "A";
            case "jack": return "J";
            case "king": return "K";
            case "queen": return "Q";
            case "10": return "T";
            default: return rank;
        }
    }



    public String getCardVal() {
        return cardVal;
    }

}
