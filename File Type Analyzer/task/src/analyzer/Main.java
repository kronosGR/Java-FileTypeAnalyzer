package analyzer;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String type = args[0];
        String file = args[1];
        String pattern = args[2];
        String resString = args[3];

        long startTime = System.nanoTime();


        try (InputStream in = new FileInputStream(file);) {
            byte[] bytesRead = in.readAllBytes();
            String tmp = new String(bytesRead, Charset.defaultCharset());

            switch (type.substring(2)) {
                case "naive":
                    if (tmp.contains(pattern)) {
                        System.out.println(resString);
                    } else {
                        System.out.println("Unknown file type");
                    }
                    break;
                case "KMP":
                    if (searchKPM(tmp, pattern)){
                        System.out.println(resString);
                    } else {
                        System.out.println("Unknown file type");
                    }
                    break;
            }
            long elapsed = System.nanoTime() - startTime;
            System.out.printf("It took %f seconds", (double)elapsed/1000000000 );

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static boolean searchKPM(String t, String p) {
        // find the prefix
        int[] prefix = getPrefix(p);

        int j = 0;
        for (int i = 0; i < t.length(); i++) {
            while (j > 0 && t.charAt(i) != p.charAt(j)) {
                j = prefix[j - 1];
            }

            if (t.charAt(i) == p.charAt(j)) {
                j += 1;
            }

            if (j == p.length()) {
                return true;

            }
        }

        return false;
    }

    private static int[] getPrefix(String s) {
        int len = s.length();
        int[] prefix = new int[len];

        for (int i = 1; i < len; i++) {
            int j = prefix[i - 1];

            while (j > 0 && s.charAt(i) != s.charAt(j)) {
                j = prefix[j - 1];
            }

            if (s.charAt(i) == s.charAt(j)) {
                j += 1;
            }
            prefix[i] = j;
        }

        return prefix;
    }
}
