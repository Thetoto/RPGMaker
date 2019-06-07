package TimerUnit;

import Model.Engine.Timer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TimerTest {

    Timer timer = null;
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(out));
        timer = new Timer(3000, actionEvent -> {
            System.out.print("Done");
            return;
        });
    }

    @After
    public void clean() {
        System.setOut(null);
    }

    @Test
    public void PauseTest() {
        try {
            timer.start();
            Thread.sleep(2000);
            timer.pause();
            Thread.sleep(3000);
            Assert.assertTrue(out.toString() + "END", out.toString().equals(""));
            timer.resume();
            Thread.sleep(2000);
            Assert.assertTrue(out.toString() + "END", out.toString().equals("Done"));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue( false);
        }
    }
}
