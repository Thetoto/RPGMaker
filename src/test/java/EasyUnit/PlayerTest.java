package EasyUnit;

import Model.World.Direction;
import Model.World.Foreground;
import Model.World.Player;
import Model.World.Tile;
import org.junit.*;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class PlayerTest {

    Player p = null;

    @Before
    public void setUp() {
        p = new Player();
    }

    @Test
    public void MoveOneUnitUpTest() {
        p.move(Direction.UP, 100);
        Assert.assertTrue(p.getPosition().equals(new Point2D.Double(0, -1)));
    }

    @Test
    public void MoveOneUnitDownTest() {
        p.move(Direction.DOWN, 100);
        Assert.assertTrue(p.getPosition().equals(new Point2D.Double(0, 1)));
    }

    @Test
    public void MoveOneUnitLeftTest() {
        p.move(Direction.LEFT, 100);
        Assert.assertTrue(p.getPosition().equals(new Point2D.Double(-1, 0)));
    }

    @Test
    public void MoveOneUnitRightTest() {
        p.move(Direction.RIGHT, 100);
        Assert.assertTrue(p.getPosition().equals(new Point2D.Double(1, 0)));
    }

    @Test
    public void MoveTwoUnitUpDownTest() {
        p.move(Direction.UP, 100);
        p.move(Direction.DOWN, 100);
        Assert.assertTrue(p.getPosition().equals(new Point2D.Double(0, 0)));
    }

    @Test
    public void MoveTwoUnitDownUpTest() {
        p.move(Direction.DOWN, 100);
        p.move(Direction.UP, 100);
        Assert.assertTrue(p.getPosition().equals(new Point2D.Double(0, 0)));
    }

    @Test
    public void MoveTwoUnitLeftRightTest() {
        p.move(Direction.LEFT, 100);
        p.move(Direction.RIGHT, 100);
        Assert.assertTrue(p.getPosition().equals(new Point2D.Double(0, 0)));
    }

    @Test
    public void MoveTwoUnitRightLeftTest() {
        p.move(Direction.RIGHT, 100);
        p.move(Direction.LEFT, 100);
        Assert.assertTrue(p.getPosition().equals(new Point2D.Double(0, 0)));
    }

    @Test
    public void MoveTwoUnitUpLeftTest() {
        p.move(Direction.UP, 100);
        p.move(Direction.LEFT, 100);
        Assert.assertTrue(p.getPosition().equals(new Point2D.Double(-1, -1)));
    }

    @Test
    public void MoveTwoUnitLeftUpTest() {
        p.move(Direction.LEFT, 100);
        p.move(Direction.UP, 100);
        Assert.assertTrue(p.getPosition().equals(new Point2D.Double(-1, -1)));
    }

    @Test
    public void MoveTwoUnitUpRightTest() {
        p.move(Direction.UP, 100);
        p.move(Direction.RIGHT, 100);
        Assert.assertTrue(p.getPosition().equals(new Point2D.Double(1, -1)));
    }

    @Test
    public void MoveTwoUnitRightUPTest() {
        p.move(Direction.RIGHT, 100);
        p.move(Direction.UP, 100);
        Assert.assertTrue(p.getPosition().equals(new Point2D.Double(1, -1)));
    }

    @Test
    public void MoveTwoUnitDownLeftTest() {
        p.move(Direction.DOWN, 100);
        p.move(Direction.LEFT, 100);
        Assert.assertTrue(p.getPosition().equals(new Point2D.Double(-1, 1)));
    }

    @Test
    public void MoveTwoUnitLeftDownTest() {
        p.move(Direction.LEFT, 100);
        p.move(Direction.DOWN, 100);
        Assert.assertTrue(p.getPosition().equals(new Point2D.Double(-1, 1)));
    }

    @Test
    public void MoveTwoUnitDownRightTest() {
        p.move(Direction.DOWN, 100);
        p.move(Direction.RIGHT, 100);
        Assert.assertTrue(p.getPosition().equals(new Point2D.Double(1, 1)));
    }

    @Test
    public void MoveTwoUnitRightDownTest() {
        p.move(Direction.RIGHT, 100);
        p.move(Direction.DOWN, 100);
        Assert.assertTrue(p.getPosition().equals(new Point2D.Double(1, 1)));
    }

    @Test
    public void MoveUpCheckDirectionTest() {
        p.move(Direction.UP, 100);
        Assert.assertTrue(p.getDirection() == Direction.UP);
    }

    @Test
    public void MoveLeftCheckDirectionTest() {
        p.move(Direction.LEFT, 100);
        Assert.assertTrue(p.getDirection() == Direction.LEFT);
    }

    @Test
    public void MoveDownCheckDirectionTest() {
        p.move(Direction.DOWN, 100);
        Assert.assertTrue(p.getDirection() == Direction.DOWN);
    }

    @Test
    public void MoveRightCheckDirectionTest() {
        p.move(Direction.RIGHT, 100);
        Assert.assertTrue(p.getDirection() == Direction.RIGHT);
    }

    @Test
    public void AddItemTest() {
        BufferedImage testImage = new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB);
        Tile testTile = new Tile("Test.png", testImage);
        Foreground testForeground = new Foreground(testTile);
        p.getItems().add(testForeground);

        Assert.assertTrue(p.hasItem("Test"));
        Assert.assertFalse(p.hasItem("test"));
    }
}
