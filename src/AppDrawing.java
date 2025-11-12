import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
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
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label.setText(printTask());
            }
        });
        label2 = new JLabel("Label2");
        frame.add(panel);
        panel.add(button);
        panel.add(button2);
        label = new JLabel();
        panel.add(label);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AppDrawing();
            }
        });
    }

    private String printTask() {
        String path = "INFO/task.txt";
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
