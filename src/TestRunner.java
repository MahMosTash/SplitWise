import java.io.*;
import java.nio.file.*;
import java.util.Arrays;

public class TestRunner {
    public static void main(String[] args) throws IOException {
        Path inputDir = Paths.get("testcases/in");
        Path outputDir = Paths.get("testcases/out");
        Files.createDirectories(outputDir);

        File[] inputFiles = inputDir.toFile().listFiles((dir, name) -> name.endsWith("_in.txt"));
        if (inputFiles == null || inputFiles.length == 0) {
            System.err.println("No input files found in testcases/in");
            return;
        }

        String javaHome = System.getProperty("java.home");
        String javaBin = Paths.get(javaHome, "bin", "java").toString();
        String classpath = System.getProperty("java.class.path");

        for (File inputFile : inputFiles) {
            String outputName = inputFile.getName().replace("_in.txt", "_out.txt");
            Path outputPath = outputDir.resolve(outputName);

            ProcessBuilder pb = new ProcessBuilder(
                    javaBin,
                    "-cp",
                    classpath,
                    "Main"
            );

            System.err.println("Running test: " + inputFile.getName());

            try {
                // Set up process I/O
                pb.redirectInput(inputFile);
                pb.redirectOutput(outputPath.toFile());
                pb.redirectError(ProcessBuilder.Redirect.INHERIT);

                // Start process and wait for completion
                Process process = pb.start();
                int exitCode = process.waitFor();

                if (exitCode != 0) {
                    System.err.println("Test failed with exit code: " + exitCode);
                }

            } catch (Exception e) {
                System.err.println("Error processing " + inputFile.getName() + ": " + e.getMessage());
            }
        }
    }
}