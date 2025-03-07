package Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class OutputRedirector {
    public static void redirectOutputToFile(String filePath) {
        try {
            PrintStream fileOut = new PrintStream(new FileOutputStream(filePath));
            System.setOut(fileOut);
        } catch (FileNotFoundException e) {
            System.err.println("Error redirecting output to file: " + e.getMessage());
        }
    }
}
