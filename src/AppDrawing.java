import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AppDrawing extends JPanel {

    private JTextField x1, y1, x2, y2;
    private JLabel resultLabel;
    private JPanel mainPanel;
    private JTextArea textArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AppDrawing();
            }
        });
    }

    AppDrawing() {
        JFrame frame = new JFrame("Креслення");
        frame.setLayout(new FlowLayout());
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new JPanel(new GridLayout(7, 1, 5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(new JLabel("x1:", JLabel.RIGHT));
        x1 = new JTextField();
        mainPanel.add(x1);

        mainPanel.add(new JLabel("y1:", JLabel.RIGHT));
        y1 = new JTextField();
        mainPanel.add(y1);

        mainPanel.add(new JLabel("x2:", JLabel.RIGHT));
        x2 = new JTextField();
        mainPanel.add(x2);

        mainPanel.add(new JLabel("y2:", JLabel.RIGHT));
        y2 = new JTextField();
        mainPanel.add(y2);

        frame.add(mainPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        mainPanel.add(buttonPanel);

        JButton buttonTask = new JButton("Завдання");
        JButton buttonCount = new JButton("Розрахунок");
        buttonPanel.add(buttonTask);
        buttonPanel.add(buttonCount);

        textArea = new JTextArea();
        JPanel panelResult = new JPanel();

        buttonTask.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setText(printTask());
                panelResult.add(textArea);
                frame.add(panelResult);
            }

        });

        buttonCount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (x1.getText().isEmpty() || y1.getText().isEmpty() || x2.getText().isEmpty() || y2.getText().isEmpty()) {
                    textArea.setText("Поля вводу не можуть бути порожніми");
                } else {
                    textArea.setText(draw(x1, y1, x2, y2));
                }
                panelResult.add(textArea);
                frame.add(panelResult);
            }
        });

        frame.setVisible(true); // отобразіть контейнер
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

    private String draw(JTextField x1, JTextField y1, JTextField x2, JTextField y2) {
        try {
            double x = Double.parseDouble(x1.getText());
            double y = Double.parseDouble(y1.getText());
            double xx = Double.parseDouble(x2.getText());
            double yy = Double.parseDouble(y2.getText());
            String result = String.format("%.2f", x + y + xx + yy);
            return result;
        } catch (NumberFormatException e) {
            return "В полях повинні бути тільки числа";
        }
    }

}
