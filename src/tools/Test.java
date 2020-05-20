package tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Class used to run tests/debug.
 */
public class Test {
    public static void main(String [] args) {
        List<String> ourHand = new ArrayList<>();
        ourHand.add("Ad");
        ourHand.add("Qd");

        List<String> flop = new ArrayList<>();
        flop.add("Js");
        flop.add("Th");
        flop.add("Tc");

        List<String> oppRange = new ArrayList<>();
//        oppRange.add("KQs");
        oppRange.add("AK");
//        oppRange.add("AKs");


        System.out.println(Calculator.getEquity(flop, ourHand, oppRange));
    }
}
