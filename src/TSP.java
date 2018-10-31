import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class TSP {
    public static int n;
    public static int[][] matrix;
    public static float[][] points;
    public static Node[] nodes;
    public static HashSet<Edge> edges;
    public static int firstVertex;


    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        importData();
        printData(matrix);
        int[][] mst = minimumSpanningTree();
        printData(mst);
        int[] path = travelTree();
        int[] prunedPath = prunePath(path);
        printPath(prunedPath);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime);
    }

    public static void importData() {
        File f = new File("src/test.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        n = sc.nextInt();
        points = new float[n][2];
        matrix = new int[n][n];
        float x, y;
        for (int i = 0; i < n; i++) {
            x = Float.parseFloat(sc.next());
            y = Float.parseFloat(sc.next());
            points[i][0] = x;
            points[i][1] = y;
            for (int j = 0; j < i; j++) {
                int d = Utils.distance(x, y, points[j][0], points[j][1]);
                matrix[i][j] = d;
                matrix[j][i] = d;
            }
        }
    }

    public static int[][] minimumSpanningTree() {
        ArrayList<Integer> covered = new ArrayList<>(n);
        int[][] mst = new int[n][n];
        nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i);
        }
        edges = new HashSet<>();
        Random r = new Random();
        firstVertex = 2;//r.nextInt(n); //6;
        covered.add(firstVertex);
        while (covered.size() < n) {
            int[] tuple = Utils.min(matrix, covered);
            int distance = tuple[0];
            int linkNode = tuple[1];
            int newNode = tuple[2];
            Edge e = new Edge(distance, linkNode, newNode);
            edges.add(e);
            nodes[linkNode].edges.add(e);
            nodes[newNode].edges.add(e);
            mst[linkNode][newNode] = distance;
            mst[newNode][linkNode] = distance;
            covered.add(newNode);
        }
        return mst;
    }

    public static int[] travelTree() {
        ArrayList<Node> nodesLeft = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            nodesLeft.add(nodes[i]);
        }
        ArrayList<Edge> edgesLeft = new ArrayList<>(2 * edges.size());
        for (Edge e : edges) { //we add them twice and will pop them each time we visit an edge
            edgesLeft.add(e);
            edgesLeft.add(e);
        }
        int[] path = new int[2 * n]; //we visit each edge  two times.
        path[0] = firstVertex;
        path = depthSearch(path, 1, nodes[firstVertex]);
        return path;
    }

    public static int[] depthSearch(int[] path, int length, Node actualNode) {
        int minimumDistance = Integer.MAX_VALUE;
        int lessVisitedEdge = 0;
        Edge bestEdge = null;
        for (Edge e : actualNode.edges) {
            if (minimumDistance >= e.distance && e.visitsLeft >= lessVisitedEdge) {
                minimumDistance = e.distance;
                bestEdge = e;
                lessVisitedEdge = e.visitsLeft;
            }
        }
        bestEdge.visitsLeft--;
        if (length >= 2 * edges.size()) {
            return path;
        } else {
            Node next = actualNode.equals(bestEdge.A) ? bestEdge.B : bestEdge.A;
            path[length] = next.index;
            int[] newPath = depthSearch(path, length + 1, next);
            return newPath;
        }
    }

    public static int[] prunePath(int[] path) {
        List<Integer> visited = new ArrayList<>(n);
        int[] prunedPath = new int[n];
        for (int i = 0; i < path.length; i++) {
            if (!visited.contains(path[i])) {
                prunedPath[visited.size()] = path[i];
                visited.add(path[i]);
            }
        }
        return prunedPath;
    }

    public static void printPath(int[] path) {
        for (int i = 0; i < path.length; i++) {
            System.out.println(path[i]);
        }
    }


    public static void printData(int[][] matrix) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j]);
                System.out.print(" ");
            }
            System.out.print('\n');
        }
    }
}
