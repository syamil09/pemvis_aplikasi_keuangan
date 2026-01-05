package helper;

import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Helper class untuk format mata uang Rupiah
 * @author syamil
 */
public class CurrencyHelper {
    
    // Locale Indonesia untuk format Rupiah
    private static final Locale LOCALE_ID = new Locale("id", "ID");
    
    // NumberFormat untuk Rupiah
    private static final NumberFormat RUPIAH_FORMAT = NumberFormat.getCurrencyInstance(LOCALE_ID);
    
    // DecimalFormat untuk custom formatting (titik sebagai separator ribuan)
    private static final DecimalFormat DECIMAL_FORMAT;
    
    static {
        java.text.DecimalFormatSymbols symbols = new java.text.DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        DECIMAL_FORMAT = new DecimalFormat("#,###", symbols);
    }
    
    /**
     * Format double ke format Rupiah lengkap (Rp 1.000.000,00)
     * @param amount jumlah dalam double
     * @return string format Rupiah
     */
    public static String formatToRupiah(double amount) {
        return RUPIAH_FORMAT.format(amount);
    }
    
    /**
     * Format double ke format Rupiah tanpa desimal (Rp 1.000.000)
     * @param amount jumlah dalam double
     * @return string format Rupiah tanpa desimal
     */
    public static String formatToRupiahWithoutDecimal(double amount) {
        return "Rp" + DECIMAL_FORMAT.format(Math.round(amount));
    }
    
    /**
     * Format double ke format angka dengan separator titik (1.000.000)
     * @param amount jumlah dalam double
     * @return string format angka dengan separator
     */
    public static String formatToNumber(double amount) {
        return DECIMAL_FORMAT.format(amount);
    }
    
    /**
     * Format string ke format Rupiah
     * @param amount jumlah dalam string
     * @return string format Rupiah
     */
    public static String formatToRupiah(String amount) {
        try {
            double value = Double.parseDouble(amount);
            return formatToRupiah(value);
        } catch (NumberFormatException e) {
            return "Rp0";
        }
    }
    
    /**
     * Parse string Rupiah ke double
     * @param rupiahString string format Rupiah (contoh: "Rp 1.000.000,00")
     * @return nilai double
     */
    public static double parseRupiahToDouble(String rupiahString) {
        try {
            // Hapus "Rp ", spasi, dan ganti koma dengan titik
            String cleanString = rupiahString.replace("Rp", "")
                                           .replace(" ", "")
                                           .replace(".", "")
                                           .replace(",", ".");
            return Double.parseDouble(cleanString);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    
    /**
     * Parse string angka dengan separator ke double
     * @param numberString string format angka (contoh: "1.000.000")
     * @return nilai double
     */
    public static double parseNumberToDouble(String numberString) {
        try {
            String cleanString = numberString.replace(".", "").replace(",", ".");
            return Double.parseDouble(cleanString);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    
    /**
     * Validasi apakah string adalah format Rupiah yang valid
     * @param rupiahString string yang akan divalidasi
     * @return true jika valid
     */
    public static boolean isValidRupiahFormat(String rupiahString) {
        try {
            parseRupiahToDouble(rupiahString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Format untuk display di tabel (singkat)
     * @param amount jumlah dalam double
     * @param showPrefix apakah menampilkan "Rp"
     * @return string format untuk tabel
     */
    public static String formatForTable(double amount, boolean showPrefix) {
        String formatted = DECIMAL_FORMAT.format(Math.round(amount));
        return showPrefix ? "Rp" + formatted : formatted;
    }
    
    /**
     * Format untuk input field dengan validasi
     * @param input string input dari user
     * @return string yang sudah diformat atau string kosong jika invalid
     */
    public static String formatInputField(String input) {
        try {
            // Hapus semua karakter non-digit kecuali titik dan koma
            String cleanInput = input.replaceAll("[^0-9.,]", "");
            
            if (cleanInput.isEmpty()) {
                return "";
            }
            
            double value = parseNumberToDouble(cleanInput);
            return formatToNumber(value);
        } catch (Exception e) {
            return input; // Return input asli jika gagal format
        }
    }
    
    /**
     * Konversi dari Long ke format Rupiah
     * @param amount jumlah dalam Long
     * @return string format Rupiah
     */
    public static String formatToRupiah(Long amount) {
        if (amount == null) return "Rp0";
        return formatToRupiah(amount.doubleValue());
    }
    
    /**
     * Konversi dari Integer ke format Rupiah
     * @param amount jumlah dalam Integer
     * @return string format Rupiah
     */
    public static String formatToRupiah(Integer amount) {
        if (amount == null) return "Rp0";
        return formatToRupiah(amount.doubleValue());
    }
    
    /**
     * Format untuk laporan keuangan (dengan tanda kurung untuk nilai negatif)
     * @param amount jumlah dalam double
     * @return string format untuk laporan
     */
    public static String formatForReport(double amount) {
        if (amount < 0) {
            return "(" + formatToRupiahWithoutDecimal(Math.abs(amount)) + ")";
        }
        return formatToRupiahWithoutDecimal(amount);
    }
    
        /**
     * Mengubah double ke format angka tanpa scientific notation
     * @param value angka double
     * @return string angka normal
     */
    public static String doubleToString(double value) {
        DecimalFormat df = new DecimalFormat("#");
        return df.format(value);
    }
    
    /**
     * Method untuk testing/debugging
     * @param args
     */
    public static void main(String[] args) {
        // Test formatting
        double testAmount = 1500000;
        
        System.out.println("=== TESTING CURRENCY HELPER ===");
        System.out.println("Original amount: " + testAmount);
        System.out.println("formatToRupiah: " + formatToRupiah(testAmount));
        System.out.println("formatToRupiahWithoutDecimal: " + formatToRupiahWithoutDecimal(testAmount));
        System.out.println("formatToNumber: " + formatToNumber(testAmount));
        System.out.println("formatForTable (with Rp): " + formatForTable(testAmount, true));
        System.out.println("formatForTable (without Rp): " + formatForTable(testAmount, false));
        System.out.println("formatForReport: " + formatForReport(testAmount));
        System.out.println("formatForReport (negative): " + formatForReport(-testAmount));
        
        // Test parsing
        String rupiahString = "Rp 1.500.000,75";
        System.out.println("\n=== TESTING PARSING ===");
        System.out.println("Rupiah string: " + rupiahString);
        System.out.println("Parsed to double: " + parseRupiahToDouble(rupiahString));
        
        // Test validation
        System.out.println("\n=== TESTING VALIDATION ===");
        System.out.println("Valid Rupiah format: " + isValidRupiahFormat("Rp 1.000.000"));
        System.out.println("Invalid Rupiah format: " + isValidRupiahFormat("Invalid"));
        
        // Test input formatting
        System.out.println("\n=== TESTING INPUT FORMATTING ===");
        System.out.println("Input '1500000' formatted: " + formatInputField("1500000"));
        System.out.println("Input 'Rp1.500.000' formatted: " + formatInputField("Rp1.500.000"));
    }
}