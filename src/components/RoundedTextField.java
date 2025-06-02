/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;

public class RoundedTextField extends JTextField {
    private String placeholder;
    private Color placeholderColor = new Color(150, 150, 150);
    private Color borderColor = new Color(200, 200, 200);
    private Color focusBorderColor = new Color(100, 150, 255);
    private int cornerRadius = 8;
    private int borderThickness = 1;
    private boolean isPlaceholderVisible = true;
    private boolean hasFocus = false;

    public RoundedTextField() {
        super();
        initTextField();
    }

    public RoundedTextField(String placeholder) {
        super();
        this.placeholder = placeholder;
        initTextField();
    }

    public RoundedTextField(int columns) {
        super(columns);
        initTextField();
    }

    public RoundedTextField(String placeholder, int columns) {
        super(columns);
        this.placeholder = placeholder;
        initTextField();
    }

    private void initTextField() {
        setOpaque(false);
        setBorder(new RoundedBorder());
        setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Padding internal
        setMargin(new Insets(8, 12, 8, 12));
        
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                hasFocus = true;
                if (isPlaceholderVisible && placeholder != null) {
                    setText("");
                    setForeground(Color.BLACK);
                    isPlaceholderVisible = false;
                }
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                hasFocus = false;
                if (getText().isEmpty() && placeholder != null) {
                    isPlaceholderVisible = true;
                    repaint();
                }
                repaint();
            }
        });

        // Set placeholder pada awal
        if (placeholder != null) {
            showPlaceholder();
        }
    }

    private void showPlaceholder() {
        isPlaceholderVisible = true;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        // Jika placeholder visible dan field kosong
        if (isPlaceholderVisible && placeholder != null && getText().isEmpty()) {
            g2d.setColor(placeholderColor);
            g2d.setFont(getFont());
            
            FontMetrics fm = g2d.getFontMetrics();
            int x = getInsets().left;
            int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
            
            g2d.drawString(placeholder, x, y);
        }

        g2d.dispose();
        super.paintComponent(g);
    }

    @Override
    public String getText() {
        return isPlaceholderVisible ? "" : super.getText();
    }

    // Custom Border Class
    private class RoundedBorder extends AbstractBorder {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Pilih warna border berdasarkan focus
            Color currentBorderColor = hasFocus ? focusBorderColor : borderColor;
            g2d.setColor(currentBorderColor);
            g2d.setStroke(new BasicStroke(borderThickness));

            // Gambar rounded border
            RoundRectangle2D border = new RoundRectangle2D.Float(
                x + borderThickness/2f, 
                y + borderThickness/2f,
                width - borderThickness, 
                height - borderThickness,
                cornerRadius, 
                cornerRadius
            );
            g2d.draw(border);
            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(8, 12, 8, 12);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = 12;
            insets.top = insets.bottom = 8;
            return insets;
        }
    }

    // Getter dan Setter untuk customization
    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        if (getText().isEmpty()) {
            showPlaceholder();
        }
    }

    public Color getPlaceholderColor() {
        return placeholderColor;
    }

    public void setPlaceholderColor(Color placeholderColor) {
        this.placeholderColor = placeholderColor;
        repaint();
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        repaint();
    }

    public Color getFocusBorderColor() {
        return focusBorderColor;
    }

    public void setFocusBorderColor(Color focusBorderColor) {
        this.focusBorderColor = focusBorderColor;
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        repaint();
    }

    public int getBorderThickness() {
        return borderThickness;
    }

    public void setBorderThickness(int borderThickness) {
        this.borderThickness = borderThickness;
        repaint();
    }

    // Contoh penggunaan
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Rounded TextField Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout());

            // TextField seperti gambar
            RoundedTextField textField1 = new RoundedTextField("Masukkan User ID");
            textField1.setPreferredSize(new Dimension(250, 40));
            textField1.setBackground(Color.WHITE);

            // TextField dengan customization
            RoundedTextField textField2 = new RoundedTextField("Enter Password");
            textField2.setPreferredSize(new Dimension(250, 40));
            textField2.setBackground(new Color(248, 248, 248));
            textField2.setBorderColor(new Color(180, 180, 180));
            textField2.setFocusBorderColor(new Color(0, 123, 255));

            // TextField dengan corner radius berbeda
            RoundedTextField textField3 = new RoundedTextField("Email Address");
            textField3.setPreferredSize(new Dimension(250, 40));
            textField3.setCornerRadius(15);
            textField3.setBackground(Color.WHITE);

            frame.add(textField1);
            frame.add(textField2);
            frame.add(textField3);

            frame.setSize(300, 200);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}