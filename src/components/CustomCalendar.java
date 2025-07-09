package components;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * CustomCalendar - Modern calendar combo box inspired by JCalendar
 * Compatible with NetBeans GUI Builder
 * Fixed version with proper date display formatting
 */
public class CustomCalendar extends JPanel {
    
    // Properties
    private Date selectedDate = new Date();
    private String dateFormat = "dd/MM/yyyy";
    private Color backgroundColor = Color.WHITE;
    private Color textColor = Color.BLACK;
    private Color borderColor = new Color(200, 200, 200);
    private Color focusBorderColor = new Color(0, 123, 255);
    private Color buttonColor = new Color(52, 73, 94);
    private int cornerRadius = 8;
    private boolean enabled = true;
    private boolean showPlaceholder = true;
    private String placeholder = "mm/dd/yyyy";
    
    // Components
    private JTextField dateField;
    private JButton dropdownButton;
    private JWindow calendarWindow;
    private JPanel calendarPanel;
    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat formatter;
    
    // State
    private boolean isCalendarVisible = false;
    private Calendar displayCalendar = Calendar.getInstance();
    private boolean hasValidDate = false;
    
    public CustomCalendar() {
        initComponents();
        updateDateFormat();
        // Set displayCalendar ke bulan/tahun saat ini
        displayCalendar.setTime(new Date());
    
        updateDisplay();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, 35));
        setBackground(backgroundColor);
        
        // Date field with custom placeholder behavior
        dateField = new JTextField();
        dateField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateField.setBorder(createRoundedBorder());
        dateField.setBackground(backgroundColor);
        dateField.setForeground(textColor);
        dateField.setEditable(false);
        
        // Set initial placeholder
        if (showPlaceholder && !hasValidDate) {
            dateField.setText(placeholder);
            dateField.setForeground(new Color(150, 150, 150)); // Gray color for placeholder
        }
        
        // Dropdown button with calendar icon - using simple text for better compatibility
        dropdownButton = new JButton("📅");
        dropdownButton.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        dropdownButton.setPreferredSize(new Dimension(55, 35));
        dropdownButton.setMinimumSize(new Dimension(55, 35));
        dropdownButton.setMaximumSize(new Dimension(55, 35));
        dropdownButton.setBorderPainted(false);
        dropdownButton.setContentAreaFilled(false);
        dropdownButton.setFocusPainted(false);
        dropdownButton.setBackground(buttonColor);
        dropdownButton.setForeground(new Color(100, 100, 100));
        dropdownButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Critical: Remove all margins and ensure no text clipping
        dropdownButton.setMargin(new Insets(0, 0, 0, 0));
        dropdownButton.setBorder(null); // Remove any border that might interfere
        dropdownButton.setBorderPainted(false);
        dropdownButton.setContentAreaFilled(false);
        dropdownButton.setFocusPainted(false);
        
        // Ensure text is centered and not clipped
        dropdownButton.setHorizontalAlignment(SwingConstants.CENTER);
        dropdownButton.setVerticalAlignment(SwingConstants.CENTER);
        dropdownButton.setHorizontalTextPosition(SwingConstants.CENTER);
        dropdownButton.setVerticalTextPosition(SwingConstants.CENTER);
        
        // Alternative fallback if emoji doesn't work
        try {
            // Test if we can display the calendar emoji
            FontMetrics fm = dropdownButton.getFontMetrics(dropdownButton.getFont());
            if (fm.stringWidth("📅") <= 0) {
                // Fallback to text-based icon
                dropdownButton.setText("CAL");
                dropdownButton.setFont(new Font("Dialog", Font.BOLD, 11));
            }
        } catch (Exception e) {
            dropdownButton.setText("CAL");
            dropdownButton.setFont(new Font("Dialog", Font.BOLD, 11));
        }
        
        // Create panel for the button with rounded right border
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.setPreferredSize(new Dimension(45, 35)); // Match button size
        buttonPanel.setBorder(createRoundedRightBorder());
        buttonPanel.add(dropdownButton, BorderLayout.CENTER);
        
        add(dateField, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
        
        setupEvents();
    }
    
    private void setupEvents() {
        // Toggle calendar on button click
        dropdownButton.addActionListener(e -> toggleCalendar());
        
        // Toggle calendar on field click
        dateField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (enabled) toggleCalendar();
            }
        });
        
        // Focus styling for the entire component
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                dateField.setBorder(createRoundedBorder(focusBorderColor));
                updateButtonPanelBorder(focusBorderColor);
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (!isCalendarVisible) {
                    dateField.setBorder(createRoundedBorder());
                    updateButtonPanelBorder(borderColor);
                }
            }
        });
        
        // Button hover effect
        dropdownButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                dropdownButton.setForeground(buttonColor);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                dropdownButton.setForeground(new Color(100, 100, 100));
            }
        });
        
        // Make the component focusable
        setFocusable(true);
        
        // Key listener for keyboard navigation
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (enabled) toggleCalendar();
                }
            }
        });
    }
    
    private void updateButtonPanelBorder(Color color) {
        Component buttonPanel = ((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.EAST);
        if (buttonPanel instanceof JPanel) {
            ((JPanel) buttonPanel).setBorder(createRoundedRightBorder(color));
            buttonPanel.repaint();
        }
    }
    
    private void toggleCalendar() {
        if (!enabled) return;
        
        if (isCalendarVisible) {
            hideCalendar();
        } else {
            showCalendar();
        }
    }
    
    private void showCalendar() {
        if (calendarWindow == null) {
            createCalendarWindow();
        }
        
        updateCalendarDisplay();
        
        Point location = getLocationOnScreen();
        calendarWindow.setLocation(location.x, location.y + getHeight() + 2);
        calendarWindow.setVisible(true);
        isCalendarVisible = true;
        
        dateField.setBorder(createRoundedBorder(focusBorderColor));
        updateButtonPanelBorder(focusBorderColor);
    }
    
    private void hideCalendar() {
        if (calendarWindow != null) {
            calendarWindow.setVisible(false);
        }
        isCalendarVisible = false;
        dateField.setBorder(createRoundedBorder());
        updateButtonPanelBorder(borderColor);
    }
    
    private void createCalendarWindow() {
        calendarWindow = new JWindow(SwingUtilities.getWindowAncestor(this));
        calendarWindow.setAlwaysOnTop(true);
        
        calendarPanel = new RoundedPanel();
        ((RoundedPanel) calendarPanel).setCustomRadius(12);
        ((RoundedPanel) calendarPanel).setCustomBackgroundColor(Color.WHITE);
        ((RoundedPanel) calendarPanel).setCustomBorderColor(borderColor);
        ((RoundedPanel) calendarPanel).setCustomBorderThickness(1);
        
        calendarPanel.setLayout(new BorderLayout());
        calendarPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        
        calendarWindow.add(calendarPanel);
        calendarWindow.setSize(340, 280); // Increased width from 320 to 340 to accommodate wider buttons
        
        // Close on focus loss
        calendarWindow.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                SwingUtilities.invokeLater(() -> hideCalendar());
            }
        });
    }
    
    private void updateCalendarDisplay() {
        calendarPanel.removeAll();
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        calendarPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Calendar grid
        JPanel gridPanel = createCalendarGrid();
        calendarPanel.add(gridPanel, BorderLayout.CENTER);
        
        // Today button
        JPanel footerPanel = createFooterPanel();
        calendarPanel.add(footerPanel, BorderLayout.SOUTH);
        
        calendarPanel.revalidate();
        calendarPanel.repaint();
    }
    
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        
        // Navigation buttons
        JButton prevButton = createNavButton("◀");
        prevButton.addActionListener(e -> {
            displayCalendar.add(Calendar.MONTH, -1);
            updateCalendarDisplay();
        });
        
        JButton nextButton = createNavButton("▶");
        nextButton.addActionListener(e -> {
            displayCalendar.add(Calendar.MONTH, 1);
            updateCalendarDisplay();
        });
        
        // Month/year label
        String monthYear = String.format("%tB %tY", displayCalendar, displayCalendar);
        JLabel monthLabel = new JLabel(monthYear, SwingConstants.CENTER);
        monthLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        monthLabel.setForeground(buttonColor);
        
        header.add(prevButton, BorderLayout.WEST);
        header.add(monthLabel, BorderLayout.CENTER);
        header.add(nextButton, BorderLayout.EAST);
        
        return header;
    }
    
    private JPanel createFooterPanel() {
        JPanel footer = new JPanel(new FlowLayout());
        footer.setOpaque(false);
        footer.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        
        JButton todayButton = new JButton("Today");
        todayButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        todayButton.setBackground(new Color(240, 240, 240));
        todayButton.setForeground(buttonColor);
        todayButton.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        todayButton.setFocusPainted(false);
        todayButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        todayButton.addActionListener(e -> {
            setDate(new Date());
            hideCalendar();
        });
        
        todayButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                todayButton.setBackground(new Color(220, 220, 220));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                todayButton.setBackground(new Color(240, 240, 240));
            }
        });
        
        footer.add(todayButton);
        return footer;
    }
    
    private JButton createNavButton(String text) {
        // Use simple ASCII arrows instead of Unicode for better compatibility
        String buttonText = text;
        if (text.equals("◀")) {
            buttonText = "<"; // Simple left arrow
        } else if (text.equals("▶")) {
            buttonText = ">"; // Simple right arrow
        }
        
        JButton button = new JButton(buttonText);
        button.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14)); // Larger font for better visibility
        button.setPreferredSize(new Dimension(36, 32));
        button.setMinimumSize(new Dimension(36, 32));
        button.setMaximumSize(new Dimension(36, 32));
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setForeground(buttonColor);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setBorder(null); // Remove any border that might interfere
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        
        // Ensure text is centered and not clipped
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setOpaque(true);
                button.setBackground(new Color(240, 240, 240));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setOpaque(false);
            }
        });
        
        return button;
    }
    
    private JPanel createCalendarGrid() {
        JPanel grid = new JPanel(new GridLayout(7, 7, 2, 2));
        grid.setOpaque(false);
        
        // Day headers
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        for (String day : days) {
            JLabel label = new JLabel(day, SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 11));
            label.setForeground(new Color(120, 120, 120));
            label.setPreferredSize(new Dimension(42, 28)); // Match button width
            grid.add(label);
        }
        
        // Calendar days
        Calendar tempCal = (Calendar) displayCalendar.clone();
        tempCal.set(Calendar.DAY_OF_MONTH, 1);
        
        int firstDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK) - 2; // Adjust for Monday start
        if (firstDayOfWeek < 0) firstDayOfWeek = 6;
        
        tempCal.add(Calendar.DAY_OF_MONTH, -firstDayOfWeek);
        
        Calendar selectedCal = Calendar.getInstance();
        if (hasValidDate) {
            selectedCal.setTime(selectedDate);
        }
        
        Calendar todayCal = Calendar.getInstance();
        
        for (int i = 0; i < 42; i++) {
            JButton dayButton = createDayButton(tempCal, selectedCal, todayCal);
            grid.add(dayButton);
            tempCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        return grid;
    }
    
    private JButton createDayButton(Calendar dayCal, Calendar selectedCal, Calendar todayCal) {
        int day = dayCal.get(Calendar.DAY_OF_MONTH);
        JButton button = new JButton(String.valueOf(day));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Reduced font size from 13 to 12
        button.setPreferredSize(new Dimension(42, 32)); // Increased width from 38 to 42
        button.setMinimumSize(new Dimension(42, 32));
        button.setMaximumSize(new Dimension(42, 32));
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        boolean isCurrentMonth = dayCal.get(Calendar.MONTH) == displayCalendar.get(Calendar.MONTH);
        boolean isSelected = hasValidDate && isSameDay(dayCal, selectedCal);
        boolean isToday = isSameDay(dayCal, todayCal);
        
        // Styling
        if (!isCurrentMonth) {
            button.setForeground(new Color(180, 180, 180));
        } else if (isSelected) {
            button.setOpaque(true);
            button.setBackground(focusBorderColor);
            button.setForeground(Color.WHITE);
        } else if (isToday) {
            button.setForeground(focusBorderColor);
            button.setFont(new Font("Segoe UI", Font.BOLD, 12)); // Consistent font size
        } else {
            button.setForeground(Color.BLACK);
        }
        
        // Click handler
        Date buttonDate = dayCal.getTime();
        button.addActionListener(e -> {
            setDate(buttonDate);
            hideCalendar();
        });
        
        // Hover effect
        if (isCurrentMonth && !isSelected) {
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setOpaque(true);
                    button.setBackground(new Color(240, 240, 240));
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    button.setOpaque(false);
                }
            });
        }
        
        return button;
    }
    
    private boolean isSameDay(Calendar cal1, Calendar cal2) {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }
    
    private Border createRoundedBorder() {
        return createRoundedBorder(borderColor);
    }
    
    private Border createRoundedBorder(Color color) {
        return new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.setStroke(new BasicStroke(1));
                
                // Draw left, top, and bottom borders only (no right border)
                // Left border with rounded corner
                g2.drawArc(x, y, cornerRadius, cornerRadius, 90, 90);
                g2.drawLine(x, y + cornerRadius/2, x, y + height - cornerRadius/2);
                g2.drawArc(x, y + height - cornerRadius - 1, cornerRadius, cornerRadius, 180, 90);
                
                // Top border
                g2.drawLine(x + cornerRadius/2, y, x + width - 1, y);
                
                // Bottom border  
                g2.drawLine(x + cornerRadius/2, y + height - 1, x + width - 1, y + height - 1);
                
                g2.dispose();
            }
            
            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(2, 8, 2, 0); // No right padding
            }
            
            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        };
    }
    
    private Border createRoundedRightBorder() {
        return createRoundedRightBorder(borderColor);
    }
    
    private Border createRoundedRightBorder(Color color) {
        return new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.setStroke(new BasicStroke(1));
                
                // Draw only right, top, and bottom borders (no left border)
                // Top border
                g2.drawLine(x, y, x + width - 1, y);
                // Right border with rounded corner
                g2.drawArc(x + width - cornerRadius - 1, y, cornerRadius, cornerRadius, 0, 90);
                g2.drawLine(x + width - 1, y + cornerRadius/2, x + width - 1, y + height - cornerRadius/2);
                g2.drawArc(x + width - cornerRadius - 1, y + height - cornerRadius - 1, cornerRadius, cornerRadius, 270, 90);
                // Bottom border
                g2.drawLine(x + width - cornerRadius/2, y + height - 1, x, y + height - 1);
                
                g2.dispose();
            }
            
            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(2, 0, 2, 2); // No left padding
            }
            
            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        };
    }
    
    
    private void updateDisplay() {
        if (dateField != null && formatter != null) {
            if (hasValidDate && selectedDate != null) {
                dateField.setText(formatter.format(selectedDate));
                dateField.setForeground(textColor);
            } else if (showPlaceholder) {
                dateField.setText(placeholder);
                dateField.setForeground(new Color(150, 150, 150));
            } else {
                dateField.setText("");
                dateField.setForeground(textColor);
            }
        }
    }
    
    private void updateDateFormat() {
        formatter = new SimpleDateFormat(dateFormat);
        updateDisplay();
    }
    
    // NetBeans Properties and Utility Methods
    
    public Date getDate() {
        return hasValidDate ? selectedDate : null;
    }
    
    /**
    * Gets date as MySQL-compatible string format (YYYY-MM-DD)
    * Regardless of display format, this always returns database-ready format
    * @return date in YYYY-MM-DD format for database storage
    */
   public String getDateSQLString() {
       if (!hasValidDate || selectedDate == null) {
           return "";
       }
       SimpleDateFormat mysqlFormat = new SimpleDateFormat("yyyy-MM-dd");
       return mysqlFormat.format(selectedDate);
   }
    
    public void setDate(Date date) {
        Date oldDate = this.selectedDate;
        
        if (date != null) {
            this.selectedDate = date;
            this.hasValidDate = true;
            calendar.setTime(this.selectedDate);
            displayCalendar.setTime(this.selectedDate);
        } else {
            this.selectedDate = new Date(); // Keep internal date for calendar display
            this.hasValidDate = false;
        }
        
        updateDisplay();
        firePropertyChange("date", oldDate, hasValidDate ? this.selectedDate : null);
    }
    
    public Date getSelectedDate() {
        return getDate();
    }
    
    public void setSelectedDate(Date date) {
        setDate(date);
    }
    
    public void setToday() {
        setDate(new Date());
    }
    
    public void clear() {
        hasValidDate = false;
        updateDisplay();
        firePropertyChange("date", selectedDate, null);
    }
    
    public boolean isEmpty() {
        return !hasValidDate;
    }
    
    public String getDateAsString() {
        return hasValidDate && formatter != null ? formatter.format(selectedDate) : "";
    }
    
    public void setDateFromString(String dateStr) {
        try {
            if (formatter != null && dateStr != null && !dateStr.trim().isEmpty()) {
                setDate(formatter.parse(dateStr));
            } else {
                clear();
            }
        } catch (Exception e) {
            System.err.println("Invalid date format: " + dateStr);
            clear();
        }
    }
    
    public void setDateFromSQLString(String sqlDateString) {
        try {
            SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sqlFormat.parse(sqlDateString);
            setDate(date);
        } catch (Exception e) {
            clear();
        }
    }
    
    public Calendar getCalendar() {
        if (hasValidDate) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(selectedDate);
            return cal;
        }
        return null;
    }
    
    public void setCalendar(Calendar calendar) {
        if (calendar != null) {
            setDate(calendar.getTime());
        } else {
            clear();
        }
    }
    
    // JavaBean Properties for NetBeans GUI Builder
    
    public String getDateFormat() {
        return dateFormat;
    }
    
    public void setDateFormat(String dateFormat) {
        String oldFormat = this.dateFormat;
        this.dateFormat = dateFormat != null ? dateFormat : "dd/MM/yyyy";
        updateDateFormat();
        firePropertyChange("dateFormat", oldFormat, this.dateFormat);
    }
    
    public String getPlaceholder() {
        return placeholder;
    }
    
    public void setPlaceholder(String placeholder) {
        String oldPlaceholder = this.placeholder;
        this.placeholder = placeholder != null ? placeholder : "mm/dd/yyyy";
        updateDisplay();
        firePropertyChange("placeholder", oldPlaceholder, this.placeholder);
    }
    
    public boolean isShowPlaceholder() {
        return showPlaceholder;
    }
    
    public void setShowPlaceholder(boolean showPlaceholder) {
        boolean oldValue = this.showPlaceholder;
        this.showPlaceholder = showPlaceholder;
        updateDisplay();
        firePropertyChange("showPlaceholder", oldValue, showPlaceholder);
    }
    
    @Override
    public Color getBackground() {
        return backgroundColor;
    }
    
    @Override
    public void setBackground(Color backgroundColor) {
        Color oldColor = this.backgroundColor;
        this.backgroundColor = backgroundColor != null ? backgroundColor : Color.WHITE;
        super.setBackground(this.backgroundColor);
        if (dateField != null) {
            dateField.setBackground(this.backgroundColor);
        }
        firePropertyChange("background", oldColor, this.backgroundColor);
    }
    
    public Color getTextColor() {
        return textColor;
    }
    
    public void setTextColor(Color textColor) {
        Color oldColor = this.textColor;
        this.textColor = textColor != null ? textColor : Color.BLACK;
        updateDisplay();
        firePropertyChange("textColor", oldColor, this.textColor);
    }
    
    public Color getBorderColor() {
        return borderColor;
    }
    
    public void setBorderColor(Color borderColor) {
        Color oldColor = this.borderColor;
        this.borderColor = borderColor != null ? borderColor : new Color(200, 200, 200);
        if (dateField != null) {
            dateField.setBorder(createRoundedBorder());
            updateButtonPanelBorder(this.borderColor);
        }
        firePropertyChange("borderColor", oldColor, this.borderColor);
    }
    
    public Color getFocusBorderColor() {
        return focusBorderColor;
    }
    
    public void setFocusBorderColor(Color focusBorderColor) {
        Color oldColor = this.focusBorderColor;
        this.focusBorderColor = focusBorderColor != null ? focusBorderColor : new Color(0, 123, 255);
        firePropertyChange("focusBorderColor", oldColor, this.focusBorderColor);
    }
    
    public int getCornerRadius() {
        return cornerRadius;
    }
    
    public void setCornerRadius(int cornerRadius) {
        int oldRadius = this.cornerRadius;
        this.cornerRadius = cornerRadius > 0 ? cornerRadius : 8;
        if (dateField != null) {
            dateField.setBorder(createRoundedBorder());
            updateButtonPanelBorder(borderColor);
        }
        firePropertyChange("cornerRadius", oldRadius, this.cornerRadius);
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        boolean oldEnabled = this.enabled;
        this.enabled = enabled;
        super.setEnabled(enabled);
        
        if (dateField != null) {
            dateField.setEnabled(enabled);
            dropdownButton.setEnabled(enabled);
        }
        
        if (!enabled && isCalendarVisible) {
            hideCalendar();
        }
        
        firePropertyChange("enabled", oldEnabled, enabled);
    }
    
    // Utility date manipulation methods
    public void addDays(int days) {
        Calendar cal = getCalendar();
        if (cal != null) {
            cal.add(Calendar.DAY_OF_MONTH, days);
            setDate(cal.getTime());
        }
    }
    
    public void addMonths(int months) {
        Calendar cal = getCalendar();
        if (cal != null) {
            cal.add(Calendar.MONTH, months);
            setDate(cal.getTime());
        }
    }
    
    public void addYears(int years) {
        Calendar cal = getCalendar();
        if (cal != null) {
            cal.add(Calendar.YEAR, years);
            setDate(cal.getTime());
        }
    }
    
    public int getDay() {
        Calendar cal = getCalendar();
        return cal != null ? cal.get(Calendar.DAY_OF_MONTH) : -1;
    }
    
    public int getMonth() {
        Calendar cal = getCalendar();
        return cal != null ? cal.get(Calendar.MONTH) + 1 : -1; // 1-based
    }
    
    public int getYear() {
        Calendar cal = getCalendar();
        return cal != null ? cal.get(Calendar.YEAR) : -1;
    }
    
    // Event listeners
    public void addDateChangeListener(PropertyChangeListener listener) {
        addPropertyChangeListener("date", listener);
    }
    
    public void removeDateChangeListener(PropertyChangeListener listener) {
        removePropertyChangeListener("date", listener);
    }
    
    // RoundedPanel class (embedded for self-contained component)
    private static class RoundedPanel extends JPanel {
        private int customRadius = 15;
        private Color customBackgroundColor = Color.WHITE;
        private Color customBorderColor = new Color(200, 200, 200);
        private int customBorderThickness = 1;
        
        public RoundedPanel() {
            setOpaque(false);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Background dengan rounded corners
            g2d.setColor(customBackgroundColor);
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), customRadius, customRadius);

            // Border
            g2d.setColor(customBorderColor);
            g2d.setStroke(new BasicStroke(customBorderThickness));
            g2d.drawRoundRect(
                customBorderThickness/2,
                customBorderThickness/2,
                getWidth() - customBorderThickness,
                getHeight() - customBorderThickness,
                customRadius,
                customRadius
            );

            g2d.dispose();
            super.paintComponent(g);
        }
        
        public void setCustomRadius(int radius) {
            this.customRadius = radius;
            repaint();
        }
        
        public void setCustomBackgroundColor(Color color) {
            this.customBackgroundColor = color;
            repaint();
        }
        
        public void setCustomBorderColor(Color color) {
            this.customBorderColor = color;
            repaint();
        }
        
        public void setCustomBorderThickness(int thickness) {
            this.customBorderThickness = thickness;
            repaint();
        }
    }
    
    // Demo and testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
//                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            JFrame frame = new JFrame("CustomCalendar - Fixed Version Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new GridBagLayout());
            frame.getContentPane().setBackground(new Color(245, 245, 245));
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(15, 15, 15, 15);
            gbc.anchor = GridBagConstraints.WEST;
            
            // Title
            JLabel titleLabel = new JLabel("CustomCalendar Component - Fixed Version");
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            titleLabel.setForeground(new Color(52, 73, 94));
            gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
            frame.add(titleLabel, gbc);
            
            gbc.gridwidth = 1;
            
            // Default calendar with mm/dd/yyyy placeholder
            gbc.gridx = 0; gbc.gridy = 1;
            frame.add(new JLabel("Default (mm/dd/yyyy):"), gbc);
            
            CustomCalendar cal1 = new CustomCalendar();
            cal1.setPlaceholder("mm/dd/yyyy");
            cal1.setPreferredSize(new Dimension(250, 40));
            gbc.gridx = 1;
            frame.add(cal1, gbc);
            
            // Calendar with dd/MM/yyyy format
            gbc.gridx = 0; gbc.gridy = 2;
            frame.add(new JLabel("DD/MM/YYYY Format:"), gbc);
            
            CustomCalendar cal2 = new CustomCalendar();
            cal2.setDateFormat("dd/MM/yyyy");
            cal2.setPlaceholder("dd/mm/yyyy");
            cal2.setPreferredSize(new Dimension(250, 40));
            gbc.gridx = 1;
            frame.add(cal2, gbc);
            
            // Calendar with yyyy-MM-dd format
            gbc.gridx = 0; gbc.gridy = 3;
            frame.add(new JLabel("ISO Format (YYYY-MM-DD):"), gbc);
            
            CustomCalendar cal3 = new CustomCalendar();
            cal3.setDateFormat("dd MMMM yyyy");
            cal3.setPlaceholder("dd MMMM yyyy");
            cal3.setFocusBorderColor(new Color(40, 167, 69));
            cal3.setPreferredSize(new Dimension(250, 40));
            cal3.setDateFromString(TOOL_TIP_TEXT_KEY);
            cal3.getDate();
            cal3.getDateSQLString();
            gbc.gridx = 1;
            frame.add(cal3, gbc);
            
            // Calendar with pre-selected date
            gbc.gridx = 0; gbc.gridy = 4;
            frame.add(new JLabel("Pre-selected Date:"), gbc);
            
            CustomCalendar cal4 = new CustomCalendar();
            cal4.setDate(new Date()); // Set to today
            cal4.setPreferredSize(new Dimension(250, 40));
            gbc.gridx = 1;
            frame.add(cal4, gbc);
            
            // Disabled calendar
            gbc.gridx = 0; gbc.gridy = 5;
            frame.add(new JLabel("Disabled State:"), gbc);
            
            CustomCalendar cal5 = new CustomCalendar();
            cal5.setEnabled(false);
            cal5.setPreferredSize(new Dimension(250, 40));
            gbc.gridx = 1;
            frame.add(cal5, gbc);
            
            // Control buttons
            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.setOpaque(false);
            
            JButton setTodayBtn = new JButton("Set Today");
            setTodayBtn.addActionListener(e -> {
                cal1.setToday();
                cal2.setToday();
                cal3.setToday();
            });
            
            JButton clearBtn = new JButton("Clear All");
            clearBtn.addActionListener(e -> {
                cal1.clear();
                cal2.clear();
                cal3.clear();
                cal4.clear();
            });
            
            
            JButton getValuesBtn = new JButton("Get Values");
            getValuesBtn.addActionListener(e -> {
                String message = String.format(
                    "Calendar Values:\n" +
                    "Default: %s\n" +
                    "DD/MM/YYYY: %s\n" +
                    "ISO Format: %s\n" +
                    "Pre-selected: %s",
                    cal1.getDateAsString(),
                    cal2.getDateAsString(),
                    cal3.getDateAsString(),
                    cal4.getDateAsString()
                );
                JOptionPane.showMessageDialog(frame, message, "Calendar Values", JOptionPane.INFORMATION_MESSAGE);
            });
            
            buttonPanel.add(setTodayBtn);
            buttonPanel.add(clearBtn);
            buttonPanel.add(getValuesBtn);
            
            gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
            frame.add(buttonPanel, gbc);
            
            // Instructions
            JTextArea instructions = new JTextArea(
                "Instructions 📅:\n" +
                "• Click on the calendar icon or field to open date picker\n" +
                "• Navigate months using arrow buttons\n" +
                "• Click 'Today' to select current date\n" +
                "• Empty calendars show placeholder text\n" +
                "• Selected dates display in the specified format"
            );
            instructions.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
            instructions.setBackground(new Color(248, 249, 250));
            instructions.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            instructions.setEditable(false);
            
            gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            frame.add(instructions, gbc);
            
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            
            // Add property change listeners for demo
            cal1.addDateChangeListener(evt -> 
                System.out.println("Calendar 1 date changed: " + cal1.getDateAsString()));
            cal2.addDateChangeListener(evt -> 
                System.out.println("Calendar 2 date changed: " + cal2.getDateAsString()));
            cal3.addDateChangeListener(evt -> 
                System.out.println("Calendar 3 date changed: " + cal3.getDateAsString()));
        });
    }
}