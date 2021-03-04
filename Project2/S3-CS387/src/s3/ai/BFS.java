package s3.ai;

import s3.base.S3;
import s3.entities.S3PhysicalEntity;
import s3.util.Node;
import s3.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class BFS {

    List<Pair<Double, Double>> path;
    double goal_x, goal_y;
    S3 the_game;

    public static int pathDistance(double start_x, double start_y, double goal_x, double goal_y, S3 the_game) {

        BFS b = new BFS(start_x, start_y, goal_x, goal_y, the_game);
        List<Pair<Double, Double>> path = b.computePath();
        if (path != null) return path.size();
        return -1;
    }

    public BFS(double start_x, double start_y, double goal_x, double goal_y, S3 the_game) {

        this.goal_x = goal_x;
        this.goal_y = goal_y;
        this.the_game = the_game;

        Node start = new Node(null, start_x, start_y, 0, 0);
        ArrayList<Node> OPEN = new ArrayList<>(Collections.singletonList(start)), CLOSED = new ArrayList<>();

        while (OPEN.size() > 0) {
            Node n = OPEN.get(0);
            OPEN.remove(n);
            CLOSED.add(n);

            if (n.x == this.goal_x && n.y == this.goal_y) {
                this.path = n.path();
                break;
            }

            this.getChildren(n).forEach(child -> {
                if (this.noDup(child, OPEN) && this.noDup(child, CLOSED)) {
                    OPEN.add(child); // adds to OPEN only if not present in both
                }
            });
        }
    }

    public List<Node> getChildren(Node node) {
        // create all possible children
        Node up = new Node(node, node.x, node.y - 1, 0, 0);
        Node right = new Node(node, node.x + 1, node.y, 0, 0);
        Node down = new Node(node, node.x, node.y + 1, 0, 0);
        Node left = new Node(node, node.x - 1, node.y, 0, 0);
        List<Node> children = Arrays.asList(up, right, down, left);

        // filters out impossible children, then returns list
        return children.stream().filter(child -> this.the_game.isSpaceFree(1, (int) child.x, (int) child.y)).collect(Collectors.toList());
    }

    public boolean noDup(Node child, List<Node> nodes) {
        // finds out if a child node is not a dup, and if it is, it must have a lower f value
        return nodes.stream().noneMatch(node -> node.x == child.x && node.y == child.y);
    }

    public List<Pair<Double, Double>> computePath() {
        return this.path;
    }
}
