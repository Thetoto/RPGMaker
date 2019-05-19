package Tools;

import Model.Editor.EditorState;
import Model.World.Map;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class PopUpManager {

    public static void Alert(String text) {
        JFrame frame = new JFrame();
        frame.setLocationRelativeTo(null);
        frame.setTitle("Alert");

        frame.setLayout(new GridLayout(2, 1));

        JLabel label = new JLabel(text);
        frame.add(label);

        JButton button = new JButton("Ok");
        frame.add(button);

        frame.pack();
        frame.setSize(new Dimension(frame.getWidth(), 100));
        frame.setVisible(true);

        button.addActionListener(evt -> frame.dispose());
    }

    public static void askNewMap(EditorState editorState) {
        JFrame frame = new JFrame();
        frame.setLayout(new GridLayout(4,2));
        frame.setLocationRelativeTo(null);
        frame.setTitle("Choose the configuration");

        JLabel name_panel = new JLabel("Name");
        JLabel width_panel = new JLabel("Width");
        JLabel height_panel = new JLabel("Height");

        JButton validate = new JButton( "Validate");
        JButton cancel = new JButton( "Cancel");
        JTextField name = new JTextField();
        JSpinner width = new JSpinner();
        JSpinner height = new JSpinner();

        name.setSize(50, 1);
        name.setText("New map");
        name.setVisible(true);
        width.setModel(new SpinnerNumberModel(20, 1, 800, 1));
        width.setVisible(true);
        height.setModel(new SpinnerNumberModel(20, 1, 800, 1));
        height.setVisible(true);

        validate.addActionListener(e -> {
            int Mwidth = (int) width.getModel().getValue();
            int Mheight = (int) height.getModel().getValue();
            String Mname = name.getText();
            frame.dispose();
            Map map = new Map(new Dimension(Mwidth, Mheight), Mname);
            if (editorState.world == null) {
                editorState.defaultWorld(map);
            } else {
                editorState.addMap(map);
            }
        });
        cancel.addActionListener(e -> frame.dispose());


        frame.add(name_panel);
        frame.add(name);
        frame.add(width_panel);
        frame.add(width);
        frame.add(height_panel);
        frame.add(height);
        frame.add(cancel);
        frame.add(validate);

        frame.validate();
        frame.setVisible(true);
        frame.pack();
        frame.setSize(250, 150);
    }
}
