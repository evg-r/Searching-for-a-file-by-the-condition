import java.io.*;
import java.util.*;

public class BinaryFile {
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