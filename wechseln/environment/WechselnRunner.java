package wechseln.environment;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.TreeSearch;
import aima.core.search.informed.AStarSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;
import aima.core.util.datastructure.XYLocation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

public class WechselnRunner {
    public static final int IDS = 0;
    public static final int ASTAR1 = 1;
    public static final int ASTAR2 = 2;

    private WechselnBoard board;

    public WechselnRunner(WechselnBoard board) {
        this.board = board;
    }

    public String run(int strategy) {
        if(strategy == IDS) {
            return runIDS();
        } else
        if(strategy == ASTAR1) {
            return runAStar1();
        } else
        if(strategy == ASTAR2) {
            return runAStar2();
        }
        return "";
    }

    private String convertToIndonesian(String direction) {
        if(direction.equals("UP"))
            return "ATAS";
        else if(direction.equals("LEFT"))
            return "KIRI";
        else if(direction.equals("DOWN"))
            return "BAWAH";
        else return "KANAN";
    }

    private String runIDS() {
        try {
            Problem problem = new Problem(board, WechselnFunctionFactory
            .getActionsFunction(), WechselnFunctionFactory
            .getResultFunction(), new WechselnGoalTest());

            Search search = new IterativeDeepeningSearch();

            SearchAgent agent = new SearchAgent(problem, search);

            String output = "";
            output += getActionNames(agent.getActions());

            System.out.println(getNumberOfNodesExpanded(agent.getInstrumentation()));

            return output;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String runAStar1() {
        try {
            Problem problem = new Problem(board, WechselnFunctionFactory
                .getActionsFunction(), WechselnFunctionFactory
                .getResultFunction(), new WechselnGoalTest());

            Search search = new AStarSearch(new TreeSearch(),
                new ManhattanHeuristicFunction());

            SearchAgent agent = new SearchAgent(problem, search);
            String output = "";

            output += getActionNames(agent.getActions());

            System.out.println(getNumberOfNodesExpanded(agent.getInstrumentation()));

            return output;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String runAStar2() {
        try {
            Problem problem = new Problem(board, WechselnFunctionFactory
                .getActionsFunction(), WechselnFunctionFactory
                .getResultFunction(), new WechselnGoalTest());

            Search search = new AStarSearch(new TreeSearch(),
                new ZeroHeuristicFunction());

            SearchAgent agent = new SearchAgent(problem, search);
            String output = "";

            output += getActionNames(agent.getActions());

            System.out.println(getNumberOfNodesExpanded(agent.getInstrumentation()));

            return output;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getActionNames(List<Action> actions) {
        ArrayList<String> actionsOne = new ArrayList<String>();
        ArrayList<String> actionsTwo = new ArrayList<String>();

        for(int i = 0; i < actions.size(); i++) {
            StringTokenizer tok = new StringTokenizer(((DynamicAction) actions.get(i)).getName(), "-");
            actionsOne.add(tok.nextToken());
            actionsTwo.add(tok.nextToken());
        }

        String output = "";
        for (String action : actionsOne)
            output += convertToIndonesian(action) + " ";
        output += "\n";
        for (String action : actionsTwo)
            output += convertToIndonesian(action) + " ";

        return output;
    }

    private String getNumberOfNodesExpanded(Properties properties) {
        String res = "";
        Iterator<Object> keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            if (key.equals("nodesExpanded"))
                res += "" + Integer.parseInt(properties.getProperty(key));
        }
        return res;
    }
}