package algorithms; 

import algorithms.ui.Ui;
import algorithms.benchmarks.*;

public class Main {
        
    /**
     * Launches the GUI app or benchmarks. 
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws java.io.IOException {
        
        if (args.length != 0) {
            if (args[0].equals("compression")) {
                CompressionBenchmark.main(args);
            } else if (args[0].equals("performance")) {
                PerformanceBenchmark.main(args); 
            } else {
                Ui.main(args);
            }
        } else {
            Ui.main(args);
        }
    }   
}
    


    


