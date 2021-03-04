import java.util.Random;

public class Edge implements Comparable<Edge> {
    int start, end, weight;

    public Edge(int start, int end) {
        this.start = start;
        this.end = end;
        this.weight = new Random().nextInt(10) + 1;
    }

    public int getWeight() {
        return this.weight;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }

    @Override
    public int compareTo(Edge that) {
        return Double.compare(this.getWeight(), that.getWeight());
    }
}
