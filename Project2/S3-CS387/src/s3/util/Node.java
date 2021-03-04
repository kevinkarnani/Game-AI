package s3.util;

import java.util.ArrayList;

/**
 * @author Kevin Karnani
 * Created: Feb 5, 2021
 * Class: CS 387 (Game AI)
 * Language: Java 8+
 */

public class Node {
    public Node parent;
    public double x, y;
    public double f, g, h;

    /**
     * <p>
     * Holds information of the positions of the map.
     * <p>
     * It includes the parent node, coordinates, distance from start, distance to goal, and heuristic value.
     * It also includes a method to find the path from the start to the current node.
     *
     * @param parent previous node
     * @param x      current x coordinate
     * @param y      current y coordinate
     * @param g      current depth (distance from start)
     * @param h      distance to goal
     */

    public Node(Node parent, double x, double y, double g, double h) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.f = g + h;
        this.g = g;
        this.h = h;
    }

    /**
     * <p>
     * Finds the path from start to current position.
     *
     * @return List of (x, y) Pairs from the start until this node.
     */

    public ArrayList<Pair<Double, Double>> path() {
        ArrayList<Pair<Double, Double>> list = new ArrayList<>();
        Node node = this;
        while (node.parent != null) {
            list.add(0, new Pair<>(node.x, node.y));
            node = node.parent;
        }
        return list;
    }
}
