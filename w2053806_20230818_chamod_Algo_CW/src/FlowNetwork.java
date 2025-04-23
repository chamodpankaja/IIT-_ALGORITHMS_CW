import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlowNetwork {

    private int nodesCount;
    private int source;
    private int target;
    private Map<Integer, List<Edge>> adjacencyList;

    public FlowNetwork(int nodesCount){

        this.nodesCount = nodesCount;
        this.source = 0;
        this.target = nodesCount-1;
        this.adjacencyList = new HashMap<>();

        for (int i=0 ; i < nodesCount ;  i++){
            adjacencyList.put(i, new ArrayList<>());
        }
    }

    public int getNodesCount(){
        return nodesCount;
    }
    public int getSource(){
        return source;
    }

    public int getTarget(){
        return target;
    }

    public List<Edge> getEdges(int node){
        return adjacencyList.get(node);
    }

    /*
       static inner class for Edge Operations
    * */
    static class Edge {

        private int from;
        private int to;
        private int capacity;
        private int flow;
        private Edge residualEdge;
    }
}
