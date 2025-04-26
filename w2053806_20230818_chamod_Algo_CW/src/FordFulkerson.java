import java.util.*;

public class FordFulkerson {

    private FlowNetwork flowNetwork;
    private int maxFlow;
    private int [] parent;
    private boolean [] visited;


    public FordFulkerson(FlowNetwork flowNetwork) {
        this.flowNetwork = flowNetwork;
        this.maxFlow = 0;
        this.parent =  new int[flowNetwork.getNodesCount()];
        this.visited = new boolean[flowNetwork.getNodesCount()];

    }

    public int computeMaximumFlow(){
        System.out.println(" --------- Starting Max Flow Computation --------- ");
        System.out.println("Source --> "+ flowNetwork.getSource() + ",  Target --> "+ flowNetwork.getTarget());

        while(bfs()){
            System.out.println("\n Found Augmenting Path");
            int bottleneck = Integer.MAX_VALUE;

            Deque<Integer> path = new ArrayDeque<>();
            for(int v =  flowNetwork.getTarget() ; v != flowNetwork.getSource() ;  v = parent[v]){

                path.push(v);
                int u = parent[v];
                List<FlowNetwork.Edge> edges = flowNetwork.getEdges(u);

                for(FlowNetwork.Edge edge :  edges){
                    if(edge.getTo() == v  && edge.getRemainingCapacity() >0){
                        bottleneck = Math.min(bottleneck,edge.getRemainingCapacity());
                        break;
                    }
                }
            }

            path.push(flowNetwork.getSource());

            System.out.print("Path : ");
            while(!path.isEmpty()){
                System.out.println(path.pop());
                if(!path.isEmpty() ){
                    System.out.print(" -> ");
                }
                System.out.println("/n Bottleneck : "+bottleneck);

            }

            for(int v= flowNetwork.getTarget(); v != flowNetwork.getSource(); v = parent[v] ){
                int u = parent[v];
                List<FlowNetwork.Edge> edges = flowNetwork.getEdges(u);

                for(FlowNetwork.Edge edge : edges){
                    if(edge.getTo() == v && edge.getRemainingCapacity() > 0){
                        edge.augmented(bottleneck);
                        break;
                    }
                }
            }

            maxFlow += bottleneck;
            System.out.println("Current Max Flow : "+maxFlow);

        }
        return maxFlow;
    }


    private boolean bfs(){

        Arrays.fill(visited,false);
        Arrays.fill(parent,-1);

        Queue<Integer> queue =  new ArrayDeque<>();
        queue.add(flowNetwork.getSource());
        visited[flowNetwork.getSource()] =  true;

        while(!queue.isEmpty()){

            int u = queue.poll();

            for(FlowNetwork.Edge edge : flowNetwork.getEdges(u)){
                int v = edge.getTo();
                if(!visited[v] && edge.getRemainingCapacity() > 0){
                    visited[v] = true;
                    parent[v] = u;
                    queue.add(v);

                    if(v == flowNetwork.getTarget()){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void printFlowNetwork(){
        System.out.println("Finalized Flow Network");

        for(int u = 0 ; u < flowNetwork.getNodesCount() ; u++){
            for(FlowNetwork.Edge edge : flowNetwork.getEdges(u)){
                if(edge.getCapacity() >0){
                    System.out.println("Edge -> " + edge.getFrom() + " ->" + edge.getTo()+
                            ", Flow = "+ edge.getFlow() + ", Capacity = "+ edge.getCapacity());
                }
            }
        }
        System.out.println("Maximum Flow : "+maxFlow);
    }

}
