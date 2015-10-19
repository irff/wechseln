package wechseln.environment;

import aima.core.search.framework.GoalTest;

public class WechselnGoalTest implements GoalTest {
    @Override
    public boolean isGoalState(Object state) {
        WechselnBoard board = (WechselnBoard) state;
        return board.getNumberOfCustomers() == 0;
    }
}