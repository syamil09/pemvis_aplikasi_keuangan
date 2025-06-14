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
    
    public CustomCalendar() {
        initComponents();
        updateDateFormat();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, 35));
        setBackground(backgroundColor);
        
        // Date field
        dateField = new JTextField();
        dateField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateField.setBorder(createRoundedBorder());
        dateField.setBackground(backgroundColor);
        dateField.setForeground(textColor);
        dateField.setEditable(false);
        
        // Dropdown button
        dropdownButton = new JButton("▼");
        dropdownButton.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        dropdownButton.setPreferredSize(new Dimension(25, 35));
        dropdownButton.setBorderPainted(false);
        dropdownButton.setContentAreaFilled(false);
        dropdownButton.setFocusPainted(false);
        dropdownButton.setBackground(buttonColor);
        dropdownButton.setForeground(Color.GRAY);
        dropdownButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        add(dateField, BorderLayout.CENTER);
        add(dropdownButton, BorderLayout.EAST);
        
        setupEvents();
        updateDisplay();
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
        
        // Focus styling
        dateField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                dateField.setBorder(createRoundedBorder(focusBorderColor));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (!isCalendarVisible) {
                    dateField.setBorder(createRoundedBorder());
                }
            }
        });
        
        // Button hover
        dropdownButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                dropdownButton.setForeground(buttonColor);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                dropdownButton.setForeground(Color.GRAY);
            }
        });
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
    }
    
    private void hideCalendar() {
        if (calendarWindow != null) {
            calendarWindow.setVisible(false);
        }
        isCalendarVisible = false;
        dateField.setBorder(createRoundedBorder());
    }
    
    private void createCalendarWindow() {
        calendarWindow = new JWindow(SwingUtilities.getWindowAncestor(this));
        calendarWindow.setAlwaysOnTop(true);
        
        calendarPanel = new JPanel(new BorderLayout());
        calendarPanel.setBackground(Color.WHITE);
        calendarPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderColor, 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        
        calendarWindow.add(calendarPanel);
        calendarWindow.setSize(300, 260);
        
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
        
        calendarPanel.revalidate();
        calendarPanel.repaint();
    }
    
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        
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
        monthLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        monthLabel.setForeground(buttonColor);
        
        header.add(prevButton, BorderLayout.WEST);
        header.add(monthLabel, BorderLayout.CENTER);
        header.add(nextButton, BorderLayout.EAST);
        
        return header;
    }
    
    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(30, 30));
        button.setMinimumSize(new Dimension(30, 30));
        button.setMaximumSize(new Dimension(30, 30));
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setForeground(buttonColor);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
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
        JPanel grid = new JPanel(new GridLayout(7, 7, 3, 3));
        grid.setOpaque(false);
        
        // Day headers
        String[] days = {"Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"};
        for (String day : days) {
            JLabel label = new JLabel(day, SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 11));
            label.setForeground(Color.GRAY);
            label.setPreferredSize(new Dimension(35, 25));
            grid.add(label);
        }
        
        // Calendar days
        Calendar tempCal = (Calendar) displayCalendar.clone();
        tempCal.set(Calendar.DAY_OF_MONTH, 1);
        
        int firstDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK) - 2; // Adjust for Monday start
        if (firstDayOfWeek < 0) firstDayOfWeek = 6;
        
        tempCal.add(Calendar.DAY_OF_MONTH, -firstDayOfWeek);
        
        Calendar selectedCal = Calendar.getInstance();
        selectedCal.setTime(selectedDate);
        
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
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setPreferredSize(new Dimension(35, 30));
        button.setMinimumSize(new Dimension(35, 30));
        button.setMaximumSize(new Dimension(35, 30));
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        boolean isCurrentMonth = dayCal.get(Calendar.MONTH) == displayCalendar.get(Calendar.MONTH);
        boolean isSelected = isSameDay(dayCal, selectedCal);
        boolean isToday = isSameDay(dayCal, todayCal);
        
        // Styling
        if (!isCurrentMonth) {
            button.setForeground(Color.LIGHT_GRAY);
        } else if (isSelected) {
            button.setOpaque(true);
            button.setBackground(focusBorderColor);
            button.setForeground(Color.WHITE);
        } else if (isToday) {
            button.setForeground(focusBorderColor);
            button.setFont(new Font("Segoe UI", Font.BOLD, 11));
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
                g2.drawRoundRect(x, y, width - 1, height - 1, cornerRadius, cornerRadius);
                g2.dispose();
            }
            
            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(2, 8, 2, 2);
            }
            
            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        };
    }
    
    private void updateDisplay() {
        if (dateField != null && formatter != null) {
            dateField.setText(formatter.format(selectedDate));
        }
    }
    
    private void updateDateFormat() {
        formatter = new SimpleDateFormat(dateFormat);
        updateDisplay();
    }
    
    // NetBeans Properties and Utility Methods
    
    public Date getDate() {
        return selectedDate;
    }
    
    public void setDate(Date date) {
        Date oldDate = this.selectedDate;
        this.selectedDate = date != null ? date : new Date();
        calendar.setTime(this.selectedDate);
        displayCalendar.setTime(this.selectedDate);
        updateDisplay();
        firePropertyChange("date", oldDate, this.selectedDate);
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
        setDate(null);
        dateField.setText("");
    }
    
    public boolean isEmpty() {
        return selectedDate == null || dateField.getText().trim().isEmpty();
    }
    
    public String getDateAsString() {
        return formatter != null ? formatter.format(selectedDate) : "";
    }
    
    public void setDateFromString(String dateStr) {
        try {
            if (formatter != null && dateStr != null && !dateStr.trim().isEmpty()) {
                setDate(formatter.parse(dateStr));
            }
        } catch (Exception e) {
            System.err.println("Invalid date format: " + dateStr);
        }
    }
    
    public Calendar getCalendar() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(selectedDate);
        return cal;
    }
    
    public void setCalendar(Calendar calendar) {
        if (calendar != null) {
            setDate(calendar.getTime());
        }
    }
    
    public void addDays(int days) {
        Calendar cal = getCalendar();
        cal.add(Calendar.DAY_OF_MONTH, days);
        setDate(cal.getTime());
    }
    
    public void addMonths(int months) {
        Calendar cal = getCalendar();
        cal.add(Calendar.MONTH, months);
        setDate(cal.getTime());
    }
    
    public void addYears(int years) {
        Calendar cal = getCalendar();
        cal.add(Calendar.YEAR, years);
        setDate(cal.getTime());
    }
    
    public int getDay() {
        Calendar cal = getCalendar();
        return cal.get(Calendar.DAY_OF_MONTH);
    }
    
    public int getMonth() {
        Calendar cal = getCalendar();
        return cal.get(Calendar.MONTH) + 1; // 1-based
    }
    
    public int getYear() {
        Calendar cal = getCalendar();
        return cal.get(Calendar.YEAR);
    }
    
    public void setMinimumDate(Date minDate) {
        // Could be implemented to restrict date selection
        firePropertyChange("minimumDate", null, minDate);
    }
    
    public void setMaximumDate(Date maxDate) {
        // Could be implemented to restrict date selection
        firePropertyChange("maximumDate", null, maxDate);
    }
    
    public void addDateChangeListener(PropertyChangeListener listener) {
        addPropertyChangeListener("date", listener);
    }
    
    public void removeDateChangeListener(PropertyChangeListener listener) {
        removePropertyChangeListener("date", listener);
    }
    
    public String getDateFormat() {
        return dateFormat;
    }
    
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        updateDateFormat();
    }
    
    @Override
    public Color getBackground() {
        return backgroundColor;
    }
    
    @Override
    public void setBackground(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        super.setBackground(backgroundColor);
        if (dateField != null) {
            dateField.setBackground(backgroundColor);
        }
    }
    
    public Color getTextColor() {
        return textColor;
    }
    
    public void setTextColor(Color textColor) {
        this.textColor = textColor;
        if (dateField != null) {
            dateField.setForeground(textColor);
        }
    }
    
    public Color getBorderColor() {
        return borderColor;
    }
    
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        if (dateField != null) {
            dateField.setBorder(createRoundedBorder());
        }
    }
    
    public Color getFocusBorderColor() {
        return focusBorderColor;
    }
    
    public void setFocusBorderColor(Color focusBorderColor) {
        this.focusBorderColor = focusBorderColor;
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        super.setEnabled(enabled);
        if (dateField != null) {
            dateField.setEnabled(enabled);
            dropdownButton.setEnabled(enabled);
        }
        if (!enabled && isCalendarVisible) {
            hideCalendar();
        }
    }
    
    // Demo
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("CustomCalendar Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
            
            CustomCalendar cal1 = new CustomCalendar();
            
            CustomCalendar cal2 = new CustomCalendar();
            cal2.setDateFormat("yyyy-MM-dd");
            cal2.setFocusBorderColor(new Color(40, 167, 69));
            
            CustomCalendar cal3 = new CustomCalendar();
            cal3.setBackground(new Color(248, 249, 250));
            cal3.setEnabled(false);
            
            frame.add(new JLabel("Default:"));
            frame.add(cal1);
            frame.add(new JLabel("Custom:"));
            frame.add(cal2);
            frame.add(new JLabel("Disabled:"));
            frame.add(cal3);
            
            frame.setSize(500, 150);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}