package tools;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Class to calculate equity of your hand given range
 */
public class Calculator {
    /**
     * Provides array of all cards
     * Spades -> s
     * Diamonds -> d
     * Clubs -> c
     * Hearts -> h
     */
    public static String[] allCards = new String[52];
    public static String[] suits = {"s", "d", "c", "h"};
    public static String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A"};

    static {
        int i = 0;
        for (String r : ranks) {
            for (String s : suits) {
                allCards[i] = r + s;
                i++;
            }
        }
    }


    /**
     * Determines the winner of the given hand.
     *
     * @param ourRanks HashMap storing count of our cards with given rank
     * @param oppRanks HashMap storing count of opponent's cards with given rank
     * @param ourSuits HashMap storing count of our cards with given suits
     * @param oppSuits HashMap storing count of opponent's cards with given rank
     * @param ourCards List storing all cards in our hand
     * @param oppCards List storing all cards in opponent's hand
     * @return
     */
    public static int findWin(HashMap<Integer, Integer> ourRanks, HashMap<Integer, Integer> oppRanks,
                              HashMap<Character, Integer> ourSuits, HashMap<Character, Integer> oppSuits,
                              List<String> ourCards, List<String> oppCards) {

        String ourHand = CardUtils.findBestHand(ourRanks, ourSuits, ourCards);
        String oppHand = CardUtils.findBestHand(oppRanks, oppSuits, oppCards);

//        System.out.println("our cards are " + ourCards);
//        System.out.println("opponents cards are " + oppCards);
//        System.out.println("Our hand is " + CardUtils.ranks.get(ourHand.charAt(0)) + " with key " + ourHand);
//        System.out.println("Opponents hand is " + CardUtils.ranks.get(oppHand.charAt(0)) + " with key " + oppHand);


        if (ourHand.charAt(0) < oppHand.charAt(0)) {
            return 1;
        } else if (ourHand.charAt(0) > oppHand.charAt(0)) {
            return 0;
        } else {
            String[] ourHandArr = ourHand.split("-");
            String[] oppHandArr = oppHand.split("-");
            for (int i = 0; i < Math.min(ourHandArr.length, oppHandArr.length); i++) {
                int ourKicker = Integer.parseInt(ourHandArr[i]);
                int oppKicker = Integer.parseInt(oppHandArr[i]);
                if (ourKicker > oppKicker) return 1;
                if (ourKicker < oppKicker) return 0;
            }
        }
        return 0;
    }

    /**
     * Determines all hands possible for a given range hand.
     * ex) There are 4 possible variations of Ace Queen Suited, denoted AQs : [AQs, AQc, AQ, AQh]
     * @param rangeHand hand considered
     * @return List of possible variations of the hand considered
     */
    public static List<String[]> getAllHands(String rangeHand) {
        List<String[]> hands = new ArrayList<>();
        String card1 = rangeHand.substring(0, 1);
        String card2 = rangeHand.substring(1, 2);
        if (rangeHand.charAt(rangeHand.length() - 1) == 's') {
            for (String suit : suits) {
                hands.add(new String[]{card1 + suit, card2 + suit});
            }
        } else {
            for (int i = 0; i < suits.length; i++) {
                for (int j = i + 1; j < suits.length; j++) {
                    hands.add(new String[]{card1 + suits[i], card2 + suits[j]});
                }
            }
        }
        return hands;
    }


    /**
     * Initializes data structures necessary for ranking hands.
     * @param ranks HashMap storing count of our cards with given rank
     * @param suits HashMap storing count of our cards with given suit
     * @param allCards List storing all cards in given hand
     */
    private static void populate(HashMap<Integer, Integer> ranks, HashMap<Character, Integer> suits, List<String> allCards) {
        for (String card : allCards) {
            int rank = CardUtils.getRank(card);
            char suit = card.charAt(card.length() - 1);
            ranks.put(rank, ranks.getOrDefault(rank, 0) + 1);
            suits.put(suit, suits.getOrDefault(suit, 0) + 1);
        }
    }

    /**
     * Determines the equity of a hand given the opponent's range.
     * @param flop first three community cards
     * @param ourHand our hole cards
     * @param oppRange range of hands our opponent could have
     * @return double representing the equity of our hand
     */
    public static double getEquity(List<String> flop, List<String> ourHand, List<String> oppRange) {
        double totalHands = 0;
        double handsWon = 0;

        double no_flush_wins = -1;
        double total_no_flush_hands = 0;

        HashSet<String> finalDeadCards = new HashSet<String>();


        HashMap<Integer, Integer> oppRanks = new HashMap<>();
        HashMap<Character, Integer> oppSuits = new HashMap<>();
        List<String> oppCards = new ArrayList<>(flop);
        populate(oppRanks, oppSuits, flop);


        HashMap<Integer, Integer> ourRanks = new HashMap<>();
        HashMap<Character, Integer> ourSuits = new HashMap<>();
        List<String> ourCards = new ArrayList<>(flop);
        ourCards.addAll(ourHand);
        populate(ourRanks, ourSuits, ourCards);


        // add dead cards
        for (String card : flop) {
            finalDeadCards.add(card);
        }
        for (String card : ourHand) {
            finalDeadCards.add(card);
        }

        for (String rangeHand : oppRange) {
            for (String[] hand : getAllHands(rangeHand)) {
                int curr_wins = 0;
                int curr_checked = 0;
                boolean impossibleHand = false;
                HashSet<String> deadCards = (HashSet<String>) finalDeadCards.clone();
                // add cards from opponent's current hand to board
                for (String card : hand) {
                    if (deadCards.contains(card)) {
                        impossibleHand = true;
                    }
                    int or = CardUtils.getRank(card);
                    char os = card.charAt(card.length() - 1);
                    oppCards.add(card);
                    oppRanks.put(or, oppRanks.getOrDefault(or, 0) + 1);
                    oppSuits.put(os, oppSuits.getOrDefault(os, 0) + 1);
                    deadCards.add(card);
                }


                // avoid unnecessary work when no flush is possible
                boolean canMakeFlush = CardUtils.checkFlush(oppSuits);
                if (!canMakeFlush && no_flush_wins != -1) {
                    handsWon += no_flush_wins;
                    totalHands += total_no_flush_hands;
                    // remove cards
                    for (String card : hand) {
                        int or = CardUtils.getRank(card);
                        char os = card.charAt(card.length() - 1);
                        oppCards.remove(oppCards.size() - 1);
                        oppRanks.put(or, oppRanks.getOrDefault(or, 0) - 1);
                        oppSuits.put(os, oppSuits.getOrDefault(os, 0) - 1);
                        deadCards.remove(card);
                    }
                    continue;
                }


                for (int i = 0; i < allCards.length; i++) {
                    if (impossibleHand) {
                        break;
                    }
                    if (deadCards.contains(allCards[i])) continue;
                    // add "i" card to our util maps
                    int r = CardUtils.getRank(allCards[i]);
                    char s = allCards[i].charAt(allCards[i].length() - 1);
                    oppCards.add(allCards[i]);
                    ourCards.add(allCards[i]);
                    ourRanks.put(r, ourRanks.getOrDefault(r, 0) + 1);
                    oppRanks.put(r, oppRanks.getOrDefault(r, 0) + 1);
                    oppSuits.put(s, oppSuits.getOrDefault(s, 0) + 1);
                    ourSuits.put(s, ourSuits.getOrDefault(s, 0) + 1);


                    for (int j = i + 1; j < allCards.length; j++) {
                        if (deadCards.contains(allCards[j])) continue;
                        // add j card to our util maps
                        int rj = CardUtils.getRank(allCards[j]);
                        char sj = allCards[j].charAt(allCards[j].length() - 1);
                        oppCards.add(allCards[j]);
                        ourCards.add(allCards[j]);
                        ourRanks.put(rj, ourRanks.getOrDefault(rj, 0) + 1);
                        oppRanks.put(rj, oppRanks.getOrDefault(rj, 0) + 1);
                        oppSuits.put(sj, oppSuits.getOrDefault(sj, 0) + 1);
                        ourSuits.put(sj, ourSuits.getOrDefault(sj, 0) + 1);


                        int result = findWin(ourRanks, oppRanks, ourSuits, oppSuits, ourCards, oppCards);
//                        System.out.println(result + " end");
//                        System.out.println(" ");

                        curr_wins += result;
                        handsWon += result;
                        curr_checked++;
                        totalHands++;

                        // remove "j" card
                        oppCards.remove(oppCards.size() - 1);
                        ourCards.remove(ourCards.size() - 1);
                        ourRanks.put(rj, ourRanks.getOrDefault(rj, 0) - 1);
                        oppRanks.put(rj, oppRanks.getOrDefault(rj, 0) - 1);
                        oppSuits.put(sj, oppSuits.getOrDefault(sj, 0) - 1);
                        ourSuits.put(sj, ourSuits.getOrDefault(sj, 0) - 1);
                    }

                    // remove "i" card
                    oppCards.remove(oppCards.size() - 1);
                    ourCards.remove(ourCards.size() - 1);
                    ourRanks.put(r, ourRanks.getOrDefault(r, 0) - 1);
                    oppRanks.put(r, oppRanks.getOrDefault(r, 0) - 1);
                    oppSuits.put(s, oppSuits.getOrDefault(s, 0) - 1);
                    ourSuits.put(s, ourSuits.getOrDefault(s, 0) - 1);
                }

                // remove added cards
                for (String card : hand) {
                    int or = CardUtils.getRank(card);
                    char os = card.charAt(card.length() - 1);
                    oppCards.remove(oppCards.size() - 1);
                    oppRanks.put(or, oppRanks.getOrDefault(or, 0) - 1);
                    oppSuits.put(os, oppSuits.getOrDefault(os, 0) - 1);
                    deadCards.remove(card);
                }

//                 update no flush hands
                if (!canMakeFlush && curr_checked != 0) {
                    no_flush_wins = curr_wins;
                    total_no_flush_hands = curr_checked;
                }

                // debug statements
                System.out.println("total hands won with opponent hand " + Arrays.toString(hand) + " is " + curr_wins);
                System.out.println("total hands checked is " + curr_checked);
                System.out.println("percent hands won is " + (double) curr_wins / curr_checked);
                System.out.println(" ");
            }

        }
        return ((double) handsWon) / totalHands;
    }
}
