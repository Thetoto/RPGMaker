package GlobalUnit;

import Controller.MainController;
import Editor.Editor;
import Engine.EngineState;
import Model.Editor.EditorState;
import Model.World.World;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import Engine.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;

public class GlobalTest {

    MainController mainController = null;

    @Before
    public void setUp() throws InterruptedException {
        mainController = new MainController();
        Thread.sleep(5000);
    }

    @After
    public void clean() throws InterruptedException {
        Editor.getInstance().dispose();
        Editor.editor = null;
        EditorState.instance = null;
    }

    @Test
    public void EasyTest() {
        Assert.assertTrue(Editor.getInstance() != null);
        Assert.assertTrue(EditorState.getInstance() != null);
    }

    @Test
    public void NewWorldTest() throws AWTException, InterruptedException {
        Robot robot = new Robot();

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_W);

        robot.keyRelease(KeyEvent.VK_W);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        Thread.sleep(2000);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        Thread.sleep(2000);

        Assert.assertTrue(EditorState.getInstance().mapState.currentMap != null);
        Assert.assertTrue(EditorState.getInstance().world.getMaps().size() == 1);
    }

    @Test
    public void NewMapTest() throws InterruptedException, AWTException {
        Robot robot = new Robot();

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_N);

        robot.keyRelease(KeyEvent.VK_N);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        Thread.sleep(2000);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        Thread.sleep(20000);

        Assert.assertTrue(EditorState.getInstance().mapState.currentMap != null);
        Assert.assertTrue(EditorState.getInstance().world.getMaps().size() == 1);
    }

    @Test
    public void LoadTileTest() throws InterruptedException, AWTException {
        Robot robot = new Robot();

        File file = new File("src/main/resources");
        EditorState.getInstance().tilesState.autoAddTiles(file);

        JButton button = Editor.getInstance().topBar.addNewTiles;
        button.requestFocus();

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        Thread.sleep(2000);

        for (int i = 0; i < 6; i++) {
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            Thread.sleep(500);
        }

        robot.keyPress(KeyEvent.VK_T);
        robot.keyRelease(KeyEvent.VK_T);
        robot.keyPress(KeyEvent.VK_E);
        robot.keyRelease(KeyEvent.VK_E);
        robot.keyPress(KeyEvent.VK_S);
        robot.keyRelease(KeyEvent.VK_S);
        robot.keyPress(KeyEvent.VK_T);
        robot.keyRelease(KeyEvent.VK_T);
        robot.keyPress(KeyEvent.VK_S);
        robot.keyRelease(KeyEvent.VK_S);

        Thread.sleep(2000);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        Thread.sleep(2000);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        Assert.assertTrue(EditorState.getInstance().tilesState.foregroundTiles.size() == 8);
        Assert.assertTrue(EditorState.getInstance().tilesState.backgroundTiles.size() == 16);
        Assert.assertTrue(EditorState.getInstance().tilesState.npcTile.size() == 13);
    }

    @Test
    public void LoadMapTest() throws AWTException, InterruptedException {
        Robot robot = new Robot();

        //LOAD TILES

        File file = new File("src/main/resources");
        EditorState.getInstance().tilesState.autoAddTiles(file);

        file = new File("tests");
        EditorState.getInstance().tilesState.autoAddTiles(file);

        //TILES LOADED
        Thread.sleep(1000);
        //LOAD MAP

        JButton button = Editor.getInstance().topBar.loadButton;
        button.requestFocus();

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        Thread.sleep(2000);

        for (int i = 0; i < 6; i++) {
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            Thread.sleep(500);
        }

        robot.keyPress(KeyEvent.VK_N);
        robot.keyRelease(KeyEvent.VK_N);
        robot.keyPress(KeyEvent.VK_I);
        robot.keyRelease(KeyEvent.VK_I);
        robot.keyPress(KeyEvent.VK_C);
        robot.keyRelease(KeyEvent.VK_C);
        robot.keyPress(KeyEvent.VK_E);
        robot.keyRelease(KeyEvent.VK_E);
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_UNDERSCORE);
        robot.keyRelease(KeyEvent.VK_UNDERSCORE);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_M);
        robot.keyRelease(KeyEvent.VK_M);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        robot.keyPress(KeyEvent.VK_P);
        robot.keyRelease(KeyEvent.VK_P);
        robot.keyPress(KeyEvent.VK_PERIOD);
        robot.keyRelease(KeyEvent.VK_PERIOD);
        robot.keyPress(KeyEvent.VK_W);
        robot.keyRelease(KeyEvent.VK_W);
        robot.keyPress(KeyEvent.VK_R);
        robot.keyRelease(KeyEvent.VK_R);
        robot.keyPress(KeyEvent.VK_L);
        robot.keyRelease(KeyEvent.VK_L);
        robot.keyPress(KeyEvent.VK_D);
        robot.keyRelease(KeyEvent.VK_D);

        Thread.sleep(5000);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        Thread.sleep(2000);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        Assert.assertTrue(EditorState.getInstance().world != null);
        Assert.assertTrue(EditorState.getInstance().world.getMaps().size() == 2);
    }

    @Test
    public void LaunchGameTest() throws InterruptedException, AWTException {
        Robot robot = new Robot();

        //LOAD TILES

        File file = new File("src/main/resources");
        EditorState.getInstance().tilesState.autoAddTiles(file);

        file = new File("tests");
        EditorState.getInstance().tilesState.autoAddTiles(file);

        //TILES LOADED
        Thread.sleep(1000);
        //LOAD MAP

        file = new File("nice_map.wrld");
        Gson gson = new Gson();
        try {
            EditorState.getInstance().world = gson.fromJson(new FileReader(file), World.class);
        } catch(Exception e) {
            e.printStackTrace();
        }
        EditorState.getInstance().world.setUpLoad();
        EditorState.getInstance().mapState.updateMap(EditorState.getInstance().world.getMaps().get(0));
        EditorState.getInstance().mapState.setPlayer(EditorState.getInstance().world.getPlayer());

        //MAP LOADED
        Thread.sleep(1000);
        //LAUNCH GAME

        Assert.assertTrue(EngineState.getInstance() == null);
        Assert.assertTrue(Engine.Engine.getInstance() == null);

        JButton button = Editor.getInstance().topBar.play;
        button.requestFocus();

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        Thread.sleep(3000);

        Assert.assertTrue(EngineState.getInstance() != null);
        Assert.assertTrue(Engine.Engine.getInstance() != null);

        robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyRelease(KeyEvent.VK_ESCAPE);

    }
}
