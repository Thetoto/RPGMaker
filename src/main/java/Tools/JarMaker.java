package Tools;

import Model.World.World;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.jar.*;

public class JarMaker {
    public static void makeJar(World w) {
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, "Engine/Main");

        String wJson = createWorldJson(w);
        InputStream wStream = new ByteArrayInputStream(wJson.getBytes(StandardCharsets.UTF_8));

        try {
            JarOutputStream target =
                    new JarOutputStream(new FileOutputStream("output.jar"), manifest);
            URL jar = JarMaker.class.getProtectionDomain().getCodeSource().getLocation();
            JarInputStream j = new JarInputStream(jar.openStream());
            addJar(j, target, wStream);
            target.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String createWorldJson(World w) {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
                .setPrettyPrinting().create();
        return gson.toJson(w);
    }

    private static void addStreamToJar(BufferedInputStream in, JarOutputStream target) throws IOException {
        byte[] buffer = new byte[1024];
        while (true) {
            int count = in.read(buffer);
            if (count == -1)
                break;
            target.write(buffer, 0, count);
        }
        target.closeEntry();
    }

    private static void addJar(JarInputStream jar, JarOutputStream target, InputStream worldStream) throws IOException {
        BufferedInputStream in = null;
        try {
            JarEntry j = jar.getNextJarEntry();
            while (j != null) {
                target.putNextEntry(j);
                in = new BufferedInputStream(jar);

                addStreamToJar(in, target);

                j = jar.getNextJarEntry();
            }
            target.putNextEntry(new JarEntry("world.json"));
            addStreamToJar(new BufferedInputStream(worldStream), target);
        } finally {
            if (in != null)
                in.close();
        }
    }
}
