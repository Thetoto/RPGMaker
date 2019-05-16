package Tools;

import java.io.File;

public class Tools {
    public static File getFileFromRessources(String path) {
        return new File(getPathFromRessources(path));
    }
    public static String getPathFromRessources(String path) {
        return ClassLoader.getSystemClassLoader().getResource(path).getPath();
    }
}
