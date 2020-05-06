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
    public static String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

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
     * Calculates equity of hand by giving percentage of hands we win given the flop.
     *
     * @param flop     first 3 cards shown
     * @param ourHand  hand we currently hold ex) {"As", "Kd"}
     * @param oppRange range of hands our opponent could have
     * @return
     */
    public static double getEquity(List<String> flop, String[] ourHand, List<String> oppRange) {
        int totalHands = 0;
        int handsWon = 0;
        HashSet<String> deadCards = new HashSet<String>();


        HashMap<Integer, Integer> oppRanks = new HashMap<>();
        HashMap<Character, Integer> oppSuits = new HashMap<>();
        List<String> oppCards = new ArrayList<>(flop);
        populate(oppRanks, oppSuits, flop);


        HashMap<Integer, Integer> ourRanks = new HashMap<>();
        HashMap<Character, Integer> ourSuits = new HashMap<>();
        List<String> ourCards = new ArrayList<>(flop);
        ourCards.addAll(Arrays.asList(ourHand));
        populate(ourRanks, ourSuits, ourCards);


        // add dead cards
        for (String card : flop) {
            deadCards.add(card);
        }
        for (String card : ourHand) {
            deadCards.add(card);
        }


        for (int i = 0; i < allCards.length; i++) {
            if (deadCards.contains(allCards[i])) continue;


            // add curr card to our util maps
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
                // add curr card to our util maps
                int rj = CardUtils.getRank(allCards[j]);
                char sj = allCards[j].charAt(allCards[j].length() - 1);
                oppCards.add(allCards[j]);
                ourCards.add(allCards[j]);
                ourRanks.put(rj, ourRanks.getOrDefault(rj, 0) + 1);
                oppRanks.put(rj, oppRanks.getOrDefault(rj, 0) + 1);
                oppSuits.put(sj, oppSuits.getOrDefault(sj, 0) + 1);
                ourSuits.put(sj, ourSuits.getOrDefault(sj, 0) + 1);


                for (String rangeHand : oppRange) {
                    for (String[] hand : getAllHands(rangeHand)) {
                        boolean impossibleHand = false;
                        for (String card : hand) {
                            if (deadCards.contains(card)) {
                                impossibleHand = true;
                            }
                            int or = CardUtils.getRank(card);
                            char os = card.charAt(card.length() - 1);
                            oppCards.add(card);
                            oppRanks.put(or, oppRanks.getOrDefault(or, 0) + 1);
                            oppSuits.put(os, oppSuits.getOrDefault(os, 0) + 1);
                        }

                        if (!impossibleHand) {
                            System.out.println("our cards : " + ourCards.toString());
                            System.out.println(" opponent's cards : " + oppCards.toString());
                            int result = findWin(ourRanks, oppRanks, ourSuits, oppSuits, ourCards, oppCards);
                            System.out.println(result + " : 1 if we win");
                            System.out.println(" ");
                            handsWon += result;
                            totalHands++;

                        }

                        // remove added cards
                        for (String card : hand) {
                            int or = CardUtils.getRank(card);
                            char os = card.charAt(card.length() - 1);
                            oppCards.remove(oppCards.size() - 1);
                            oppRanks.put(or, oppRanks.getOrDefault(or, 0) - 1);
                            oppSuits.put(os, oppSuits.getOrDefault(os, 0) - 1);
                        }
                    }
                }

                // remove j card
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
        return ((double) handsWon) / totalHands;
    }


    public static int findWin(HashMap<Integer, Integer> ourRanks, HashMap<Integer, Integer> oppRanks,
                              HashMap<Character, Integer> ourSuits, HashMap<Character, Integer> oppSuits,
                              List<String> ourCards, List<String> oppCards) {

        String ourHand = CardUtils.findBestHand(ourRanks, ourSuits, ourCards);
        String oppHand = CardUtils.findBestHand(oppRanks, oppSuits, oppCards);

        if (ourHand.charAt(0) < oppHand.charAt(0)) {
            return 1;
        } else if (ourHand.charAt(0) > oppHand.charAt(0)) {
            return 0;
        } else {

            for (int i = 0; i < Math.min(ourHand.length(), oppHand.length()); i++) {
                if (Character.isDigit(ourHand.charAt(i)) && Character.isDigit(oppHand.charAt(i))) {
                    if (ourHand.charAt(i) > oppHand.charAt(i)) return 1;
                    if (ourHand.charAt(i) < oppHand.charAt(i)) return 0;
                }
            }
        }
        return 0;
    }


    private static List<String[]> getAllHands(String rangeHand) {
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


    private static void populate(HashMap<Integer, Integer> ranks, HashMap<Character, Integer> suits, List<String> allCards) {
        for (String card : allCards) {
            int rank = CardUtils.getRank(card);
            char suit = card.charAt(card.length() - 1);
            ranks.put(rank, ranks.getOrDefault(rank, 0) + 1);
            suits.put(suit, suits.getOrDefault(suit, 0) + 1);
        }
    }


    public static double getEquity2(List<String> flop, String[] ourHand, List<String> oppRange) {
        int totalHands = 0;
        int handsWon = 0;
        HashSet<String> deadCards = new HashSet<String>();


        HashMap<Integer, Integer> oppRanks = new HashMap<>();
        HashMap<Character, Integer> oppSuits = new HashMap<>();
        List<String> oppCards = new ArrayList<>(flop);
        populate(oppRanks, oppSuits, flop);


        HashMap<Integer, Integer> ourRanks = new HashMap<>();
        HashMap<Character, Integer> ourSuits = new HashMap<>();
        List<String> ourCards = new ArrayList<>(flop);
        ourCards.addAll(Arrays.asList(ourHand));
        populate(ourRanks, ourSuits, ourCards);


        // add dead cards
        for (String card : flop) {
            deadCards.add(card);
        }
        for (String card : ourHand) {
            deadCards.add(card);
        }

        for (String rangeHand : oppRange) {
            for (String[] hand : getAllHands(rangeHand)) {
                int curr_wins = 0;
                int curr_checked = 0;
                boolean impossibleHand = false;
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

                        curr_wins += result;
                        handsWon += result;
                        curr_checked ++;
                        totalHands ++;

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
                System.out.println("total hands won with opponent hand " + Arrays.toString(hand) + " is " + curr_wins);
                System.out.println("total hands checked is " + curr_checked);
                System.out.println(" ");
            }

        }
        return ((double) handsWon) / totalHands;
    }


}
