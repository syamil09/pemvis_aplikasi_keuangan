/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

/**
 *
 * @author Leonovo
 */

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import javax.swing.JScrollPane;

/**
 * Modern Custom JScrollPane with clean design and modern scrollbars
 */
public class CustomScrollPane extends JScrollPane {
    
    // Color scheme
    private static final Color TRACK_COLOR = new Color(248, 249, 250);
    private static final Color THUMB_COLOR = new Color(189, 195, 199);
    private static final Color THUMB_HOVER_COLOR = new Color(149, 165, 166);
    private static final Color THUMB_PRESSED_COLOR = new Color(127, 140, 141);
    
    // Dimensions
    private static final int SCROLLBAR_WIDTH = 8;
    private static final int THUMB_MARGIN = 2;
    
    public CustomScrollPane() {
        super();
        initializeModernStyle();
    }
    
    public CustomScrollPane(Component view) {
        super(view);
        initializeModernStyle();
    }
    
    public CustomScrollPane(int vsbPolicy, int hsbPolicy) {
        super(vsbPolicy, hsbPolicy);
        initializeModernStyle();
    }
    
    public CustomScrollPane(Component view, int vsbPolicy, int hsbPolicy) {
        super(view, vsbPolicy, hsbPolicy);
        initializeModernStyle();
    }
    
    private void initializeModernStyle() {
        // Remove default border
        setBorder(null);
        
        // Set transparent background
        setOpaque(false);
        getViewport().setOpaque(false);
        
        // Configure scrollbars
        setVerticalScrollBar(new ModernScrollBar(JScrollBar.VERTICAL));
        setHorizontalScrollBar(new ModernScrollBar(JScrollBar.HORIZONTAL));
        
        // Set policies
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        // Smooth scrolling
        getVerticalScrollBar().setUnitIncrement(16);
        getHorizontalScrollBar().setUnitIncrement(16);
        getVerticalScrollBar().setBlockIncrement(64);
        getHorizontalScrollBar().setBlockIncrement(64);
        
        // Remove corner
        setCorner(JScrollPane.UPPER_RIGHT_CORNER, null);
        setCorner(JScrollPane.LOWER_RIGHT_CORNER, null);
        setCorner(JScrollPane.UPPER_LEFT_CORNER, null);
        setCorner(JScrollPane.LOWER_LEFT_CORNER, null);
    }
    
    /**
     * Custom Modern ScrollBar
     */
    private static class ModernScrollBar extends JScrollBar {
        
        public ModernScrollBar(int orientation) {
            super(orientation);
            setOpaque(false);
            setPreferredSize(new Dimension(SCROLLBAR_WIDTH, SCROLLBAR_WIDTH));
            setUI(new ModernScrollBarUI());
        }
    }
    
    /**
     * Custom ScrollBar UI with modern flat design
     */
    private static class ModernScrollBarUI extends BasicScrollBarUI {
        
        private boolean isThumbHover = false;
        private boolean isThumbPressed = false;
        
        @Override
        protected void configureScrollBarColors() {
            // Set transparent colors (we'll handle painting ourselves)
            thumbColor = new Color(0, 0, 0, 0);  // Black with 0% opacity (transparent)
            trackColor = new Color(0, 0, 0, 0);  // Black with 0% opacity (transparent)
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
            
            // Paint track background
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
            
            // Calculate thumb bounds with margin
            int x = thumbBounds.x + THUMB_MARGIN;
            int y = thumbBounds.y + THUMB_MARGIN;
            int width = thumbBounds.width - (THUMB_MARGIN * 2);
            int height = thumbBounds.height - (THUMB_MARGIN * 2);
            
            // Choose color based on state
            Color thumbColor;
            if (isThumbPressed) {
                thumbColor = THUMB_PRESSED_COLOR;
            } else if (isThumbHover) {
                thumbColor = THUMB_HOVER_COLOR;
            } else {
                thumbColor = THUMB_COLOR;
            }
            
            // Paint thumb
            g2.setColor(thumbColor);
            g2.fillRoundRect(x, y, width, height, width, width);
            
            g2.dispose();
        }
        
        @Override
        protected void installListeners() {
            super.installListeners();
            
            // Add hover effects
            scrollbar.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isThumbHover = true;
                    scrollbar.repaint();
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    isThumbHover = false;
                    scrollbar.repaint();
                }
                
                @Override
                public void mousePressed(MouseEvent e) {
                    isThumbPressed = true;
                    scrollbar.repaint();
                }
                
                @Override
                public void mouseReleased(MouseEvent e) {
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
    
    // Utility methods for easy customization
    public void setScrollBarWidth(int width) {
        // This would require rebuilding the scrollbars with new width
        // For now, we use the constant SCROLLBAR_WIDTH
    }
    
    public void setSmoothScrolling(boolean enabled) {
        if (enabled) {
            getVerticalScrollBar().setUnitIncrement(16);
            getHorizontalScrollBar().setUnitIncrement(16);
        } else {
            getVerticalScrollBar().setUnitIncrement(1);
            getHorizontalScrollBar().setUnitIncrement(1);
        }
    }
    
    public void setScrollSpeed(int speed) {
        getVerticalScrollBar().setUnitIncrement(speed);
        getHorizontalScrollBar().setUnitIncrement(speed);
        getVerticalScrollBar().setBlockIncrement(speed * 4);
        getHorizontalScrollBar().setBlockIncrement(speed * 4);
    }
}

// Usage Example:
/*
// Replace your JScrollPane with ModernScrollPane
ModernScrollPane modernScrollPane = new ModernScrollPane(yourContentPanel);
modernScrollPane.setScrollSpeed(20); // Optional: adjust scroll speed

// Add to your container
parentPanel.add(modernScrollPane, BorderLayout.CENTER);
*/
