package Editor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManager {
    public static File getFile() {
        File file = null;
        JFileChooser fc = setUpFileChooser();
        JFrame frame = setUpFrame(fc);
        int res = fc.showOpenDialog(frame);
        if (res == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
        }
        frame.setVisible(false);
        frame.dispose();
        return file;
    }

    public static void saveFile(Object o) {
        File file = null;
        JFileChooser fc = setUpFileChooser();
        JFrame frame = setUpFrame(fc);
        int res = fc.showSaveDialog(frame);
        if (res == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            if (file == null)
                return;
            Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
                    .setPrettyPrinting().create();
            String world_json = gson.toJson(o);
            try {
                file.createNewFile();
                FileOutputStream writer = new FileOutputStream(file);
                writer.write(world_json.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        frame.setVisible(false);
        frame.dispose();
    }

    private static JFrame setUpFrame(JFileChooser fc) {
        JFrame frame = new JFrame();
        frame.setContentPane(fc);
        return frame;
    }

    private static JFileChooser setUpFileChooser() {
        File current = new File(System.getProperty("user.dir"));
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(current);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        return fc;
    }
}
