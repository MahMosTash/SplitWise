import Test.InputRedirector;
import Test.OutputRedirector;

import java.io.File;


public class TestRunner {
    public static void main(String[] args) {
        File inputDir = new File("testcases/in");
        File outputDir = new File("testcases/out");

        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        File[] inputFiles = inputDir.listFiles((dir, name) -> name.endsWith(".txt"));
        if (inputFiles == null) {
            System.err.println("No input files found in testcases/in");
            return;
        }

        for (File inputFile : inputFiles) {

            String outputFileName = inputFile.getName().replace("_in.txt", "_out.txt");
            File outputFile = new File(outputDir, outputFileName);

            InputRedirector.redirectInputFromFile(inputFile.getAbsolutePath());
            OutputRedirector.redirectOutputToFile(outputFile.getAbsolutePath());


            try {
                // Run the main application
                Main.main(null);
            } catch (RuntimeException e) {
                System.err.println("Main method terminated: " + e.getMessage());
            }

        }
    }
}