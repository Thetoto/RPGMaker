import Controller.MainController;
import Model.World.Map;
import Model.World.World;
import com.google.gson.Gson;

import java.awt.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        new MainController();

        World world = new World("test");
        Map map = new Map(new Dimension(100, 100), "Nice");
        world.addMap(map);

        Gson gson = new Gson();
        String res = gson.toJson(world);

        try {
            File file = new File("test.wrld");
            file.createNewFile();
            FileOutputStream writer = new FileOutputStream(file);
            writer.write(res.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
