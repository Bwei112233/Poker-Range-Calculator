package tools;

import java.util.*;

public class CardUtils {
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

    public static String findBestHand(HashMap<Integer, Integer> ranks, HashMap<Character, Integer> suits, List<String> cards) {
        String bHand = "";

        // check quads
        bHand = getQuads(ranks);
        if (bHand.charAt(0) != '0') return 1 + bHand;

        // check full house
        bHand = getFullHouse(ranks);
        if (bHand.charAt(0) != '0') return 2 + bHand;

        // check flush
        bHand = getFlush(suits, cards);
        if (bHand.charAt(0) != '0') return 3 + bHand;

        // check straight
        bHand = getStraight(ranks);
        if (bHand.charAt(0) != '0') return 4 + bHand;

        // check set
        bHand = getSet(ranks);
        if (bHand.charAt(0) != '0') return 5 + bHand;

        // check two pair
        bHand = getTwoPair(ranks);
        if (bHand.charAt(0) != '0') return 6 + bHand;

        // check pair
        bHand = getPair(ranks);
        if (bHand.charAt(0) != '0') return 7 + bHand;

        // high card
        return  8 + getHighCard(ranks);
    }

    public static String getStraightFlush(HashMap<Integer, Integer> ranks, HashMap<Integer, Integer> suits){return "";}

    public static String getQuads(HashMap<Integer, Integer> cards) {
        int quadRank = 0;
        int kickerRank = 0;
        for (Integer card : cards.keySet()) {
            if (cards.get(card) == 4) {
                quadRank = card;
            } else {
                kickerRank = card;
            }
        }
        return quadRank + "+" + kickerRank;
    }


    public static String getFullHouse(HashMap<Integer, Integer> cards) {
        int maxSetRank = 0;
        int maxPairRank = 0;
        for (Integer card : cards.keySet()) {
            if (cards.get(card) == 3 && card > maxSetRank) {
                maxSetRank = card;
            }
            if (cards.get(card) == 2 && card > maxPairRank) {
                maxPairRank = card;
            }
        }
        return maxSetRank + "-" + maxPairRank;
    }

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

    public static String getStraight(HashMap<Integer, Integer> cards) {
        for (int i = 14; i >= 5; i--) {
            int start = i;
            int count = 0;
            while (count <= 5) {
                if (!cards.containsKey(i) || cards.get(i) <= 0) {
                    break;
                }
                if (count == 5) {
                    return Integer.toString(start);
                }
                i --;
                count ++;
            }
        }
        return "0";
    }


    public static String getSet(HashMap<Integer, Integer> cards) {
        int bigKicker = 0;
        int smallKicker = 0;
        int set = 0;
        for (Integer card : cards.keySet()) {
            if (cards.get(card) == 3) {
                set = Math.max(card, set);
            } else {
                if (card > bigKicker) {
                    bigKicker = card;
                }
                else if (card > smallKicker) {
                    smallKicker = card;
                }
            }
        }
        return set + "-" + bigKicker + "-" + smallKicker;
    }

    public static String getTwoPair(HashMap<Integer, Integer> cards) {
        int bigPair = 0;
        int smallPair = 0;
        int kicker = 0;
        for (Integer card : cards.keySet()) {
            if (cards.get(card) == 2) {
                if (card > bigPair) {
                    smallPair = bigPair;
                    bigPair = card;
                } else if (card > smallPair) {
                    smallPair = card;
                }
            } else {
                kicker = Math.max(kicker, card);
            }
        }

        if (bigPair == 0 || smallPair == 0) {
            return "0";
        }
        return bigPair + "-" + smallPair + "-" + kicker;
    }

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
            }
        }
        throw new RuntimeException("Invalid Input in getRank()");
    }

}
