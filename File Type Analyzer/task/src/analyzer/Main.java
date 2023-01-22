package analyzer;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String path = args[0];
        if (!Files.isDirectory(Path.of(path))) {
            System.out.println("The directory-path gives '" + path + "' is not valid!");
            System.exit(1);
        }

        String patternsFile = args[1];
        List<String[]> patterns = new ArrayList<>();
        try(Scanner sc = new Scanner(new File(patternsFile))){
            while(sc.hasNext()){
                patterns.add(sc.nextLine().replaceAll("[\"]", "").split(";"));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // reverse the list because of higher priority = higher number
        Collections.reverse(patterns);

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Path.of(path))) {
            directoryStream.forEach(p->{
                if (Files.isRegularFile(p)){
                    // execute the file analyze
                    Thread thead = new Thread(()->{
                        boolean exists = false;
                        for (String[] pattern: patterns){
                            try {
                                if (existsKBPSearch(p,pattern[1], pattern[2])){
                                    System.out.println(p.getFileName().toString() + ": " + pattern[2]);
                                    exists = true;
                                    break;
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        if (!exists){
                            System.out.println(p.getFileName().toString()+": Unknown file type");
                        }
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

    private static boolean existsKBPSearch(Path path, String text, String result) throws IOException {
        String tmp = readFile(path.toString());
        if (Algorithms.searchKPM(tmp, text)) {
            return true;
        }
        return false;
    }

    private static String readFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }


}