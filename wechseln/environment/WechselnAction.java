package wechseln.environment;

import aima.core.agent.impl.DynamicAction;

public class WechselnAction extends DynamicAction {
    public static final String MOVE_UP      = "UP";
    public static final String MOVE_DOWN    = "DOWN";
    public static final String MOVE_LEFT    = "LEFT";
    public static final String MOVE_RIGHT   = "RIGHT";

    public WechselnAction(String type) {
        super(type);
    }
}