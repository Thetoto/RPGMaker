package Engine;

import Model.Editor.EditorState;
import Model.World.ImportedTile;
import Model.World.Map;
import Model.World.Player;
import Tools.Draw;
import Tools.ThreadLauncher;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
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

    BufferedImage background = null;
    BufferedImage foreground = null;

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

        this.background = back;

        BufferedImage resized = getResizedImage(back);

        backLayer.setIcon(new ImageIcon(resized));
        backLayer.setBounds(0, 0, resized.getWidth(), resized.getHeight());
    }

    public void drawBackLayerUpdate() {
        if (background == null)
            return;
        BufferedImage resized = getResizedImage(background);

        backLayer.setIcon(new ImageIcon(resized));
        backLayer.setBounds(0, 0, resized.getWidth(), resized.getHeight());
    }

    public void drawForeLayer(Map map) {
        BufferedImage fore = createImage(map.getDim());

        Graphics2D g = fore.createGraphics();

        Draw.drawForeTiles(g, map, 16);
        g.dispose();

        this.foreground = fore;

        BufferedImage resized = getResizedImage(fore);

        foreLayer.setIcon(new ImageIcon(resized));
        foreLayer.setBounds(0, 0, resized.getWidth(), resized.getHeight());
    }

    public void drawForeLayerUpdate() {
        if (foreground == null)
            return;
        BufferedImage resized = getResizedImage(foreground);

        foreLayer.setIcon(new ImageIcon(resized));
        foreLayer.setBounds(0, 0, resized.getWidth(), resized.getHeight());
    }

    public void drawNpcLayer(Map map) {
        BufferedImage npc = createImage(map.getDim());

        Graphics2D g = npc.createGraphics();
        Draw.drawNPC(g, map, 16);
        g.dispose();

        BufferedImage resized = getResizedImage(npc);

        npcLayer.setIcon(new ImageIcon(resized));
        npcLayer.setBounds(0, 0,  resized.getWidth(), resized.getHeight());
    }

    public void drawPlayerLayer(EngineState state) {
        BufferedImage player = createImage(state.currentMap.getDim());

        Graphics2D g = player.createGraphics();
        ImportedTile t = state.player.getAnim().toImportedTile(state.player.getDirection());
        Draw.drawImported(g, t, state.player.getPosition(), 16);
        g.dispose();

        BufferedImage resized = getResizedImage(player);

        playerLayer.setIcon(new ImageIcon(resized));
        playerLayer.setBounds(0, 0, resized.getWidth(), resized.getHeight());
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

        BufferedImage resized = getResizedImage(time);

        timeCycleLayer.setIcon(new ImageIcon(resized));
        timeCycleLayer.setBounds(0, 0, resized.getWidth(), resized.getHeight());
    }

    public void drawAll(EngineState state) {
        ThreadLauncher.execute(() -> drawBackLayer(state.currentMap));
        ThreadLauncher.execute(() -> drawForeLayer(state.currentMap));
        ThreadLauncher.execute(() -> drawNpcLayer(state.currentMap));
        ThreadLauncher.execute(() -> drawPlayerLayer(state));
        if (state.world.timeCycle.isActive());
            ThreadLauncher.execute(() -> drawTimeCycleLayer(state.currentMap, state.world.timeCycle.isNight()));
    }

    public void drawUpdate (EngineState state) {
        drawBackLayerUpdate();
        drawForeLayerUpdate();
        drawNpcLayer(state.currentMap);
        drawPlayerLayer(state);
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
                drawUpdate(state);
                repaint();
            }
            if (str.equals("Update NPC")) {
                drawNpcLayer(state.currentMap);
                repaint();
            }
        }
    }

    private BufferedImage getResizedImage(BufferedImage image) {
        Map map = EditorState.getInstance().mapState.currentMap;
        int width = Math.min(992, image.getWidth());
        int height = Math.min(688, image.getHeight());

        Player p = EngineState.getInstance().player;

        int deltaX = 0;
        int deltaY = 0;

        Point2D pos = p.getPosition();

        if (pos.getX() * 16 >= width / 2) {
            if (pos.getX() * 16 > (map.getDim().width * 16) - width / 2)
                deltaX = (map.getDim().width * 16) - width;
            else
                deltaX = (int) ((pos.getX() * 16) - (width / 2));
        }
        if (pos.getY() * 16 >= height / 2) {
            if (pos.getY() * 16 > (map.getDim().height * 16) - height / 2)
                deltaY = (map.getDim().height * 16) - height;
            else
                deltaY = (int) ((pos.getY() * 16) - (height / 2));
        }

        BufferedImage tmp2 = image.getSubimage(deltaX, deltaY, width, height);

        return tmp2;
    }

    private void setSizeMap() {
        Map map = EngineState.getInstance().currentMap;

        int width = Math.min(992, map.getDim().width * 16);
        int height = Math.min(688, map.getDim().height * 16);

        System.out.println(width + " " + height);
        this.setPreferredSize(new Dimension(width, height));
        this.setSize(new Dimension(width, height));

        Engine.validateAll(this);
    }
}
