package tools;
import java.util.ArrayList;
import java.util.List;

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

    public final static String [] rankings = ranks.split(",");


    /**
     * Stores range based on multiple actions villain can take.
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
     * Constructs range based on customRange
     * @param customRange custom range to build off of
     */
    public Range(List<String> customRange) {
        range = new ArrayList<>(customRange);
    }
}