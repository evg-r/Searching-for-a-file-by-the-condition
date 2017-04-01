import java.io.*;
import java.util.*;

// 1. Написать метод получения коллекции строк из файла. (TextFile)
// 2. Написать метод получение всего текста из файла одной строкой (BinaryFile)

// 3.
// Найти все файлы в директории.
// Отфильтровать по regex.
// Найти файлы которые не начинаются с 4-х байт 0xCA 0xFE 0xBA 0xBE

public class SearchFilesByCond {
    public static ArrayList<File> files = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        final byte[] signature = {(byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE};

        getFiles(".", ".*\\.class");

        for (File file : files) {
            System.out.println(file.getName());

            byte[] arr = BinaryFile.read(file);

            for (int i = 0; i < signature.length; i++) {
                if (arr[i] != signature[i]) {
                    System.err.println(file.getName() + " is corrupt!");

                    break;
                }
            }
        }
    }

    public static void getFiles(String dir, String regex) throws IOException {
        File file = new File(dir);

        for (File item : file.listFiles()) {
            if (item.isFile()) {
                if (item.getName().matches(regex)) {
                    files.add(item);
                }
            } else {
                getFiles(item.getAbsolutePath(), regex);
            }
        }
    }
}

class BinaryFile {
    public static byte[] read(File file) throws IOException {
        try (BufferedInputStream bf = new BufferedInputStream(new FileInputStream(file))) {
            byte[] data = new byte[bf.available()];

            bf.read(data);

            return data;
        }
    }

    public static byte[] read(String file) throws IOException {
        return read(new File(file).getAbsoluteFile());
    }
}

class TextFile extends ArrayList<String> {
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
