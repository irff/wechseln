package wechseln.environment;

import aima.core.util.datastructure.XYLocation;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class WechselnBoard  {
    public static final int COURIER_ONE = 0;
    public static final int COURIER_TWO = 1;
    public static final int RIVER = 2;
    public static final int CUSTOMER = 3;
    public static final int ONEWAY_UP = 4;
    public static final int ONEWAY_RIGHT = 5;
    public static final int ONEWAY_DOWN = 6;
    public static final int ONEWAY_LEFT = 7;

    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int DOWN = 2;
    public static final int RIGHT = 3;

    private int size;
    private XYLocation courierOne;
    private XYLocation courierTwo;

    private Set<XYLocation> rivers;
    private Set<XYLocation> customers;
    private Set<XYLocation> oneWayUp;
    private Set<XYLocation> oneWayRight;
    private Set<XYLocation> oneWayDown;
    private Set<XYLocation> oneWayLeft;

    public WechselnBoard(int size) {
        this.size = size;

        rivers = new LinkedHashSet<XYLocation>();
        customers = new LinkedHashSet<XYLocation>();
        oneWayUp = new LinkedHashSet<XYLocation>();
        oneWayRight = new LinkedHashSet<XYLocation>();
        oneWayDown = new LinkedHashSet<XYLocation>();
        oneWayLeft = new LinkedHashSet<XYLocation>();
    }

    public WechselnBoard copyWithout() {
        WechselnBoard copy = new WechselnBoard(size);

        copy.rivers = rivers;
        copy.oneWayUp = oneWayUp;
        copy.oneWayRight = oneWayRight;
        copy.oneWayDown = oneWayDown;
        copy.oneWayLeft = oneWayLeft;

        return copy;
    }

    public void putElement(int type, XYLocation loc) {
        switch (type) {
            case COURIER_ONE    : courierOne = loc; break;
            case COURIER_TWO    : courierTwo = loc; break;
            case RIVER          : rivers.add(loc); break;
            case CUSTOMER       : customers.add(loc); break;
            case ONEWAY_UP      : oneWayUp.add(loc); break;
            case ONEWAY_RIGHT   : oneWayRight.add(loc); break;
            case ONEWAY_DOWN    : oneWayDown.add(loc); break;
            case ONEWAY_LEFT    : oneWayLeft.add(loc); break;
        }
    }

    public int getNumberOfCustomers() {
        return customers.size();
    }

    public XYLocation getCourierOne() {
        return courierOne;
    }

    public XYLocation getCourierTwo() {
        return courierTwo;
    }

    public Set<XYLocation> getCustomers() {
        return customers;
    }

    public XYLocation getSurroundingLocation(XYLocation object, int direction) {
        if(direction == UP) return object.up();
        else if(direction == LEFT) return object.left();
        else if(direction == DOWN) return object.down();
        else return object.right();
    }

    public List<XYLocation> getSurroundings(XYLocation object)  {
        List<XYLocation> surroundings = new ArrayList<XYLocation>();

        for(int dir = UP; dir <= RIGHT; dir++) {
            XYLocation dest = getSurroundingLocation(object, dir);
            if(isValid(dest))
                surroundings.add(dest);
        }

        return surroundings;
    }


    public boolean isValid(XYLocation loc) {
        return isInBoard(loc);
    }

    public boolean isInBoard(XYLocation loc) {
        return loc.getXCoOrdinate() >= 0 && loc.getXCoOrdinate() < size &&
            loc.getYCoOrdinate() >= 0 && loc.getYCoOrdinate() < size;
    }

    public boolean canMove(XYLocation courier, XYLocation dest) {
        if(!isRiver(dest) && isValidDirection(courier, dest)) return true;
        return false;
    }

    public boolean isRiver(XYLocation loc) {
        for(XYLocation river : rivers)
            if(loc.equals(river))
                return true;
        return false;
    }

    public int oneWayDirection(XYLocation loc) {
        for(XYLocation way : oneWayUp)
            if(way.equals(loc)) return UP;
        for(XYLocation way : oneWayLeft)
            if(way.equals(loc)) return LEFT;
        for(XYLocation way : oneWayDown)
            if(way.equals(loc)) return DOWN;
        for(XYLocation way : oneWayRight)
            if(way.equals(loc)) return RIGHT;
        return -1;
    }

    public boolean isValidDirection(XYLocation start, XYLocation finish) {
        int startDir = oneWayDirection(start);
        if(startDir >= 0) {
            if(startDir == oneWayDirection(finish)) return true;
            return false;
        }
        return true;
    }

    public String getDirectionName(XYLocation start, XYLocation finish) {
        if(start.up().equals(finish)) return "UP";
        else if(start.left().equals(finish)) return "LEFT";
        else if(start.down().equals(finish)) return "DOWN";
        else if(start.right().equals(finish)) return "RIGHT";

        return "CAN'T GO THERE";
    }

    public void print() {
        for(int j = 0; j < size; j++) {
            for(int i = 0; i < size; i++) {
                if(courierOne.equals(new XYLocation(i, j))) {
                    System.out.print('A');
                } else
                if(courierTwo.equals(new XYLocation(i, j))) {
                    System.out.print('B');
                } else {
                    char c = '.';
                    for(XYLocation loc : rivers)
                        if(loc.equals(new XYLocation(i, j)))
                            c = '#';
                        
                    for(XYLocation loc : customers)
                        if(loc.equals(new XYLocation(i, j)))
                            c = 'C';

                    for(XYLocation loc : oneWayUp)
                        if(loc.equals(new XYLocation(i, j)))
                            c = '^';

                    for(XYLocation loc : oneWayRight)
                        if(loc.equals(new XYLocation(i, j)))
                            c = '>';

                    for(XYLocation loc : oneWayDown)
                        if(loc.equals(new XYLocation(i, j)))
                            c = 'V';

                    for(XYLocation loc : oneWayLeft)
                        if(loc.equals(new XYLocation(i, j)))
                            c = '<';

                    System.out.print(c);
                }
            }
            System.out.println();
        }
    }
}