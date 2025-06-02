import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Dashboard extends javax.swing.JFrame {

    /**
     * Creates new form Dashboard
     */
    public Dashboard() {
        initComponents();
        // Custom initialization after initComponents
        customizeComponents();
    }

    private void customizeComponents() {
        // Set frame properties
        setTitle("Financia Dashboard");
        setSize(1200, 700); // Adjusted size for better layout
        setLocationRelativeTo(null); // Center the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Apply custom styles that might be harder to set in the form editor
        // Sidebar styling
        panelSidebar.setBackground(new Color(52, 73, 94)); // Dark slate gray
        lblFinancia.setForeground(new Color(46, 204, 113)); // Green
        lblFinancia.setFont(new Font("Segoe UI", Font.BOLD, 28));

        Color sidebarTextColor = new Color(236, 240, 241); // Light gray/white
        Color sectionHeaderColor = new Color(149, 165, 166); // Slightly darker gray for headers

        lblFormMaster.setForeground(sectionHeaderColor);
        lblFormMaster.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPengguna.setForeground(sidebarTextColor);
        lblAkunCOA.setForeground(sidebarTextColor);
        lblKategoriTransaksi.setForeground(sidebarTextColor);
        lblPelangganKlien.setForeground(sidebarTextColor);
        lblProyek.setForeground(sidebarTextColor);

        lblTransaksi.setForeground(sectionHeaderColor);
        lblTransaksi.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblFakturPenjualan.setForeground(sidebarTextColor);
        lblPengeluaran.setForeground(sidebarTextColor);
        lblPenerimaanLainnya.setForeground(sidebarTextColor);

        lblLaporan.setForeground(sectionHeaderColor);
        lblLaporan.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblLabaRugi.setForeground(sidebarTextColor);
        lblNeraca.setForeground(sidebarTextColor);
        lblDaftarPiutang.setForeground(sidebarTextColor);
        lblKeseluruhanJurnal.setForeground(sidebarTextColor);

        // Main content panel styling
        panelMainContent.setBackground(new Color(236, 240, 241)); // Light gray background

        // Top bar styling
        panelTopBar.setBackground(Color.WHITE);
        panelTopBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220))); // Bottom border
        lblWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUserProfile.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Placeholder for icon

        // Highlight panel styling
        panelHighlight.setBackground(new Color(240, 255, 240)); // Light greenish yellow
        panelHighlight.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 200)), // Subtle border
            new EmptyBorder(20, 20, 20, 20) // Padding
        ));
        lblCeria.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblCeria.setForeground(new Color(52, 73, 94));
        lblPantauKeuangan.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblPantauKeuangan.setForeground(new Color(52, 73, 94));
        lblKelolaSemua.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblKelolaSemua.setForeground(new Color(100, 100, 100));
        lblChartIcon.setFont(new Font("Segoe UI", Font.PLAIN, 48)); // Placeholder for chart icon
        lblChartIcon.setForeground(new Color(46, 204, 113));

        // Adding some padding to sidebar labels
        int topPadding = 5;
        int bottomPadding = 5;
        int leftPadding = 20; // Increased left padding

        lblPengguna.setBorder(new EmptyBorder(topPadding, leftPadding, bottomPadding, 10));
        lblAkunCOA.setBorder(new EmptyBorder(topPadding, leftPadding, bottomPadding, 10));
        lblKategoriTransaksi.setBorder(new EmptyBorder(topPadding, leftPadding, bottomPadding, 10));
        lblPelangganKlien.setBorder(new EmptyBorder(topPadding, leftPadding, bottomPadding, 10));
        lblProyek.setBorder(new EmptyBorder(topPadding, leftPadding, bottomPadding, 10));

        lblFakturPenjualan.setBorder(new EmptyBorder(topPadding, leftPadding, bottomPadding, 10));
        lblPengeluaran.setBorder(new EmptyBorder(topPadding, leftPadding, bottomPadding, 10));
        lblPenerimaanLainnya.setBorder(new EmptyBorder(topPadding, leftPadding, bottomPadding, 10));

        lblLabaRugi.setBorder(new EmptyBorder(topPadding, leftPadding, bottomPadding, 10));
        lblNeraca.setBorder(new EmptyBorder(topPadding, leftPadding, bottomPadding, 10));
        lblDaftarPiutang.setBorder(new EmptyBorder(topPadding, leftPadding, bottomPadding, 10));
        lblKeseluruhanJurnal.setBorder(new EmptyBorder(topPadding, leftPadding, bottomPadding, 10));

        lblFormMaster.setBorder(new EmptyBorder(15, leftPadding, 5, 10));
        lblTransaksi.setBorder(new EmptyBorder(15, leftPadding, 5, 10));
        lblLaporan.setBorder(new EmptyBorder(15, leftPadding, 5, 10));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panelSidebar = new javax.swing.JPanel();
        lblFinancia = new javax.swing.JLabel();
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
        panelMainArea = new javax.swing.JPanel();
        panelTopBar = new javax.swing.JPanel();
        lblWelcome = new javax.swing.JLabel();
        lblUserProfile = new javax.swing.JLabel();
        panelMainContent = new javax.swing.JPanel();
        panelHighlight = new javax.swing.JPanel();
        panelHighlightText = new javax.swing.JPanel();
        lblCeria = new javax.swing.JLabel();
        lblPantauKeuangan = new javax.swing.JLabel();
        lblKelolaSemua = new javax.swing.JLabel();
        lblChartIcon = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1200, 700));

        panelSidebar.setBackground(new java.awt.Color(52, 73, 94));
        panelSidebar.setPreferredSize(new java.awt.Dimension(250, 600));
        panelSidebar.setLayout(new java.awt.GridBagLayout());

        lblFinancia.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblFinancia.setForeground(new java.awt.Color(46, 204, 113));
        lblFinancia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblFinancia.setText(" Financia");
        lblFinancia.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 10, 20, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        panelSidebar.add(lblFinancia, gridBagConstraints);

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

        lblPengguna.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPengguna.setForeground(new java.awt.Color(236, 240, 241));
        lblPengguna.setText("🙎‍♂️ Pengguna"); // Placeholder icon
        lblPengguna.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblPengguna.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblPengguna, gridBagConstraints);

        lblAkunCOA.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblAkunCOA.setForeground(new java.awt.Color(236, 240, 241));
        lblAkunCOA.setText("🧾 Akun (COA)"); // Placeholder icon
        lblAkunCOA.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAkunCOA.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblAkunCOA, gridBagConstraints);

        lblKategoriTransaksi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblKategoriTransaksi.setForeground(new java.awt.Color(236, 240, 241));
        lblKategoriTransaksi.setText("🏷️ Kategori Transaksi"); // Placeholder icon
        lblKategoriTransaksi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblKategoriTransaksi.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblKategoriTransaksi, gridBagConstraints);

        lblPelangganKlien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPelangganKlien.setForeground(new java.awt.Color(236, 240, 241));
        lblPelangganKlien.setText("👥 Pelanggan/Klien"); // Placeholder icon
        lblPelangganKlien.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblPelangganKlien.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblPelangganKlien, gridBagConstraints);

        lblProyek.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblProyek.setForeground(new java.awt.Color(236, 240, 241));
        lblProyek.setText("🏗️ Proyek"); // Placeholder icon
        lblProyek.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblProyek.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
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

        lblFakturPenjualan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblFakturPenjualan.setForeground(new java.awt.Color(236, 240, 241));
        lblFakturPenjualan.setText("💵 Faktur Penjualan"); // Placeholder icon
        lblFakturPenjualan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblFakturPenjualan.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblFakturPenjualan, gridBagConstraints);

        lblPengeluaran.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPengeluaran.setForeground(new java.awt.Color(236, 240, 241));
        lblPengeluaran.setText("💸 Pengeluaran"); // Placeholder icon
        lblPengeluaran.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblPengeluaran.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblPengeluaran, gridBagConstraints);

        lblPenerimaanLainnya.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPenerimaanLainnya.setForeground(new java.awt.Color(236, 240, 241));
        lblPenerimaanLainnya.setText("🪙 Penerimaan Lainnya"); // Placeholder icon
        lblPenerimaanLainnya.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblPenerimaanLainnya.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
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

        lblLabaRugi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblLabaRugi.setForeground(new java.awt.Color(236, 240, 241));
        lblLabaRugi.setText("📈 Laba/Rugi"); // Placeholder icon
        lblLabaRugi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLabaRugi.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblLabaRugi, gridBagConstraints);

        lblNeraca.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNeraca.setForeground(new java.awt.Color(236, 240, 241));
        lblNeraca.setText("📊 Neraca"); // Placeholder icon
        lblNeraca.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblNeraca.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblNeraca, gridBagConstraints);

        lblDaftarPiutang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDaftarPiutang.setForeground(new java.awt.Color(236, 240, 241));
        lblDaftarPiutang.setText("📒 Daftar Piutang"); // Placeholder icon
        lblDaftarPiutang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblDaftarPiutang.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblDaftarPiutang, gridBagConstraints);

        lblKeseluruhanJurnal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblKeseluruhanJurnal.setForeground(new java.awt.Color(236, 240, 241));
        lblKeseluruhanJurnal.setText("📚 Keseluruhan Jurnal"); // Placeholder icon
        lblKeseluruhanJurnal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblKeseluruhanJurnal.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 20, 5, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSidebar.add(lblKeseluruhanJurnal, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        panelSidebar.add(filler1, gridBagConstraints);

        getContentPane().add(panelSidebar, java.awt.BorderLayout.WEST);

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
        lblUserProfile.setText("👤 "); // Placeholder for user icon
        lblUserProfile.setToolTipText("Profil Pengguna");
        panelTopBar.add(lblUserProfile, java.awt.BorderLayout.EAST);

        panelMainArea.add(panelTopBar, java.awt.BorderLayout.NORTH);

        panelMainContent.setBackground(new java.awt.Color(236, 240, 241));
        panelMainContent.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelMainContent.setLayout(new java.awt.GridBagLayout());

        panelHighlight.setBackground(new java.awt.Color(240, 255, 240));
        panelHighlight.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 220, 200)));
        panelHighlight.setLayout(new java.awt.BorderLayout(20, 0));

        panelHighlightText.setOpaque(false);
        panelHighlightText.setLayout(new javax.swing.BoxLayout(panelHighlightText, javax.swing.BoxLayout.Y_AXIS));

        lblCeria.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCeria.setText("⭐ Ceria   Keuangan Mudah");
        lblCeria.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 10, 1));
        panelHighlightText.add(lblCeria);

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
        lblChartIcon.setText("📊"); // Placeholder for chart icon
        lblChartIcon.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 20));
        panelHighlight.add(lblChartIcon, java.awt.BorderLayout.EAST);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        panelMainContent.add(panelHighlight, gridBagConstraints);
        // Filler to push highlight panel to the top
        javax.swing.Box.Filler fillerContent = new javax.swing.Box.Filler(new java.awt.Dimension(0,0), new java.awt.Dimension(0,0), new java.awt.Dimension(0, Short.MAX_VALUE));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        panelMainContent.add(fillerContent, gridBagConstraints);


        panelMainArea.add(panelMainContent, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelMainArea, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>                        

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
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel lblAkunCOA;
    private javax.swing.JLabel lblCeria;
    private javax.swing.JLabel lblChartIcon;
    private javax.swing.JLabel lblDaftarPiutang;
    private javax.swing.JLabel lblFakturPenjualan;
    private javax.swing.JLabel lblFinancia;
    private javax.swing.JLabel lblFormMaster;
    private javax.swing.JLabel lblKategoriTransaksi;
    private javax.swing.JLabel lblKelolaSemua;
    private javax.swing.JLabel lblKeseluruhanJurnal;
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
    private javax.swing.JPanel panelSidebar;
    private javax.swing.JPanel panelTopBar;
    // End of variables declaration                   
}