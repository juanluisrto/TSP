import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Utils {


    public static int[] min (int [][] mst, ArrayList<Integer> covered){
        int minimum = Integer.MAX_VALUE;
        int argminX = 0;
        int argminY = 0;
        for (int i: covered){
            for(int j = 0; j < TSP.n; j++)
            if (mst[i][j] < minimum && !covered.contains(j)){
                minimum = mst[i][j];
                argminX = i;
                argminY = j;
            }
        }
        int [] tuple = {minimum, argminX, argminY};
        return tuple;
    }
    public static int min(int [] array){
        int minimum = Integer.MAX_VALUE;
        for (int i = 0; i < array.length; i++){
            if(array[i] != 0 && array[i] < minimum){
                minimum = array[i];
            }
        }
        return minimum;
    }
    public static int distance(float x1, float y1, float x2, float y2) {
        return (int) Math.round(Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2)));
    }
}

class Node{
    HashSet<Edge> edges = new HashSet<>(3);
    //HashMap <Integer,Node> children = new HashMap<>(3);
    int index;
    public Node(int index){
        this.index = index;
    }
}
class Edge{
    int distance;
    int visitsLeft = 2;
    Node A,B;
    public Edge(int d, int linkedNode, int newNode){
        this.distance = d;
        A = TSP.nodes[linkedNode];
        B = TSP.nodes[newNode];
    }
}