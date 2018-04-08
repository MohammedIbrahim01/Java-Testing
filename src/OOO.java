
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class OOO {

    public static void main(String[] args) {
        File wanted;
        try {
            wanted = new File("/media/khalilubuntu/5CD0108044EE96B9/To Drive/");
            dec(wanted, 2535);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void enc(File wanted, int z) throws FileNotFoundException, IOException {
        if (wanted.isDirectory()) {
            File[] files = wanted.listFiles();
            if (null != files) {
                for (File file : files) {
                    enc(file, z);
                }
            }
        } else if (!"ф".equals(wanted.getName().substring(wanted.getName().length() - 1, wanted.getName().length()))) {
            File to = new File(wanted.getPath() + "ф");
            FileReader readme = new FileReader(wanted);
            FileWriter writeme = new FileWriter(to);
            while (readme.ready()) {
                writeme.append((char) (readme.read() + z));
            }
            wanted.delete();
            writeme.close();
            readme.close();
            System.out.println("e");
        }
    }

    private static void dec(File wanted, int z) throws FileNotFoundException, IOException {
        if (wanted.isDirectory()) {
            File[] files = wanted.listFiles();
            if (null != files) {
                for (File file : files) {
                    dec(file, z);
                }
            }
        } else if ("ф".equals(wanted.getName().substring(wanted.getName().length() - 1, wanted.getName().length()))) {

            FileReader readme = new FileReader(wanted);
            String path = wanted.getPath();
            FileWriter writeme = new FileWriter(new File(path.substring(0, path.length() - 1)));
            while (readme.ready()) {
                writeme.append((char) (readme.read() - z));
            }
            writeme.close();
            readme.close();
            wanted.delete();
            System.out.println("d");
        }
    }
}
