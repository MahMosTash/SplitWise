package Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class InputRedirector {
    public static void redirectInputFromFile(String filePath) {
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            System.setIn(fileIn);
        } catch (FileNotFoundException e) {
            System.err.println("Error redirecting input: " + e.getMessage());
        }
    }
}