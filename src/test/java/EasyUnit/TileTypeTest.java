package EasyUnit;

import Model.Editor.TileType;
import Model.World.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;

public class TileTypeTest {

    BufferedImage tileImage = null;
    BufferedImage bigTileImage = null;
    BufferedImage animationImage = null;

    @Before
    public void setUp() {
        tileImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        bigTileImage = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        animationImage = new BufferedImage(384, 32, BufferedImage.TYPE_INT_ARGB);
    }

    @Test
    public void tileNameTest() {
        Tile tile = new Tile("tile.png", tileImage);
        Assert.assertTrue(tile.getName().equals("tile.png"));
    }

    @Test
    public void tileStringTest() {
        Tile tile = new Tile("tile.png", tileImage);
        System.out.println(tile.toString());
        Assert.assertTrue(tile.toString().equals("tile"));
    }

    @Test
    public void AnimationTypeTest() {
        Animation animation = new Animation("animation", animationImage);
        Assert.assertTrue(animation.getType() == TileType.NPC);
    }

    @Test
    public void ForegroundTypeTest() {
        ImportedTile foreground  = new ImportedTile("foreground", bigTileImage);
        Assert.assertTrue(foreground.getType() == TileType.FOREGROUND);
    }

    @Test
    public void BackgroundTypeTest() {
        BigTile background = new BigTile("background", bigTileImage);
        Assert.assertTrue(background.getType() == TileType.BACKGROUND);
    }

    @Test
    public void PlaceholderTest() {
        Tile placeholder = Tile.getPlaceholder();
        Assert.assertTrue(placeholder != null);
        Assert.assertTrue(placeholder.toString().equals("PlaceHolder"));
    }

    @Test
    public void TransPlaceholderTest() {
        Tile transPlaceholder = Tile.getTransPlaceholder();
        Assert.assertTrue(transPlaceholder != null);
        Assert.assertTrue(transPlaceholder.toString().equals("TransPlaceHolder"));
    }

    @Test
    public void AnimationUpTest() {
        Animation animation = new Animation("animation", animationImage);
        BufferedImage image = animation.get(Direction.UP);
        Assert.assertTrue(image != null);
        Assert.assertTrue(image.getWidth() == 32);
        Assert.assertTrue(image.getHeight() == 32);
    }

    @Test
    public void AnimationDownTest() {
        Animation animation = new Animation("animation", animationImage);
        BufferedImage image = animation.get(Direction.DOWN);
        Assert.assertTrue(image != null);
        Assert.assertTrue(image.getWidth() == 32);
        Assert.assertTrue(image.getHeight() == 32);
    }

    @Test
    public void AnimationLeftTest() {
        Animation animation = new Animation("animation", animationImage);
        BufferedImage image = animation.get(Direction.LEFT);
        Assert.assertTrue(image != null);
        Assert.assertTrue(image.getWidth() == 32);
        Assert.assertTrue(image.getHeight() == 32);
    }

    @Test
    public void AnimationRightTest() {
        Animation animation = new Animation("animation", animationImage);
        BufferedImage image = animation.get(Direction.RIGHT);
        Assert.assertTrue(image != null);
        Assert.assertTrue(image.getWidth() == 32);
        Assert.assertTrue(image.getHeight() == 32);
    }
}
