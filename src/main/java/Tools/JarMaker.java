package Tools;

import Model.World.Map;
import Model.World.NPC;
import Model.World.Tile;
import Model.World.World;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
            addImagesJar(target, w);
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

    private static void addImagesJar(JarOutputStream target, World w) {
        for (Map m : w.maps) {
            for (Tile t : m.getForegroundSet().values()) {
                addEntryImageJar(target, t.get(), "foreground/" + t.getName());
            }
            for (Tile t : m.getBackground()) {
                addEntryImageJar(target, t.get(), "background/" + t.getName());
            }
            for (NPC npc : m.getNpcs()) {
                Tile t = npc.getAnimation();
                addEntryImageJar(target, t.get(), "npc/" + t.getName());
            }
            addEntryImageJar(target, m.getBackgroundTile().get(), "background/" + m.getBackgroundTile().getName());
            addEntryImageJar(target, w.getPlayer().getAnim().get(), "npc/" + w.getPlayer().getAnim().getName());
        }
    }

    public static void addEntryImageJar(JarOutputStream target, BufferedImage obj, String name) {
        try {
            target.putNextEntry(new JarEntry(name));

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(obj, "png", out);

            addStreamToJar(new BufferedInputStream(new ByteArrayInputStream(out.toByteArray())), target);
        } catch (Exception e) {
        }
    }
}
