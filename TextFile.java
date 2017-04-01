import java.io.*;
import java.util.*;

public class TextFile extends ArrayList<String> {
    public static String read(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader in = new BufferedReader(new FileReader(new File(fileName).getAbsoluteFile()))) {
            String string;

            while ((string = in.readLine()) != null) {
                stringBuilder.append(string);
                stringBuilder.append("\n");
            }

            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        } catch (IOException err) {
            throw new RuntimeException(err);
        }

        return stringBuilder.toString();
    }

    public static void write(String fileName, String string) {
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.print(string);
        } catch (IOException err) {
            throw new RuntimeException(err);
        }
    }

    public TextFile(String fileName, String splitter) {
        super(Arrays.asList(read(fileName).split(splitter)));

        if (get(0).equals("")) remove(0);
    }

    public TextFile(String fileName) {
        this(fileName, "\n");
    }

    public void write(String fileName) {
        try (PrintWriter out = new PrintWriter(new File(fileName).getAbsoluteFile())) {
            for (String item : this) {
                out.println(item);
            }
        } catch (IOException err) {
            throw new RuntimeException(err);
        }
    }
}