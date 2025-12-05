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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (executionTimes == null || executionTimes.isEmpty()) {
            g.setColor(new Color(90, 90, 90));
            g.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            g.drawString("Run sorting to see performance graph", 30, getHeight() / 2);
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int paddingX = 60;
        int paddingY = 50;
        int graphHeight = height - paddingY * 2;

        int barCount = executionTimes.size();
        int spacing = 30;
        int totalSpace = (barCount - 1) * spacing;

        int barWidth = (width - 2 * paddingX - totalSpace) / barCount;

        long maxTime = executionTimes.values().stream().max(Long::compare).orElse(1L);

        // ------- Soft Gridlines -------
        g2.setColor(new Color(230, 230, 230));
        for (int i = 0; i < 6; i++) {
            int y = paddingY + i * (graphHeight / 5);
            g2.drawLine(paddingX - 10, y, width - paddingX, y);
        }

        // ------- Draw Bars -------
        int x = paddingX;

        for (Map.Entry<String, Long> entry : executionTimes.entrySet()) {
            String name = entry.getKey();
            long time = entry.getValue();

            double scale = (double) time / maxTime;
            int barHeight = (int) (scale * graphHeight);

            // Drop shadow
            g2.setColor(new Color(180, 180, 180, 90));
            g2.fill(new RoundRectangle2D.Double(x + 3, height - paddingY - barHeight + 3,
                    barWidth, barHeight, 12, 12));

            // Bar gradient color
            if (name.equals(fastestAlgo)) {
                g2.setPaint(new GradientPaint(
                        x, height - paddingY - barHeight,
                        new Color(80, 200, 120),
                        x, height - paddingY,
                        new Color(60, 160, 90)
                ));
            } else {
                g2.setPaint(new GradientPaint(
                        x, height - paddingY - barHeight,
                        new Color(120, 160, 230),
                        x, height - paddingY,
                        new Color(90, 130, 200)
                ));
            }

            // Draw bar
            g2.fill(new RoundRectangle2D.Double(
                    x, height - paddingY - barHeight,
                    barWidth, barHeight, 12, 12
            ));

            // Algorithm name
            g2.setColor(new Color(60, 60, 60));
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            g2.drawString(name, x, height - paddingY + 20);

            // Time label above bar
            g2.setFont(new Font("Consolas", Font.PLAIN, 11));
            g2.drawString(time + " ns", x, height - paddingY - barHeight - 5);

            x += barWidth + spacing;
        }

        // ------- Best Performer Label -------
        g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        g2.setColor(new Color(20, 150, 100));
        g2.drawString("\nFastest Algorithm: " + fastestAlgo, paddingX, paddingY - 20);
    }

}
