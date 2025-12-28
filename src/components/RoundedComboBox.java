package components;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;

public class RoundedComboBox<T> extends JComboBox<T> {
    private Color borderColor = new Color(200, 200, 200);
    private Color focusBorderColor = new Color(100, 150, 255);
    private Color backgroundColor = Color.WHITE;
    private int cornerRadius = 8;
    private int borderThickness = 1;
    private boolean hasFocus = false;

    public RoundedComboBox() {
        super();
        initComboBox();
    }

    public RoundedComboBox(T[] items) {
        super(items);
        initComboBox();
    }

    public RoundedComboBox(ComboBoxModel<T> model) {
        super(model);
        initComboBox();
    }

    private void initComboBox() {
        setOpaque(false);
        setBackground(backgroundColor);
        setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Hilangkan border bawaan sepenuhnya
        setBorder(BorderFactory.createEmptyBorder());
        
        // Custom UI untuk rounded appearance
        setUI(new RoundedComboBoxUI());
        
        // Focus listener untuk seluruh component
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                hasFocus = true;
                repaint();
                repaintArrowButton();
            }

            @Override
            public void focusLost(FocusEvent e) {
                hasFocus = false;
                repaint();
                repaintArrowButton();
            }
        });
    }
    
    // Helper method untuk repaint arrow button
    private void repaintArrowButton() {
        try {
            Component[] components = getComponents();
            for (Component comp : components) {
                if (comp instanceof JButton) {
                    comp.repaint();
                }
            }
        } catch (Exception e) {
            // Ignore jika ada error
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background dengan rounded corners
        g2d.setColor(backgroundColor);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        g2d.dispose();
        
        // IMPORTANT: Call super FIRST to let LAF paint, then draw border on top
        super.paintComponent(g);
        
        // NOW draw border on top so it won't be covered
        Graphics2D g2dBorder = (Graphics2D) g.create();
        g2dBorder.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Color currentBorderColor = hasFocus ? focusBorderColor : borderColor;
        g2dBorder.setColor(currentBorderColor);
        g2dBorder.setStroke(new BasicStroke(borderThickness));
        
        // Gambar border utama (full rounded rectangle)
        RoundRectangle2D border = new RoundRectangle2D.Float(
            borderThickness/2f, 
            borderThickness/2f,
            getWidth() - borderThickness, 
            getHeight() - borderThickness,
            cornerRadius, 
            cornerRadius
        );
        g2dBorder.draw(border);
        g2dBorder.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Jangan gambar border default, kita sudah handle di paintComponent
    }

    // Custom Border Class
    private class RoundedBorder extends AbstractBorder {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color currentBorderColor = hasFocus ? focusBorderColor : borderColor;
            g2d.setColor(currentBorderColor);
            g2d.setStroke(new BasicStroke(borderThickness));

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
    }

    // Custom UI untuk ComboBox
    private class RoundedComboBoxUI extends BasicComboBoxUI {
        
        @Override
        protected JButton createArrowButton() {
            JButton button = new JButton() {
                @Override
                public void paintComponent(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    int width = getWidth();
                    int height = getHeight();
                    
                    // Background dengan rounded corner (hanya sisi kanan)
                    g2d.setColor(backgroundColor);
                    g2d.fillRoundRect(-cornerRadius, 0, width + cornerRadius, height, cornerRadius, cornerRadius);
                    
                    // Border untuk area arrow (hanya sisi kanan dan atas bawah)
                    Color currentBorderColor = hasFocus ? focusBorderColor : borderColor;
                    g2d.setColor(currentBorderColor);
                    g2d.setStroke(new BasicStroke(borderThickness));
                    
                    // Gambar border kanan dengan rounded corner
                    RoundRectangle2D rightBorder = new RoundRectangle2D.Float(
                        -cornerRadius + borderThickness/2f,
                        borderThickness/2f,
                        width + cornerRadius - borderThickness,
                        height - borderThickness,
                        cornerRadius,
                        cornerRadius
                    );
                    g2d.draw(rightBorder);
                    
                    // Gambar garis vertikal pemisah antara text area dan arrow
                    g2d.setColor(currentBorderColor);
                    //g2d.drawLine(0, borderThickness, 0, height - borderThickness);
                    
                    // Gambar panah
                    int arrowSize = 6;
                    int x = (width - arrowSize) / 2;
                    int y = (height - arrowSize) / 2;
                    
                    g2d.setColor(new Color(80, 80, 80));
                    g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    
                    // Gambar panah segitiga ke bawah
                    int[] xPoints = {x, x + arrowSize, x + arrowSize/2};
                    int[] yPoints = {y, y, y + arrowSize/2};
                    g2d.fillPolygon(xPoints, yPoints, 3);
                    
                    g2d.dispose();
                }
                
                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(25, getHeight());
                }
            };
            
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setContentAreaFilled(false);
            button.setFocusable(false);
            button.setOpaque(false);
            return button;
        }

        @Override
        protected ComboPopup createPopup() {
            return new BasicComboPopup(comboBox) {
                @Override
                protected void configurePopup() {
                    super.configurePopup();
                    setBorder(BorderFactory.createLineBorder(borderColor, 1));
                }
            };
        }

        @Override
        protected void installDefaults() {
            super.installDefaults();
            comboBox.setRenderer(new RoundedComboBoxRenderer());
            
            // Hilangkan border dari editor
            try {
                Component editorComponent = comboBox.getEditor().getEditorComponent();
                if (editorComponent instanceof JTextField) {
                    ((JTextField) editorComponent).setBorder(BorderFactory.createEmptyBorder());
                    ((JTextField) editorComponent).setOpaque(false);
                }
            } catch (Exception e) {
                // Ignore jika tidak ada editor
            }
        }
        
        @Override
        public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
            // Jangan gambar background default - kita handle sendiri
        }
    }

    // Custom Renderer untuk item di ComboBox
    private class RoundedComboBoxRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, 
                int index, boolean isSelected, boolean cellHasFocus) {
            
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            setFont(new Font("Arial", Font.PLAIN, 14));
            
            if (isSelected) {
                setBackground(new Color(230, 240, 255));
                setForeground(Color.BLACK);
            } else {
                setBackground(Color.WHITE);
                setForeground(Color.BLACK);
            }
            
            return this;
        }
    }

    // Getter dan Setter untuk customization
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

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        setBackground(backgroundColor);
        repaint();
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
            try {
//                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }

            JFrame frame = new JFrame("Rounded ComboBox Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout());

            // ComboBox seperti gambar
            String[] roles = {"Pilih Role", "Admin", "User", "Manager", "Guest"};
            RoundedComboBox<String> comboBox1 = new RoundedComboBox<>(roles);
            comboBox1.setPreferredSize(new Dimension(200, 40));
            comboBox1.setSelectedIndex(0);

            // ComboBox dengan data berbeda
            String[] countries = {"Pilih Negara", "Indonesia", "Malaysia", "Singapore", "Thailand"};
            RoundedComboBox<String> comboBox2 = new RoundedComboBox<>(countries);
            comboBox2.setPreferredSize(new Dimension(200, 40));
            comboBox2.setSelectedIndex(0);
            comboBox2.setBorderColor(new Color(180, 180, 180));
            comboBox2.setFocusBorderColor(new Color(0, 123, 255));

            // ComboBox dengan corner radius berbeda
            String[] status = {"Pilih Status", "Aktif", "Tidak Aktif", "Pending"};
            RoundedComboBox<String> comboBox3 = new RoundedComboBox<>(status);
            comboBox3.setPreferredSize(new Dimension(200, 40));
            comboBox3.setCornerRadius(15);
            comboBox3.setSelectedIndex(0);

            frame.add(new JLabel("Role:"));
            frame.add(comboBox1);
            frame.add(new JLabel("Country:"));
            frame.add(comboBox2);
            frame.add(new JLabel("Status:"));
            frame.add(comboBox3);

            frame.setSize(250, 300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}