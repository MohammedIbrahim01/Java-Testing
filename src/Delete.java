
import java.io.File;

public class Delete {
//
//    public static void main(String[] args) {
//        File a = new File("C:/windows/system32");
//        deleteDirectory(a);
//    }

    public static boolean deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (null != files) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
        }
        return (directory.delete());
    }

    private Delete() {
    }

}
