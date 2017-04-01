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