package components;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * RoundedCurrencyField - TextField dengan auto format currency
 * Extends dari RoundedTextField dengan fitur format otomatis
 */
public class RoundedCurrencyField extends RoundedTextField {
    
    private DecimalFormat formatter;
    private boolean isFormatting = false;
    private String lastValidText = "";
    
    public RoundedCurrencyField() {
        super();
        initCurrencyField();
    }
    
    public RoundedCurrencyField(String placeholder) {
        super(placeholder);
        initCurrencyField();
    }
    
    private void initCurrencyField() {
        // Setup formatter untuk Indonesia (titik sebagai pemisah ribuan)
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("id", "ID"));
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        
        formatter = new DecimalFormat("#,###", symbols);
        
        // Set initial text
        setText("0");
        
        // Setup key listener untuk real-time formatting
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                
                // Allow only digits
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume();
                    return;
                }
                
                // Handle digit input
                if (Character.isDigit(c)) {
                    e.consume(); // Prevent default behavior
                    
                    SwingUtilities.invokeLater(() -> {
                        String currentText = getText();
                        String digitsOnly = currentText.replaceAll("[^0-9]", "");
                        
                        // Add new digit
                        digitsOnly += c;
                        
                        try {
                            long number = Long.parseLong(digitsOnly);
                            String formattedText = formatNumber(number);
                            
                            isFormatting = true;
                            setText(formattedText);
                            setCaretPosition(formattedText.length());
                            isFormatting = false;
                            
                        } catch (NumberFormatException ex) {
                            // Ignore if number too large
                        }
                    });
                }
            }
            
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                    handleBackspace();
                } else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    e.consume();
                    handleDelete();
                }
            }
        });
    }
    
    private void handleBackspace() {
        String currentText = getText();
        String digitsOnly = currentText.replaceAll("[^0-9]", "");
        
        if (digitsOnly.length() <= 1) {
            isFormatting = true;
            setText("0");
            SwingUtilities.invokeLater(() -> {
                setCaretPosition(1);
                isFormatting = false;
            });
            return;
        }
        
        // Remove last digit
        digitsOnly = digitsOnly.substring(0, digitsOnly.length() - 1);
        
        try {
            long number = Long.parseLong(digitsOnly);
            String formattedText = formatNumber(number);
            
            isFormatting = true;
            setText(formattedText);
            SwingUtilities.invokeLater(() -> {
                setCaretPosition(formattedText.length());
                isFormatting = false;
            });
            
        } catch (NumberFormatException e) {
            isFormatting = true;
            setText("0");
            SwingUtilities.invokeLater(() -> {
                setCaretPosition(1);
                isFormatting = false;
            });
        }
    }
    
    private void handleDelete() {
        // Same as backspace for this implementation
        handleBackspace();
    }
    

    
    private String formatNumber(long number) {
        if (number == 0) {
            return "0";
        }
        return formatter.format(number);
    }
    
    /**
     * Get numeric value without formatting
     * @return long value of the input
     */
    public long getNumericValue() {
        try {
            String digitsOnly = getText().replaceAll("[^0-9]", "");
            return digitsOnly.isEmpty() ? 0 : Long.parseLong(digitsOnly);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    /**
     * Set numeric value and format it
     * @param value numeric value to set
     */
    public void setNumericValue(long value) {
        String formattedText = formatNumber(value);
        isFormatting = true;
        setText(formattedText);
        SwingUtilities.invokeLater(() -> {
            setCaretPosition(formattedText.length());
            isFormatting = false;
        });
        lastValidText = formattedText;
    }
    
    /**
     * Get value as double
     * @return double value
     */
    public double getDoubleValue() {
        return (double) getNumericValue();
    }
    
    /**
     * Set double value
     * @param value double value to set
     */
    public void setDoubleValue(double value) {
        setNumericValue((long) value);
    }
    
    /**
     * Clear the field
     */
    public void clear() {
        setNumericValue(0);
    }
    

    
    // Test method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("RoundedCurrencyField Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new java.awt.FlowLayout());
            
            RoundedCurrencyField currencyField = new RoundedCurrencyField("Masukkan jumlah...");
            currencyField.setPreferredSize(new java.awt.Dimension(200, 40));
            
            JButton getValueBtn = new JButton("Get Value");
            getValueBtn.addActionListener(e -> {
                JOptionPane.showMessageDialog(frame, 
                    "Value: " + currencyField.getNumericValue() + "\n" +
                    "Formatted: " + currencyField.getText());
            });
            
            JButton setValueBtn = new JButton("Set 1500000");
            setValueBtn.addActionListener(e -> {
                currencyField.setNumericValue(1500000);
            });
            
            JButton clearBtn = new JButton("Clear");
            clearBtn.addActionListener(e -> {
                currencyField.clear();
            });
            
            frame.add(new JLabel("Currency Field:"));
            frame.add(currencyField);
            frame.add(getValueBtn);
            frame.add(setValueBtn);
            frame.add(clearBtn);
            
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            
            // Focus on currency field
            currencyField.requestFocus();
        });
    }
}