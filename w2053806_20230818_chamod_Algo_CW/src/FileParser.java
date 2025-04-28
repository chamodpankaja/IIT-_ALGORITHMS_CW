import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileParser {

    // method to parse the text file for compute the max flow
    public static FlowNetwork parseFile(String filePath){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))){

            // read the first line and initialize the number of nodes of the network
            int nodesCount =  Integer.parseInt(bufferedReader.readLine().trim());
            FlowNetwork flowNetwork = new FlowNetwork(nodesCount);

            String line;
            // read the other data line by line
            while((line =  bufferedReader.readLine()) != null){
                line = line.trim();
                // skip empty lines
                if(line.isEmpty()){
                    continue;
                }
                // separate the line by spaces
                String[] values = line.split("\\s+");
                // check if each line has three elements
                if(values.length !=3){
                    throw new IllegalArgumentException("Invalid file format");
                }
                // parse the values
                int from = Integer.parseInt(values[0]);
                int to = Integer.parseInt(values[1]);
                int capacity = Integer.parseInt(values[2]);
                //add the edge
                flowNetwork.addEdge(from, to, capacity);
            }
            return flowNetwork;
        }catch(IOException e){
            System.out.println(" Error reading file");
            return null;
        }

    }
}
