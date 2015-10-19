package wechseln.environment;

import aima.core.search.framework.HeuristicFunction;

public class ZeroHeuristicFunction implements HeuristicFunction {
    @Override
    public double h(Object state) {
        return 0;
    }
}