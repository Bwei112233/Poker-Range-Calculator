package tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Test {
    public static void main(String [] args) {
        long start = System.nanoTime();
        Range range = new Range(.20);
        List<String> oppRange = range.range;
        List<String> oppRange1 = new ArrayList<>();
        oppRange1.add("T9");
        String [] ourHand = {"Ac", "Kc"};
        List<String> flop = new ArrayList<>();
        flop.add("As");
        flop.add("Qh");
        flop.add("2d");


//        System.out.println(CardUtils.getStraight(cards));
        System.out.println("second method : " + Calculator.getEquity(flop, ourHand, oppRange1));
        long endTime = System.nanoTime();


        long timeElapsed = endTime - start;
        System.out.println("Execution time : " + timeElapsed / 1000000 );

    }

}
