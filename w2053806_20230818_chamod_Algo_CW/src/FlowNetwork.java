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

    public void addEdge(int from, int to, int capacity){

        Edge forwardEdge = new Edge(from, to, capacity);
        Edge backwardEdge = new Edge(to ,from,0);

        forwardEdge.setResidualEdge(backwardEdge);
        backwardEdge.setResidualEdge(forwardEdge);

        adjacencyList.get(from).add(forwardEdge);
        adjacencyList.get(to).add(backwardEdge);

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

        public Edge(int from , int to, int capacity){

            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.flow = 0;
        }

        public void setResidualEdge(Edge residualEdge) {
            this.residualEdge = residualEdge;
        }
        public int getRemainingCapacity(){

            return capacity-flow;
        }

        public void augmented(int bottleneck){
            flow+=bottleneck;
            residualEdge.flow-=bottleneck;
        }
        public int getFrom() {
            return from;
        }

        public int getTo() {
            return to;
        }

        public int getCapacity() {
            return capacity;
        }

        public int getFlow() {
            return flow;
        }

    }
}
