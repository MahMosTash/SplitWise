import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

            List<String> command = new ArrayList<>();
            command.add("java");
            command.add("-cp");
            command.add("out/production/SplitWise");
            command.add("Main");
            command.add("<");
            command.add(inputFile.getAbsolutePath());
            command.add(">");
            command.add(outputFile.getAbsolutePath());

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);

            try {
                Process process = processBuilder.start();
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                System.err.println("Error running the command: " + e.getMessage());
            }
        }
    }
}