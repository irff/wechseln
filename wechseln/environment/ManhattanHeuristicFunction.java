package wechseln.environment;

import java.util.Set;
import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

public class ManhattanHeuristicFunction implements HeuristicFunction {
    private int manhattanDistance(XYLocation a, XYLocation b) {
        return Math.abs(a.getXCoOrdinate() - b.getXCoOrdinate()) +
               Math.abs(a.getYCoOrdinate() - b.getYCoOrdinate());
    }

    @Override
    public double h(Object state) {
        WechselnBoard board = (WechselnBoard) state;

        XYLocation courierOne = board.getCourierOne();
        XYLocation courierTwo = board.getCourierTwo();

        Set<XYLocation> customers = board.getCustomers();

        int maxDist = 0;

        for(XYLocation customer : customers) {
            int distOne = manhattanDistance(courierOne, customer);
            int distTwo = manhattanDistance(courierTwo, customer);

            maxDist = Math.max(maxDist, Math.min(distOne, distTwo));
        }

        return maxDist;
    }
}