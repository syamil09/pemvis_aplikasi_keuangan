package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class RoundedButton extends JButton {
    // Default colors - bisa diubah dari GUI Builder
    private Color backgroundColor = new Color(34, 139, 34); // Default hijau
    private Color hoverColor = new Color(46, 125, 50); // Default hijau lebih gelap
    private Color pressedColor = new Color(27, 94, 32); // Default hijau sangat gelap
    private Color textColor = Color.WHITE; // Default text putih
    private int cornerRadius = 8;
    private boolean isHovered = false;
    private boolean isPressed = false;
    
    // Enum untuk color schemes
    public enum ColorScheme {
        CUSTOM, PRIMARY, SUCCESS, WARNING, DANGER, SECONDARY
    }
    
    private ColorScheme currentScheme = ColorScheme.CUSTOM;
    
    // Konstruktor kosong untuk GUI Builder
    public RoundedButton() {
        super();
        initButton();
    }
    
    public RoundedButton(String text) {
        super(text);
        initButton();
    }

    private void initButton() {
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
        setForeground(textColor);
        setFont(new Font("Noto Color Emoji", Font.BOLD, 14));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Mouse listener untuk hover dan press effect
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Tentukan warna berdasarkan state
        Color currentColor = backgroundColor;
        if (isPressed) {
            currentColor = pressedColor;
        } else if (isHovered) {
            currentColor = hoverColor;
        }

        // Gambar background dengan rounded corners
        RoundRectangle2D roundRect = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        g2d.setColor(currentColor);
        g2d.fill(roundRect);

        g2d.dispose();
        super.paintComponent(g);
    }

    // JavaBean Properties untuk GUI Builder dengan prefix "custom"
    
    // Custom Background Color Property
    public Color getCustomBackgroundColor() {
        return backgroundColor;
    }

    public void setCustomBackgroundColor(Color backgroundColor) {
        Color oldValue = this.backgroundColor;
        this.backgroundColor = backgroundColor;
        firePropertyChange("customBackgroundColor", oldValue, backgroundColor);
        repaint();
    }

    // Custom Hover Color Property
    public Color getCustomHoverColor() {
        return hoverColor;
    }

    public void setCustomHoverColor(Color hoverColor) {
        Color oldValue = this.hoverColor;
        this.hoverColor = hoverColor;
        firePropertyChange("customHoverColor", oldValue, hoverColor);
        repaint();
    }

    // Custom Pressed Color Property
    public Color getCustomPressedColor() {
        return pressedColor;
    }

    public void setCustomPressedColor(Color pressedColor) {
        Color oldValue = this.pressedColor;
        this.pressedColor = pressedColor;
        firePropertyChange("customPressedColor", oldValue, pressedColor);
        repaint();
    }

    // Custom Text Color Property
    public Color getCustomTextColor() {
        return textColor;
    }

    public void setCustomTextColor(Color textColor) {
        Color oldValue = this.textColor;
        this.textColor = textColor;
        setForeground(textColor);
        firePropertyChange("customTextColor", oldValue, textColor);
        repaint();
    }

    // Custom Corner Radius Property
    public int getCustomCornerRadius() {
        return cornerRadius;
    }

    public void setCustomCornerRadius(int cornerRadius) {
        int oldValue = this.cornerRadius;
        this.cornerRadius = cornerRadius;
        firePropertyChange("customCornerRadius", oldValue, cornerRadius);
        repaint();
    }

    // Custom Color Scheme Property untuk GUI Builder
    public ColorScheme getCustomColorScheme() {
        return currentScheme;
    }

    public void setCustomColorScheme(ColorScheme scheme) {
        ColorScheme oldValue = this.currentScheme;
        this.currentScheme = scheme;
        
        switch (scheme) {
            case PRIMARY:
                setPrimaryScheme();
                break;
            case SUCCESS:
                setSuccessScheme();
                break;
            case WARNING:
                setWarningScheme();
                break;
            case DANGER:
                setDangerScheme();
                break;
            case SECONDARY:
                setSecondaryScheme();
                break;
            case CUSTOM:
            default:
                // Keep current colors for CUSTOM
                break;
        }
        
        firePropertyChange("customColorScheme", oldValue, scheme);
    }

    // Deprecated method untuk backward compatibility
    @Deprecated
    public void setForegroundColor(Color color) {
        setCustomTextColor(color);
    }

    // Predefined Color Schemes untuk kemudahan
    public void setPrimaryScheme() {
        this.currentScheme = ColorScheme.PRIMARY;
        setCustomBackgroundColor(new Color(33, 150, 243)); // Blue
        setCustomHoverColor(new Color(25, 118, 210));
        setCustomPressedColor(new Color(21, 101, 192));
        setCustomTextColor(Color.WHITE);
    }

    public void setSuccessScheme() {
        this.currentScheme = ColorScheme.SUCCESS;
        setCustomBackgroundColor(new Color(76, 175, 80)); // Green
        setCustomHoverColor(new Color(67, 160, 71));
        setCustomPressedColor(new Color(56, 142, 60));
        setCustomTextColor(Color.WHITE);
    }

    public void setWarningScheme() {
        this.currentScheme = ColorScheme.WARNING;
        setCustomBackgroundColor(new Color(255, 193, 7)); // Yellow/Orange
        setCustomHoverColor(new Color(255, 183, 77));
        setCustomPressedColor(new Color(255, 171, 64));
        setCustomTextColor(Color.BLACK);
    }

    public void setDangerScheme() {
        this.currentScheme = ColorScheme.DANGER;
        setCustomBackgroundColor(new Color(244, 67, 54)); // Red
        setCustomHoverColor(new Color(229, 57, 53));
        setCustomPressedColor(new Color(211, 47, 47));
        setCustomTextColor(Color.WHITE);
    }

    public void setSecondaryScheme() {
        this.currentScheme = ColorScheme.SECONDARY;
        setCustomBackgroundColor(new Color(158, 158, 158)); // Gray
        setCustomHoverColor(new Color(117, 117, 117));
        setCustomPressedColor(new Color(97, 97, 97));
        setCustomTextColor(Color.WHITE);
    }

    // Convenience method untuk set semua warna sekaligus
    public void setColorScheme(Color background, Color hover, Color pressed, Color text) {
        setCustomBackgroundColor(background);
        setCustomHoverColor(hover);
        setCustomPressedColor(pressed);
        setCustomTextColor(text);
    }

    // Method untuk reset ke default
    public void resetToDefault() {
        setCustomBackgroundColor(new Color(34, 139, 34));
        setCustomHoverColor(new Color(46, 125, 50));
        setCustomPressedColor(new Color(27, 94, 32));
        setCustomTextColor(Color.WHITE);
        setCustomCornerRadius(8);
    }

    // Contoh penggunaan
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
//                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }

            JFrame frame = new JFrame("Flexible Rounded Button Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout());

            // Button dengan default colors
            RoundedButton button1 = new RoundedButton("Default");
            button1.setPreferredSize(new Dimension(120, 40));

            // Button dengan custom colors menggunakan custom properties
            RoundedButton button2 = new RoundedButton("⟳ Custom");
            button2.setCustomBackgroundColor(new Color(156, 39, 176)); // Purple
            button2.setCustomHoverColor(new Color(142, 36, 170));
            button2.setCustomPressedColor(new Color(123, 31, 162));
            button2.setCustomTextColor(Color.WHITE);
            button2.setPreferredSize(new Dimension(120, 40));

            // Button dengan predefined scheme
            RoundedButton button3 = new RoundedButton("⟲ Primary");
            button3.setCustomColorScheme(ColorScheme.PRIMARY);
            button3.setPreferredSize(new Dimension(120, 40));

            RoundedButton button4 = new RoundedButton("✏️ Warning");
            button4.setCustomColorScheme(ColorScheme.WARNING);
            button4.setPreferredSize(new Dimension(120, 40));

            RoundedButton button5 = new RoundedButton("Danger");
            button5.setCustomColorScheme(ColorScheme.DANGER);
            button5.setPreferredSize(new Dimension(120, 40));

            // Button dengan corner radius berbeda
            RoundedButton button6 = new RoundedButton("Rounded");
            button6.setCustomColorScheme(ColorScheme.SUCCESS);
            button6.setCustomCornerRadius(20);
            button6.setPreferredSize(new Dimension(120, 40));

            frame.add(button1);
            frame.add(button2);
            frame.add(button3);
            frame.add(button4);
            frame.add(button5);
            frame.add(button6);

            frame.setSize(500, 200);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}