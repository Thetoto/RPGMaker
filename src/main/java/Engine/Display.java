package Engine;

import Model.World.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Display extends JLayeredPane {
    static Integer BACK_LAYER = 0;
    static Integer FORE_LAYER = 1;
    static Integer NPC_LAYER = 2;
    static Integer PLAYER_PLAYER = 3;

    JLabel backLayer;
    JLabel foreLayer;
    JLabel npcLayer;
    JLabel playerLayer;

    public Display() {
        backLayer = new JLabel();
        this.add(backLayer, BACK_LAYER);

        foreLayer = new JLabel();
        this.add(foreLayer, FORE_LAYER);

        npcLayer = new JLabel();
        this.add(npcLayer, NPC_LAYER);

        playerLayer = new JLabel();
        this.add(playerLayer, PLAYER_PLAYER);
    }



}
