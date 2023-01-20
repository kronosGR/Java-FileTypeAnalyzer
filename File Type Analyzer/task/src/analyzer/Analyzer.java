package analyzer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;

public class Analyzer {

    private int BUFFER_SIZE = 68108864;
    private String search;
    private String searchMessage;

    public Analyzer(String search, String searchMessage) {
        this.search = search;
        this.searchMessage = searchMessage;
    }

    public void execute(Path path) {
        try (InputStream inputStream = new FileInputStream(path.toString())) {
            byte[] buffer = new byte[search.length() + BUFFER_SIZE];

            while (inputStream.read(buffer, search.length(), BUFFER_SIZE) != -1) {
                String tmp = new String(buffer, Charset.defaultCharset());
                if (Algorithms.searchKPM(tmp, search)) {
                    System.out.println(path.getFileName().toString() + ": " + searchMessage);
                } else {
                    System.out.println(path.getFileName().toString() + ": Unknown file type");
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
