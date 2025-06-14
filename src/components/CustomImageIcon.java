package components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.URL;

/**
 * CustomImageIcon - Reusable image component with automatic scaling
 * Compatible with NetBeans GUI Builder
 */
public class CustomImageIcon extends JLabel {
    
    // Properties for NetBeans GUI Builder
    private String imageIconPath = "";
    private int imageIconWidth = 50;
    private int imageIconHeight = 50;
    private ScaleMode imageIconScaleMode = ScaleMode.FIT;
    private boolean imageIconMaintainAspectRatio = true;
    private Color imageIconBackgroundColor = Color.WHITE;
    private boolean imageIconShowBorder = false;
    private Color imageIconBorderColor = Color.GRAY;
    private int imageIconBorderWidth = 1;
    
    // Original image storage
    private BufferedImage originalImage;
    private BufferedImage scaledImage;
    
    // Scale mode options
    public enum ScaleMode {
        FIT,        // Fit image within bounds maintaining aspect ratio
        FILL,       // Fill the entire area (may crop)
        STRETCH,    // Stretch to exact dimensions (may distort)
        CENTER      // Center image without scaling
    }
    
    public CustomImageIcon() {
        super();
        initializeComponent();
    }
    
    public CustomImageIcon(String imageIconPath) {
        super();
        this.imageIconPath = imageIconPath;
        initializeComponent();
        loadImage();
    }
    
    public CustomImageIcon(String imageIconPath, int width, int height) {
        super();
        this.imageIconPath = imageIconPath;
        this.imageIconWidth = width;
        this.imageIconHeight = height;
        initializeComponent();
        loadImage();
    }
    
    private void initializeComponent() {
        setOpaque(true);
        setBackground(imageIconBackgroundColor);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setPreferredSize(new Dimension(imageIconWidth, imageIconHeight));
        
        if (imageIconShowBorder) {
            setBorder(BorderFactory.createLineBorder(imageIconBorderColor, imageIconBorderWidth));
        }
    }
    
    // NetBeans GUI Builder compatible properties
    
    public String getImageIconPath() {
        return imageIconPath;
    }
    
    public void setImageIconPath(String imageIconPath) {
        this.imageIconPath = imageIconPath;
        loadImage();
        repaint();
    }
    
    public int getImageIconWidth() {
        return imageIconWidth;
    }
    
    public void setImageIconWidth(int imageIconWidth) {
        this.imageIconWidth = imageIconWidth;
        setPreferredSize(new Dimension(imageIconWidth, imageIconHeight));
        updateScaledImage();
        repaint();
    }
    
    public int getImageIconHeight() {
        return imageIconHeight;
    }
    
    public void setImageIconHeight(int imageIconHeight) {
        this.imageIconHeight = imageIconHeight;
        setPreferredSize(new Dimension(imageIconWidth, imageIconHeight));
        updateScaledImage();
        repaint();
    }
    
    public ScaleMode getImageIconScaleMode() {
        return imageIconScaleMode;
    }
    
    public void setImageIconScaleMode(ScaleMode imageIconScaleMode) {
        this.imageIconScaleMode = imageIconScaleMode;
        updateScaledImage();
        repaint();
    }
    
    public boolean isImageIconMaintainAspectRatio() {
        return imageIconMaintainAspectRatio;
    }
    
    public void setImageIconMaintainAspectRatio(boolean imageIconMaintainAspectRatio) {
        this.imageIconMaintainAspectRatio = imageIconMaintainAspectRatio;
        updateScaledImage();
        repaint();
    }
    
    public Color getImageIconBackgroundColor() {
        return imageIconBackgroundColor;
    }
    
    public void setImageIconBackgroundColor(Color imageIconBackgroundColor) {
        this.imageIconBackgroundColor = imageIconBackgroundColor;
        super.setBackground(imageIconBackgroundColor);
        repaint();
    }
    
    public boolean isImageIconShowBorder() {
        return imageIconShowBorder;
    }
    
    public void setImageIconShowBorder(boolean imageIconShowBorder) {
        this.imageIconShowBorder = imageIconShowBorder;
        updateBorder();
    }
    
    public Color getImageIconBorderColor() {
        return imageIconBorderColor;
    }
    
    public void setImageIconBorderColor(Color imageIconBorderColor) {
        this.imageIconBorderColor = imageIconBorderColor;
        updateBorder();
    }
    
    public int getImageIconBorderWidth() {
        return imageIconBorderWidth;
    }
    
    public void setImageIconBorderWidth(int imageIconBorderWidth) {
        this.imageIconBorderWidth = imageIconBorderWidth;
        updateBorder();
    }
    
    private void updateBorder() {
        if (imageIconShowBorder) {
            setBorder(BorderFactory.createLineBorder(imageIconBorderColor, imageIconBorderWidth));
        } else {
            setBorder(null);
        }
        repaint();
    }
    
    // Image loading methods
    private void loadImage() {
        if (imageIconPath == null || imageIconPath.trim().isEmpty()) {
            originalImage = null;
            scaledImage = null;
            setIcon(null);
            return;
        }
        
        try {
            // Try to load from file path
            File file = new File(imageIconPath);
            if (file.exists()) {
                originalImage = ImageIO.read(file);
            } else {
                // Try to load from resources
                URL resource = getClass().getClassLoader().getResource(imageIconPath);
                if (resource != null) {
                    originalImage = ImageIO.read(resource);
                } else {
                    // Try as absolute path or URL
                    originalImage = ImageIO.read(new URL(imageIconPath));
                }
            }
            
            updateScaledImage();
            
        } catch (IOException e) {
            System.err.println("Failed to load image: " + imageIconPath);
            originalImage = null;
            scaledImage = null;
            setIcon(null);
        }
    }
    
    private void updateScaledImage() {
        if (originalImage == null) {
            setIcon(null);
            return;
        }
        
        Dimension targetSize = new Dimension(imageIconWidth, imageIconHeight);
        scaledImage = scaleImage(originalImage, targetSize);
        
        if (scaledImage != null) {
            setIcon(new ImageIcon(scaledImage));
        }
    }
    
    private BufferedImage scaleImage(BufferedImage original, Dimension targetSize) {
        if (original == null) return null;
        
        int targetWidth = targetSize.width;
        int targetHeight = targetSize.height;
        int originalWidth = original.getWidth();
        int originalHeight = original.getHeight();
        
        // Calculate dimensions based on scale mode
        switch (imageIconScaleMode) {
            case FIT:
                if (imageIconMaintainAspectRatio) {
                    double scaleX = (double) targetWidth / originalWidth;
                    double scaleY = (double) targetHeight / originalHeight;
                    double scale = Math.min(scaleX, scaleY);
                    targetWidth = (int) (originalWidth * scale);
                    targetHeight = (int) (originalHeight * scale);
                }
                break;
                
            case FILL:
                if (imageIconMaintainAspectRatio) {
                    double scaleX = (double) targetWidth / originalWidth;
                    double scaleY = (double) targetHeight / originalHeight;
                    double scale = Math.max(scaleX, scaleY);
                    targetWidth = (int) (originalWidth * scale);
                    targetHeight = (int) (originalHeight * scale);
                }
                break;
                
            case CENTER:
                targetWidth = originalWidth;
                targetHeight = originalHeight;
                break;
                
            case STRETCH:
                // Use target dimensions as-is
                break;
        }
        
        // Create scaled image
        BufferedImage scaledImage = new BufferedImage(
            this.imageIconWidth, this.imageIconHeight, BufferedImage.TYPE_INT_ARGB
        );
        
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Fill background
        g2d.setColor(imageIconBackgroundColor);
        g2d.fillRect(0, 0, this.imageIconWidth, this.imageIconHeight);
        
        // Calculate position for centering
        int x = (this.imageIconWidth - targetWidth) / 2;
        int y = (this.imageIconHeight - targetHeight) / 2;
        
        // Draw scaled image
        g2d.drawImage(original, x, y, targetWidth, targetHeight, null);
        g2d.dispose();
        
        return scaledImage;
    }
    
    // Utility methods
    public void loadImageFromFile(File file) {
        if (file != null && file.exists()) {
            setImageIconPath(file.getAbsolutePath());
        }
    }
    
    public void loadImageFromResource(String resourcePath) {
        URL resource = getClass().getClassLoader().getResource(resourcePath);
        if (resource != null) {
            setImageIconPath(resource.toString());
        }
    }
    
    public void setImageIconSize(int width, int height) {
        this.imageIconWidth = width;
        this.imageIconHeight = height;
        setPreferredSize(new Dimension(width, height));
        updateScaledImage();
        repaint();
    }
    
    public BufferedImage getOriginalImage() {
        return originalImage;
    }
    
    public BufferedImage getScaledImage() {
        return scaledImage;
    }
    
    // Override getPreferredSize to ensure proper sizing
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(imageIconWidth, imageIconHeight);
    }
    
    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }
    
    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }
    
    // Demo main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("CustomImageIcon Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new GridLayout(2, 3, 10, 10));
            frame.getContentPane().setBackground(Color.WHITE);
            
            // Demo different scale modes
            CustomImageIcon icon1 = new CustomImageIcon();
            icon1.setImageIconPath("images/unindra.jpg");
            icon1.setImageIconSize(150, 150);
            icon1.setImageIconScaleMode(ScaleMode.FIT);
            icon1.setImageIconShowBorder(true);
            frame.add(createDemoPanel("FIT Mode", icon1));
            
            CustomImageIcon icon2 = new CustomImageIcon();
            icon2.setImageIconPath("images/unindra.jpg");
            icon2.setImageIconSize(150, 150);
            icon2.setImageIconScaleMode(ScaleMode.FILL);
            icon2.setImageIconShowBorder(true);
            frame.add(createDemoPanel("FILL Mode", icon2));
            
            CustomImageIcon icon3 = new CustomImageIcon();
            icon3.setImageIconPath("images/unindra.jpg");
            icon3.setImageIconSize(150, 150);
            icon3.setImageIconScaleMode(ScaleMode.STRETCH);
            icon3.setImageIconShowBorder(true);
            frame.add(createDemoPanel("STRETCH Mode", icon3));
            
            CustomImageIcon icon4 = new CustomImageIcon();
            icon4.setImageIconPath("images/unindra.jpg");
            icon4.setImageIconSize(150, 150);
            icon4.setImageIconScaleMode(ScaleMode.CENTER);
            icon4.setImageIconShowBorder(true);
            frame.add(createDemoPanel("CENTER Mode", icon4));
            
            CustomImageIcon icon5 = new CustomImageIcon();
//            icon5.setImagePath("https://via.placeholder.com/300x200/FFEAA7/000000?text=Custom");
//            icon5.setImageSize(150, 150);
//            icon5.setBackground(new Color(240, 240, 240));
//            icon5.setBorderColor(Color.BLUE);
//            icon5.setBorderWidth(3);
//            icon5.setShowBorder(true);
//            frame.add(createDemoPanel("Custom Style", icon5));
//            
//            // Error handling demo
//            CustomImageIcon icon6 = new CustomImageIcon();
//            icon6.setImagePath("nonexistent.jpg");
//            icon6.setImageSize(150, 150);
//            icon6.setBackground(Color.LIGHT_GRAY);
//            icon6.setShowBorder(true);
//            frame.add(createDemoPanel("Error Handling", icon6));
            
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
    
    private static JPanel createDemoPanel(String title, CustomImageIcon icon) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(icon, BorderLayout.CENTER);
        return panel;
    }
}