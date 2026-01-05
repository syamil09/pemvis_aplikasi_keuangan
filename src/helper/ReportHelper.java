package helper;

import connection.DBConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import view.PopupReportDateRange;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import java.awt.*;

/**
 * Helper class untuk generate dan menampilkan laporan JasperReports
 * 
 * @author Leonovo
 */
public class ReportHelper {
    
    // simpan reference viewer yang sedang aktif
    private static JasperViewer currentViewer = null;
    private static JDialog loadingDialog = null;
    
    /**
     * Generate dan tampilkan report dari file jrxml
     * @param jrxmlPath path relatif ke file jrxml (dari root project)
     */
    public static void showReport(String jrxmlPath) {
        showReport(jrxmlPath, new HashMap<>());
    }
    
    /**
     * Generate dan tampilkan report dengan parameter (dengan loading)
     * @param jrxmlPath path relatif ke file jrxml
     * @param parameters parameter untuk report
     */
    public static void showReport(String jrxmlPath, Map<String, Object> parameters) {
        // show loading dialog
        showLoading();
        
        // run report generation in background
        SwingWorker<JasperPrint, Void> worker = new SwingWorker<JasperPrint, Void>() {
            @Override
            protected JasperPrint doInBackground() throws Exception {
                // tutup viewer sebelumnya jika ada
                if (currentViewer != null) {
                    currentViewer.dispose();
                    currentViewer = null;
                }
                
                Connection conn = DBConnection.getConnection();
                
                // compile jrxml to jasper
                JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlPath);
                
                // fill report dengan data dari database
                return JasperFillManager.fillReport(jasperReport, parameters, conn);
            }
            
            @Override
            protected void done() {
                hideLoading();
                try {
                    JasperPrint jasperPrint = get();
                    // tampilkan report di window terpisah
                    currentViewer = new JasperViewer(jasperPrint, false);
                    currentViewer.setTitle("Laporan");
                    currentViewer.setVisible(true);
                } catch (Exception e) {
                    System.out.println("Error generating report: " + e.getMessage());
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, 
                        "Gagal membuat laporan: " + e.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }
    
    /**
     * Tampilkan laporan jurnal dengan input tanggal dari popup
     * Menampilkan popup date range picker terlebih dahulu
     */
    public static void showJournalReport() {
        String[] dates = PopupReportDateRange.showDialog();
        
        if (dates != null) {
            Map<String, Object> params = new HashMap<>();
            params.put("startDate", dates[0]);
            params.put("endDate", dates[1]);
            
            showReport("src/view/jurnal.jrxml", params);
        }
    }
    
    /**
     * Tampilkan laporan laba rugi dengan input tanggal dari popup
     */
    public static void showLabaRugiReport() {
        String[] dates = PopupReportDateRange.showDialog();
        
        if (dates != null) {
            Map<String, Object> params = new HashMap<>();
            params.put("startDate", dates[0]);
            params.put("endDate", dates[1]);
            
            showReport("src/view/laporan_keuangan.jrxml", params);
        }
    }
    
    /**
     * Tampilkan laporan neraca dengan input tanggal dari popup
     * Neraca hanya butuh tanggal akhir (per tanggal tertentu)
     */
    public static void showNeracaReport() {
        String[] dates = PopupReportDateRange.showDialog();
        
        if (dates != null) {
            Map<String, Object> params = new HashMap<>();
            params.put("endDate", dates[1]); // neraca hanya pakai tanggal akhir
            
            showReport("src/view/neraca.jrxml", params);
        }
    }
    
    /**
     * Tampilkan laporan piutang langsung tanpa popup
     * Report piutang tidak perlu filter tanggal
     */
    public static void showPiutangReport() {
        showReport("src/view/piutang.jrxml");
    }
    
    /**
     * Show loading dialog
     */
    private static void showLoading() {
        if (loadingDialog != null) {
            loadingDialog.dispose();
        }
        
        loadingDialog = new JDialog((Frame) null, "Memuat...", true);
        loadingDialog.setUndecorated(true);
        loadingDialog.setSize(200, 80);
        loadingDialog.setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        JLabel label = new JLabel("Memuat laporan...");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        progressBar.setMaximumSize(new Dimension(160, 20));
        
        panel.add(label);
        panel.add(Box.createVerticalStrut(10));
        panel.add(progressBar);
        
        loadingDialog.add(panel);
        
        // show dialog in separate thread to prevent blocking
        new Thread(() -> loadingDialog.setVisible(true)).start();
        
        // small delay to ensure dialog is displayed
        try { Thread.sleep(100); } catch (InterruptedException e) {}
    }
    
    /**
     * Hide loading dialog
     */
    private static void hideLoading() {
        if (loadingDialog != null) {
            loadingDialog.dispose();
            loadingDialog = null;
        }
    }
}
