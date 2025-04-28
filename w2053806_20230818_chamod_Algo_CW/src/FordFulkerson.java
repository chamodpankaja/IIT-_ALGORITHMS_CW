import java.util.*;

public class FordFulkerson {

    private FlowNetwork flowNetwork;// The flow network
    private int maxFlow; // maximum flow
    private int [] parent; // array to the store path
    private boolean [] visited;// array to mark visited nodes


    public FordFulkerson(FlowNetwork flowNetwork) {
        this.flowNetwork = flowNetwork;
        this.maxFlow = 0; // initial max flow is zero
        this.parent =  new int[flowNetwork.getNodesCount()]; // define parent array with the size of the flow network
        this.visited = new boolean[flowNetwork.getNodesCount()];// define visited array with the size of the flow network

    }

    public int computeMaximumFlow(){
        System.out.println("\n--------- Starting Max Flow Computation --------- ");
        // print the start node and the target node
        System.out.println("Source --> "+ flowNetwork.getSource() + ",  Target --> "+ flowNetwork.getTarget());

        while(bfs()){
            System.out.println("\nFound Augmenting Path");
            System.out.println("--------------------------------");
            int bottleneck = Integer.MAX_VALUE;// initialize the bottleneck

            // backtrack from the target to the source
            Deque<Integer> path = new ArrayDeque<>();
            for(int v =  flowNetwork.getTarget() ; v != flowNetwork.getSource() ;  v = parent[v]){

                path.push(v);// add nodes to path in reverse order
                int u = parent[v];
                List<FlowNetwork.Edge> edges = flowNetwork.getEdges(u);

                // find the edge and update the bottleneck
                for(FlowNetwork.Edge edge :  edges){
                    if(edge.getTo() == v  && edge.getRemainingCapacity() >0){
                        bottleneck = Math.min(bottleneck,edge.getRemainingCapacity());
                        break;
                    }
                }
            }

            path.push(flowNetwork.getSource());//add the source to the path

            // print the augmenting path
            System.out.print("Path : ");
            while(!path.isEmpty()){
                System.out.print(path.pop());
                if(!path.isEmpty() ){
                    System.out.print(" -> ");
                }
            }
            System.out.println("\nBottleneck : "+bottleneck);

            //augment the flow through the path
            for(int v= flowNetwork.getTarget(); v != flowNetwork.getSource(); v = parent[v] ){
                int u = parent[v];
                List<FlowNetwork.Edge> edges = flowNetwork.getEdges(u);

                for(FlowNetwork.Edge edge : edges){
                    if(edge.getTo() == v && edge.getRemainingCapacity() > 0){
                        edge.augmented(bottleneck);// push the flow through the edge
                        break;
                    }
                }
            }

            maxFlow += bottleneck; // update the max flow
            System.out.println("Current Max Flow : "+maxFlow);

        }
        return maxFlow; // return the maximum flow
    }


    // Breadth First Search to find an augmenting path
    private boolean bfs(){

        Arrays.fill(visited,false); // reset the visited array
        Arrays.fill(parent,-1);// reset the parent array

        Queue<Integer> queue =  new ArrayDeque<>();
        queue.add(flowNetwork.getSource());
        visited[flowNetwork.getSource()] =  true;

        while(!queue.isEmpty()){

            int u = queue.poll();// dequeue the next node

            for(FlowNetwork.Edge edge : flowNetwork.getEdges(u)){
                int v = edge.getTo();
                // if v is not visited and there is remaining capacity
                if(!visited[v] && edge.getRemainingCapacity() > 0){
                    visited[v] = true;
                    parent[v] = u; // set parent of v
                    queue.add(v);

                    //exit the loop if v is the target
                    if(v == flowNetwork.getTarget()){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    // print the flow network with flow and capacity with edges
    public void printFlowNetwork(){
        System.out.println("\nFinalized Flow Network\n");

        for(int u = 0 ; u < flowNetwork.getNodesCount() ; u++){
            for(FlowNetwork.Edge edge : flowNetwork.getEdges(u)){
                if(edge.getCapacity() >0){
                    System.out.println("Edge  " + edge.getFrom() + " ->" + edge.getTo()+
                            ", Flow = "+ edge.getFlow() + ", Capacity = "+ edge.getCapacity());
                }
            }
        }

    }

}
