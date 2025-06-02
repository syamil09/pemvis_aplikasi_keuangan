/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FinanciaSidebar extends JFrame {
    
    public FinanciaSidebar() {
        setTitle("Financia Sidebar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(280, 600);
        setLocationRelativeTo(null);
        
        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Header
        JPanel headerPanel = createHeader();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Content panel with menu and user profile
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        
        // Menu panel
        JPanel menuPanel = createMenuPanel();
        JScrollPane scrollPane = new JScrollPane(menuPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        // User profile at bottom
        JPanel userPanel = createUserPanel();
        contentPanel.add(userPanel, BorderLayout.SOUTH);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(new Color(139, 195, 74));
        header.setBorder(new EmptyBorder(12, 16, 12, 16));
        
        // Hamburger menu icon
        JLabel hamburgerIcon = new JLabel("☰");
        hamburgerIcon.setFont(new Font("Arial", Font.PLAIN, 18));
        hamburgerIcon.setForeground(Color.WHITE);
        hamburgerIcon.setBorder(new EmptyBorder(0, 0, 0, 12));
        
        // Title
        JLabel title = new JLabel("Financia");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        
        header.add(hamburgerIcon);
        header.add(title);
        return header;
    }
    
    private JPanel createMenuPanel() {
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(Color.WHITE);
        menu.setBorder(new EmptyBorder(16, 0, 16, 0));
        
        // FORM MASTER section
        Object[][] formMasterItems = {
            {"👤", "Pengguna", new Color(76, 175, 80)},
            {"💰", "Akun (COA)", new Color(255, 193, 7)},
            {"🏷️", "Kategori Transaksi", new Color(255, 152, 0)},
            {"👥", "Pelanggan/Klien", new Color(33, 150, 243)},
            {"📋", "Proyek", new Color(233, 30, 99)}
        };
        menu.add(createMenuSection("FORM MASTER", formMasterItems));
        menu.add(Box.createVerticalStrut(20));
        
        // TRANSAKSI section  
        Object[][] transaksiItems = {
            {"📄", "Faktur Penjualan", new Color(255, 193, 7)},
            {"💸", "Pengeluaran", new Color(76, 175, 80)},
            {"📊", "Penerimaan Lainnya", new Color(33, 150, 243)}
        };
        menu.add(createMenuSection("TRANSAKSI", transaksiItems));
        menu.add(Box.createVerticalStrut(20));
        
        // LAPORAN section
        Object[][] laporanItems = {
            {"📈", "Laba/Rugi", new Color(156, 39, 176)},
            {"📊", "Neraca", new Color(156, 39, 176)},
            {"📋", "Daftar Piutang", new Color(233, 30, 99)},
            {"📚", "Keseluruhan Jurnal", new Color(76, 175, 80)}
        };
        menu.add(createMenuSection("LAPORAN", laporanItems));
        
        return menu;
    }
    
    private JPanel createMenuSection(String sectionTitle, Object[][] menuItems) {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(Color.WHITE);
        section.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Section header
        JLabel sectionLabel = new JLabel(sectionTitle);
        sectionLabel.setFont(new Font("Arial", Font.BOLD, 11));
        sectionLabel.setForeground(new Color(150, 150, 150));
        sectionLabel.setBorder(new EmptyBorder(0, 20, 8, 20));
        sectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        section.add(sectionLabel);
        
        // Menu items
        for (Object[] item : menuItems) {
            JPanel menuItem = createMenuItem((String)item[0], (String)item[1], (Color)item[2]);
            section.add(menuItem);
        }
        
        return section;
    }
    
    private JPanel createMenuItem(String iconText, String labelText, Color iconColor) {
        JPanel item = new JPanel(new BorderLayout());
        item.setBackground(Color.WHITE);
        item.setBorder(new EmptyBorder(12, 20, 12, 20));
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, item.getPreferredSize().height));
        
        // Icon panel to control icon appearance
        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        iconPanel.setBackground(Color.WHITE);
        
        // Create colored circle background for icon
        JPanel iconBackground = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(iconColor);
                g2d.fillOval(2, 2, 20, 20);
                g2d.dispose();
            }
        };
        iconBackground.setPreferredSize(new Dimension(24, 24));
        iconBackground.setOpaque(false);
        
        // Icon label with emoji support
        JLabel iconLabel = new JLabel(iconText);
        // Try to use a font that supports emoji
        Font emojiFont = getEmojiSupportedFont();
        iconLabel.setFont(emojiFont);
        iconLabel.setForeground(Color.WHITE);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setVerticalAlignment(SwingConstants.CENTER);
        
        iconBackground.setLayout(new BorderLayout());
        iconBackground.add(iconLabel, BorderLayout.CENTER);
        iconPanel.add(iconBackground);
        iconPanel.setBorder(new EmptyBorder(0, 0, 0, 12));
        
        // Text
        JLabel textLabel = new JLabel(labelText);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        textLabel.setForeground(new Color(80, 80, 80));
        
        item.add(iconPanel, BorderLayout.WEST);
        item.add(textLabel, BorderLayout.CENTER);
        
        // Add hover effect
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                item.setBackground(new Color(245, 245, 245));
                iconPanel.setBackground(new Color(245, 245, 245));
                item.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                item.setBackground(Color.WHITE);
                iconPanel.setBackground(Color.WHITE);
                item.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Clicked: " + labelText);
                JOptionPane.showMessageDialog(item, "You clicked: " + labelText);
            }
        });
        
        return item;
    }
    
    private JPanel createUserPanel() {
        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.setBackground(new Color(245, 245, 245));
        userPanel.setBorder(new EmptyBorder(16, 20, 16, 20));
        
        // User avatar (circular)
        JPanel avatarPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw circular background
                g2d.setColor(new Color(76, 175, 80));
                g2d.fillOval(0, 0, 40, 40);
                
                // Draw user emoji
                g2d.setColor(Color.WHITE);
                Font emojiFont = getEmojiSupportedFont();
                g2d.setFont(emojiFont);
                FontMetrics fm = g2d.getFontMetrics();
                String text = "👤";
                int x = (40 - fm.stringWidth(text)) / 2;
                int y = ((40 - fm.getHeight()) / 2) + fm.getAscent();
                g2d.drawString(text, x, y);
                
                g2d.dispose();
            }
        };
        avatarPanel.setPreferredSize(new Dimension(40, 40));
        avatarPanel.setOpaque(false);
        
        // User info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(245, 245, 245));
        infoPanel.setBorder(new EmptyBorder(0, 12, 0, 0));
        
        JLabel nameLabel = new JLabel("Ari Pratama");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setForeground(new Color(80, 80, 80));
        
        JLabel roleLabel = new JLabel("Project Manager");
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        roleLabel.setForeground(new Color(120, 120, 120));
        
        infoPanel.add(nameLabel);
        infoPanel.add(roleLabel);
        
        // Settings icon with emoji
        JLabel settingsIcon = new JLabel("⚙️");
        Font emojiFont = getEmojiSupportedFont();
        settingsIcon.setFont(emojiFont);
        settingsIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        settingsIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(userPanel, "Settings clicked");
            }
        });
        
        userPanel.add(avatarPanel, BorderLayout.WEST);
        userPanel.add(infoPanel, BorderLayout.CENTER);
        userPanel.add(settingsIcon, BorderLayout.EAST);
        
        return userPanel;
    }
    
    // Method to get a font that supports emoji
    private Font getEmojiSupportedFont() {
        // Try different fonts that support emoji
        String[] emojiSupportedFonts = {
            "Segoe UI Emoji",    // Windows
            "Apple Color Emoji", // macOS
            "Noto Color Emoji",  // Linux
            "Twemoji",           // Twitter emoji
            "Symbola",           // Fallback
            "Arial Unicode MS",  // Fallback
            "Lucida Grande",     // macOS fallback
            "Dialog"             // Java default
        };
        
//        for (String fontName : emojiSupportedFonts) {
//            Font font = new Font(fontName, Font.PLAIN, 14);
//            if (font.canDisplay('👤')) { // Test with a simple emoji
//                return font;
//            }
//        }
        
        // If no emoji font found, return default
        return new Font("Dialog", Font.PLAIN, 14);
    }
    
    public static void main(String[] args) {
        // Enable anti-aliasing for better emoji rendering
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        
        SwingUtilities.invokeLater(() -> {
            new FinanciaSidebar().setVisible(true);
        });
    }
}