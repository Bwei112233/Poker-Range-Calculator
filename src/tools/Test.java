package tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Test {
    public static void main(String [] args) {
        List<String> ourHand = new ArrayList<>();
        ourHand.add("Ah");
        ourHand.add("As");

        List<String> flop = new ArrayList<>();
        flop.add("3h");
        flop.add("5d");
        flop.add("9c");

        List<String> oppRange = new ArrayList<>();
        oppRange.add("AKs");


        HashMap<Integer, Integer> ranks = new HashMap<>();
        ranks.put(10, 2);
        ranks.put(2, 3);
        ranks.put(12, 1);
        ranks.put(9, 1);



        System.out.println(Calculator.getEquity(flop, ourHand, oppRange));
    }
}
