package s3.ai;

import s3.base.S3;
import s3.util.Node;
import s3.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LRTAStar {
    List<Pair<Double, Double>> path;
    double goal_x, goal_y;
    S3 the_game;
    List<Node> allNodes;

    public static int pathDistance(double start_x, double start_y, double goal_x, double goal_y, S3 the_game) {
        LRTAStar a = new LRTAStar(start_x, start_y, goal_x, goal_y, the_game);
        List<Pair<Double, Double>> path = a.computePath();
        if (path != null) return path.size();
        return -1;
    }

    public LRTAStar(double start_x, double start_y, double goal_x, double goal_y, S3 the_game) {
        this.goal_x = goal_x;
        this.goal_y = goal_y;
        this.the_game = the_game;

        Node start = new Node(null, start_x, start_y, 0, this.heuristic(start_x, start_y));
        this.allNodes = new ArrayList<>();

        for (double i = 0; i < the_game.getMap().getWidth(); i++) {
            for (double j = 0; j < the_game.getMap().getHeight(); j++) {
                if (this.the_game.isSpaceFree(1, (int) i, (int) j) || (i == start_x && j == start_y)) {
                    this.allNodes.add(new Node(null, i, j, 0, this.heuristic(i, j)));
                }
            }
        }

        Node current = start;
        while (true) {
            if (current.x == this.goal_x && current.y == this.goal_y) {
                this.path = current.path();
                break;
            }
            Node next = this.getChildren(current).stream().min(Comparator.comparingDouble(node -> node.f)).get();
            Node finalCurrent = current;
            this.allNodes.stream().filter(node -> node.x == finalCurrent.x && node.y == finalCurrent.y).findFirst().get().f = 1 + next.f;
            current = next;
        }
    }

    public List<Node> getChildren(Node node) {
        List<Node> children = new ArrayList<>();

        this.allNodes.stream().filter(n -> (n.x != node.x && n.y != node.y)
                || n.x == node.x || n.x == node.x + 1 || n.x == node.x - 1
                || n.y == node.y || n.y == node.y + 1 || n.y == node.y - 1)
                .forEach(n -> {
                    n.parent = node;
                    children.add(n);
                });
        return children;
    }

    public double heuristic(double x, double y) {
        // calculates Manhattan Distance
        return Math.abs(this.goal_x - x) + Math.abs(this.goal_y - y);
    }

    public List<Pair<Double, Double>> computePath() {
        return this.path;
    }
}
