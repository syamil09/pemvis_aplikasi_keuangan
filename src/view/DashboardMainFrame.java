/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import components.CustomScrollPane;
import components.CustomTable;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import constant.PageMenu;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.HashMap;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Leonovo
 */
public class DashboardMainFrame extends javax.swing.JFrame {
    
    PageMenu selectedMenu = null;
    HashMap<PageMenu, JLabel> pageMenuMap = new HashMap<>();

    /**
     * Creates new form DashboardForm
     */
    public DashboardMainFrame() {
        initComponents();
        initPageMenuMap();
        
        // tampilkan halaman dashboard pertama kal
        setSelectedMenu(PageMenu.DASHBOARD);
        DashboardPage overviewFrame = new DashboardPage();
        showContentFromPanel(overviewFrame.getMainPanel());
        
        initActionPageMenu();
        
        makeMainContentScrollable();
        
    }
    
    private void makeMainContentScrollable() {
        try {
            // Remove panelMainContent from panelMainArea
            panelMainArea.remove(panelMainContent);

            // Create CustomScrollPane directly with panelMainContent (no wrapper)
            CustomScrollPane scrollPane = new CustomScrollPane(panelMainContent);

            // Configure scroll pane
            scrollPane.setScrollSpeed(20);
            scrollPane.setSmoothScrolling(true);
            scrollPane.setBorder(null);
            scrollPane.setOpaque(false);
            scrollPane.getViewport().setOpaque(false);

            // IMPORTANT: Set proper size constraints
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

            // Add back to parent
            panelMainArea.add(scrollPane, BorderLayout.CENTER);

            // Force proper sizing calculation
            panelMainArea.revalidate();
            panelMainArea.repaint();

        } catch (Exception e) {
            System.err.println("Error making content scrollable: " + e.getMessage());
            e.printStackTrace();
        }
    }


    
    private JLabel getMenuLabel(PageMenu menu) {
        return pageMenuMap.get(menu);
    }
    
    private void initActionPageMenu() {

        // Loop untuk memasang listener pada semua menu
        for (PageMenu menu : pageMenuMap.keySet()) {
            JLabel label = getMenuLabel(menu);
            // Set properti awal untuk semua label
            label.setOpaque(true);
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    label.setBackground(new Color(46,204,113));
                    label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    System.out.println("MENU EXIT : "+label.getText());
                    if (label != getMenuLabel(selectedMenu)) {
                        System.out.println("selected menu : "+selectedMenu);
                        label.setBackground(new Color(52,73,94));
                        label.setCursor(Cursor.getDefaultCursor());
                    }
                }
                
                @Override
                public void mousePressed(MouseEvent e) {
                    // clear previous selected menu
                    System.out.println("MENU PRESSED : "+label.getText());
                    removeSelectedMenu();
                    
                    // set active selected menu
                    setSelectedMenu(menu);
                   
                }
               
            });
        }
    }
    
    private void initPageMenuMap() {
        pageMenuMap.put(PageMenu.DASHBOARD, lblDashboard);
        pageMenuMap.put(PageMenu.PENGGUNA, lblPengguna);
        pageMenuMap.put(PageMenu.AKUN, lblAkunCOA);
        pageMenuMap.put(PageMenu.KATEGORI_TRANSAKSI, lblKategoriTransaksi);
        pageMenuMap.put(PageMenu.PELANGGAN, lblPelangganKlien);
        pageMenuMap.put(PageMenu.PROYEK, lblProyek);
        pageMenuMap.put(PageMenu.FAKTUR_PENJUALAN, lblFakturPenjualan);
        pageMenuMap.put(PageMenu.PENGELUARAN, lblPengeluaran);
        pageMenuMap.put(PageMenu.PENERIMAAN, lblPenerimaanLainnya);
        pageMenuMap.put(PageMenu.LABA_RUGI, lblLabaRugi);
        pageMenuMap.put(PageMenu.NERACA, lblNeraca);
        pageMenuMap.put(PageMenu.PIUTANG, lblDaftarPiutang);
        pageMenuMap.put(PageMenu.JURNAL, lblKeseluruhanJurnal);
    }
    
    private void setSelectedMenu(PageMenu menu) {
        selectedMenu = menu;
        getMenuLabel(menu).setBackground(new Color(46,204,113));
        getMenuLabel(menu).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
    
    private void removeSelectedMenu() {
        if (getMenuLabel(selectedMenu) != null) {
            getMenuLabel(selectedMenu).setBackground(new Color(52,73,94));
            getMenuLabel(selectedMenu).setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void showContentFromPanel(JPanel panel) {
    try {
        // Clear current content
        panelMainContent.removeAll();
        panelMainContent.setLayout(new BorderLayout());
        
        // Set proper constraints on the panel
        panel.setMaximumSize(null);
        panel.setPreferredSize(null);
        
        // Add directly without wrapper (the CustomScrollPane handles scrolling)
        panelMainContent.add(panel, BorderLayout.CENTER);
        
        // Refresh in proper order
        panelMainContent.revalidate();
        panelMainContent.repaint();
        panelMainArea.revalidate();
        panelMainArea.repaint();
        
        System.out.println("Content panel loaded successfully");
        
    } catch (Exception e) {
        System.err.println("Error loading content from panel: " + e.getMessage());
        e.printStackTrace();
    }
}
    
    private JPanel extractContentFromFrame(JFrame frame) {
        JPanel extractedPanel = new JPanel(new BorderLayout());
        extractedPanel.setBackground(new Color(236,240,241));
        
        // Ambil semua komponen dari frame
        Container content = frame.getContentPane();
        Component[] components = content.getComponents();
        
        for (Component comp : components) {
            content.remove(comp);
            extractedPanel.add(comp);
        }
        
        frame.dispose(); // Hapus frame karena sudah tidak dibutuhkan
        return extractedPanel;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panelSidebar = new javax.swing.JPanel();
        lblHome = new javax.swing.JLabel();
        lblDashboard = new javax.swing.JLabel();
        lblFormMaster = new javax.swing.JLabel();
        lblPengguna = new javax.swing.JLabel();
        lblAkunCOA = new javax.swing.JLabel();
        lblKategoriTransaksi = new javax.swing.JLabel();
        lblPelangganKlien = new javax.swing.JLabel();
        lblProyek = new javax.swing.JLabel();
        lblTransaksi = new javax.swing.JLabel();
        lblFakturPenjualan = new javax.swing.JLabel();
        lblPengeluaran = new javax.swing.JLabel();
        lblPenerimaanLainnya = new javax.swing.JLabel();
        lblLaporan = new javax.swing.JLabel();
        lblLabaRugi = new javax.swing.JLabel();
        lblNeraca = new javax.swing.JLabel();
        lblDaftarPiutang = new javax.swing.JLabel();
        lblKeseluruhanJurnal = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        roundedButton3 = new components.RoundedButton();
        panelMainArea = new javax.swing.JPanel();
        panelTopBar = new javax.swing.JPanel();
        lblWelcome = new javax.swing.JLabel();
        lblUserProfile = new javax.swing.JLabel();
        panelMainContent = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Aplikasi Keuangan");

        panelSidebar.setBackground(new java.awt.Color(52, 73, 94));
        panelSidebar.setLayout(new java.awt.GridBagLayout());

        lblHome.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblHome.setForeground(new java.awt.Color(46, 204, 113));
        lblHome.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHome.setText("Financial");
        lblHome.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 10, 20, 10));
        lblHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblHomeMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        panelSidebar.add(lblHome, gridBagConstraints);

        lblDashboard.setBackground(new java.awt.Color(52, 73, 94));
        lblDashboard.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        lblDashboard.setForeground(new java.awt.Color(236, 240, 241));
        lblDashboard.setText("📈 Dashboard"); // NOI18N
        lblDashboard.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
        lblDashboard.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDashboardMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblDashboardMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblDashboardMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblDashboard, gridBagConstraints);

        lblFormMaster.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblFormMaster.setForeground(new java.awt.Color(149, 165, 166));
        lblFormMaster.setText("FORM MASTER");
        lblFormMaster.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 20, 5, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblFormMaster, gridBagConstraints);

        lblPengguna.setBackground(new java.awt.Color(52, 73, 94));
        lblPengguna.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        lblPengguna.setForeground(new java.awt.Color(236, 240, 241));
        lblPengguna.setText("👤 Pengguna"); // NOI18N
        lblPengguna.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
        lblPengguna.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblPengguna.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPenggunaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblPenggunaMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblPenggunaMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblPengguna, gridBagConstraints);

        lblAkunCOA.setBackground(new java.awt.Color(52, 73, 94));
        lblAkunCOA.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        lblAkunCOA.setForeground(new java.awt.Color(236, 240, 241));
        lblAkunCOA.setText("🧾 Akun (COA)");
        lblAkunCOA.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
        lblAkunCOA.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAkunCOA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAkunCOAMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAkunCOAMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblAkunCOAMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblAkunCOAMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblAkunCOA, gridBagConstraints);

        lblKategoriTransaksi.setBackground(new java.awt.Color(52, 73, 94));
        lblKategoriTransaksi.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        lblKategoriTransaksi.setForeground(new java.awt.Color(236, 240, 241));
        lblKategoriTransaksi.setText("🏷 Kategori Transaksi");
        lblKategoriTransaksi.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
        lblKategoriTransaksi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblKategoriTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblKategoriTransaksiMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblKategoriTransaksi, gridBagConstraints);

        lblPelangganKlien.setBackground(new java.awt.Color(52, 73, 94));
        lblPelangganKlien.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        lblPelangganKlien.setForeground(new java.awt.Color(236, 240, 241));
        lblPelangganKlien.setText("👥 Pelanggan/Klien");
        lblPelangganKlien.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
        lblPelangganKlien.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblPelangganKlien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblPelangganKlienMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblPelangganKlien, gridBagConstraints);

        lblProyek.setBackground(new java.awt.Color(52, 73, 94));
        lblProyek.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        lblProyek.setForeground(new java.awt.Color(236, 240, 241));
        lblProyek.setText("🏗 Proyek");
        lblProyek.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
        lblProyek.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblProyek.setOpaque(true);
        lblProyek.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblProyekMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblProyek, gridBagConstraints);

        lblTransaksi.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblTransaksi.setForeground(new java.awt.Color(149, 165, 166));
        lblTransaksi.setText("TRANSAKSI");
        lblTransaksi.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 20, 5, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblTransaksi, gridBagConstraints);

        lblFakturPenjualan.setBackground(new java.awt.Color(52, 73, 94));
        lblFakturPenjualan.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        lblFakturPenjualan.setForeground(new java.awt.Color(236, 240, 241));
        lblFakturPenjualan.setText("💵 Faktur Penjualan");
        lblFakturPenjualan.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
        lblFakturPenjualan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblFakturPenjualan.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblFakturPenjualan, gridBagConstraints);

        lblPengeluaran.setBackground(new java.awt.Color(52, 73, 94));
        lblPengeluaran.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        lblPengeluaran.setForeground(new java.awt.Color(236, 240, 241));
        lblPengeluaran.setText("💸 Pengeluaran");
        lblPengeluaran.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
        lblPengeluaran.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblPengeluaran.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblPengeluaran, gridBagConstraints);

        lblPenerimaanLainnya.setBackground(new java.awt.Color(52, 73, 94));
        lblPenerimaanLainnya.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        lblPenerimaanLainnya.setForeground(new java.awt.Color(236, 240, 241));
        lblPenerimaanLainnya.setText("🪙 Penerimaan Lainnya");
        lblPenerimaanLainnya.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
        lblPenerimaanLainnya.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblPenerimaanLainnya.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblPenerimaanLainnya, gridBagConstraints);

        lblLaporan.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblLaporan.setForeground(new java.awt.Color(149, 165, 166));
        lblLaporan.setText("LAPORAN");
        lblLaporan.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 20, 5, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblLaporan, gridBagConstraints);

        lblLabaRugi.setBackground(new java.awt.Color(52, 73, 94));
        lblLabaRugi.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        lblLabaRugi.setForeground(new java.awt.Color(236, 240, 241));
        lblLabaRugi.setText("📈 Laba/Rugi");
        lblLabaRugi.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
        lblLabaRugi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLabaRugi.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblLabaRugi, gridBagConstraints);

        lblNeraca.setBackground(new java.awt.Color(52, 73, 94));
        lblNeraca.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        lblNeraca.setForeground(new java.awt.Color(236, 240, 241));
        lblNeraca.setText("📊 Neraca");
        lblNeraca.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
        lblNeraca.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblNeraca.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblNeraca, gridBagConstraints);

        lblDaftarPiutang.setBackground(new java.awt.Color(52, 73, 94));
        lblDaftarPiutang.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        lblDaftarPiutang.setForeground(new java.awt.Color(236, 240, 241));
        lblDaftarPiutang.setText("📒 Daftar Piutang");
        lblDaftarPiutang.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
        lblDaftarPiutang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblDaftarPiutang.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblDaftarPiutang, gridBagConstraints);

        lblKeseluruhanJurnal.setBackground(new java.awt.Color(52, 73, 94));
        lblKeseluruhanJurnal.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        lblKeseluruhanJurnal.setForeground(new java.awt.Color(236, 240, 241));
        lblKeseluruhanJurnal.setText("📚 Keseluruhan Jurnal");
        lblKeseluruhanJurnal.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
        lblKeseluruhanJurnal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblKeseluruhanJurnal.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblKeseluruhanJurnal, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        panelSidebar.add(filler1, gridBagConstraints);

        roundedButton3.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        roundedButton3.setText("❮❮   Logout");
        roundedButton3.setCustomCornerRadius(20);
        roundedButton3.setMaximumSize(new java.awt.Dimension(100, 50));
        roundedButton3.setMinimumSize(new java.awt.Dimension(100, 50));
        roundedButton3.setPreferredSize(new java.awt.Dimension(180, 60));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        panelSidebar.add(roundedButton3, gridBagConstraints);

        panelMainArea.setBackground(new java.awt.Color(236, 240, 241));
        panelMainArea.setLayout(new java.awt.BorderLayout());

        panelTopBar.setBackground(new java.awt.Color(255, 255, 255));
        panelTopBar.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panelTopBar.setPreferredSize(new java.awt.Dimension(750, 60));
        panelTopBar.setLayout(new java.awt.BorderLayout());

        lblWelcome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblWelcome.setText("Selamat datang, Hanif Maulana!");
        panelTopBar.add(lblWelcome, java.awt.BorderLayout.CENTER);

        lblUserProfile.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblUserProfile.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUserProfile.setText("👤 ");
        lblUserProfile.setToolTipText("Profil Pengguna");
        panelTopBar.add(lblUserProfile, java.awt.BorderLayout.EAST);

        panelMainArea.add(panelTopBar, java.awt.BorderLayout.NORTH);

        panelMainContent.setBackground(new java.awt.Color(236, 240, 241));
        panelMainContent.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));

        javax.swing.GroupLayout panelMainContentLayout = new javax.swing.GroupLayout(panelMainContent);
        panelMainContent.setLayout(panelMainContentLayout);
        panelMainContentLayout.setHorizontalGroup(
            panelMainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 910, Short.MAX_VALUE)
        );
        panelMainContentLayout.setVerticalGroup(
            panelMainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        panelMainArea.add(panelMainContent, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1200, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panelSidebar, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, 0)
                    .addComponent(panelMainArea, javax.swing.GroupLayout.PREFERRED_SIZE, 950, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panelSidebar, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(panelMainArea, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblPenggunaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPenggunaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblPenggunaMouseEntered

    private void lblAkunCOAMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAkunCOAMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblAkunCOAMouseEntered

    private void lblAkunCOAMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAkunCOAMouseExited

    }//GEN-LAST:event_lblAkunCOAMouseExited

    private void lblPenggunaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPenggunaMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_lblPenggunaMouseClicked

    private void lblHomeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHomeMousePressed
        // TODO add your handling code here:
        removeSelectedMenu();
    }//GEN-LAST:event_lblHomeMousePressed

    private void lblDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDashboardMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblDashboardMouseClicked

    private void lblDashboardMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDashboardMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblDashboardMouseEntered

    private void lblKategoriTransaksiMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblKategoriTransaksiMousePressed
        // TODO add your handling code here:
        MasterTransactionCategoryPage transactionCategoryFrame = new MasterTransactionCategoryPage();
        showContentFromPanel(transactionCategoryFrame.getMainPanel());
    }//GEN-LAST:event_lblKategoriTransaksiMousePressed

    private void lblDashboardMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDashboardMousePressed
        // TODO add your handling code here:
        DashboardPage overviewFrame = new DashboardPage();
        showContentFromPanel(overviewFrame.getMainPanel());
    }//GEN-LAST:event_lblDashboardMousePressed

    private void lblPenggunaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPenggunaMousePressed
        // TODO add your handling code here:
        MasterUserPage userFrame = new MasterUserPage();
        showContentFromPanel(userFrame.getMainPanel());
    }//GEN-LAST:event_lblPenggunaMousePressed

    private void lblAkunCOAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAkunCOAMouseClicked
        // TODO add your handling code here:
        MasterAccountPage accountFrame = new MasterAccountPage();
        showContentFromPanel(accountFrame.getMainPanel());
    }//GEN-LAST:event_lblAkunCOAMouseClicked

    private void lblAkunCOAMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAkunCOAMousePressed
        // TODO add your handling code here:
        MasterAccountPage accountFrame = new MasterAccountPage();
        showContentFromPanel(accountFrame.getMainPanel());
    }//GEN-LAST:event_lblAkunCOAMousePressed

    private void lblPelangganKlienMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPelangganKlienMousePressed
        // TODO add your handling code here:
        MasterAccountPage accountFrame = new MasterAccountPage();
        showContentFromPanel(accountFrame.getMainPanel());
    }//GEN-LAST:event_lblPelangganKlienMousePressed

    private void lblProyekMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProyekMousePressed
        // TODO add your handling code here:
        MasterProjectPage projectFrame = new MasterProjectPage();
        showContentFromPanel(projectFrame.getMainPanel());
    }//GEN-LAST:event_lblProyekMousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DashboardMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DashboardMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DashboardMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashboardMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DashboardMainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel lblAkunCOA;
    private javax.swing.JLabel lblDaftarPiutang;
    private javax.swing.JLabel lblDashboard;
    private javax.swing.JLabel lblFakturPenjualan;
    private javax.swing.JLabel lblFormMaster;
    private javax.swing.JLabel lblHome;
    private javax.swing.JLabel lblKategoriTransaksi;
    private javax.swing.JLabel lblKeseluruhanJurnal;
    private javax.swing.JLabel lblLabaRugi;
    private javax.swing.JLabel lblLaporan;
    private javax.swing.JLabel lblNeraca;
    private javax.swing.JLabel lblPelangganKlien;
    private javax.swing.JLabel lblPenerimaanLainnya;
    private javax.swing.JLabel lblPengeluaran;
    private javax.swing.JLabel lblPengguna;
    private javax.swing.JLabel lblProyek;
    private javax.swing.JLabel lblTransaksi;
    private javax.swing.JLabel lblUserProfile;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JPanel panelMainArea;
    private javax.swing.JPanel panelMainContent;
    private javax.swing.JPanel panelSidebar;
    private javax.swing.JPanel panelTopBar;
    private components.RoundedButton roundedButton3;
    // End of variables declaration//GEN-END:variables

}
