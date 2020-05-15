package GUI;

import javafx.scene.image.Image;

/**
 * Represents a Card in GUI
 */
public class Card {
    private String cardVal;
    private Image image;

    /**
     * Creates a Card from a file name of the card.
     * @param fileName name of the file
     */
    public Card(String fileName) {
        this.image = new Image("./GUI/images/" + fileName + ".png");
        cardVal = findRank(fileName) + findSuit(fileName);
    }


    /**
     * Finds the suit of the card given file name. Used to find suit format used by Calculator.
     * @param fileName name of the file
     * @return suit of the card
     */
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

    /**
     * Finds the rank of the suit given file name. Used to find rank format used by Calculator
     * @param fileName name of the file
     * @return rank of the card
     */
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

    /**
     * returns Image associated with current Card.
     * @return Image of Card
     */
    public Image getImage() {
        return image;
    }

    /**
     * returns card representation used by Calculator.
     * @return rank of Card
     */
    public String getCardVal() {
        return cardVal;
    }

}
