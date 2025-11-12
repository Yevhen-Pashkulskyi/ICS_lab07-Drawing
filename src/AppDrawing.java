import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class AppDrawing extends JPanel {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AppDrawing();
            }
        });
    }

    AppDrawing() {
        JFrame frame = new JFrame("Креслення");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();

        JButton buttonTask = new JButton("Завдання");
        JButton button2 = new JButton("Кнопка");
        JLabel label = new JLabel();
        buttonTask.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label.setText(printTask());
            }
        });

        frame.add(panel);
        panel.add(buttonTask);
        panel.add(button2);
        panel.add(label);

        frame.setVisible(true);
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
