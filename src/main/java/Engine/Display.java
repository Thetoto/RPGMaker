package Engine;

import Model.World.ImportedTile;
import Model.World.Map;
import Tools.Draw;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

public class Display extends JLayeredPane implements Observer {
    static Integer BACK_LAYER = 0;
    static Integer FORE_LAYER = 1;
    static Integer NPC_LAYER = 2;
    static Integer PLAYER_LAYER = 3;
    static Integer TIME_CYCLE_LAYER = 4;

    JLabel backLayer;
    JLabel foreLayer;
    JLabel npcLayer;
    JLabel playerLayer;
    JLabel timeCycleLayer;

    public static BufferedImage createImage(Dimension dim) {
        return new BufferedImage(dim.width * 16, dim.height * 16, BufferedImage.TYPE_INT_ARGB);
    }

    public Display() {
        backLayer = new JLabel();
        this.add(backLayer, BACK_LAYER);

        foreLayer = new JLabel();
        this.add(foreLayer, FORE_LAYER);

        npcLayer = new JLabel();
        this.add(npcLayer, NPC_LAYER);

        playerLayer = new JLabel();
        this.add(playerLayer, PLAYER_LAYER);

        timeCycleLayer = new JLabel();
        this.add(timeCycleLayer, TIME_CYCLE_LAYER);
    }

    public void drawBackLayer(Map map) {
        Dimension dim = map.getDim();
        BufferedImage back = createImage(map.getDim());

        Graphics2D g = back.createGraphics();
        Draw.drawBackground(g, map.getBackgroundTile(), dim, 16);
        Draw.drawBackTiles(g, map, 16);
        g.dispose();

        backLayer.setIcon(new ImageIcon(back));
        backLayer.setBounds(0, 0, back.getWidth(), back.getHeight());
    }

    public void drawForeLayer(Map map) {
        BufferedImage fore = createImage(map.getDim());

        Graphics2D g = fore.createGraphics();

        Draw.drawForeTiles(g, map, 16);
        g.dispose();

        foreLayer.setIcon(new ImageIcon(fore));
        foreLayer.setBounds(0, 0, fore.getWidth(), fore.getHeight());
    }

    public void drawNpcLayer(Map map) {
        BufferedImage npc = createImage(map.getDim());

        Graphics2D g = npc.createGraphics();
        Draw.drawNPC(g, map, 16);
        g.dispose();

        npcLayer.setIcon(new ImageIcon(npc));
        npcLayer.setBounds(0, 0, npc.getWidth(), npc.getHeight());
    }

    public void drawPlayerLayer(EngineState state) {
        BufferedImage player = createImage(state.currentMap.getDim());

        Graphics2D g = player.createGraphics();
        ImportedTile t = state.player.getAnim().toImportedTile(state.player.getDirection());
        Draw.drawImported(g, t, state.player.getPosition(), 16);
        g.dispose();

        playerLayer.setIcon(new ImageIcon(player));
        playerLayer.setBounds(0, 0, player.getWidth(), player.getHeight());
    }

    public void drawTimeCycleLayer(Map map, boolean isNight) {
        BufferedImage time = createImage(map.getDim());

        Graphics2D g = time.createGraphics();
        int alpha = 0;
        if (isNight)
            alpha = 100;
        g.setColor(new Color(100, 100, 100, alpha));
        g.fillRect(0,0,time.getWidth(), time.getHeight());
        g.dispose();

        timeCycleLayer.setIcon(new ImageIcon(time));
        timeCycleLayer.setBounds(0, 0, time.getWidth(), time.getHeight());
    }

    public void drawAll(EngineState state) {
        drawBackLayer(state.currentMap);
        drawForeLayer(state.currentMap);
        drawNpcLayer(state.currentMap);
        drawPlayerLayer(state);
        if (state.world.timeCycle.isActive());
            drawTimeCycleLayer(state.currentMap, state.world.timeCycle.isNight());
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof EngineState && o instanceof String) {
            EngineState state = (EngineState)observable;
            String str = (String)o;
            if (str.equals("Change Map")) {
                drawAll(state);
                setSizeMap();
                repaint();
            }
            if (str.equals("Update Perso")) {
                drawPlayerLayer(state);
                repaint();
            }
            if (str.equals("Update NPC")) {
                drawNpcLayer(state.currentMap);
                repaint();
            }
        }
    }

    private void setSizeMap() {
        Image image = ((ImageIcon) backLayer.getIcon()).getImage();
        this.setPreferredSize(new Dimension((int)(image.getWidth(null)), (int)(image.getHeight(null))));
        this.setSize(new Dimension((int)(image.getWidth(null)), (int)(image.getHeight(null))));

        Engine.validateAll(this);
    }
}
