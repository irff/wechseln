package wechseln.environment;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;
import aima.core.util.datastructure.XYLocation;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class WechselnFunctionFactory {
    private static ActionsFunction _actionsFunction = null;
    private static ResultFunction _resultFunction = null;

    public static ActionsFunction getActionsFunction() {
        if(_actionsFunction == null) {
            _actionsFunction = new WechselnActionsFunction();
        }
        return _actionsFunction;
    }

    public static ResultFunction getResultFunction() {
        if(_resultFunction == null) {
            _resultFunction = new WechselnResultFunction();
        }
        return _resultFunction;
    }

    private static class WechselnActionsFunction implements ActionsFunction {
        @Override
        public Set<Action> actions(Object state) {
            WechselnBoard board = (WechselnBoard) state;

            Set<Action> actions = new LinkedHashSet<Action>();

            XYLocation courierOne = board.getCourierOne();
            XYLocation courierTwo = board.getCourierTwo();
            XYLocation[] couriers = { courierOne, courierTwo };

            ArrayList<String> actionsOne = new ArrayList<String>();
            ArrayList<String> actionsTwo = new ArrayList<String>();


            for(XYLocation courier : couriers) {
                ArrayList<String> acts = (courier == courierOne) ? actionsOne : actionsTwo;
                for(XYLocation surrounding: board.getSurroundings(courier)) {
                    if(board.canMove(courier, surrounding)) {
                        acts.add(board.getDirectionName(courier, surrounding));
                    }
                }
            }

            for(String actionOne : actionsOne) {
                for(String actionTwo : actionsTwo) {
                    actions.add(new WechselnAction(actionOne + "-" + actionTwo));
                }
            }

            return actions;
        }
    }

    private static class WechselnResultFunction implements ResultFunction {
        @Override
        public Object result(Object s, Action a) {
            if(a instanceof WechselnAction) {
                WechselnAction wa = (WechselnAction) a;
                WechselnBoard board = (WechselnBoard) s;
                WechselnBoard newBoard = board.copyWithout();

                XYLocation courierOne = board.getCourierOne();
                XYLocation courierTwo = board.getCourierTwo();
                XYLocation[] couriers = { courierOne, courierTwo };

                Set<XYLocation> customers = board.getCustomers();

                StringTokenizer tok = new StringTokenizer(wa.getName(), "-");

                String courierOneAction = tok.nextToken();
                String courierTwoAction = tok.nextToken();

                XYLocation newCourierOne = courierOne;
                XYLocation newCourierTwo = courierTwo;

                for(XYLocation courier : couriers) {
                    String action = (courier == courierOne) ? courierOneAction : courierTwoAction;
                    XYLocation newCourier = null;

                    if(action.equals(WechselnAction.MOVE_UP))
                        newCourier = board.getSurroundingLocation(courier, WechselnBoard.UP);
                    else if(action.equals(WechselnAction.MOVE_LEFT))
                        newCourier = board.getSurroundingLocation(courier, WechselnBoard.LEFT);
                    else if(action.equals(WechselnAction.MOVE_DOWN))
                        newCourier = board.getSurroundingLocation(courier, WechselnBoard.DOWN);
                    else if(action.equals(WechselnAction.MOVE_RIGHT))
                        newCourier = board.getSurroundingLocation(courier, WechselnBoard.RIGHT);

                    if(newCourier != null) {
                        if(courier == courierOne) newCourierOne = newCourier;
                        else newCourierTwo = newCourier;
                    }
                }

                Set<XYLocation> newCustomers = new LinkedHashSet<XYLocation>();
                
                for(XYLocation customer : customers) {
                    if(!customer.equals(newCourierOne) && !customer.equals(newCourierTwo))
                        newBoard.putElement(WechselnBoard.CUSTOMER, customer);
                }

                newBoard.putElement(WechselnBoard.COURIER_ONE, newCourierOne);
                newBoard.putElement(WechselnBoard.COURIER_TWO, newCourierTwo);

                s = newBoard;
            }
            return s;
        }
    }
}