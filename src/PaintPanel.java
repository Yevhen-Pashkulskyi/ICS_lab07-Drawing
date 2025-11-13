import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class PaintPanel extends JPanel {
    Insets ins;
    Random rand;

    public PaintPanel() {
        setBorder(BorderFactory.createLineBorder(Color.RED, 5));
        rand = new Random();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x, y, x2, y2;
        int width = getWidth();
        int height = getHeight();
        ins = getInsets();
        x = ((width - getWidth()) / 2) - ins.left;
        y = ((height - getHeight()) / 2) - ins.bottom;
        x2 = (getWidth() - x) - ins.right;
        y2 = (getHeight() - y) - ins.bottom;
        g.setColor(Color.green);
        g.drawLine(x, y, x2, y2);
    }
}

class PaintDemo  {
    PaintPanel paintPanel;
    public PaintDemo() {
        JFrame frame = new JFrame("PaintDemo");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        paintPanel = new PaintPanel();
        frame.add(paintPanel);
        frame.setVisible(true);
    }
}