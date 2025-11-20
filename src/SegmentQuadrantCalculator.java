import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SegmentQuadrantCalculator extends JPanel {

    private JTextField x1Field, y1Field, x2Field, y2Field;
    private JLabel resultLabel, taskLabel;
    private JPanel graphPanel;
    private static final int MAX_COORD = 12; // Максимальна координата на графіку

    public SegmentQuadrantCalculator() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Верхня панель: поля вводу + кнопки
        add(createInputPanel(), BorderLayout.NORTH);

        // Центральна панель: графік
        graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGraph((Graphics2D) g);
            }
        };
        graphPanel.setPreferredSize(new Dimension(400, 400));
        graphPanel.setBorder(BorderFactory.createTitledBorder("Координатна площина"));
        add(graphPanel, BorderLayout.CENTER);

        // Нижня панель: результат + завдання
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        resultLabel = new JLabel("<html><i>Введіть координати...</i></html>", SwingConstants.CENTER);
        resultLabel.setBorder(BorderFactory.createTitledBorder("Результат"));
        bottomPanel.add(resultLabel);

        taskLabel = new JLabel("<html><i>Натисніть кнопку «Завдання»</i></html>", SwingConstants.CENTER);
        taskLabel.setBorder(BorderFactory.createTitledBorder("Текст завдання"));
        bottomPanel.add(taskLabel);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Поля вводу
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("X₁ :"), gbc);
        x1Field = new JTextField("3", 6);
        gbc.gridx = 1;
        inputPanel.add(x1Field, gbc);

        gbc.gridx = 2;
        inputPanel.add(new JLabel("Y₁ :"), gbc);
        y1Field = new JTextField("4", 6);
        gbc.gridx = 3;
        inputPanel.add(y1Field, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("X₂ :"), gbc);
        x2Field = new JTextField("-2", 6);
        gbc.gridx = 1;
        inputPanel.add(x2Field, gbc);

        gbc.gridx = 2;
        inputPanel.add(new JLabel("Y₂ :"), gbc);
        y2Field = new JTextField("5", 6);
        gbc.gridx = 3;
        inputPanel.add(y2Field, gbc);

        // Кнопка "Розрахувати"
        JButton calcButton = new JButton("Розрахувати");
        calcButton.addActionListener(e -> calculateAndDraw());
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        inputPanel.add(calcButton, gbc);

        // Кнопка "Завдання"
        JButton taskButton = new JButton("Завдання");
        taskButton.addActionListener(e -> showTaskFromFile());
        gbc.gridx = 2; gbc.gridwidth = 2;
        inputPanel.add(taskButton, gbc);

        return inputPanel;
    }

    // Метод для читання завдання з файлу
    private void showTaskFromFile() {
        String content = readTaskFromFile();
        if (content != null) {
            taskLabel.setText("<html><pre>" + content + "</pre></html>");
        } else {
            taskLabel.setText("<html><font color='red'>Файл не знайдено: INFO/task.txt</font></html>");
        }
    }

    private String readTaskFromFile() {
        String path = "INFO/task.txt";
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString().trim();
        } catch (IOException e) {
            System.err.println("Помилка читання файлу: " + e.getMessage());
            return null;
        }
    }

    private void calculateAndDraw() {
        try {
            double x1 = Double.parseDouble(x1Field.getText());
            double y1 = Double.parseDouble(y1Field.getText());
            double x2 = Double.parseDouble(x2Field.getText());
            double y2 = Double.parseDouble(y2Field.getText());

            // Перевірка: координати не повинні виходити за межі графіку
            if (Math.abs(x1) > MAX_COORD || Math.abs(y1) > MAX_COORD ||
                    Math.abs(x2) > MAX_COORD || Math.abs(y2) > MAX_COORD) {
                resultLabel.setText("<html><font color='red'>Помилка: координати мають бути в межах [-" + MAX_COORD + ", " + MAX_COORD + "]</font></html>");
                graphPanel.repaint();
                return;
            }

            String result = calculateSegmentQuadrants(x1, y1, x2, y2);
            resultLabel.setText("<html>" + result.replace("\n", "<br>") + "</html>");
            graphPanel.repaint();

        } catch (NumberFormatException e) {
            resultLabel.setText("<html><font color='red'>Помилка: введіть числові значення!</font></html>");
        }
    }

    private String calculateSegmentQuadrants(double x1, double y1, double x2, double y2) {
        StringBuilder sb = new StringBuilder();

        String qa = getQuadrantName(x1, y1);
        String qb = getQuadrantName(x2, y2);

        sb.append("Точка A(").append(format(x1)).append(", ").append(format(y1))
                .append(") → ").append(qa).append("<br>");
        sb.append("Точка B(").append(format(x2)).append(", ").append(format(y2))
                .append(") → ").append(qb).append("<br><br>");

        boolean crossesX = (y1 >= 0 && y2 <= 0) || (y1 <= 0 && y2 >= 0);
        boolean crossesY = (x1 >= 0 && x2 <= 0) || (x1 <= 0 && x2 >= 0);

        if (crossesX) sb.append("Перетинає вісь X<br>");
        if (crossesY) sb.append("Перетинає вісь Y<br>");

        sb.append("<br><b>Відрізок проходить через:</b><br>");
        if (qa.equals(qb) && !qa.equals("На осі")) {
            sb.append(qa).append(" квадрант");
        } else {
            sb.append(qa).append(" і ").append(qb).append(" квадранти");
            if (crossesX || crossesY) sb.append(" (перетинає осі)");
        }

        return sb.toString();
    }

    // Метод для перевірки точки в якому квадранті вона знаходиться
    private String getQuadrantName(double x, double y) {
        if (x > 0 && y > 0) return "I";
        if (x < 0 && y > 0) return "II";
        if (x < 0 && y < 0) return "III";
        if (x > 0 && y < 0) return "IV";
        return "На осі";
    }

    // Метод для форматування значення в строку
    private String format(double d) {
        return d == (int)d ? String.valueOf((int)d) : String.format("%.1f", d);
    }

    private void drawGraph(Graphics2D g) {
        int w = graphPanel.getWidth();
        int h = graphPanel.getHeight();
        int cx = w / 2, cy = h / 2;
        int scale = 20;

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, w, h);

        // Осі
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.drawLine(30, cy, w - 30, cy);
        g.drawLine(cx, 30, cx, h - 30);
        g.drawString("X", w - 40, cy + 15);
        g.drawString("Y", cx + 5, 40);

        // Мітки по осях
        g.setColor(Color.GRAY);
        for (int i = -MAX_COORD; i <= MAX_COORD; i++) {
            if (i == 0) continue;
            int px = cx + i * scale;
            int py = cy - i * scale;
            g.drawLine(px, cy - 3, px, cy + 3);
            g.drawLine(cx - 3, py, cx + 3, py);
            if (i % 5 == 0) {
                g.drawString(String.valueOf(i), px - 5, cy + 15);
                g.drawString(String.valueOf(i), cx - 25, py + 5);
            }
        }

        // Квадранти
        g.setFont(new Font("SansSerif", Font.PLAIN, 14));
        g.drawString("I", cx + 20, cy - 20);
        g.drawString("II", cx - 50, cy - 20);
        g.drawString("III", cx - 50, cy + 40);
        g.drawString("IV", cx + 20, cy + 40);

        // Точки та відрізок
        try {
            double x1 = Double.parseDouble(x1Field.getText());
            double y1 = Double.parseDouble(y1Field.getText());
            double x2 = Double.parseDouble(x2Field.getText());
            double y2 = Double.parseDouble(y2Field.getText());

            if (Math.abs(x1) <= MAX_COORD && Math.abs(y1) <= MAX_COORD &&
                    Math.abs(x2) <= MAX_COORD && Math.abs(y2) <= MAX_COORD) {

                int ax = cx + (int)(x1 * scale);
                int ay = cy - (int)(y1 * scale);
                int bx = cx + (int)(x2 * scale);
                int by = cy - (int)(y2 * scale);

                // Відрізок
                g.setColor(Color.GREEN.darker());
                g.setStroke(new BasicStroke(3));
                g.drawLine(ax, ay, bx, by);

                // Точки
                g.setColor(Color.RED);
                g.fillOval(ax - 6, ay - 6, 12, 12);
                g.setColor(Color.BLUE);
                g.fillOval(bx - 6, by - 6, 12, 12);

                // Підписи
                g.setColor(Color.BLACK);
                g.setFont(new Font("SansSerif", Font.BOLD, 12));
                g.drawString("A(" + format(x1) + "," + format(y1) + ")", ax + 8, ay - 8);
                g.drawString("B(" + format(x2) + "," + format(y2) + ")", bx + 8, by - 8);
            }
        } catch (Exception ignored) {}
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Калькулятор квадрантів відрізка");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new SegmentQuadrantCalculator());
            frame.pack();
            frame.setMinimumSize(new Dimension(630, 950));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}