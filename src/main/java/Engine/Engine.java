package Engine;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class Engine extends JFrame implements Observer {
    public Display mapPanel;
    public Dialog dialog;

    public Engine() {
        this.setSize(1080,720);
        this.setTitle("BibleEngine");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);


        mapPanel = new Display();
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.add(mapPanel);
        panel.setVisible(true);

        dialog = new Dialog();
        dialog.setSize(100, 100);
        dialog.setVisible(false);

        JPanel level = new JPanel();
        level.setLayout(new BorderLayout());
        level.add(panel, BorderLayout.CENTER);
        level.add(dialog,  BorderLayout.PAGE_END);
        level.setVisible(true);

        this.add(level);
        this.setVisible(true);
    }

    public static void validateAll(Container j) {
        while (j != null) {
            j.validate();
            j = j.getParent();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof EngineState) {
            if (arg instanceof String) {
                String s = (String) arg;
                if (s.equals("Update Message")) {
                    dialog.setText(EngineState.getInstance().currentMessage);
                    dialog.setVisible(true);
                    validateAll(dialog);
                }
            }
        }
    }
}
