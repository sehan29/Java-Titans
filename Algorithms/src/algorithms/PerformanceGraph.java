package algorithms;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Map;

public class PerformanceGraph extends JPanel {
    private Map<String, Long> executionTimes;
    private String fastestAlgo;

    public PerformanceGraph() {
        setPreferredSize(new Dimension(600, 300));
        setBackground(Color.WHITE); // Light theme
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                "Execution Time Analysis (Nanoseconds)",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 13),
                new Color(50, 50, 50)
        ));
    }
    public void setResults(Map<String, Long> times, String fastest) {
        this.executionTimes = times;
        this.fastestAlgo = fastest;
        repaint();
    }

}
