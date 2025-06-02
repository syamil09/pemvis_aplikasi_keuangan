package components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedPanel extends JPanel {
    private int customRadius = 15;
    private Color customBackgroundColor = Color.WHITE;
    private Color customBorderColor = new Color(200, 200, 200);
    private int customBorderThickness = 1;
    private boolean customHasBorder = true;

    // Konstruktor kosong untuk GUI Builder
    public RoundedPanel() {
        super();
        initPanel();
    }

    public RoundedPanel(int radius) {
        super();
        this.customRadius = radius;
        initPanel();
    }

    public RoundedPanel(LayoutManager layout) {
        super(layout);
        initPanel();
    }

    public RoundedPanel(LayoutManager layout, int radius) {
        super(layout);
        this.customRadius = radius;
        initPanel();
    }

    private void initPanel() {
        setOpaque(false); // Important untuk custom painting
        setBackground(customBackgroundColor);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background dengan rounded corners
        g2d.setColor(customBackgroundColor);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), customRadius, customRadius);

        // Border jika diperlukan
        if (customHasBorder) {
            g2d.setColor(customBorderColor);
            g2d.setStroke(new BasicStroke(customBorderThickness));
            RoundRectangle2D border = new RoundRectangle2D.Float(
                customBorderThickness/2f,
                customBorderThickness/2f,
                getWidth() - customBorderThickness,
                getHeight() - customBorderThickness,
                customRadius,
                customRadius
            );
            g2d.draw(border);
        }

        g2d.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Jangan gambar border default, kita sudah handle di paintComponent
    }

    // JavaBean Properties untuk GUI Builder dengan prefix "custom"
    
    // Custom Radius Property
    public int getCustomRadius() {
        return customRadius;
    }

    public void setCustomRadius(int customRadius) {
        int oldValue = this.customRadius;
        this.customRadius = customRadius;
        firePropertyChange("customRadius", oldValue, customRadius);
        repaint();
    }

    // Custom Background Color Property
    public Color getCustomBackgroundColor() {
        return customBackgroundColor;
    }

    public void setCustomBackgroundColor(Color customBackgroundColor) {
        Color oldValue = this.customBackgroundColor;
        this.customBackgroundColor = customBackgroundColor;
        setBackground(customBackgroundColor);
        firePropertyChange("customBackgroundColor", oldValue, customBackgroundColor);
        repaint();
    }

    // Custom Border Color Property
    public Color getCustomBorderColor() {
        return customBorderColor;
    }

    public void setCustomBorderColor(Color customBorderColor) {
        Color oldValue = this.customBorderColor;
        this.customBorderColor = customBorderColor;
        firePropertyChange("customBorderColor", oldValue, customBorderColor);
        repaint();
    }

    // Custom Border Thickness Property
    public int getCustomBorderThickness() {
        return customBorderThickness;
    }

    public void setCustomBorderThickness(int customBorderThickness) {
        int oldValue = this.customBorderThickness;
        this.customBorderThickness = customBorderThickness;
        firePropertyChange("customBorderThickness", oldValue, customBorderThickness);
        repaint();
    }

    // Custom Has Border Property
    public boolean getCustomHasBorder() {
        return customHasBorder;
    }

    public void setCustomHasBorder(boolean customHasBorder) {
        boolean oldValue = this.customHasBorder;
        this.customHasBorder = customHasBorder;
        firePropertyChange("customHasBorder", oldValue, customHasBorder);
        repaint();
    }

    // Predefined Styles untuk kemudahan
    public void setCardStyle() {
        setCustomRadius(12);
        setCustomBackgroundColor(Color.WHITE);
        setCustomBorderColor(new Color(220, 220, 220));
        setCustomBorderThickness(1);
        setCustomHasBorder(true);
    }

    public void setModernStyle() {
        setCustomRadius(20);
        setCustomBackgroundColor(new Color(248, 249, 250));
        setCustomBorderColor(new Color(108, 117, 125));
        setCustomBorderThickness(1);
        setCustomHasBorder(true);
    }

    public void setPrimaryStyle() {
        setCustomRadius(15);
        setCustomBackgroundColor(new Color(13, 110, 253));
        setCustomBorderColor(new Color(10, 88, 202));
        setCustomBorderThickness(2);
        setCustomHasBorder(true);
    }

    public void setSuccessStyle() {
        setCustomRadius(15);
        setCustomBackgroundColor(new Color(25, 135, 84));
        setCustomBorderColor(new Color(20, 108, 67));
        setCustomBorderThickness(2);
        setCustomHasBorder(true);
    }

    public void setWarningStyle() {
        setCustomRadius(15);
        setCustomBackgroundColor(new Color(255, 193, 7));
        setCustomBorderColor(new Color(255, 171, 0));
        setCustomBorderThickness(2);
        setCustomHasBorder(true);
    }

    public void setDangerStyle() {
        setCustomRadius(15);
        setCustomBackgroundColor(new Color(220, 53, 69));
        setCustomBorderColor(new Color(187, 45, 59));
        setCustomBorderThickness(2);
        setCustomHasBorder(true);
    }

    public void setFlatStyle() {
        setCustomRadius(8);
        setCustomBackgroundColor(new Color(248, 249, 250));
        setCustomHasBorder(false);
    }

    public void setShadowStyle() {
        setCustomRadius(16);
        setCustomBackgroundColor(Color.WHITE);
        setCustomBorderColor(new Color(0, 0, 0, 30)); // Semi-transparent
        setCustomBorderThickness(3);
        setCustomHasBorder(true);
    }

    // Method untuk reset ke default
    public void resetToDefault() {
        setCustomRadius(15);
        setCustomBackgroundColor(Color.WHITE);
        setCustomBorderColor(new Color(200, 200, 200));
        setCustomBorderThickness(1);
        setCustomHasBorder(true);
    }

    // Deprecated methods untuk backward compatibility
    @Deprecated
    public int getCornerRadius() {
        return getCustomRadius();
    }

    @Deprecated
    public void setCornerRadius(int radius) {
        setCustomRadius(radius);
    }

    @Deprecated
    public Color getBackgroundColor() {
        return getCustomBackgroundColor();
    }

    @Deprecated
    public void setBackgroundColor(Color color) {
        setCustomBackgroundColor(color);
    }

    @Deprecated
    public Color getBorderColor() {
        return getCustomBorderColor();
    }

    @Deprecated
    public void setBorderColor(Color color) {
        setCustomBorderColor(color);
    }

    @Deprecated
    public int getBorderThickness() {
        return getCustomBorderThickness();
    }

    @Deprecated
    public void setBorderThickness(int thickness) {
        setCustomBorderThickness(thickness);
    }

    @Deprecated
    public boolean isHasBorder() {
        return getCustomHasBorder();
    }

    @Deprecated
    public void setHasBorder(boolean hasBorder) {
        setCustomHasBorder(hasBorder);
    }

    // Contoh penggunaan
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
//                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }

            JFrame frame = new JFrame("Reusable Rounded Panel Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
            frame.getContentPane().setBackground(new Color(240, 240, 240));

            // Panel 1 - Default
            RoundedPanel panel1 = new RoundedPanel();
            panel1.setPreferredSize(new Dimension(180, 120));
            panel1.setLayout(new BorderLayout());
            panel1.add(new JLabel("Default Style", JLabel.CENTER));

            // Panel 2 - Card style
            RoundedPanel panel2 = new RoundedPanel();
            panel2.setPreferredSize(new Dimension(180, 120));
            panel2.setCardStyle();
            panel2.setLayout(new BorderLayout());
            panel2.add(new JLabel("Card Style", JLabel.CENTER));

            // Panel 3 - Modern style
            RoundedPanel panel3 = new RoundedPanel();
            panel3.setPreferredSize(new Dimension(180, 120));
            panel3.setModernStyle();
            panel3.setLayout(new BorderLayout());
            panel3.add(new JLabel("Modern Style", JLabel.CENTER));

            // Panel 4 - Primary style
            RoundedPanel panel4 = new RoundedPanel();
            panel4.setPreferredSize(new Dimension(180, 120));
            panel4.setPrimaryStyle();
            panel4.setLayout(new BorderLayout());
            JLabel label4 = new JLabel("Primary Style", JLabel.CENTER);
            label4.setForeground(Color.WHITE);
            panel4.add(label4);

            // Panel 5 - Success style
            RoundedPanel panel5 = new RoundedPanel();
            panel5.setPreferredSize(new Dimension(180, 120));
            panel5.setSuccessStyle();
            panel5.setLayout(new BorderLayout());
            JLabel label5 = new JLabel("Success Style", JLabel.CENTER);
            label5.setForeground(Color.WHITE);
            panel5.add(label5);

            // Panel 6 - Warning style
            RoundedPanel panel6 = new RoundedPanel();
            panel6.setPreferredSize(new Dimension(180, 120));
            panel6.setWarningStyle();
            panel6.setLayout(new BorderLayout());
            JLabel label6 = new JLabel("Warning Style", JLabel.CENTER);
            label6.setForeground(Color.BLACK);
            panel6.add(label6);

            // Panel 7 - Danger style
            RoundedPanel panel7 = new RoundedPanel();
            panel7.setPreferredSize(new Dimension(180, 120));
            panel7.setDangerStyle();
            panel7.setLayout(new BorderLayout());
            JLabel label7 = new JLabel("Danger Style", JLabel.CENTER);
            label7.setForeground(Color.WHITE);
            panel7.add(label7);

            // Panel 8 - Flat style
            RoundedPanel panel8 = new RoundedPanel();
            panel8.setPreferredSize(new Dimension(180, 120));
            panel8.setFlatStyle();
            panel8.setLayout(new BorderLayout());
            panel8.add(new JLabel("Flat Style", JLabel.CENTER));

            // Panel 9 - Custom settings
            RoundedPanel panel9 = new RoundedPanel();
            panel9.setPreferredSize(new Dimension(180, 120));
            panel9.setCustomRadius(25);
            panel9.setCustomBackgroundColor(new Color(255, 182, 193));
            panel9.setCustomBorderColor(new Color(219, 112, 147));
            panel9.setCustomBorderThickness(3);
            panel9.setLayout(new BorderLayout());
            panel9.add(new JLabel("Custom Settings", JLabel.CENTER));

            frame.add(panel1);
            frame.add(panel2);
            frame.add(panel3);
            frame.add(panel4);
            frame.add(panel5);
            frame.add(panel6);
            frame.add(panel7);
            frame.add(panel8);
            frame.add(panel9);

            frame.setSize(600, 450);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}