package components;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;

public class RoundedTextArea extends JPanel {
    private String placeholder;
    private Color placeholderColor = new Color(150, 150, 150);
    private Color borderColor = new Color(200, 200, 200);
    private Color focusBorderColor = new Color(100, 150, 255);
    private Color backgroundColor = Color.WHITE;
    private Color textColor = Color.BLACK;
    private int cornerRadius = 12;
    private int borderThickness = 2;
    private boolean isPlaceholderVisible = false;
    private boolean hasFocus = false;
    
    // Components
    private JTextArea textArea;
    private JScrollPane scrollPane;
    
    public RoundedTextArea() {
        super();
        initTextArea();
    }
    
    public RoundedTextArea(String placeholder) {
        super();
        this.placeholder = placeholder;
        initTextArea();
    }
    
    public RoundedTextArea(int rows, int columns) {
        super();
        initTextArea();
        textArea.setRows(rows);
        textArea.setColumns(columns);
    }
    
    public RoundedTextArea(String placeholder, int rows, int columns) {
        super();
        this.placeholder = placeholder;
        initTextArea();
        textArea.setRows(rows);
        textArea.setColumns(columns);
    }
    
    private void initTextArea() {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBackground(backgroundColor);
        
        // Create text area with placeholder support
        textArea = new JTextArea() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                // Draw placeholder if needed
                if (isPlaceholderVisible && placeholder != null && !placeholder.isEmpty()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    g2.setColor(placeholderColor);
                    g2.setFont(getFont());
                    
                    FontMetrics fm = g2.getFontMetrics();
                    Insets insets = getInsets();
                    int x = insets.left;
                    int y = insets.top + fm.getAscent();
                    
                    g2.drawString(placeholder, x, y);
                    g2.dispose();
                }
            }
        };
        
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textArea.setBackground(backgroundColor);
        textArea.setForeground(textColor);
        textArea.setCaretColor(textColor);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        textArea.setOpaque(true);
        
        // Create scroll pane
        scrollPane = new JScrollPane(textArea);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setOpaque(false);
        
        // Set custom border for rounded appearance
        scrollPane.setBorder(new RoundedBorder());
        
        // Custom scrollbars
        scrollPane.setVerticalScrollBar(new ModernScrollBar(JScrollBar.VERTICAL));
        scrollPane.setHorizontalScrollBar(new ModernScrollBar(JScrollBar.HORIZONTAL));
        
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        // Smooth scrolling
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        
        add(scrollPane, BorderLayout.CENTER);
        
        setupEvents();
        updatePlaceholderVisibility();
    }
    
    private void setupEvents() {
        // Focus listener for placeholder and border
        textArea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                hasFocus = true;
                if (isPlaceholderVisible) {
                    isPlaceholderVisible = false;
                    textArea.repaint();
                }
                repaint(); // Repaint the whole component for border update
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                hasFocus = false;
                updatePlaceholderVisibility();
                repaint(); // Repaint the whole component for border update
            }
        });
        
        // Document listener for placeholder
        textArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updatePlaceholderVisibility();
            }
            
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updatePlaceholderVisibility();
            }
            
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updatePlaceholderVisibility();
            }
        });
    }
    
    private void updatePlaceholderVisibility() {
        boolean shouldShowPlaceholder = textArea.getText().isEmpty() && 
                                      placeholder != null && 
                                      !placeholder.isEmpty() && 
                                      !hasFocus;
        
        if (isPlaceholderVisible != shouldShowPlaceholder) {
            isPlaceholderVisible = shouldShowPlaceholder;
            textArea.repaint();
        }
    }
    
    // Custom Border Class for rounded appearance
    private class RoundedBorder extends AbstractBorder {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Background
            g2d.setColor(backgroundColor);
            g2d.fillRoundRect(x, y, width, height, cornerRadius, cornerRadius);

            // Border
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
            return new Insets(borderThickness + 2, borderThickness + 2, 
                             borderThickness + 2, borderThickness + 2);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = insets.top = insets.bottom = borderThickness + 2;
            return insets;
        }
    }
    
    // Modern ScrollBar implementation
    private class ModernScrollBar extends JScrollBar {
        private final Color TRACK_COLOR = new Color(248, 249, 250);
        private final Color THUMB_COLOR = new Color(189, 195, 199);
        private final Color THUMB_HOVER_COLOR = new Color(149, 165, 166);
        private final Color THUMB_PRESSED_COLOR = new Color(127, 140, 141);
        private final int SCROLLBAR_WIDTH = 8;
        
        public ModernScrollBar(int orientation) {
            super(orientation);
            setOpaque(false);
            setPreferredSize(new Dimension(SCROLLBAR_WIDTH, SCROLLBAR_WIDTH));
            setUI(new ModernScrollBarUI());
        }
        
        private class ModernScrollBarUI extends BasicScrollBarUI {
            private boolean isThumbHover = false;
            private boolean isThumbPressed = false;
            
            @Override
            protected void configureScrollBarColors() {
                thumbColor = new Color(0, 0, 0, 0);
                trackColor = new Color(0, 0, 0, 0);
            }
            
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createInvisibleButton();
            }
            
            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createInvisibleButton();
            }
            
            private JButton createInvisibleButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                button.setOpaque(false);
                button.setBorderPainted(false);
                button.setContentAreaFilled(false);
                return button;
            }
            
            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(TRACK_COLOR);
                g2.fillRoundRect(trackBounds.x, trackBounds.y, 
                               trackBounds.width, trackBounds.height, 
                               SCROLLBAR_WIDTH, SCROLLBAR_WIDTH);
                
                g2.dispose();
            }
            
            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
                    return;
                }
                
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int margin = 2;
                int x = thumbBounds.x + margin;
                int y = thumbBounds.y + margin;
                int width = thumbBounds.width - (margin * 2);
                int height = thumbBounds.height - (margin * 2);
                
                Color thumbColor;
                if (isThumbPressed) {
                    thumbColor = THUMB_PRESSED_COLOR;
                } else if (isThumbHover) {
                    thumbColor = THUMB_HOVER_COLOR;
                } else {
                    thumbColor = THUMB_COLOR;
                }
                
                g2.setColor(thumbColor);
                g2.fillRoundRect(x, y, width, height, width, width);
                
                g2.dispose();
            }
            
            @Override
            protected void installListeners() {
                super.installListeners();
                
                scrollbar.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent e) {
                        isThumbHover = true;
                        scrollbar.repaint();
                    }
                    
                    @Override
                    public void mouseExited(java.awt.event.MouseEvent e) {
                        isThumbHover = false;
                        scrollbar.repaint();
                    }
                    
                    @Override
                    public void mousePressed(java.awt.event.MouseEvent e) {
                        isThumbPressed = true;
                        scrollbar.repaint();
                    }
                    
                    @Override
                    public void mouseReleased(java.awt.event.MouseEvent e) {
                        isThumbPressed = false;
                        scrollbar.repaint();
                    }
                });
            }
            
            @Override
            public Dimension getPreferredSize(JComponent c) {
                if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
                    return new Dimension(SCROLLBAR_WIDTH, 100);
                } else {
                    return new Dimension(100, SCROLLBAR_WIDTH);
                }
            }
        }
    }
    
    // Public methods - delegate to textArea
    public String getText() {
        if (isPlaceholderVisible) {
            return "";
        }
        return textArea.getText();
    }
    
    public void setText(String text) {
        textArea.setText(text);
        updatePlaceholderVisibility();
    }
    
    public void append(String text) {
        textArea.append(text);
        updatePlaceholderVisibility();
    }
    
    public void setRows(int rows) {
        textArea.setRows(rows);
    }
    
    public int getRows() {
        return textArea.getRows();
    }
    
    public void setColumns(int columns) {
        textArea.setColumns(columns);
    }
    
    public int getColumns() {
        return textArea.getColumns();
    }
    
    public void setLineWrap(boolean wrap) {
        textArea.setLineWrap(wrap);
    }
    
    public boolean getLineWrap() {
        return textArea.getLineWrap();
    }
    
    public void setWrapStyleWord(boolean word) {
        textArea.setWrapStyleWord(word);
    }
    
    public boolean getWrapStyleWord() {
        return textArea.getWrapStyleWord();
    }
    
    public void setEditable(boolean editable) {
        textArea.setEditable(editable);
    }
    
    public boolean isEditable() {
        return textArea.isEditable();
    }
    
    public void setCaretPosition(int position) {
        textArea.setCaretPosition(position);
    }
    
    public int getCaretPosition() {
        return textArea.getCaretPosition();
    }
    
    public void selectAll() {
        textArea.selectAll();
    }
    
    public void copy() {
        textArea.copy();
    }
    
    public void cut() {
        textArea.cut();
    }
    
    public void paste() {
        textArea.paste();
    }
    
    public JTextArea getTextArea() {
        return textArea;
    }
    
    public JScrollPane getScrollPane() {
        return scrollPane;
    }
    
    // JavaBean Properties untuk GUI Builder NetBeans dengan prefix "custom"
    
    /**
     * Gets the placeholder text
     * @return placeholder text
     */
    public String getCustomPlaceholder() {
        return placeholder;
    }
    
    /**
     * Sets the placeholder text
     * @param placeholder the placeholder text to show when empty
     */
    public void setCustomPlaceholder(String placeholder) {
        String oldValue = this.placeholder;
        this.placeholder = placeholder;
        updatePlaceholderVisibility();
        firePropertyChange("customPlaceholder", oldValue, placeholder);
    }
    
    /**
     * Gets the placeholder text color
     * @return placeholder text color
     */
    public Color getCustomPlaceholderColor() {
        return placeholderColor;
    }
    
    /**
     * Sets the placeholder text color
     * @param placeholderColor color for placeholder text
     */
    public void setCustomPlaceholderColor(Color placeholderColor) {
        Color oldValue = this.placeholderColor;
        this.placeholderColor = placeholderColor != null ? placeholderColor : new Color(150, 150, 150);
        textArea.repaint();
        firePropertyChange("customPlaceholderColor", oldValue, this.placeholderColor);
    }
    
    /**
     * Gets the border color
     * @return border color
     */
    public Color getCustomBorderColor() {
        return borderColor;
    }
    
    /**
     * Sets the border color
     * @param borderColor color for the border
     */
    public void setCustomBorderColor(Color borderColor) {
        Color oldValue = this.borderColor;
        this.borderColor = borderColor != null ? borderColor : new Color(200, 200, 200);
        repaint();
        firePropertyChange("customBorderColor", oldValue, this.borderColor);
    }
    
    /**
     * Gets the focus border color
     * @return focus border color
     */
    public Color getCustomFocusBorderColor() {
        return focusBorderColor;
    }
    
    /**
     * Sets the focus border color
     * @param focusBorderColor color for border when focused
     */
    public void setCustomFocusBorderColor(Color focusBorderColor) {
        Color oldValue = this.focusBorderColor;
        this.focusBorderColor = focusBorderColor != null ? focusBorderColor : new Color(100, 150, 255);
        firePropertyChange("customFocusBorderColor", oldValue, this.focusBorderColor);
    }
    
    /**
     * Gets the background color
     * @return background color
     */
    public Color getCustomBackgroundColor() {
        return backgroundColor;
    }
    
    /**
     * Sets the background color
     * @param backgroundColor background color for the text area
     */
    public void setCustomBackgroundColor(Color backgroundColor) {
        Color oldValue = this.backgroundColor;
        this.backgroundColor = backgroundColor != null ? backgroundColor : Color.WHITE;
        super.setBackground(this.backgroundColor);
        if (textArea != null) {
            textArea.setBackground(this.backgroundColor);
        }
        firePropertyChange("customBackgroundColor", oldValue, this.backgroundColor);
    }
    
    /**
     * Gets the text color
     * @return text color
     */
    public Color getCustomTextColor() {
        return textColor;
    }
    
    /**
     * Sets the text color
     * @param textColor color for the text
     */
    public void setCustomTextColor(Color textColor) {
        Color oldValue = this.textColor;
        this.textColor = textColor != null ? textColor : Color.BLACK;
        if (textArea != null) {
            textArea.setForeground(this.textColor);
            textArea.setCaretColor(this.textColor);
        }
        firePropertyChange("customTextColor", oldValue, this.textColor);
    }
    
    /**
     * Gets the corner radius
     * @return corner radius in pixels
     */
    public int getCustomCornerRadius() {
        return cornerRadius;
    }
    
    /**
     * Sets the corner radius
     * @param cornerRadius radius for rounded corners in pixels
     */
    public void setCustomCornerRadius(int cornerRadius) {
        int oldValue = this.cornerRadius;
        this.cornerRadius = cornerRadius > 0 ? cornerRadius : 12;
        repaint();
        firePropertyChange("customCornerRadius", oldValue, this.cornerRadius);
    }
    
    /**
     * Gets the border thickness
     * @return border thickness in pixels
     */
    public int getCustomBorderThickness() {
        return borderThickness;
    }
    
    /**
     * Sets the border thickness
     * @param borderThickness thickness of the border in pixels
     */
    public void setCustomBorderThickness(int borderThickness) {
        int oldValue = this.borderThickness;
        this.borderThickness = borderThickness > 0 ? borderThickness : 2;
        repaint();
        firePropertyChange("customBorderThickness", oldValue, this.borderThickness);
    }
    
    /**
     * Gets the number of rows
     * @return number of rows
     */
    public int getCustomRows() {
        return textArea != null ? textArea.getRows() : 4;
    }
    
    /**
     * Sets the number of rows
     * @param rows number of rows for the text area
     */
    public void setCustomRows(int rows) {
        int oldValue = getCustomRows();
        if (textArea != null) {
            textArea.setRows(rows > 0 ? rows : 4);
        }
        firePropertyChange("customRows", oldValue, rows);
    }
    
    /**
     * Gets the number of columns
     * @return number of columns
     */
    public int getCustomColumns() {
        return textArea != null ? textArea.getColumns() : 20;
    }
    
    /**
     * Sets the number of columns
     * @param columns number of columns for the text area
     */
    public void setCustomColumns(int columns) {
        int oldValue = getCustomColumns();
        if (textArea != null) {
            textArea.setColumns(columns > 0 ? columns : 20);
        }
        firePropertyChange("customColumns", oldValue, columns);
    }
    
    /**
     * Gets whether line wrap is enabled
     * @return true if line wrap is enabled
     */
    public boolean getCustomLineWrap() {
        return textArea != null ? textArea.getLineWrap() : true;
    }
    
    /**
     * Sets whether line wrap is enabled
     * @param lineWrap true to enable line wrap
     */
    public void setCustomLineWrap(boolean lineWrap) {
        boolean oldValue = getCustomLineWrap();
        if (textArea != null) {
            textArea.setLineWrap(lineWrap);
        }
        firePropertyChange("customLineWrap", oldValue, lineWrap);
    }
    
    /**
     * Gets whether word wrap style is enabled
     * @return true if word wrap style is enabled
     */
    public boolean getCustomWrapStyleWord() {
        return textArea != null ? textArea.getWrapStyleWord() : true;
    }
    
    /**
     * Sets whether word wrap style is enabled
     * @param wrapStyleWord true to enable word wrap style
     */
    public void setCustomWrapStyleWord(boolean wrapStyleWord) {
        boolean oldValue = getCustomWrapStyleWord();
        if (textArea != null) {
            textArea.setWrapStyleWord(wrapStyleWord);
        }
        firePropertyChange("customWrapStyleWord", oldValue, wrapStyleWord);
    }
    
    /**
     * Gets whether the text area is editable
     * @return true if editable
     */
    public boolean getCustomEditable() {
        return textArea != null ? textArea.isEditable() : true;
    }
    
    /**
     * Sets whether the text area is editable
     * @param editable true to make editable
     */
    public void setCustomEditable(boolean editable) {
        boolean oldValue = getCustomEditable();
        if (textArea != null) {
            textArea.setEditable(editable);
        }
        firePropertyChange("customEditable", oldValue, editable);
    }
    
    /**
     * Gets the text content
     * @return text content
     */
    public String getCustomText() {
        return getText();
    }
    
    /**
     * Sets the text content
     * @param text text content to set
     */
    public void setCustomText(String text) {
        String oldValue = getCustomText();
        setText(text != null ? text : "");
        firePropertyChange("customText", oldValue, text);
    }
    
    /**
     * Gets the font for the text area
     * @return font for text area
     */
    public Font getCustomFont() {
        return textArea != null ? textArea.getFont() : new Font("Segoe UI", Font.PLAIN, 14);
    }
    
    /**
     * Sets the font for the text area
     * @param font font for text area
     */
    public void setCustomFont(Font font) {
        Font oldValue = getCustomFont();
        if (textArea != null && font != null) {
            textArea.setFont(font);
        }
        firePropertyChange("customFont", oldValue, font);
    }
    
    // Deprecated methods untuk backward compatibility
    @Deprecated
    public String getPlaceholder() {
        return getCustomPlaceholder();
    }
    
    @Deprecated
    public void setPlaceholder(String placeholder) {
        setCustomPlaceholder(placeholder);
    }
    
    @Deprecated
    public Color getPlaceholderColor() {
        return getCustomPlaceholderColor();
    }
    
    @Deprecated
    public void setPlaceholderColor(Color placeholderColor) {
        setCustomPlaceholderColor(placeholderColor);
    }
    
    @Deprecated
    public Color getBorderColor() {
        return getCustomBorderColor();
    }
    
    @Deprecated
    public void setBorderColor(Color borderColor) {
        setCustomBorderColor(borderColor);
    }
    
    @Deprecated
    public Color getFocusBorderColor() {
        return getCustomFocusBorderColor();
    }
    
    @Deprecated
    public void setFocusBorderColor(Color focusBorderColor) {
        setCustomFocusBorderColor(focusBorderColor);
    }
    
    @Deprecated
    public Color getTextColor() {
        return getCustomTextColor();
    }
    
    @Deprecated
    public void setTextColor(Color textColor) {
        setCustomTextColor(textColor);
    }
    
    @Deprecated
    public int getCornerRadius() {
        return getCustomCornerRadius();
    }
    
    @Deprecated
    public void setCornerRadius(int cornerRadius) {
        setCustomCornerRadius(cornerRadius);
    }
    
    @Deprecated
    public int getBorderThickness() {
        return getCustomBorderThickness();
    }
    
    @Deprecated
    public void setBorderThickness(int borderThickness) {
        setCustomBorderThickness(borderThickness);
    }
    
    @Override
    public void setFont(Font font) {
        super.setFont(font);
        if (textArea != null) {
            textArea.setFont(font);
        }
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (textArea != null) {
            textArea.setEnabled(enabled);
        }
    }
    
    // Demo method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
//                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            JFrame frame = new JFrame("RoundedTextArea Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new GridBagLayout());
            frame.getContentPane().setBackground(new Color(240, 240, 240));
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(15, 15, 15, 15);
            gbc.fill = GridBagConstraints.BOTH;
            
            // Default text area
            RoundedTextArea textArea1 = new RoundedTextArea("Masukkan deskripsi proyek...", 4, 30);
            textArea1.setPreferredSize(new Dimension(300, 120));
            
            // Custom styled text area
            RoundedTextArea textArea2 = new RoundedTextArea("Catatan tambahan...", 4, 30);
            textArea2.setPreferredSize(new Dimension(300, 120));
            textArea2.setBorderColor(new Color(180, 180, 180));
            textArea2.setFocusBorderColor(new Color(0, 123, 255));
            textArea2.setCornerRadius(15);
            textArea2.setBorderThickness(2);
            
            // Large text area
            RoundedTextArea textArea3 = new RoundedTextArea("Tulis laporan lengkap di sini...", 6, 40);
            textArea3.setPreferredSize(new Dimension(400, 180));
            textArea3.setBackground(new Color(248, 248, 248));
            textArea3.setCornerRadius(20);
            
            // Pre-filled text area
            RoundedTextArea textArea4 = new RoundedTextArea("", 4, 30);
            textArea4.setText("Ini adalah contoh teks yang sudah diisi sebelumnya.\n\nBisa multiple lines dan akan wrap dengan baik ketika teks panjang.");
            textArea4.setPreferredSize(new Dimension(300, 120));
            textArea4.setBorderColor(new Color(46, 204, 113));
            textArea4.setFocusBorderColor(new Color(39, 174, 96));
            
            gbc.gridx = 0; gbc.gridy = 0;
            frame.add(new JLabel("Default TextArea:"), gbc);
            gbc.gridy = 1;
            frame.add(textArea1, gbc);
            
            gbc.gridx = 1; gbc.gridy = 0;
            frame.add(new JLabel("Custom Style:"), gbc);
            gbc.gridy = 1;
            frame.add(textArea2, gbc);
            
            gbc.gridx = 0; gbc.gridy = 2;
            frame.add(new JLabel("Large TextArea:"), gbc);
            gbc.gridy = 3;
            frame.add(textArea3, gbc);
            
            gbc.gridx = 1; gbc.gridy = 2;
            frame.add(new JLabel("Pre-filled:"), gbc);
            gbc.gridy = 3;
            frame.add(textArea4, gbc);
            
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}