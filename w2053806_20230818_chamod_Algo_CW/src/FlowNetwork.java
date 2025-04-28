import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlowNetwork {

    private int nodesCount; // total number of nodes of the flow network
    private int source; // start node
    private int target; // end node
    private Map<Integer, List<Edge>> adjacencyList; // adjacency list to store the edges for each node

    public FlowNetwork(int nodesCount){

        this.nodesCount = nodesCount;
        this.source = 0; // default source
        this.target = nodesCount-1; //
        this.adjacencyList = new HashMap<>();// initialize the empty adjacency list

        // creates an empty list of edges for each node
        for (int i=0 ; i < nodesCount ;  i++){
            adjacencyList.put(i, new ArrayList<>());
        }
    }

    // method for adding an edge
    public void addEdge(int from, int to, int capacity){

        // create a forward edge and a backward edge( residual edge)
        Edge forwardEdge = new Edge(from, to, capacity);
        Edge backwardEdge = new Edge(to ,from,0);

        //link the edges as residual edges
        forwardEdge.setResidualEdge(backwardEdge);
        backwardEdge.setResidualEdge(forwardEdge);

        // add edges to an adjacency list
        adjacencyList.get(from).add(forwardEdge);
        adjacencyList.get(to).add(backwardEdge);

    }

    // return the number of the nodes in network
    public int getNodesCount(){
        return nodesCount;
    }
    // return the source node of the network
    public int getSource(){
        return source;
    }

    // return the target node of the network
    public int getTarget(){
        return target;
    }

    // return the all edges connected to the specific node
    public List<Edge> getEdges(int node){
        return adjacencyList.get(node);
    }

    /*
       static inner class for Edge Operations
    * */
    static class Edge {

        private int from; // starting node of the edge
        private int to; // ending node of the edge
        private int capacity; // maximum flow capacity of the edge
        private int flow; // current flow of the edge
        private Edge residualEdge; // residual edge

        //define the edge with zero initial flow
        public Edge(int from , int to, int capacity){

            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.flow = 0; // the initial flow is zero
        }

        // set the residual edge of the edge
        public void setResidualEdge(Edge residualEdge) {
            this.residualEdge = residualEdge;
        }
        //return the remaining capacity that can be pushed through the edge
        public int getRemainingCapacity(){

            return capacity-flow;
        }

        // augment the flow of the edge
        public void augmented(int bottleneck){
            flow+=bottleneck;// increase the flow of the edge
            residualEdge.flow-=bottleneck;// decrease the flow of the residual edge
        }
        // get the starting node of the edge
        public int getFrom() {
            return from;
        }

        // get the ending node of the edge
        public int getTo() {
            return to;
        }
        // get the capacity of the edge
        public int getCapacity() {
            return capacity;
        }

        // get the flow of the edge
        public int getFlow() {
            return flow;
        }

    }
}
