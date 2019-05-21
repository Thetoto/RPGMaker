package Model.World;

import java.awt.image.BufferedImage;

public class NPC extends Animation {
    String message;

    public NPC(String name, BufferedImage bi) {
        super(name, bi);
        message = "Hello!";
    }

    @Override
    public String toString() {
        return getName();
    }
}
