/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Leonovo
 */
public class DashboardForm extends javax.swing.JFrame {
    
    PageMenu selectedMenu = null;

    /**
     * Creates new form DashboardForm
     */
    public DashboardForm() {
        initComponents();
        setHoverEffectMenu();
        
        roundedComboBox1.setBorderColor(new Color(180, 180, 180));
        roundedComboBox1.setFocusBorderColor(new Color(0, 123, 255));
        
        txtUsername.setText("default username");
        txtUsername.setEnabled(false);
        
        
        DefaultTableModel tableModel = new DefaultTableModel();
        Object[] colums = {"ID Pelanggan", "Nama", "Jenis Kelamin", "No. Telepon", "Aksi"};
        tableModel.setColumnIdentifiers(colums);
        tableModel.addRow(new Object[]{"PEL001", "John", "Laki-laki", "0912092109", ""});
        tableModel.addRow(new Object[]{"PEL001", "John", "Laki-laki", "0912092109", ""});
        tableModel.addRow(new Object[]{"PEL001", "John", "Laki-laki", "0912092109", ""});
        tableModel.addRow(new Object[]{"PEL001", "John", "Laki-laki", "0912092109", ""});
        tableModel.addRow(new Object[]{"PEL001", "John", "Laki-laki", "0912092109", ""});
        tableModel.addRow(new Object[]{"PEL001", "John", "Laki-laki", "0912092109", ""});
        tableModel.addRow(new Object[]{"PEL001", "John", "Laki-laki", "0912092109", ""});


        customTable1.setModel(tableModel);
        customTable1.setShowActionButtons(true);
        
        customTable1.setActionButtonListener(new CustomTable.ActionButtonListener() {
            @Override
            public void onEdit(int row, Object[] rowData) {
                JOptionPane.showMessageDialog(null, "Edit User: " + rowData[2]);
            }
            
            @Override
            public void onDelete(int row, Object[] rowData) {
                int result = JOptionPane.showConfirmDialog(null, 
                    "Delete User " + rowData[2] + "?", 
                    "Confirm", 
                    JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    customTable1.removeRow(row);
                }
            }
        });
    }
    
    private void setHoverEffectMenu() {
        // Buat List dari label-label menu
        List<JLabel> menuLabels = Arrays.asList(
            lblPengguna, lblAkunCOA, lblKategoriTransaksi, lblPelangganKlien, lblProyek, lblFakturPenjualan,
            lblPengeluaran, lblPenerimaanLainnya, lblLabaRugi, lblNeraca, lblDaftarPiutang, lblKeseluruhanJurnal
        );

        // Loop untuk memasang listener pada semua menu
        for (JLabel label : menuLabels) {
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
                    label.setBackground(new Color(52,73,94));
                    label.setCursor(Cursor.getDefaultCursor());
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    // Handle klik berdasarkan label yang diklik
                    if (label == lblPengguna) {
                        // Action untuk menu Pengguna
                    } else if (label == lblAkunCOA) {
                        // Action untuk menu Klien
                    }
                }
            });
        }
    }
    private void showContentFromPanel(JPanel panel) {
    try {
        // Clear current content completely
        panelMainContent.removeAll();
        
        // Set layout to BorderLayout for proper content display
        panelMainContent.setLayout(new BorderLayout());
        
        // Create wrapper panel
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setOpaque(false); // Membuat background transparan
        wrapperPanel.add(panel, BorderLayout.CENTER);
        
        // Add to main panel with BorderLayout.CENTER
        panelMainContent.add(wrapperPanel, BorderLayout.CENTER);
        
        // Debug info
        System.out.println("Panel added successfully");
        System.out.println("Component count: " + wrapperPanel.getComponentCount());
        System.out.println("Panel size: " + panel.getPreferredSize());
        
        // Force refresh
        panelMainContent.revalidate();
        panelMainContent.repaint();
        
        // Also refresh parent containers
        panelMainArea.revalidate();
        panelMainArea.repaint();
        this.repaint();
        
    } catch (Exception e) {
        System.err.println("Error loading content from panel: " + e.getMessage());
        e.printStackTrace();
    }
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
        lblKeseluruhanJurnal1 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        panelMainArea = new javax.swing.JPanel();
        panelTopBar = new javax.swing.JPanel();
        lblWelcome = new javax.swing.JLabel();
        lblUserProfile = new javax.swing.JLabel();
        panelMainContent = new javax.swing.JPanel();
        panelHighlight = new javax.swing.JPanel();
        panelHighlightText = new javax.swing.JPanel();
        lblPantauKeuangan = new javax.swing.JLabel();
        lblKelolaSemua = new javax.swing.JLabel();
        lblChartIcon = new javax.swing.JLabel();
        panelPageMenu = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtUsername = new components.RoundedTextField();
        roundedTextField2 = new components.RoundedTextField();
        roundedTextField3 = new components.RoundedTextField();
        roundedComboBox1 = new components.RoundedComboBox();
        roundedButton2 = new components.RoundedButton();
        customTable1 = new components.CustomTable();
        roundedButton1 = new components.RoundedButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Aplikasi Keuangan");

        panelSidebar.setBackground(new java.awt.Color(52, 73, 94));
        panelSidebar.setLayout(new java.awt.GridBagLayout());

        lblHome.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblHome.setForeground(new java.awt.Color(46, 204, 113));
        lblHome.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHome.setText(" Home");
        lblHome.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 10, 20, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        panelSidebar.add(lblHome, gridBagConstraints);

        lblFormMaster.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblFormMaster.setForeground(new java.awt.Color(149, 165, 166));
        lblFormMaster.setText("FORM MASTER");
        lblFormMaster.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 20, 5, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
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
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
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
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAkunCOAMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblAkunCOAMouseExited(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblAkunCOA, gridBagConstraints);

        lblKategoriTransaksi.setBackground(new java.awt.Color(52, 73, 94));
        lblKategoriTransaksi.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        lblKategoriTransaksi.setForeground(new java.awt.Color(236, 240, 241));
        lblKategoriTransaksi.setText("🏷 Kategori Transaksi");
        lblKategoriTransaksi.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
        lblKategoriTransaksi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblKategoriTransaksi, gridBagConstraints);

        lblPelangganKlien.setBackground(new java.awt.Color(52, 73, 94));
        lblPelangganKlien.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        lblPelangganKlien.setForeground(new java.awt.Color(236, 240, 241));
        lblPelangganKlien.setText("👥 Pelanggan/Klien");
        lblPelangganKlien.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
        lblPelangganKlien.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblProyek, gridBagConstraints);

        lblTransaksi.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblTransaksi.setForeground(new java.awt.Color(149, 165, 166));
        lblTransaksi.setText("TRANSAKSI");
        lblTransaksi.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 20, 5, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
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
        gridBagConstraints.gridy = 8;
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
        gridBagConstraints.gridy = 9;
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
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblPenerimaanLainnya, gridBagConstraints);

        lblLaporan.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblLaporan.setForeground(new java.awt.Color(149, 165, 166));
        lblLaporan.setText("LAPORAN");
        lblLaporan.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 20, 5, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
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
        gridBagConstraints.gridy = 12;
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
        gridBagConstraints.gridy = 13;
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
        gridBagConstraints.gridy = 14;
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
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblKeseluruhanJurnal, gridBagConstraints);

        lblKeseluruhanJurnal1.setBackground(new java.awt.Color(52, 73, 94));
        lblKeseluruhanJurnal1.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        lblKeseluruhanJurnal1.setForeground(new java.awt.Color(236, 240, 241));
        lblKeseluruhanJurnal1.setText("📚 Keseluruhan Jurnal");
        lblKeseluruhanJurnal1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
        lblKeseluruhanJurnal1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblKeseluruhanJurnal1.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblKeseluruhanJurnal1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        panelSidebar.add(filler1, gridBagConstraints);

        panelMainArea.setLayout(new java.awt.BorderLayout());

        panelTopBar.setBackground(new java.awt.Color(255, 255, 255));
        panelTopBar.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panelTopBar.setPreferredSize(new java.awt.Dimension(750, 60));
        panelTopBar.setLayout(new java.awt.BorderLayout());

        lblWelcome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblWelcome.setText("Selamat datang, Ari Pratama!");
        panelTopBar.add(lblWelcome, java.awt.BorderLayout.CENTER);

        lblUserProfile.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblUserProfile.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUserProfile.setText("👤 ");
        lblUserProfile.setToolTipText("Profil Pengguna");
        panelTopBar.add(lblUserProfile, java.awt.BorderLayout.EAST);

        panelMainArea.add(panelTopBar, java.awt.BorderLayout.NORTH);

        panelMainContent.setBackground(new java.awt.Color(236, 240, 241));
        panelMainContent.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelHighlight.setBackground(new java.awt.Color(240, 255, 240));
        panelHighlight.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 220, 200)), javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        panelHighlight.setLayout(new java.awt.BorderLayout(20, 0));

        panelHighlightText.setOpaque(false);
        panelHighlightText.setLayout(new javax.swing.BoxLayout(panelHighlightText, javax.swing.BoxLayout.Y_AXIS));

        lblPantauKeuangan.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblPantauKeuangan.setText("Pantau Keuangan Perusahaan dengan Mudah!");
        lblPantauKeuangan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 5, 1));
        panelHighlightText.add(lblPantauKeuangan);

        lblKelolaSemua.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblKelolaSemua.setText("Kelola semua transaksi, laporan, dan data master dalam satu dashboard yang cerah, bersih, dan menyenangkan.");
        panelHighlightText.add(lblKelolaSemua);

        panelHighlight.add(panelHighlightText, java.awt.BorderLayout.CENTER);

        lblChartIcon.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        lblChartIcon.setForeground(new java.awt.Color(46, 204, 113));
        lblChartIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblChartIcon.setText("📊");
        lblChartIcon.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 20));
        panelHighlight.add(lblChartIcon, java.awt.BorderLayout.EAST);

        panelPageMenu.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Master Data Penguna");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Username : ");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Password : ");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Nama Lengkap : ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Role : ");

        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });

        roundedTextField3.setText("roundedTextField3");

        roundedComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Admin", "Finance", "Manager" }));

        roundedButton2.setText("⟳ reset form");
        roundedButton2.setAutoscrolls(true);
        roundedButton2.setCustomColorScheme(components.RoundedButton.ColorScheme.WARNING);
        roundedButton2.setCustomCornerRadius(20);

        customTable1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                customTable1PropertyChange(evt);
            }
        });

        roundedButton1.setText("roundedButton1");

        javax.swing.GroupLayout panelPageMenuLayout = new javax.swing.GroupLayout(panelPageMenu);
        panelPageMenu.setLayout(panelPageMenuLayout);
        panelPageMenuLayout.setHorizontalGroup(
            panelPageMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPageMenuLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(panelPageMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(panelPageMenuLayout.createSequentialGroup()
                        .addGroup(panelPageMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPageMenuLayout.createSequentialGroup()
                                .addGroup(panelPageMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addGap(12, 12, 12)
                                .addGroup(panelPageMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(roundedTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(roundedTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(roundedComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                            .addGroup(panelPageMenuLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(roundedButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelPageMenuLayout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(roundedButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(customTable1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelPageMenuLayout.setVerticalGroup(
            panelPageMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPageMenuLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(17, 17, 17)
                .addGroup(panelPageMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelPageMenuLayout.createSequentialGroup()
                        .addGroup(panelPageMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(panelPageMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(roundedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelPageMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPageMenuLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5))
                            .addGroup(panelPageMenuLayout.createSequentialGroup()
                                .addComponent(roundedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(roundedComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(34, 34, 34)
                        .addComponent(roundedButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(roundedButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(customTable1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(86, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelMainContentLayout = new javax.swing.GroupLayout(panelMainContent);
        panelMainContent.setLayout(panelMainContentLayout);
        panelMainContentLayout.setHorizontalGroup(
            panelMainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMainContentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelHighlight, javax.swing.GroupLayout.DEFAULT_SIZE, 914, Short.MAX_VALUE)
                    .addComponent(panelPageMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelMainContentLayout.setVerticalGroup(
            panelMainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMainContentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelHighlight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelPageMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        lblAkunCOA.setOpaque(true);
        lblAkunCOA.setBackground(Color.red);
    }//GEN-LAST:event_lblAkunCOAMouseEntered

    private void lblAkunCOAMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAkunCOAMouseExited
        lblAkunCOA.setBackground(new Color(52,73,94));
    }//GEN-LAST:event_lblAkunCOAMouseExited

    private void lblPenggunaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPenggunaMouseClicked
        // TODO add your handling code here:
        selectedMenu = PageMenu.PENGGUNA;
        MasterUserForm userFrame = new MasterUserForm();
        showContentFromPanel(userFrame.extractMainPanel());
    }//GEN-LAST:event_lblPenggunaMouseClicked

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void customTable1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_customTable1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_customTable1PropertyChange

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
            java.util.logging.Logger.getLogger(DashboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DashboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DashboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DashboardForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private components.CustomTable customTable1;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblAkunCOA;
    private javax.swing.JLabel lblChartIcon;
    private javax.swing.JLabel lblDaftarPiutang;
    private javax.swing.JLabel lblFakturPenjualan;
    private javax.swing.JLabel lblFormMaster;
    private javax.swing.JLabel lblHome;
    private javax.swing.JLabel lblKategoriTransaksi;
    private javax.swing.JLabel lblKelolaSemua;
    private javax.swing.JLabel lblKeseluruhanJurnal;
    private javax.swing.JLabel lblKeseluruhanJurnal1;
    private javax.swing.JLabel lblLabaRugi;
    private javax.swing.JLabel lblLaporan;
    private javax.swing.JLabel lblNeraca;
    private javax.swing.JLabel lblPantauKeuangan;
    private javax.swing.JLabel lblPelangganKlien;
    private javax.swing.JLabel lblPenerimaanLainnya;
    private javax.swing.JLabel lblPengeluaran;
    private javax.swing.JLabel lblPengguna;
    private javax.swing.JLabel lblProyek;
    private javax.swing.JLabel lblTransaksi;
    private javax.swing.JLabel lblUserProfile;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JPanel panelHighlight;
    private javax.swing.JPanel panelHighlightText;
    private javax.swing.JPanel panelMainArea;
    private javax.swing.JPanel panelMainContent;
    private javax.swing.JPanel panelPageMenu;
    private javax.swing.JPanel panelSidebar;
    private javax.swing.JPanel panelTopBar;
    private components.RoundedButton roundedButton1;
    private components.RoundedButton roundedButton2;
    private components.RoundedComboBox roundedComboBox1;
    private components.RoundedTextField roundedTextField2;
    private components.RoundedTextField roundedTextField3;
    private components.RoundedTextField txtUsername;
    // End of variables declaration//GEN-END:variables

}
