package tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Test {
    public static void main(String [] args) {
        List<String> ourHand = new ArrayList<>();
        ourHand.add("Qs");
        ourHand.add("Qc");
        List<String> flop = new ArrayList<>();
        flop.add("8c");
        flop.add("8h");
        flop.add("5s");

        List<String> oppRange = new ArrayList<>();
        oppRange.add("TT");


        List<String> bugRange = new Range(.10).range;

        System.out.println(Calculator.getEquity(flop, ourHand, oppRange));
    }
}
