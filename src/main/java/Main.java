import Controller.MainController;
import Model.World.Map;
import Model.World.World;
import com.google.gson.Gson;
import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.jar.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        makeJar();

        System.out.println("Hello World!");
        new MainController();
    }

    public static void makeJar() {
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, "Engine/Main");

        try {
            JarOutputStream target =
                new JarOutputStream(new FileOutputStream("output.jar"), manifest);
            URL jar = Main.class.getProtectionDomain().getCodeSource().getLocation();
            JarInputStream j = new JarInputStream(jar.openStream());
            addJar(j, target);
            target.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addJar(JarInputStream jar, JarOutputStream target) throws IOException {
        BufferedInputStream in = null;
        try {
            JarEntry j = jar.getNextJarEntry();
            while (j != null) {
                target.putNextEntry(j);
                in = new BufferedInputStream(jar);

                byte[] buffer = new byte[1024];
                while (true) {
                    int count = in.read(buffer);
                    if (count == -1)
                        break;
                    target.write(buffer, 0, count);
                }
                target.closeEntry();

                j = jar.getNextJarEntry();
            }
        } finally {
            if (in != null)
                in.close();
        }
    }
}
