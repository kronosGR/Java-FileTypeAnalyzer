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
    private static int THREADS = 10;
    public static void main(String[] args) {

        String path = args[0];
        if (!Files.isDirectory(Path.of(path))) {
            System.out.println("The directory-path gives '" + path + "' is not valid!");
            System.exit(1);
        }

        String search = args[1];
        String searchMessage = args[2];

        ExecutorService executor = Executors.newFixedThreadPool(THREADS);
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Path.of(path))) {
            directoryStream.forEach(p->{
                if (Files.isRegularFile(p)){
                    // execute the file analyze
                    executor.submit(() -> new Analyzer(search, searchMessage).execute(p));
                }
            });
        } catch (IOException e) {
            System.out.printf("Could not read from directory '%s'!%n", path);
        }
        finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(100, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }


    }


}
