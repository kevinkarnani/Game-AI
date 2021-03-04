import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

public class Graph {
    int numVertices, height, width;
    List<Edge> edges;

    public Graph(int width, int height) {
        this.width = width;
        this.height = height;
        this.numVertices = width * height;
    }

    public void populateEdges() {
        this.edges = new ArrayList<>();

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                int curr = i * this.width + j;
                if (i != this.width - 1) {
                    int down = (i + 1) * this.width + j;
                    this.edges.add(new Edge(curr, down));
                }
                if (j != this.height - 1) {
                    int right = i * this.width + (j + 1);
                    this.edges.add(new Edge(curr, right));
                }
            }
        }
    }

    public PriorityQueue<Edge> kruskal() {
        this.populateEdges();
        PriorityQueue<Edge> edges = new PriorityQueue<>(this.edges);
        int[] parents = IntStream.range(0, this.numVertices).toArray(); // load 0...numVertices - 1
        PriorityQueue<Edge> mst = new PriorityQueue<>();

        int index = 0;
        while (!edges.isEmpty() && index < this.numVertices - 1) {
            Edge e = edges.remove();
            int set_1 = this.find(parents, e.getStart());
            int set_2 = this.find(parents, e.getEnd());

            if (set_1 != set_2) {
                mst.add(e);
                index++;
                this.union(parents, set_1, set_2);
            }
        }
        return mst;
    }

    /**
     * Finds the parent of a vertex
     *
     * @param parent: int array
     * @param vertex: vertex
     * @return parent vertex
     */
    public int find(int[] parent, int vertex) {
        if (parent[vertex] != vertex)
            return this.find(parent, parent[vertex]);
        return vertex;
    }

    /**
     * Sets the parent of y to parent of x
     * Thus, x and y are now siblings
     *
     * @param parent: int array
     * @param set_1:  vertex1
     * @param set_2:  vertex2
     */
    public void union(int[] parent, int set_1, int set_2) {
        parent[this.find(parent, set_2)] = this.find(parent, set_1);
    }
}
