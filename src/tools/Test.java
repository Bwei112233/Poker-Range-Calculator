package tools;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String [] args) {
        List<String> oppRange = new ArrayList<>();
        oppRange.add("KQs");
        String [] ourHand = {"As", "10s"};
        List<String> flop = new ArrayList<>();
        flop.add("Ad");
        flop.add("Qh");
        flop.add("2d");
//        System.out.println("first method : " + Calculator.getEquity(flop, ourHand, oppRange));
        System.out.println("second method : " + Calculator.getEquity2(flop, ourHand, oppRange));
    }

}
