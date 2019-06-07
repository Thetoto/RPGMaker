package EasyUnit;

import Model.World.Map;
import Model.World.World;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

public class WorldTest {

    World world = null;

    @Before
    public void setUp() {
        world = new World("world");
    }

    @Test
    public void StringTest() {
        Assert.assertEquals("world", world.toString());
    }

    @Test
    public void EmptyTest() {
        Assert.assertNotNull(world.getPlayer());
        Assert.assertEquals(0, world.getMaps().size());
    }

    @Test
    public void MapTest() {
        Dimension dimension = new Dimension(100, 100);
        Map map = new Map(dimension, "map");

        world.addMap(map);

        Assert.assertEquals(1, world.getMaps().size());
        Assert.assertNotNull(world.getMapById(map.id));
    }
}
