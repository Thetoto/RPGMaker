package EasyUnit;

import Model.World.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class MapObjectsTest {

    NPC npc = null;
    Teleporter teleporter = null;
    TimeCycle timeCycle = null;
    Map map = null;

    @Before
    public void setUp() {
        BufferedImage animationImage = new BufferedImage(384, 48, BufferedImage.TYPE_INT_ARGB);
        Animation animation = new Animation("npc", animationImage);
        Point p = new Point(50, 50);
        npc = new NPC(animation, p);

        Point point = new Point(0, 0);
        Dimension mapDimension = new Dimension(100, 100);
        map = new Map(mapDimension, "map");
        teleporter = new Teleporter(map, "teleporter", point);

        timeCycle = new TimeCycle();
    }

    @Test
    public void DefaultMessageTest() {
        Assert.assertTrue(npc.getMessage().equals("Hello!"));
    }

    @Test
    public void MessageTest() {
        npc.setMessage("Hello Toi!");
        Assert.assertTrue(npc.getMessage().equals("Hello Toi!"));
    }

    @Test
    public void MoveOneUnitUpTest() {
        npc.setDest(new Point2D.Double(0, 0));
        npc.move(Direction.UP, 1000);
        Assert.assertTrue(npc.getCoordinates().equals(new Point2D.Double(50, 47)));
    }

    @Test
    public void MoveOneUnitDownTest() {
        npc.setDest(new Point2D.Double(100, 100));
        npc.move(Direction.DOWN, 1000);
        Assert.assertTrue(npc.getCoordinates().equals(new Point2D.Double(50, 53)));
    }

    @Test
    public void MoveOneUnitLeftTest() {
        npc.setDest(new Point2D.Double(0, 0));
        npc.move(Direction.LEFT, 1000);
        Assert.assertTrue(npc.getCoordinates().equals(new Point2D.Double(47, 50)));
    }

    @Test
    public void MoveOneUnitRightTest() {
        npc.setDest(new Point2D.Double(100, 100));
        npc.move(Direction.RIGHT, 1000);
        Assert.assertTrue(npc.getCoordinates().equals(new Point2D.Double(53, 50)));
    }

    @Test
    public void MoveTwoUnitUpDownTest() {
        npc.setDest(new Point2D.Double(0, 0));
        npc.move(Direction.UP, 1000);
        npc.setDest(new Point2D.Double(100, 100));
        npc.move(Direction.DOWN, 1000);
        Assert.assertTrue(npc.getCoordinates().equals(new Point2D.Double(50, 50)));
    }

    @Test
    public void MoveTwoUnitDownUpTest() {
        npc.setDest(new Point2D.Double(100, 100));
        npc.move(Direction.DOWN, 1000);
        npc.setDest(new Point2D.Double(0, 0));
        npc.move(Direction.UP, 1000);
        Assert.assertTrue(npc.getCoordinates().equals(new Point2D.Double(50, 50)));
    }

    @Test
    public void MoveTwoUnitLeftRightTest() {
        npc.setDest(new Point2D.Double(0, 0));
        npc.move(Direction.LEFT, 1000);
        npc.setDest(new Point2D.Double(100, 100));
        npc.move(Direction.RIGHT, 1000);
        Assert.assertTrue(npc.getCoordinates().equals(new Point2D.Double(50, 50)));
    }

    @Test
    public void MoveTwoUnitRightLeftTest() {
        npc.setDest(new Point2D.Double(100, 100));
        npc.move(Direction.RIGHT, 1000);
        npc.setDest(new Point2D.Double(0, 0));
        npc.move(Direction.LEFT, 1000);
        Assert.assertTrue(npc.getCoordinates().equals(new Point2D.Double(50, 50)));
    }

    @Test
    public void MoveTwoUnitUpLeftTest() {
        npc.setDest(new Point2D.Double(0, 0));
        npc.move(Direction.UP, 1000);
        npc.move(Direction.LEFT, 1000);
        Assert.assertTrue(npc.getCoordinates().equals(new Point2D.Double(47, 47)));
    }

    @Test
    public void MoveTwoUnitLeftUpTest() {
        npc.setDest(new Point2D.Double(0, 0));
        npc.move(Direction.LEFT, 1000);
        npc.move(Direction.UP, 1000);
        Assert.assertTrue(npc.getCoordinates().equals(new Point2D.Double(47, 47)));
    }

    @Test
    public void MoveTwoUnitUpRightTest() {
        npc.setDest(new Point2D.Double(0, 0));
        npc.move(Direction.UP, 1000);
        npc.setDest(new Point2D.Double(100, 100));
        npc.move(Direction.RIGHT, 1000);
        Assert.assertTrue(npc.getCoordinates().equals(new Point2D.Double(53, 47)));
    }

    @Test
    public void MoveTwoUnitRightUPTest() {
        npc.setDest(new Point2D.Double(100, 100));
        npc.move(Direction.RIGHT, 1000);
        npc.setDest(new Point2D.Double(0, 0));
        npc.move(Direction.UP, 1000);
        Assert.assertTrue(npc.getCoordinates().equals(new Point2D.Double(53, 47)));
    }

    @Test
    public void MoveTwoUnitDownLeftTest() {
        npc.setDest(new Point2D.Double(100, 100));
        npc.move(Direction.DOWN, 1000);
        npc.setDest(new Point2D.Double(0, 0));
        npc.move(Direction.LEFT, 1000);
        Assert.assertTrue(npc.getCoordinates().equals(new Point2D.Double(47, 53)));
    }

    @Test
    public void MoveTwoUnitLeftDownTest() {
        npc.setDest(new Point2D.Double(0, 0));
        npc.move(Direction.LEFT, 1000);
        npc.setDest(new Point2D.Double(100, 100));
        npc.move(Direction.DOWN, 1000);
        Assert.assertTrue(npc.getCoordinates().equals(new Point2D.Double(47, 53)));
    }

    @Test
    public void MoveTwoUnitDownRightTest() {
        npc.setDest(new Point2D.Double(100, 100));
        npc.move(Direction.DOWN, 1000);
        npc.move(Direction.RIGHT, 1000);
        Assert.assertTrue(npc.getCoordinates().equals(new Point2D.Double(53, 53)));
    }

    @Test
    public void MoveTwoUnitRightDownTest() {
        npc.setDest(new Point2D.Double(100, 100));
        npc.move(Direction.RIGHT, 1000);
        npc.move(Direction.DOWN, 1000);
        Assert.assertTrue(npc.getCoordinates().equals(new Point2D.Double(53, 53)));
    }

    @Test
    public void MoveUpCheckDirectionTest() {
        npc.setDest(new Point2D.Double(100, 100));
        npc.move(Direction.UP, 100);
        Assert.assertTrue(npc.getDirection() == Direction.UP);
    }

    @Test
    public void MoveLeftCheckDirectionTest() {
        npc.setDest(new Point2D.Double(100, 100));
        npc.move(Direction.LEFT, 100);
        Assert.assertTrue(npc.getDirection() == Direction.LEFT);
    }

    @Test
    public void MoveDownCheckDirectionTest() {
        npc.setDest(new Point2D.Double(100, 100));
        npc.move(Direction.DOWN, 100);
        Assert.assertTrue(npc.getDirection() == Direction.DOWN);
    }

    @Test
    public void MoveRightCheckDirectionTest() {
        npc.setDest(new Point2D.Double(100, 100));
        npc.move(Direction.RIGHT, 100);
        Assert.assertTrue(npc.getDirection() == Direction.RIGHT);
    }

    @Test
    public void TeleporterStringTest() {
        Assert.assertTrue(teleporter.toString().equals("map | teleporter"));
    }

    @Test
    public void TeleporterPositionTest() {
        Assert.assertTrue(teleporter.getPosition().equals(new Point(0, 0)));
    }

    @Test
    public void TeleporterDestinationTest() {
        Point point = new Point(50, 50);
        teleporter.setDest(map, point);
        Assert.assertTrue(teleporter.getPointDest().equals(point));
        Assert.assertTrue(teleporter.getMapDestId() == map.id);
    }

    @Test
    public void TimeCycleDelayNightTest() {
        timeCycle.setNightDuration(1000);
        Assert.assertFalse(timeCycle.isNight());
        Assert.assertTrue(timeCycle.getNightDuration() == 1000);
    }

    @Test
    public void TimeCycleDelayDayTest() {
        timeCycle.setDayDuration(1000);
        Assert.assertFalse(timeCycle.isNight());
        Assert.assertTrue(timeCycle.getDayDuration() == 1000);
    }

    @Test
    public void TimeCycleNightTest() {
        timeCycle.switchTime();
        timeCycle.setNightDuration(1000);
        Assert.assertTrue(timeCycle.isNight());
        Assert.assertTrue(timeCycle.getDelay() == 1000);
    }

    @Test
    public void TimeCycleDayTest() {
        timeCycle.setDayDuration(1000);
        Assert.assertFalse(timeCycle.isNight());
        Assert.assertTrue(timeCycle.getDelay() == 1000);
    }
}
