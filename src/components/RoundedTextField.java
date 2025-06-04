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
    private boolean isPlaceholderVisible = false;
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
                // Jika placeholder sedang ditampilkan, hapus dan set ke mode normal
                if (isPlaceholderVisible) {
                    isPlaceholderVisible = false;
                    setText(""); // Clear text
                    setForeground(Color.BLACK);
                }
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                hasFocus = false;
                // Jika field kosong dan ada placeholder, tampilkan placeholder
                if (getText().isEmpty() && placeholder != null && !placeholder.isEmpty()) {
                    isPlaceholderVisible = true;
                }
                repaint();
            }
        });

        // Set placeholder pada awal jika ada
        if (placeholder != null && !placeholder.isEmpty()) {
            isPlaceholderVisible = true;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        g2d.dispose();
        
        // Paint the text field content FIRST
        super.paintComponent(g);
        
        // Gambar placeholder jika diperlukan
        if (isPlaceholderVisible && placeholder != null && !placeholder.isEmpty()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2.setColor(placeholderColor);
            g2.setFont(getFont());
            
            FontMetrics fm = g2.getFontMetrics();
            Insets insets = getInsets();
            int x = insets.left;
            int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
            
            g2.drawString(placeholder, x, y);
            g2.dispose();
        }
    }

    @Override
    public String getText() {
        // Jika placeholder sedang ditampilkan, return empty string
        if (isPlaceholderVisible) {
            return "";
        }
        return super.getText();
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        // Jika text di-set dari luar dan tidak kosong, hilangkan placeholder
        if (text != null && !text.isEmpty()) {
            isPlaceholderVisible = false;
        } else if (text == null || text.isEmpty()) {
            // Jika text kosong dan ada placeholder, tampilkan placeholder
            if (placeholder != null && !placeholder.isEmpty() && !hasFocus) {
                isPlaceholderVisible = true;
            }
        }
        repaint();
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
        
        // Jika field kosong dan tidak focus, tampilkan placeholder
        if ((super.getText() == null || super.getText().isEmpty()) && !hasFocus) {
            if (placeholder != null && !placeholder.isEmpty()) {
                isPlaceholderVisible = true;
            } else {
                isPlaceholderVisible = false;
            }
        }
        repaint();
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

    // Test method to demonstrate working placeholder
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
//                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }

            JFrame frame = new JFrame("Fixed RoundedTextField with Working Placeholder");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new GridBagLayout());
            frame.getContentPane().setBackground(new Color(240, 240, 240));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.anchor = GridBagConstraints.WEST;

            // Create multiple text fields with different placeholders
            RoundedTextField userIdField = new RoundedTextField("Masukkan User ID");
            userIdField.setPreferredSize(new Dimension(250, 40));
            userIdField.setBackground(Color.WHITE);

            RoundedTextField passwordField = new RoundedTextField("Enter Password");
            passwordField.setPreferredSize(new Dimension(250, 40));
            passwordField.setBackground(new Color(248, 248, 248));
            passwordField.setBorderColor(new Color(180, 180, 180));
            passwordField.setFocusBorderColor(new Color(0, 123, 255));

            RoundedTextField emailField = new RoundedTextField();
            emailField.setPlaceholder("Email Address");
            emailField.setPreferredSize(new Dimension(250, 40));
            emailField.setCornerRadius(15);
            emailField.setBackground(Color.WHITE);

            RoundedTextField phoneField = new RoundedTextField("Phone Number", 20);
            phoneField.setPreferredSize(new Dimension(250, 40));
            phoneField.setPlaceholderColor(new Color(100, 100, 100));
            phoneField.setBackground(new Color(255, 255, 255));

            // Test field with pre-filled text
            RoundedTextField prefilledField = new RoundedTextField("Search...");
            prefilledField.setPreferredSize(new Dimension(250, 40));
            prefilledField.setText("Pre-filled text"); // This should hide placeholder
            prefilledField.setBackground(Color.WHITE);

            // Add labels and fields
            gbc.gridx = 0; gbc.gridy = 0;
            frame.add(new JLabel("User ID:"), gbc);
            gbc.gridx = 1;
            frame.add(userIdField, gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            frame.add(new JLabel("Password:"), gbc);
            gbc.gridx = 1;
            frame.add(passwordField, gbc);

            gbc.gridx = 0; gbc.gridy = 2;
            frame.add(new JLabel("Email:"), gbc);
            gbc.gridx = 1;
            frame.add(emailField, gbc);

            gbc.gridx = 0; gbc.gridy = 3;
            frame.add(new JLabel("Phone:"), gbc);
            gbc.gridx = 1;
            frame.add(phoneField, gbc);

            gbc.gridx = 0; gbc.gridy = 4;
            frame.add(new JLabel("Pre-filled:"), gbc);
            gbc.gridx = 1;
            frame.add(prefilledField, gbc);

            // Add test buttons
            gbc.gridx = 0; gbc.gridy = 5;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.setOpaque(false);

            JButton clearButton = new JButton("Clear All");
            clearButton.addActionListener(e -> {
                userIdField.setText("");
                passwordField.setText("");
                emailField.setText("");
                phoneField.setText("");
                prefilledField.setText("");
            });

            JButton fillButton = new JButton("Fill Fields");
            fillButton.addActionListener(e -> {
                userIdField.setText("admin");
                passwordField.setText("password123");
                emailField.setText("user@example.com");
                phoneField.setText("+1234567890");
                prefilledField.setText("Filled data");
            });

            JButton getValuesButton = new JButton("Get Values");
            getValuesButton.addActionListener(e -> {
                String message = String.format(
                    "Values:\nUser ID: '%s'\nPassword: '%s'\nEmail: '%s'\nPhone: '%s'\nPre-filled: '%s'",
                    userIdField.getText(),
                    passwordField.getText(),
                    emailField.getText(),
                    phoneField.getText(),
                    prefilledField.getText()
                );
                JOptionPane.showMessageDialog(frame, message, "Field Values", JOptionPane.INFORMATION_MESSAGE);
            });

            buttonPanel.add(clearButton);
            buttonPanel.add(fillButton);
            buttonPanel.add(getValuesButton);

            frame.add(buttonPanel, gbc);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Instructions
            JOptionPane.showMessageDialog(frame, 
                "Instructions:\n" +
                "• Click on any field to see placeholder disappear\n" +
                "• Click outside to see placeholder reappear (if field is empty)\n" +
                "• Use buttons to test different scenarios\n" +
                "• Fields return empty string when placeholder is visible",
                "How to Test", 
                JOptionPane.INFORMATION_MESSAGE);
        });
    }
}