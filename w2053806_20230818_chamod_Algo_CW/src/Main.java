import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("\n---------------------------------------------------------------------------------------");
        System.out.println("                           Welcome to Max Flow Solver                                ");
        System.out.println("---------------------------------------------------------------------------------------");

        Scanner scanner = new Scanner(System.in);// scanner for user input
        long startTime = System.currentTimeMillis(); // get the start time

        // check there is a 'benchmarks' directory
        File benchmarksDirectory = new File("benchmarks");
        if(!benchmarksDirectory.exists() || !benchmarksDirectory.isDirectory()){
            System.out.println("\n'benchmarks' directory not found in  the project directory");
            return;
        }

        // get the all .txt files from 'benchmarks' directory
        File[] benchmarkFiles = benchmarksDirectory.listFiles(((dir, name) -> name.endsWith(".txt")));
        if(benchmarkFiles == null || benchmarkFiles.length ==0){
            System.out.println("\nNo benchmarks file found in 'benchmarks' directory.");
            return;
        }

        // ask from user to select file
        System.out.print("\nEnter the file name to Compute the Max Flow (e.g. bridge_1.txt  , ladder_1.txt , test_1.txt): ");
        String fileName =  scanner.nextLine().trim();

        // check the selected file exists
        File selectedFile =  new File("benchmarks/"+fileName);
        if(!selectedFile.exists()){
            System.out.println("File not found in 'benchmarks' directory");
            return;
        }
        System.out.println("\nProcessing the benchmark file : " + selectedFile.getName());
        // compute and display the maximum flow
        FlowNetwork flowNetwork  = FileParser.parseFile(selectedFile.getPath());
        FordFulkerson maxFlowSolver = new FordFulkerson(flowNetwork);
        int maxFlow = maxFlowSolver.computeMaximumFlow();
        maxFlowSolver.printFlowNetwork();
        System.out.println("\nMaximum Flow : "+maxFlow);

        long endTime = System.currentTimeMillis();// get the end time
        System.out.println("\nExecution Time : "+(endTime-startTime)+" ms");// print the execution time

        // close the scanner
        scanner.close();
    }
}