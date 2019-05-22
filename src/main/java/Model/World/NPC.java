package Model.World;

import java.awt.*;

public class NPC {
    private String name;
    private String message;
    private Direction direction;
    private Animation anim;
    private Point coordinates;

    public NPC(Animation anim, Point coordinates) {
        this.anim = anim;
        this.direction = Direction.DOWN;
        this.message = "Hello!";
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        if (name == null)
            return anim.getName();
        return name;
    }

    public Animation getAnimation() {
        return anim;
    }

    public String getName() {
        if (name == null)
            return anim.getName();
        return name;
    }

    public Point getPoint() {
        return this.coordinates;
    }

    public void setAnimation(Animation animation) {
        anim = animation;
    }
}
