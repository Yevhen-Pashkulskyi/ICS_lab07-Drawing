import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class AppDrawing extends JPanel {

    private JTextField x1, y1, x2, y2;
    private JLabel resultLabel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AppDrawing();
            }
        });
    }

    AppDrawing() {
        JFrame frame = new JFrame("Креслення");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel input = new JPanel(new GridLayout(5, 2,10,10));
        input.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        input.add(new JLabel("x1:"));
        x1 = new JTextField();
        input.add(x1);
        input.add(new JLabel("y1:"));
        y1 = new JTextField();
        input.add(y1);
        input.add(new JLabel("x2:"));
        x2 = new JTextField();
        input.add(x2);
        input.add(new JLabel("y2:"));
        y2 = new JTextField();
        input.add(y2);

//        JPanel panelResult = new JPanel();

        JButton buttonTask = new JButton("Завдання");
        JButton buttonCount = new JButton("Розрахунок");

        JTextArea textArea = new JTextArea();
        buttonTask.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setText(printTask());
            }
        });


        frame.add(input);
        input.add(buttonTask);
        input.add(buttonCount);
        input.add(textArea);

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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

}
