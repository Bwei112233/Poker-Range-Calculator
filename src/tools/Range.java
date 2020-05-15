package tools;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Range {

    /**
     * Stores all starting hands in poker based on EV in descending order
     * Source: https://www.tightpoker.com/poker_hands.html
     */
    static String ranks = "AA,KK,QQ,JJ,AKs,AQs,TT,AK,AJs,KQs,99,ATs,AQ,KJs,88,QJs,KTs,A9s,AJ,QTs,KQ,77,JTs,A8s,K9s,AT,A5s"+
            ",A7s,KJ,66,T9s,A4s,Q9s,J9s,QJ,A6s,55,A3s,K8s,KT,98s,T8s,K7s,A2s,87s,QT,Q8s,44,A9,J8s,76s,JT,97s," +
            "K6s,K5s,K4s,T7s,Q7s,K9,65s,T9,86s,A8,J7s,33,54s,Q6s,K3s,Q9,75s,22,J9,64s,Q5s,K2s,96s,Q3s,J8,98,T8," +
            "97,A7,T7,Q4s,Q8,J5s,T6,75,J4s,74s,K8,86,53s,K7,63s,J6s,85,T6s,76,A6,T2,95s,84,62,T5s,95,A5,Q7,T5," +
            "87,83,65,Q2s,94,74,54,A4,T4,82,64,42,J7,93,85s,73,53,T3,63" +
            ",K6,J6,96,92,72,52,Q4,K5,J5,43s,Q3,43,K4,J4,T4s,Q6,Q2,J3s,J3,T3s,A3,Q5,J2,84s,82s,42s,93s," +
            "73s,K3,J2s,92s,52s,K2,T2s,62s,32,A2,83s,94s,72s,32s";

    public static String rangeChart = "AA AKs AQs AJs ATs A9s A8s A7s A6s A5s A4s A3s A2s " +
            "AKo KK KQs KJs KTs K9s K8s K7s K6s K5s K4s K3s K2s " +
            "AQo KQo QQ QJs QTs Q9s Q8s Q7s Q6s Q5s Q4s Q3s Q2s " +
            "AJo KJo QJo JJ JTs J9s J8s J7s J6s J5s J4s J3s J2s " +
            "ATo KTo QTo JTo TT T9s T8s T7s T6s T5s T4s T3s T2s " +
            "A9o K9o Q9o J9o T9o 99 98s 97s 96s 95s 94s 93s 92s " +
            "A8o K8o Q8o J8o T8o 98o 88 87s 86s 85s 84s 83s 82s " +
            "A7o K7o Q7o J7o T7o 97o 87o 77 76s 75s 74s 73s 72s " +
            "A6o K6o Q6o J6o T6o 96o 86o 76o 66 65s 64s 63s 62s " +
            "A5o K5o Q5o J5o T5o 95o 85o 75o 65o 55 54s 53s 52s " +
            "A4o K4o Q4o J4o T4o 94o 84o 74o 64o 54o 44 43s 42s " +
            "A3o K3o Q3o J3o T3o 93o 83o 73o 63o 53o 43o 33 32s " +
            "A2o K2o Q2o J2o T2o 92o 82o 72o 62o 52o 42o 32o 22 ";

    public final static String [] rankings = ranks.split(",");

    /** stores percent of hands opponent would play given their position and
     * action. Percentages are not 100% accurate and subject to change
      */
    public static HashMap<String, Double> percentages = new HashMap<>();
    static {
        getPercentages();
    }

    /**
     * Stores range based on multiple actions opponent can take.
     */
    public List<String> range;


    /**
     * Constructs range based on the percentage of hands that opponent would take given action.
     * @param percent Percent of hands to consider
     */
    public Range(double percent) {
        range = new ArrayList<>();
        int numHands = (int) (Math.ceil(169 * percent));
        for (int i = 0; i < numHands; i++) {
            range.add(rankings[i]);
        }
    }

    /**
     * Generates range based on opponent position and opponent hand.
     * @param oppPos position our opponent is in
     * @param oppAction action opponent takes preflop
     */
    public Range(int oppPos, int oppAction) {
        this(percentages.get(oppPos + "-" + oppAction));
    }


    /**
     * Constructs range based on customRange
     * @param customRange custom range to build off of
     */
    public Range(List<String> customRange) {
        range = new ArrayList<>(customRange);
    }



    private static void getPercentages() {
        percentages.put("0-0", .1);
        percentages.put("0-1", .14);
        percentages.put("0-2", .1);
        percentages.put("0-3", .1);
        percentages.put("1-0", .1);
        percentages.put("1-1", .1);
        percentages.put("1-2", .1);
        percentages.put("1-3", .1);
        percentages.put("2-0", .1);
        percentages.put("2-1", .1);
        percentages.put("2-2", .1);
        percentages.put("2-3", .1);
        percentages.put("3-0", .1);
        percentages.put("3-1", .1);
        percentages.put("3-2", .1);
        percentages.put("3-3", .1);
        percentages.put("4-0", .1);
        percentages.put("4-1", .1);
        percentages.put("4-2", .1);
        percentages.put("4-3", .1);
        percentages.put("5-0", .1);
        percentages.put("5-1", .1);
        percentages.put("5-2", .1);
        percentages.put("5-3", .1);
        percentages.put("6-0", .1);
        percentages.put("6-1", .1);
        percentages.put("6-2", .1);
        percentages.put("6-3", .1);
        percentages.put("7-0", .1);
        percentages.put("7-1", .1);
        percentages.put("7-2", .1);
        percentages.put("7-3", .1);
        percentages.put("8-0", .1);
        percentages.put("8-1", .1);
        percentages.put("8-2", .1);
        percentages.put("8-3", .1);
    }
}
