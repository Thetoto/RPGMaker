package Editor;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;

public class TreePanel extends JTree {
    File dir = null;

    public TreePanel() {
    }

    public void setDir(File directory) {
        dir  = directory;
        show_dir();
    }

    public void show_dir() {
        if (dir == null)
            return;
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(new FileNode(dir));
        DefaultTreeModel model = new DefaultTreeModel(root);
        this.setModel(model);
        this.setShowsRootHandles(true);

        CreateChildNodes ccn = new CreateChildNodes(dir, root);
        new Thread(ccn).start();
    }
}

class CreateChildNodes implements Runnable {

    private DefaultMutableTreeNode root;

    private File fileRoot;

    public CreateChildNodes(File fileRoot, DefaultMutableTreeNode root) {
        this.fileRoot = fileRoot;
        this.root = root;
    }

    public void run() {
        createChildren(fileRoot, root);
    }

    private void createChildren(File fileRoot,
                                DefaultMutableTreeNode node) {
        File[] files = fileRoot.listFiles();
        if (files == null) return;

        for (File file : files) {
            DefaultMutableTreeNode childNode =
                    new DefaultMutableTreeNode(new FileNode(file));
            node.add(childNode);
            if (file.isDirectory()) {
                createChildren(file, childNode);
            }
        }
    }

}

class FileNode {
    private File file;

    public FileNode(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        String name = file.getName();
        if (name.equals("")) {
            return file.getAbsolutePath();
        } else {
            return name;
        }
    }
}
