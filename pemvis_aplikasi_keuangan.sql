-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 10, 2025 at 01:52 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pemvis_aplikasi_keuangan`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE `accounts` (
  `account_id` varchar(20) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL COMMENT 'asset/liabilitas/pendapatan/beban/ekuitas'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`account_id`, `name`, `type`) VALUES
('1101', 'Kas Kecil', 'asset'),
('1102', 'Bank BCA Operasional', 'asset'),
('1103', 'Bank Mandiri Payroll', 'asset'),
('1111', 'Piutang Usaha - Klien', 'asset'),
('1112', 'Piutang Karyawan', 'asset'),
('1121', 'Persediaan Software License', 'asset'),
('1131', 'PPN Masukan', 'asset'),
('1132', 'Biaya Dibayar Dimuka', 'asset'),
('1201', 'Peralatan Komputer', 'asset'),
('1202', 'Furniture & Fixtures', 'asset'),
('1203', 'Kendaraan Operasional', 'asset'),
('1211', 'Akumulasi Penyusutan - Komputer', 'asset'),
('1212', 'Akumulasi Penyusutan - Furniture', 'asset'),
('1213', 'Akumulasi Penyusutan - Kendaraan', 'asset'),
('2101', 'Hutang Usaha - Supplier', 'liabilitas'),
('2102', 'Hutang Gaji & Tunjangan', 'liabilitas'),
('2103', 'Hutang PPh 21 Karyawan', 'liabilitas'),
('2104', 'Hutang PPN Keluaran', 'liabilitas'),
('2105', 'Hutang PPh Badan', 'liabilitas'),
('2106', 'Hutang BPJS Kesehatan', 'liabilitas'),
('2107', 'Hutang BPJS Ketenagakerjaan', 'liabilitas'),
('2108', 'Hutang Listrik & Utilities', 'liabilitas'),
('2201', 'Hutang Bank - KUR', 'liabilitas'),
('2202', 'Hutang Leasing Kendaraan', 'liabilitas'),
('3101', 'Modal Pemilik', 'ekuitas'),
('3201', 'Laba Ditahan', 'ekuitas'),
('3301', 'Laba/Rugi Tahun Berjalan', 'ekuitas'),
('4101', 'Pendapatan Jasa Pembuatan Software', 'pendapatan'),
('4102', 'Pendapatan Jasa Konsultasi IT', 'pendapatan'),
('4103', 'Pendapatan Maintenance & Support', 'pendapatan'),
('4104', 'Pendapatan Training & Workshop', 'pendapatan'),
('4105', 'Pendapatan Hosting & Domain', 'pendapatan'),
('4201', 'Pendapatan Lain-lain', 'pendapatan'),
('4202', 'Pendapatan Bunga Bank', 'pendapatan'),
('5101', 'Beban Gaji & Tunjangan', 'beban'),
('5102', 'Beban PPh 21 Ditanggung Perusahaan', 'beban'),
('5103', 'Beban BPJS Kesehatan Perusahaan', 'beban'),
('5104', 'Beban BPJS Ketenagakerjaan Perusahaan', 'beban'),
('5105', 'Beban Bonus & Insentif Karyawan', 'beban'),
('5106', 'Beban Training & Development', 'beban'),
('5201', 'Beban Sewa Kantor', 'beban'),
('5202', 'Beban Listrik', 'beban'),
('5203', 'Beban Internet & Telepon', 'beban'),
('5204', 'Beban Air & Kebersihan', 'beban'),
('5205', 'Beban Keamanan Kantor', 'beban'),
('5206', 'Beban Maintenance Kantor', 'beban'),
('5301', 'Beban Software License & Tools', 'beban'),
('5302', 'Beban Cloud Hosting (AWS/GCP)', 'beban'),
('5303', 'Beban Domain & SSL Certificate', 'beban'),
('5304', 'Beban Development Tools', 'beban'),
('5305', 'Beban Maintenance Hardware', 'beban'),
('5306', 'Beban Third Party API', 'beban'),
('5401', 'Beban Iklan Online (Google/FB Ads)', 'beban'),
('5402', 'Beban Promosi & Event', 'beban'),
('5403', 'Beban Website & SEO', 'beban'),
('5404', 'Beban Social Media Marketing', 'beban'),
('5405', 'Beban Sales Commission', 'beban'),
('5501', 'Beban ATK & Office Supplies', 'beban'),
('5502', 'Beban Konsumsi & Meeting', 'beban'),
('5503', 'Beban Transportasi & BBM', 'beban'),
('5504', 'Beban Konsultan (Legal/Pajak)', 'beban'),
('5505', 'Beban Bank & Administrasi', 'beban'),
('5506', 'Beban Asuransi', 'beban'),
('5601', 'Beban Penyusutan Komputer', 'beban'),
('5602', 'Beban Penyusutan Furniture', 'beban'),
('5603', 'Beban Penyusutan Kendaraan', 'beban'),
('5701', 'Beban Bunga Bank', 'beban'),
('5702', 'Beban Bunga Leasing', 'beban'),
('5703', 'Beban Administrasi Bank', 'beban'),
('5801', 'Beban PPh Badan', 'beban'),
('5802', 'Beban Pajak Lainnya', 'beban');

-- --------------------------------------------------------

--
-- Table structure for table `clients`
--

CREATE TABLE `clients` (
  `client_id` varchar(20) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `contact_person` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `payment_terms` varchar(50) DEFAULT NULL,
  `credit_limit` decimal(15,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `clients`
--

INSERT INTO `clients` (`client_id`, `name`, `contact_person`, `phone`, `email`, `payment_terms`, `credit_limit`) VALUES
('CLI001', 'PT Maju Jaya', 'John Doe', '081234567890', 'john@majujaya.com', 'Net 30', 50000000.00),
('CLI002', 'CV Sukses Mandiri', 'Jane Smith', '081234567891', 'jane@sukses.com', 'Net 45', 25000000.00),
('CLI003', 'PT Teknologi Nusantara', 'Ahmad Rahman', '081234567892', 'ahmad@teknologi.com', 'Net 30', 75000000.00),
('CLI004', 'UD Berkah Jaya', 'Siti Nurhaliza', '081234567893', 'siti@berkah.com', 'Net 15', 15000000.00),
('CLI005', 'PT Digital Solution', 'Robert Wilson', '081234567894', 'robert@digital.com', 'Net 30', 100000000.00);

-- --------------------------------------------------------

--
-- Table structure for table `expenses`
--

CREATE TABLE `expenses` (
  `expense_id` varchar(20) NOT NULL,
  `project_id` varchar(40) DEFAULT NULL,
  `category_id` varchar(20) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `amount` decimal(15,2) DEFAULT NULL,
  `expense_date` date DEFAULT NULL,
  `receipt_number` varchar(50) DEFAULT NULL,
  `created_by` varchar(20) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `expenses`
--

INSERT INTO `expenses` (`expense_id`, `project_id`, `category_id`, `description`, `amount`, `expense_date`, `receipt_number`, `created_by`, `created_at`) VALUES
('EXP001', NULL, 'TRC101', 'Gaji Karyawan Januari 2025', 45000000.00, '2025-01-31', 'PAY-2025-001', 'USR001', '2025-01-31 10:00:00'),
('EXP002', NULL, 'TRC201', 'Sewa Kantor Januari 2025', 15000000.00, '2025-01-05', 'RENT-2025-001', 'USR001', '2025-01-05 03:00:00'),
('EXP003', NULL, 'TRC202', 'Tagihan Listrik Desember 2024', 2500000.00, '2025-01-10', 'PLN-2024-012', 'USR001', '2025-01-10 07:30:00'),
('EXP004', NULL, 'TRC203', 'Internet & Telepon Januari 2025', 1500000.00, '2025-01-15', 'TEL-2025-001', 'USR001', '2025-01-15 02:15:00'),
('EXP005', NULL, 'TRC301', 'Lisensi Software Development Tools', 8000000.00, '2025-01-20', 'LIC-2025-001', 'USR001', '2025-01-20 04:45:00'),
('EXP006', 'PRJ001', 'TRC302', 'AWS Cloud Hosting - PRJ001', 3000000.00, '2025-01-25', 'AWS-2025-001', 'USR001', '2025-01-25 09:20:00'),
('EXP007', 'PRJ003', 'TRC306', 'Third Party API Banking - PRJ003', 5000000.00, '2025-02-01', 'API-2025-001', 'USR001', '2025-02-01 06:10:00'),
('EXP008', NULL, 'TRC501', 'ATK dan Office Supplies', 750000.00, '2025-02-05', 'ATK-2025-001', 'USR001', '2025-02-05 01:30:00'),
('EXP009', NULL, 'TRC503', 'Transportasi & BBM Tim', 2000000.00, '2025-02-10', 'TRANS-2025-001', 'USR001', '2025-02-10 08:45:00'),
('EXP010', NULL, 'TRC701', 'Pembelian Laptop Development', 25000000.00, '2025-02-15', 'LAPTOP-2025-001', 'USR001', '2025-02-15 05:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `invoices`
--

CREATE TABLE `invoices` (
  `invoice_id` varchar(20) NOT NULL,
  `project_id` varchar(40) DEFAULT NULL,
  `client_id` varchar(20) DEFAULT NULL,
  `invoice_number` varchar(50) DEFAULT NULL,
  `invoice_date` date DEFAULT NULL,
  `due_date` date DEFAULT NULL,
  `subtotal` decimal(15,2) DEFAULT NULL,
  `tax_amount` decimal(15,2) DEFAULT NULL,
  `total_amount` decimal(15,2) DEFAULT NULL,
  `paid_amount` decimal(15,2) DEFAULT NULL,
  `status` enum('draft','sent','paid','overdue','cancelled') DEFAULT NULL,
  `created_by` varchar(20) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `invoices`
--

INSERT INTO `invoices` (`invoice_id`, `project_id`, `client_id`, `invoice_number`, `invoice_date`, `due_date`, `subtotal`, `tax_amount`, `total_amount`, `paid_amount`, `status`, `created_by`, `created_at`) VALUES
('INV001', 'PRJ001', 'CLI001', 'INV/2025/001', '2025-01-15', '2025-02-14', 135135135.14, 14864864.86, 150000000.00, 150000000.00, 'paid', 'USR001', '2025-01-15 03:00:00'),
('INV002', 'PRJ002', 'CLI002', 'INV/2025/002', '2025-01-20', '2025-02-19', 45045045.05, 4954954.95, 50000000.00, 25000000.00, 'sent', 'USR001', '2025-01-20 07:30:00'),
('INV003', 'PRJ003', 'CLI003', 'INV/2025/003', '2025-01-25', '2025-02-24', 90090090.09, 9909909.91, 100000000.00, 0.00, 'sent', 'USR001', '2025-01-25 02:15:00'),
('INV004', 'PRJ004', 'CLI004', 'INV/2025/004', '2025-02-01', '2025-03-03', 22522522.52, 2477477.48, 25000000.00, 0.00, 'overdue', 'USR001', '2025-02-01 04:45:00'),
('INV005', 'PRJ005', 'CLI005', 'INV/2025/005', '2025-02-10', '2025-03-12', 67567567.57, 7432432.43, 75000000.00, 0.00, 'draft', 'USR001', '2025-02-10 09:20:00');

-- --------------------------------------------------------

--
-- Table structure for table `invoice_items`
--

CREATE TABLE `invoice_items` (
  `item_id` varchar(20) NOT NULL,
  `invoice_id` varchar(20) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `quantity` decimal(10,2) DEFAULT NULL,
  `unit_price` decimal(15,2) DEFAULT NULL,
  `amount` decimal(15,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `invoice_items`
--

INSERT INTO `invoice_items` (`item_id`, `invoice_id`, `description`, `quantity`, `unit_price`, `amount`) VALUES
('ITM001', 'INV001', 'Analisis & Desain Sistem', 1.00, 30000000.00, 30000000.00),
('ITM002', 'INV001', 'Development Backend API', 1.00, 50000000.00, 50000000.00),
('ITM003', 'INV001', 'Development Frontend Web', 1.00, 35000000.00, 35000000.00),
('ITM004', 'INV001', 'Testing & Deployment', 1.00, 20000000.00, 20000000.00),
('ITM005', 'INV002', 'Website E-commerce Basic', 1.00, 25000000.00, 25000000.00),
('ITM006', 'INV002', 'Payment Gateway Integration', 1.00, 15000000.00, 15000000.00),
('ITM007', 'INV002', 'Admin Panel & CMS', 1.00, 10000000.00, 10000000.00),
('ITM008', 'INV003', 'Mobile App Development - Phase 1', 1.00, 60000000.00, 60000000.00),
('ITM009', 'INV003', 'Security Implementation', 1.00, 30000000.00, 30000000.00),
('ITM010', 'INV003', 'API Integration', 1.00, 10000000.00, 10000000.00),
('ITM011', 'INV004', 'POS System Development', 1.00, 15000000.00, 15000000.00),
('ITM012', 'INV004', 'Hardware Integration', 1.00, 7500000.00, 7500000.00),
('ITM013', 'INV004', 'Training & Support', 1.00, 2500000.00, 2500000.00),
('ITM014', 'INV005', 'LMS Core Development', 1.00, 45000000.00, 45000000.00),
('ITM015', 'INV005', 'Video Streaming Module', 1.00, 20000000.00, 20000000.00),
('ITM016', 'INV005', 'Assessment & Quiz System', 1.00, 10000000.00, 10000000.00);

-- --------------------------------------------------------

--
-- Table structure for table `journal_entries`
--

CREATE TABLE `journal_entries` (
  `entry_id` varchar(20) NOT NULL,
  `entry_number` varchar(30) DEFAULT NULL,
  `transaction_type` enum('invoice','expense','receipt','adjustment','payment') NOT NULL,
  `transaction_id` varchar(20) DEFAULT NULL,
  `category_id` varchar(20) DEFAULT NULL,
  `entry_date` date NOT NULL,
  `description` text DEFAULT NULL,
  `debit_account_id` varchar(20) NOT NULL,
  `credit_account_id` varchar(20) NOT NULL,
  `amount` decimal(15,2) NOT NULL,
  `reference_number` varchar(50) DEFAULT NULL,
  `created_by` varchar(20) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `journal_entries`
--

INSERT INTO `journal_entries` (`entry_id`, `entry_number`, `transaction_type`, `transaction_id`, `category_id`, `entry_date`, `description`, `debit_account_id`, `credit_account_id`, `amount`, `reference_number`, `created_by`, `created_at`, `updated_at`) VALUES
('JE001', 'JE-2025-001', 'invoice', 'INV001', 'TRC001', '2025-01-15', 'Faktur Sistem Inventory Management - PT Maju Jaya', '1111', '4101', 150000000.00, 'INV/2025/001', 'USR001', '2025-01-15 03:00:00', '2025-07-09 09:59:07'),
('JE002', 'JE-2025-002', 'invoice', 'INV002', 'TRC001', '2025-01-20', 'Faktur Website E-commerce - CV Sukses Mandiri', '1111', '4101', 50000000.00, 'INV/2025/002', 'USR001', '2025-01-20 07:30:00', '2025-07-09 09:59:07'),
('JE003', 'JE-2025-003', 'invoice', 'INV003', 'TRC001', '2025-01-25', 'Faktur Aplikasi Mobile Banking - PT Teknologi Nusantara', '1111', '4101', 100000000.00, 'INV/2025/003', 'USR001', '2025-01-25 02:15:00', '2025-07-09 09:59:07'),
('JE004', 'JE-2025-004', 'invoice', 'INV004', 'TRC001', '2025-02-01', 'Faktur Sistem Point of Sale - UD Berkah Jaya', '1111', '4101', 25000000.00, 'INV/2025/004', 'USR001', '2025-02-01 04:45:00', '2025-07-09 09:59:07'),
('JE005', 'JE-2025-005', 'payment', 'INV001', 'TRC007', '2025-02-10', 'Pembayaran Invoice INV/2025/001 - PT Maju Jaya', '1102', '1111', 150000000.00, 'BCA-IN-2025-001', 'USR001', '2025-02-10 08:30:00', '2025-07-09 10:01:01'),
('JE006', 'JE-2025-006', 'payment', 'INV002', 'TRC007', '2025-02-15', 'Pembayaran Parsial Invoice INV/2025/002 - CV Sukses Mandiri', '1102', '1111', 25000000.00, 'BCA-IN-2025-002', 'USR001', '2025-02-15 04:20:00', '2025-07-09 10:01:05'),
('JE007', 'JE-2025-007', 'expense', 'EXP001', 'TRC101', '2025-01-31', 'Gaji Karyawan Januari 2025', '5101', '1103', 45000000.00, 'PAY-2025-001', 'USR001', '2025-01-31 10:00:00', '2025-07-09 09:59:07'),
('JE008', 'JE-2025-008', 'expense', 'EXP002', 'TRC201', '2025-01-05', 'Sewa Kantor Januari 2025', '5201', '1102', 15000000.00, 'RENT-2025-001', 'USR001', '2025-01-05 03:00:00', '2025-07-09 09:59:07'),
('JE009', 'JE-2025-009', 'expense', 'EXP003', 'TRC202', '2025-01-10', 'Tagihan Listrik Desember 2024', '5202', '1102', 2500000.00, 'PLN-2024-012', 'USR001', '2025-01-10 07:30:00', '2025-07-09 09:59:07'),
('JE010', 'JE-2025-010', 'expense', 'EXP004', 'TRC203', '2025-01-15', 'Internet & Telepon Januari 2025', '5203', '1102', 1500000.00, 'TEL-2025-001', 'USR001', '2025-01-15 02:15:00', '2025-07-09 09:59:07'),
('JE011', 'JE-2025-011', 'expense', 'EXP005', 'TRC301', '2025-01-20', 'Lisensi Software Development Tools', '5301', '1102', 8000000.00, 'LIC-2025-001', 'USR001', '2025-01-20 04:45:00', '2025-07-09 09:59:07'),
('JE012', 'JE-2025-012', 'expense', 'EXP006', 'TRC302', '2025-01-25', 'AWS Cloud Hosting - PRJ001', '5302', '1102', 3000000.00, 'AWS-2025-001', 'USR001', '2025-01-25 09:20:00', '2025-07-09 09:59:07'),
('JE013', 'JE-2025-013', 'expense', 'EXP007', 'TRC306', '2025-02-01', 'Third Party API Banking - PRJ003', '5306', '1102', 5000000.00, 'API-2025-001', 'USR001', '2025-02-01 06:10:00', '2025-07-09 09:59:07'),
('JE014', 'JE-2025-014', 'expense', 'EXP008', 'TRC501', '2025-02-05', 'ATK dan Office Supplies', '5501', '1101', 750000.00, 'ATK-2025-001', 'USR001', '2025-02-05 01:30:00', '2025-07-09 09:59:07'),
('JE015', 'JE-2025-015', 'expense', 'EXP009', 'TRC503', '2025-02-10', 'Transportasi & BBM Tim', '5503', '1102', 2000000.00, 'TRANS-2025-001', 'USR001', '2025-02-10 08:45:00', '2025-07-09 09:59:07'),
('JE016', 'JE-2025-016', 'expense', 'EXP010', 'TRC701', '2025-02-15', 'Pembelian Laptop Development', '1201', '1102', 25000000.00, 'LAPTOP-2025-001', 'USR001', '2025-02-15 05:00:00', '2025-07-09 09:59:07'),
('JE017', 'JE-2025-017', 'receipt', 'RCP001', 'TRC006', '2025-01-31', 'Bunga Bank BCA Januari 2025', '1102', '4202', 150000.00, 'BCA-BUNGA-2025-001', 'USR001', '2025-01-31 03:00:00', '2025-07-09 09:59:07'),
('JE018', 'JE-2025-018', 'receipt', 'RCP002', 'TRC006', '2025-01-31', 'Bunga Bank Mandiri Januari 2025', '1103', '4202', 120000.00, 'MDR-BUNGA-2025-001', 'USR001', '2025-01-31 03:30:00', '2025-07-09 09:59:07'),
('JE019', 'JE-2025-019', 'receipt', 'RCP003', 'TRC006', '2025-02-05', 'Cashback Kartu Kredit Bisnis', '1102', '4201', 500000.00, 'BCA-CASHBACK-2025-001', 'USR001', '2025-02-05 07:15:00', '2025-07-09 09:59:07'),
('JE020', 'JE-2025-020', 'receipt', 'RCP004', 'TRC006', '2025-02-10', 'Refund Biaya Berlangganan', '1102', '4201', 300000.00, 'GOOGLE-REFUND-2025-001', 'USR001', '2025-02-10 02:30:00', '2025-07-09 09:59:07'),
('JE021', 'JE-2025-021', 'receipt', 'RCP005', 'TRC006', '2025-02-15', 'Penjualan Laptop Bekas', '1102', '4201', 8000000.00, 'LAPTOP-SALE-2025-001', 'USR001', '2025-02-15 09:45:00', '2025-07-09 09:59:07');

-- --------------------------------------------------------

--
-- Table structure for table `other_receipts`
--

CREATE TABLE `other_receipts` (
  `receipt_id` varchar(20) NOT NULL,
  `category_id` varchar(20) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `amount` decimal(15,2) DEFAULT NULL,
  `receipt_date` date DEFAULT NULL,
  `source` varchar(100) DEFAULT NULL,
  `created_by` varchar(20) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `other_receipts`
--

INSERT INTO `other_receipts` (`receipt_id`, `category_id`, `description`, `amount`, `receipt_date`, `source`, `created_by`, `created_at`) VALUES
('RCP001', 'TRC006', 'Bunga Bank BCA Januari 2025', 150000.00, '2025-01-31', 'Bank BCA', 'USR001', '2025-01-31 03:00:00'),
('RCP002', 'TRC006', 'Bunga Bank Mandiri Januari 2025', 120000.00, '2025-01-31', 'Bank Mandiri', 'USR001', '2025-01-31 03:30:00'),
('RCP003', 'TRC006', 'Cashback Kartu Kredit Bisnis', 500000.00, '2025-02-05', 'Bank BCA Credit Card', 'USR001', '2025-02-05 07:15:00'),
('RCP004', 'TRC006', 'Refund Biaya Berlangganan', 300000.00, '2025-02-10', 'Google Workspace', 'USR001', '2025-02-10 02:30:00'),
('RCP005', 'TRC006', 'Penjualan Laptop Bekas', 8000000.00, '2025-02-15', 'Karyawan Internal', 'USR001', '2025-02-15 09:45:00');

-- --------------------------------------------------------

--
-- Table structure for table `projects`
--

CREATE TABLE `projects` (
  `project_id` varchar(40) NOT NULL,
  `client_id` varchar(20) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `budget` decimal(15,2) DEFAULT NULL,
  `status` enum('planning','ongoing','completed') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `projects`
--

INSERT INTO `projects` (`project_id`, `client_id`, `name`, `start_date`, `end_date`, `budget`, `status`) VALUES
('PRJ001', 'CLI001', 'Sistem Inventory Management', '2024-01-15', '2024-06-30', 150000000.00, 'completed'),
('PRJ002', 'CLI004', 'Website E-commerce', '2024-03-01', '2024-08-31', 75000000.00, 'ongoing'),
('PRJ003', 'CLI003', 'Aplikasi Mobile Banking', '2024-05-01', '2024-12-31', 300000000.00, 'ongoing'),
('PRJ004', 'CLI004', 'Sistem Point of Sale', '2024-06-01', '2024-09-30', 50000000.00, 'planning'),
('PRJ005', 'CLI005', 'Platform Learning Management', '2024-07-01', '2025-02-28', 200000000.00, 'planning'),
('qwqw', 'CLI001', 'qwq', '2025-07-02', '2025-07-25', 12000000.00, 'ongoing');

-- --------------------------------------------------------

--
-- Table structure for table `transaction_categories`
--

CREATE TABLE `transaction_categories` (
  `category_id` varchar(20) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `debit_account_id` varchar(20) DEFAULT NULL,
  `credit_account_id` varchar(20) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transaction_categories`
--

INSERT INTO `transaction_categories` (`category_id`, `name`, `debit_account_id`, `credit_account_id`, `description`) VALUES
('TRC001', 'Penjualan Software Custom', '1101', '1201', 'Revenue dari pembuatan software custom untuk klien (include PPN 11%)'),
('TRC0012', 'Penjualan Barang bekas pakai', '1101', '1103', 'semua barang barang bekas'),
('TRC002', 'Jasa Maintenance & Support', '1111', '4103', 'Revenue dari layanan maintenance dan support berkelanjutan (include PPN 11%)'),
('TRC003', 'Konsultasi IT', '1111', '4102', 'Revenue dari jasa konsultasi teknologi informasi (include PPN 11%)'),
('TRC004', 'Training & Workshop', '1111', '4104', 'Revenue dari pelatihan software dan workshop development (include PPN 11%)'),
('TRC005', 'Hosting & Domain Services', '1102', '4105', 'Revenue dari layanan hosting dan domain management (include PPN 11%)'),
('TRC006', 'Pendapatan Lainnya', '1102', '4201', 'Pendapatan di luar core business seperti bunga bank'),
('TRC007', 'Terima Pembayaran Piutang', '1102', '1111', 'Pembayaran invoice dari klien (pelunasan piutang)'),
('TRC101', 'Gaji & Tunjangan Karyawan', '5101', '1103', 'Pengeluaran gaji bulanan dan tunjangan karyawan'),
('TRC102', 'Bonus & Insentif', '5105', '1102', 'Bonus kinerja dan insentif project completion'),
('TRC103', 'Training Karyawan', '5106', '1102', 'Biaya pelatihan dan pengembangan skill karyawan'),
('TRC104', 'Bayar PPh 21 Karyawan', '2103', '1102', 'Pembayaran PPh 21 karyawan ke negara'),
('TRC105', 'Bayar BPJS Kesehatan', '2106', '1102', 'Pembayaran iuran BPJS Kesehatan karyawan'),
('TRC106', 'Bayar BPJS Ketenagakerjaan', '2107', '1102', 'Pembayaran iuran BPJS Ketenagakerjaan karyawan'),
('TRC201', 'Sewa Kantor', '5201', '1102', 'Biaya sewa tempat usaha bulanan'),
('TRC202', 'Listrik & Air', '5202', '1102', 'Biaya listrik, air, dan utilities kantor'),
('TRC203', 'Internet & Komunikasi', '5203', '1102', 'Biaya internet, telepon, dan komunikasi kantor'),
('TRC204', 'Kebersihan & Keamanan', '5205', '1102', 'Biaya cleaning service dan keamanan kantor'),
('TRC205', 'Maintenance Kantor', '5206', '1102', 'Biaya perawatan dan perbaikan fasilitas kantor'),
('TRC301', 'Software License & Tools', '5301', '1102', 'Biaya lisensi software development dan design tools'),
('TRC302', 'Cloud Hosting Services', '5302', '1102', 'Biaya AWS, Google Cloud, Azure, dan hosting services'),
('TRC303', 'Domain & SSL Certificate', '5303', '1102', 'Biaya domain registration dan SSL certificate'),
('TRC304', 'Development Tools', '5304', '1102', 'Biaya tools development seperti IDE, framework premium'),
('TRC305', 'Third Party API & Services', '5306', '1102', 'Biaya API external, payment gateway, analytics tools'),
('TRC306', 'Hardware Maintenance', '5305', '1102', 'Biaya maintenance dan repair peralatan IT'),
('TRC401', 'Digital Marketing', '5401', '1102', 'Biaya iklan Google Ads, Facebook Ads, LinkedIn'),
('TRC402', 'Website & SEO', '5403', '1102', 'Biaya maintenance website company dan SEO tools'),
('TRC403', 'Event & Promosi', '5402', '1102', 'Biaya participate event, tech conference, promosi'),
('TRC404', 'Sales Commission', '5405', '1102', 'Komisi sales dan marketing untuk closing deals'),
('TRC501', 'ATK & Office Supplies', '5501', '1101', 'Alat tulis kantor dan supplies operasional harian'),
('TRC502', 'Konsumsi & Meeting', '5502', '1101', 'Biaya makan meeting, snack kantor, client meeting'),
('TRC503', 'Transportasi & BBM', '5503', '1102', 'Biaya transport karyawan, BBM, parking, ojek online'),
('TRC504', 'Konsultan Legal & Pajak', '5504', '1102', 'Fee konsultan hukum, akuntan, dan konsultan pajak'),
('TRC505', 'Bank & Administrasi', '5505', '1102', 'Biaya admin bank, transfer, materai, notaris'),
('TRC506', 'Asuransi', '5506', '1102', 'Premi asuransi kendaraan, kesehatan, kecelakaan kerja'),
('TRC601', 'Bunga & Biaya Bank', '5701', '1102', 'Bunga pinjaman KUR dan biaya administrasi bank'),
('TRC602', 'Bayar Hutang Supplier', '2101', '1102', 'Pembayaran hutang ke supplier dan vendor'),
('TRC603', 'Bayar PPN ke Negara', '2104', '1102', 'Setor PPN keluaran dikurangi PPN masukan'),
('TRC701', 'Pembelian Komputer & Laptop', '1201', '1102', 'Pembelian hardware development laptop, PC, server (include PPN 11%)'),
('TRC702', 'Furniture & Office Equipment', '1202', '1102', 'Pembelian meja, kursi, AC, printer (include PPN 11%)'),
('TRC703', 'Kendaraan Operasional', '1203', '1102', 'Pembelian atau DP kendaraan untuk operasional (include PPN 11%)'),
('TRC801', 'Transfer BCA ke Mandiri', '1103', '1102', 'Transfer dana dari BCA Operasional ke Mandiri Payroll'),
('TRC802', 'Transfer Mandiri ke BCA', '1102', '1103', 'Transfer dana dari Mandiri Payroll ke BCA Operasional'),
('TRC803', 'Setor Kas ke Bank', '1102', '1101', 'Setoran kas kecil ke rekening bank BCA'),
('TRC804', 'Ambil Kas dari Bank', '1101', '1102', 'Pengambilan cash dari bank untuk kas kecil operasional'),
('TRC901', 'Penyesuaian Debit', '1102', '3301', 'Jurnal penyesuaian untuk koreksi saldo (debit)'),
('TRC902', 'Penyesuaian Kredit', '3301', '1102', 'Jurnal penyesuaian untuk koreksi saldo (kredit)'),
('wddsd', 'sdsds', '1101', '1102', 'sdds');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` varchar(20) NOT NULL,
  `username` varchar(20) DEFAULT NULL,
  `role` varchar(20) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `fullname` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `role`, `password`, `fullname`) VALUES
('', '', 'Admin', '', ''),
('12121', 'user baru', 'Manager', '12121', 'udin suudin'),
('lsjdlka', 'aji', 'Admin', 'lsdfjsdf', 'agus sutiaji'),
('NewUser', 'user user user', 'Admin', 'password', 'new user'),
('qqee', 'qweqwe', 'Admin', 'qweqe', 'qweqe'),
('sdsds', 'sdsd', 'Admin', 'sdsd', 'sdsds'),
('USR001', 'admin', 'Finance', 'admin', 'Administrator System'),
('USR002', 'accountant1', 'accountant', 'password', 'Sari Wijaya'),
('USR003', 'manager1', 'manager', 'password', 'Budi Santoso'),
('USR004', 'staff1', 'staff', 'password', 'Andi Pratama'),
('USR0999', 'admin2', 'Admin', 'admin2', 'hanif');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accounts`
--
ALTER TABLE `accounts`
  ADD PRIMARY KEY (`account_id`);

--
-- Indexes for table `clients`
--
ALTER TABLE `clients`
  ADD PRIMARY KEY (`client_id`);

--
-- Indexes for table `expenses`
--
ALTER TABLE `expenses`
  ADD PRIMARY KEY (`expense_id`);

--
-- Indexes for table `invoices`
--
ALTER TABLE `invoices`
  ADD PRIMARY KEY (`invoice_id`);

--
-- Indexes for table `invoice_items`
--
ALTER TABLE `invoice_items`
  ADD PRIMARY KEY (`item_id`);

--
-- Indexes for table `journal_entries`
--
ALTER TABLE `journal_entries`
  ADD PRIMARY KEY (`entry_id`),
  ADD UNIQUE KEY `entry_number` (`entry_number`),
  ADD KEY `credit_account_id` (`credit_account_id`),
  ADD KEY `category_id` (`category_id`),
  ADD KEY `idx_entry_date` (`entry_date`),
  ADD KEY `idx_transaction_type` (`transaction_type`),
  ADD KEY `idx_accounts` (`debit_account_id`,`credit_account_id`);

--
-- Indexes for table `other_receipts`
--
ALTER TABLE `other_receipts`
  ADD PRIMARY KEY (`receipt_id`);

--
-- Indexes for table `projects`
--
ALTER TABLE `projects`
  ADD PRIMARY KEY (`project_id`),
  ADD KEY `client_id` (`client_id`);

--
-- Indexes for table `transaction_categories`
--
ALTER TABLE `transaction_categories`
  ADD PRIMARY KEY (`category_id`),
  ADD KEY `account_id` (`debit_account_id`),
  ADD KEY `transaction_categories_ibfk_2` (`credit_account_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `journal_entries`
--
ALTER TABLE `journal_entries`
  ADD CONSTRAINT `journal_entries_ibfk_1` FOREIGN KEY (`debit_account_id`) REFERENCES `accounts` (`account_id`),
  ADD CONSTRAINT `journal_entries_ibfk_2` FOREIGN KEY (`credit_account_id`) REFERENCES `accounts` (`account_id`),
  ADD CONSTRAINT `journal_entries_ibfk_3` FOREIGN KEY (`category_id`) REFERENCES `transaction_categories` (`category_id`);

--
-- Constraints for table `projects`
--
ALTER TABLE `projects`
  ADD CONSTRAINT `projects_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`);

--
-- Constraints for table `transaction_categories`
--
ALTER TABLE `transaction_categories`
  ADD CONSTRAINT `transaction_categories_ibfk_1` FOREIGN KEY (`debit_account_id`) REFERENCES `accounts` (`account_id`),
  ADD CONSTRAINT `transaction_categories_ibfk_2` FOREIGN KEY (`credit_account_id`) REFERENCES `accounts` (`account_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
