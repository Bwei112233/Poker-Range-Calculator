package tools;

import java.util.*;

public class CardUtils {
    /**
     * maps int rank of a hand to its literal value.
     */
    public static HashMap<Character, String> ranks = new HashMap<>();
    static {
        ranks.put('0', "Straight Flush");
        ranks.put('1', "Quads");
        ranks.put('2', "Full House");
        ranks.put('3', "Flush");
        ranks.put('4', "Straight");
        ranks.put('5', "Set");
        ranks.put('6', "Two Pair");
        ranks.put('7', "Pair");
        ranks.put('8', "High Card");
    }

    /**
     * Finds the best hand given the hole and community cards of a player.
     * @param ranks HashMap storing count of cards with given rank
     * @param suits HashMap storing count of cards with given suit
     * @param cards all cards that a player is holding
     * @return String representing the best hand. In format of (hand rank)-(kickers)
     *             ex) 2-14-2 denotes the best hand is a full house, Aces full of Deuces (3 Aces, 2 twos)
     */
    public static String findBestHand(HashMap<Integer, Integer> ranks, HashMap<Character, Integer> suits, List<String> cards) {
        String bHand = "";
        boolean hashFlush = checkFlush(suits);

        // check straight flush
//        bHand = getStraightFlush(cards, hashFlush);
//        if (bHand.charAt(0) != '0') return "1-" + bHand;

        // check quads
        bHand = getQuads(ranks);
        if (bHand.charAt(0) != '0') return "1-" + bHand;

        // check full house
        bHand = getFullHouse(ranks);
        if (bHand.charAt(0) != '0' && !bHand.split("-")[1].equals("0")) return "2-" + bHand;

        // check flush
        bHand = getFlush(suits, cards);
        if (bHand.charAt(0) != '0') return "3-" + bHand;

        // check straight
        bHand = getStraight(ranks);
        if (bHand.charAt(0) != '0') return "4-" + bHand;

        // check set
        bHand = getSet(ranks);
        if (bHand.charAt(0) != '0') return "5-" + bHand;

        // check two pair
        bHand = getTwoPair(ranks);
        if (bHand.charAt(0) != '0') return "6-" + bHand;

        // check pair
        bHand = getPair(ranks);
        if (bHand.charAt(0) != '0') return "7-" + bHand;

        // high card
        return  "8-" + getHighCard(ranks);
    }


    /**
     * Checks if a Straight Flush can be made.
     * @param cards all cards
     * @param hasFlush true if a flush is possible
     * @param flushSuit suit of the flush if a flush is possible
     * @return
     */
    public static String getStraightFlush(List<String> cards, boolean hasFlush, char flushSuit){
        if (!hasFlush) {
            return "0";
        }
        HashMap<Integer, Integer> stCards = new HashMap<>();
        for (String card : cards) {
            if (card.charAt(card.length() - 1) == flushSuit) {
                stCards.put(getRank(card), stCards.getOrDefault(getRank(card), 0) + 1);
            }
        }
        return getSet(stCards);
    }


    /**
     * Checks if Quads can be made from the given hand.
     * @param cards HashMap storing count of cards with given rank
     * @return String representing best Quads and kicker that can be made, "0-0" if none exists
     */
    public static String getQuads(HashMap<Integer, Integer> cards) {
        int quadRank = 0;
        int kickerRank = 0;
        for (Integer card : cards.keySet()) {
            if (cards.get(card) == 4) {
                quadRank = card;
            } else {
                if (cards.get(card) != 0) {
                    kickerRank = Math.max(kickerRank, card);
                }
            }
        }
        return quadRank + "-" + kickerRank;
    }

    /**
     * Checks if a Full House can be made from the given hand.
     * @param cards HashMap storing count of cards with given rank
     * @return String representing best Full House that can be made, "0-0" if none can be made
     */
    public static String getFullHouse(HashMap<Integer, Integer> cards) {
        int maxSetRank = 0;
        int maxPairRank = 0;
        for (Integer card : cards.keySet()) {
            if (cards.get(card) == 3) {
                int temp = maxSetRank;
                if (card > maxSetRank) {
                    maxSetRank = card;
                    if (temp != 0) {
                        maxPairRank = Math.max(maxPairRank, temp);
                    }
                } else {
                    maxPairRank = Math.max(maxPairRank, card);
                }
            }
            if (cards.get(card) == 2 && card > maxPairRank) {
                maxPairRank = card;
            }
        }
        return maxSetRank + "-" + maxPairRank;
    }

    /**
     * Checks if a Flush can be made from the given hand.
     * @param suits HashMap storing count of cards with given suit
     * @param cards List storing all cards in player's hand
     * @return String representing best Flush, "0" if no Flush is possible
     */
    public static String getFlush(HashMap<Character, Integer> suits, List<String> cards) {

        char flushSuit = '0';
        for (Character suit : suits.keySet()) {
            if (suits.get(suit) >= 5) {
                flushSuit = suit;
                break;
            }
        }

        if (flushSuit == '0') {
            return "0";
        }
        List<String> copy = new ArrayList<>(cards);
        Collections.sort(copy, new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return getRank(s) - getRank(t1);
            }
        });
        String kickers = "";
        for (int i = cards.size() - 1; i >= 0; i--) {
            String c = cards.get(i);
            if (c.charAt(c.length() - 1) == flushSuit) {
                kickers += getRank(c) + "-";
            }
        }
        return kickers;
    }

    /**
     * Checks is a Flush can be made given the current hand.
     * @param suits HashMap storing count of cards with given suits
     * @return true if Flush is possible, false otherwise
     */
    public static boolean checkFlush(HashMap<Character, Integer> suits) {
        for (Integer i : suits.values()) {
            if (i >= 3) {
                return true;
            }
        }
        return false;
    }


    /**
     * Checks if a Straight can be made the current hand.
     * @param cards HashMap storing count of cards with given rank
     * @return String representing highest card in the best straight possible, "0" if no straight is possible
     */
    public static String getStraight(HashMap<Integer, Integer> cards) {
        boolean possibleWheel = false;
        if (cards.containsKey(14) && cards.get(14) != 0) {
            possibleWheel = true;
        }
        for (int i = 14; i >= 5; i--) {
            int start = i;
            int count = 0;
            while (count < 5) {
                if (!cards.containsKey(i) || cards.get(i) <= 0) {
                    break;
                }
                if (count == 4) {
                    return Integer.toString(start);
                }
                i --;
                count ++;
            }
        }
        if (possibleWheel) {
            return getWheel(cards);
        }
        return "0";
    }

    /**
     * Checks if there is a A-5 Straight
     * @param cards ranks of the cards
     * @return "5" if there is wheel, "0" otherwise
     */
    private static String getWheel(HashMap<Integer, Integer> cards) {
        for (int i = 2; i <= 5; i++) {
            if (!cards.containsKey(i) || cards.get(i) == 0) {
                return "0";
            }
        }
        return "5";
    }

    /**
     * Checks if a Set (3 of a kind) can be made given the current hand.
     * @param cards HashMap storing count of cards with given rank
     * @return String representing the best Set, "0-0-0" if no set is possible
     */
    public static String getSet(HashMap<Integer, Integer> cards) {
        int bigKicker = 0;
        int smallKicker = 0;
        int set = 0;
        for (Integer card : cards.keySet()) {
            if (cards.get(card) == 3) {
                set = Math.max(card, set);
            } else if (cards.get(card) != 0){
                if (card > bigKicker) {
                    smallKicker = bigKicker;
                    bigKicker = card;
                }
                else if (card > smallKicker) {
                    smallKicker = card;
                }
            }
        }
        return set + "-" + bigKicker + "-" + smallKicker;
    }

    /**
     * Checks if Two Pair can be made given the current hand.
     * @param cards HashMap storing count of cards with given rank
     * @return String representing best Two Pair, "0-0-0" if not possible
     */
    public static String getTwoPair(HashMap<Integer, Integer> cards) {
        int bigPair = 0;
        int smallPair = 0;
        int kicker = 0;
        for (Integer card : cards.keySet()) {
            if (cards.get(card) == 2) {
                if (card > bigPair) {
                    kicker = Math.max(kicker, smallPair);
                    smallPair = bigPair;
                    bigPair = card;
                } else if (card > smallPair) {
                    kicker = Math.max(kicker, smallPair);
                    smallPair = card;
                } else {
                    kicker = Math.max(kicker, card);
                }
            } else if (cards.get(card) > 0) {
                kicker = Math.max(kicker, card);
            }
        }

        if (bigPair == 0 || smallPair == 0) {
            return "0";
        }
        return bigPair + "-" + smallPair + "-" + kicker;
    }

    /**
     * Checks if a Pair can be made given the current hand.
     * @param cards HashMap storing count of cards with given rank
     * @return String representing the best Pair that can be made, "0-0-0-0" otherwise
     */
    public static String getPair(HashMap<Integer, Integer> cards) {
        int pair = 0;
        String kickers = "-";
        for (Integer card : cards.keySet()) {
            if (cards.get(card) == 2) {
                pair = card;
                break;
            }
        }

        if (pair == 0) return "0";
        List<Integer> allCards = new ArrayList<>();
        for (Integer card : cards.keySet()) {
            if (cards.get(card) > 0) {
                allCards.add(card);
            }
        }
        Collections.sort(allCards);
        for (int i = allCards.size() - 1; i >= 2; i --) {
            int rankVal = allCards.get(i);
            if (rankVal != pair) {
                kickers += rankVal + "-";
            }
        }
        return pair + kickers;
    }

    /**
     * Returns the 5 highest ranked cards in the hand for High GUI.Card.
     * @param ranks HashMap storing count of cards with given rank
     * @return String representing the best High GUI.Card hand.
     */
    public static String getHighCard(HashMap<Integer, Integer> ranks) {
        List<Integer> allCards = new ArrayList<>();
        for (Integer key : ranks.keySet()) {
            if (ranks.get(key) > 0) {
                allCards.add(key);
            }
        }
        String kickers = "";
        Collections.sort(allCards);
        for (int i = allCards.size() - 1; i >= 2; i--) {
            kickers += allCards.get(i) + "-";
        }
        return kickers;
    }

    /**
     * Determines the integer rank of a card. The greater the integer value, the higher the rank the card is
     * @param card card to be ranked
     * @return int representing the rank of a card.
     */
    public static int getRank(String card) {
        try {
            int num = Integer.parseInt(card.substring(0, card.length() - 1));
            return num;
        } catch (NumberFormatException e) {
            switch (card.charAt(0)) {
                case 'A': return 14;
                case 'K': return 13;
                case 'Q': return 12;
                case 'J': return 11;
                case 'T': return 10;
            }
        }
        throw new RuntimeException("Invalid Input in getRank()");
    }

}
