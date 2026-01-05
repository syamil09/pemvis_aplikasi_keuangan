package view;

import components.CustomCalendar;
import components.RoundedButton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Popup dialog untuk input tanggal awal dan akhir laporan
 * 
 * @author Leonovo
 */
public class PopupReportDateRange extends JDialog {
    
    private CustomCalendar startDatePicker;
    private CustomCalendar endDatePicker;
    private boolean confirmed = false;
    
    public PopupReportDateRange(Frame parent) {
        super(parent, "Pilih Rentang Tanggal", true);
        initComponents();
        setLocationRelativeTo(parent);
    }
    
    public PopupReportDateRange() {
        super((Frame) null, "Pilih Rentang Tanggal", true);
        initComponents();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setSize(400, 320);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(236, 240, 241));
        
        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(236, 240, 241));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // title
        JLabel titleLabel = new JLabel("Pilih Rentang Tanggal Laporan");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(52, 73, 94));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        
        // start date
        JLabel startLabel = new JLabel("Tanggal Mulai");
        startLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        startLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(startLabel);
        mainPanel.add(Box.createVerticalStrut(5));
        
        startDatePicker = new CustomCalendar();
        startDatePicker.setPlaceholder("Pilih tanggal mulai");
        startDatePicker.setAlignmentX(Component.LEFT_ALIGNMENT);
        startDatePicker.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        mainPanel.add(startDatePicker);
        mainPanel.add(Box.createVerticalStrut(15));
        
        // end date
        JLabel endLabel = new JLabel("Tanggal Akhir");
        endLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        endLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(endLabel);
        mainPanel.add(Box.createVerticalStrut(5));
        
        endDatePicker = new CustomCalendar();
        endDatePicker.setPlaceholder("Pilih tanggal akhir");
        endDatePicker.setAlignmentX(Component.LEFT_ALIGNMENT);
        endDatePicker.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        mainPanel.add(endDatePicker);
        mainPanel.add(Box.createVerticalStrut(20));
        
        // button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(new Color(236, 240, 241));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        RoundedButton cancelBtn = new RoundedButton();
        cancelBtn.setText("Batal");
        cancelBtn.setPreferredSize(new Dimension(100, 35));
        cancelBtn.setBackground(new Color(189, 195, 199));
        cancelBtn.addActionListener(e -> {
            confirmed = false;
            dispose();
        });
        
        RoundedButton generateBtn = new RoundedButton();
        generateBtn.setText("Generate");
        generateBtn.setPreferredSize(new Dimension(100, 35));
        generateBtn.setBackground(new Color(46, 204, 113));
        generateBtn.addActionListener(e -> {
            if (validateInput()) {
                confirmed = true;
                dispose();
            }
        });
        
        buttonPanel.add(cancelBtn);
        buttonPanel.add(generateBtn);
        mainPanel.add(buttonPanel);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // close on escape key
        getRootPane().registerKeyboardAction(
            e -> dispose(),
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_IN_FOCUSED_WINDOW
        );
    }
    
    private boolean validateInput() {
        if (startDatePicker.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Silakan pilih tanggal mulai", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (endDatePicker.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Silakan pilih tanggal akhir", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // check if start date is before end date
        if (startDatePicker.getDate().after(endDatePicker.getDate())) {
            JOptionPane.showMessageDialog(this, 
                "Tanggal mulai harus sebelum tanggal akhir", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
    
    public String getStartDate() {
        return startDatePicker.getDateSQLString();
    }
    
    public String getEndDate() {
        return endDatePicker.getDateSQLString();
    }
    
    /**
     * Show dialog and return dates if confirmed
     * @return String array [startDate, endDate] in YYYY-MM-DD format, or null if cancelled
     */
    public static String[] showDialog() {
        PopupReportDateRange popup = new PopupReportDateRange();
        popup.setVisible(true);
        
        if (popup.isConfirmed()) {
            return new String[] { popup.getStartDate(), popup.getEndDate() };
        }
        return null;
    }
}
