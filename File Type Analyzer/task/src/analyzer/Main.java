package analyzer;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static int THREADS = 5;
    public static void main(String[] args) {

        String path = args[0];
        if (!Files.isDirectory(Path.of(path))) {
            System.out.println("The directory-path gives '" + path + "' is not valid!");
            System.exit(1);
        }

        String search = args[1];
        String searchMessage = args[2];

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Path.of(path))) {
            directoryStream.forEach(p->{
                if (Files.isRegularFile(p)){
                    // execute the file analyze
                    Thread thead = new Thread(()->{
                       Analyzer tmp = new Analyzer(search, searchMessage);
                       tmp.execute(p);
                    });
                    thead.start();
                    try {
                        thead.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (IOException e) {
            System.out.printf("Could not read from directory '%s'!%n", path);
        }



    }


}