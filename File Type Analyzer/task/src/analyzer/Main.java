package analyzer;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String file = args[0];
        String pattern = args[1];
        String resString = args[2];


        try(InputStream in = new FileInputStream(file);) {
            byte[] bytesRead = in.readAllBytes();
            String tmp = new String(bytesRead, Charset.defaultCharset());
            if (tmp.contains(pattern)) {
                System.out.println(resString);
            } else {
                System.out.println("Unknown file type");
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
