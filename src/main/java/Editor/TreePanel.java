package Editor;

import Model.Editor.EditorState;
import Model.World.Map;
import Model.World.Player;
import Model.World.Teleporter;
import Model.World.World;
import Tools.Tools;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;

public class TreePanel extends JTree {

    public TreePanel() {
        setModel(null);
        this.setCellRenderer(new WorldTreeCellRenderer());
    }

    public void show_world(World world) {
        if (world == null)
            return;

        DefaultMutableTreeNode Tworld = new DefaultMutableTreeNode(world);
        DefaultTreeModel model = new DefaultTreeModel(Tworld);

        DefaultMutableTreeNode Tmaps = new DefaultMutableTreeNode("Maps");
        DefaultMutableTreeNode Tplayer = new DefaultMutableTreeNode(world.getPlayer());

        Tworld.add(Tmaps);
        Tworld.add(Tplayer);

        TreePath curPath = null;

        for (Map map : world.getMaps()) {
            DefaultMutableTreeNode Tmap = new DefaultMutableTreeNode(map);
            for (Teleporter t : map.getTeleporters()) {
                DefaultMutableTreeNode teleporter = new DefaultMutableTreeNode(t);
                Tmap.add(teleporter);
            }
            Tmaps.add(Tmap);
            if (map == EditorState.getInstance().mapState.currentMap)
                curPath = new TreePath(Tmap.getPath());
        }
        this.setModel(model);
        this.setShowsRootHandles(true);
        this.expandPath(new TreePath(Tmaps.getPath()));
        if (curPath != null)
            this.expandPath(curPath);

    }

    public void update_map(Map map) {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) getModel().getRoot();
        World w = (World) root.getUserObject();
        show_world(w);
    }
}

class WorldTreeCellRenderer implements TreeCellRenderer {
    Icon map_icon = new ImageIcon(Tools.getPathFromRessources("map_icon.png"));
    Icon world_icon = new ImageIcon(Tools.getPathFromRessources("world_icon.png"));
    Icon player_icon = new ImageIcon(Tools.getPathFromRessources("player_icon.png"));
    Icon teleporter_icon = new ImageIcon(Tools.getPathFromRessources("teleporter_icon.png"));

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        Object o = ((DefaultMutableTreeNode) value).getUserObject();
        JLabel label = new JLabel();

        if (o instanceof Map) {
            Map map = (Map) o;
            label.setText(map.toString());
            label.setIcon(map_icon);
        } else if (o instanceof World) {
            World world = (World) o;
            label.setText(world.toString());
            label.setIcon(world_icon);
        } else if (o instanceof Player) {
            label.setText("Player");
            label.setIcon(player_icon);
        } else if (o instanceof Teleporter) {
            Teleporter t = (Teleporter) o;
            label.setText(t.toString());
            label.setIcon(teleporter_icon);
        } else {
                label.setText(o.toString());
        }
        return label;
    }
}