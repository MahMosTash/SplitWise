package Test;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class OutputRedirector {
    private static final PrintStream originalOut = System.out;
    private static PrintStream currentOut;

    public static void redirectOutputToFile(String filePath) {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            currentOut = new PrintStream(bos, true); // true enables auto-flush
            System.setOut(currentOut);
        } catch (Exception e) {
            e.printStackTrace();
            System.setOut(originalOut);
        }
    }

    public static void resetOutput() {
        if (currentOut != null) {
            currentOut.flush();
            currentOut.close();
            currentOut = null;
        }
        System.setOut(originalOut);
    }
}