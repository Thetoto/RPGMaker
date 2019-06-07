package EasyUnit;

import Model.Editor.EditorState;
import Model.World.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MapTest {

    Map map = null;

    @Before
    public void SetUp() {
        Dimension mapDimension = new Dimension(100, 100);
        map = new Map(mapDimension, "Test");
    }

    @Test
    public void DrawEasyTest() {
        BufferedImage tileImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Tile tile = new Tile("testTile", tileImage);
        Point in = new Point(0, 0);
        Point out = new Point(0, 0);
        map.draw(tile, in, out);
        Assert.assertTrue(map.getBackground().contains(tile));
    }

    @Test
    public void DrawBigTileTest() {
        BufferedImage tileImage = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        BigTile tile = new BigTile("testTile", tileImage);
        Point in = new Point(0, 0);
        Point out = new Point(0, 0);
        map.draw(tile, in, out);
        Assert.assertTrue(map.getBackground().contains(tile.getTile(1, 1)));
    }

    @Test
    public void DrawForegroundTest() {
        BufferedImage tileImage = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        ImportedTile tile = new ImportedTile("testTile", tileImage);
        Point in = new Point(0, 0);
        Point out = new Point(0, 0);
        map.draw(tile, in, out);
        Assert.assertTrue(map.getForegroundSet().size() == 1);
    }

    @Test
    public void DrawNPCTest() {
        BufferedImage tileImage = new BufferedImage(384, 48, BufferedImage.TYPE_INT_ARGB);
        Animation tile = new Animation("testTile", tileImage);
        Point in = new Point(0, 0);
        Point out = new Point(0, 0);
        map.draw(tile, in, out);

        Assert.assertTrue(map.getNpcs().size() == 1);
    }

    @Test
    public void EraseNPCTest() {
        EditorState editorState = new EditorState();

        BufferedImage tileImage = new BufferedImage(384, 48, BufferedImage.TYPE_INT_ARGB);
        Animation tile = new Animation("testTile", tileImage);
        Point in = new Point(0, 0);
        Point out = new Point(0, 0);
        map.draw(tile, in, out);

        BufferedImage eraserImage = new BufferedImage(384, 48, BufferedImage.TYPE_INT_ARGB);
        Animation eraser = new Animation("eraser.png", eraserImage);
        map.draw(eraser, in, out);

        Assert.assertTrue(map.getNpcs().size() == 0);
    }

    @Test
    public void EraseForegroundTest() {
        EditorState editorState = new EditorState();

        BufferedImage tileImage = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        ImportedTile tile = new ImportedTile("testTile", tileImage);
        Point in = new Point(0, 0);
        Point out = new Point(0, 0);
        map.draw(tile, in, out);

        BufferedImage eraserImage = new BufferedImage(384, 48, BufferedImage.TYPE_INT_ARGB);
        ImportedTile eraser = new ImportedTile("eraser.png", eraserImage);
        editorState.mapState.updateRequestIn = in;
        editorState.mapState.updateRequestOut = out;
        map.draw(eraser, in, out);

        Assert.assertTrue(map.getForegroundSet().size() == 0);
    }
}
