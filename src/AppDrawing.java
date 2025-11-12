import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class AppDrawing extends JPanel {
    JFrame frame;
    JPanel panel;
    JButton button;
    JButton button2;
    JLabel label;
    JLabel label2;

    AppDrawing() {
        frame = new JFrame("Drawing");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        button = new JButton("Draw_button_1");
        button2 = new JButton("Draw_button_2");
        label = new JLabel("Завдання");
        label2 = new JLabel("Label2");
        frame.add(panel);
        panel.add(button);
        panel.add(button2);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AppDrawing();
            }
        });
    }

}
