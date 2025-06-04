-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 04 Jun 2025 pada 15.31
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

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
-- Struktur dari tabel `accounts`
--

CREATE TABLE `accounts` (
  `account_id` varchar(20) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL COMMENT 'asset/liabilitas/pendapatan/beban/ekuitas'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `accounts`
--

INSERT INTO `accounts` (`account_id`, `name`, `type`) VALUES
('ACC001', 'Kas', 'asset'),
('ACC002', 'Bank BCA', 'asset'),
('ACC003', 'Piutang Usaha', 'asset'),
('ACC004', 'Persediaan', 'asset'),
('ACC005', 'Utang Usaha', 'liabilitas'),
('ACC006', 'Utang Bank', 'liabilitas'),
('ACC007', 'Pendapatan Jasa', 'pendapatan'),
('ACC008', 'Pendapatan Penjualan', 'pendapatan'),
('ACC009', 'Beban Gaji', 'beban'),
('ACC010', 'Beban Sewa', 'beban'),
('ACC011', 'Modal Saham', 'ekuitas'),
('ACC012', 'Laba Ditahan', 'ekuitas');

-- --------------------------------------------------------

--
-- Struktur dari tabel `clients`
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
-- Dumping data untuk tabel `clients`
--

INSERT INTO `clients` (`client_id`, `name`, `contact_person`, `phone`, `email`, `payment_terms`, `credit_limit`) VALUES
('CLI001', 'PT Maju Jaya', 'John Doe', '081234567890', 'john@majujaya.com', 'Net 30', 50000000.00),
('CLI002', 'CV Sukses Mandiri', 'Jane Smith', '081234567891', 'jane@sukses.com', 'Net 45', 25000000.00),
('CLI003', 'PT Teknologi Nusantara', 'Ahmad Rahman', '081234567892', 'ahmad@teknologi.com', 'Net 30', 75000000.00),
('CLI004', 'UD Berkah Jaya', 'Siti Nurhaliza', '081234567893', 'siti@berkah.com', 'Net 15', 15000000.00),
('CLI005', 'PT Digital Solution', 'Robert Wilson', '081234567894', 'robert@digital.com', 'Net 30', 100000000.00);

-- --------------------------------------------------------

--
-- Struktur dari tabel `projects`
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
-- Dumping data untuk tabel `projects`
--

INSERT INTO `projects` (`project_id`, `client_id`, `name`, `start_date`, `end_date`, `budget`, `status`) VALUES
('PRJ001', 'CLI001', 'Sistem Inventory Management', '2024-01-15', '2024-06-30', 150000000.00, 'completed'),
('PRJ002', 'CLI002', 'Website E-commerce', '2024-03-01', '2024-08-31', 75000000.00, 'ongoing'),
('PRJ003', 'CLI003', 'Aplikasi Mobile Banking', '2024-05-01', '2024-12-31', 300000000.00, 'ongoing'),
('PRJ004', 'CLI004', 'Sistem Point of Sale', '2024-06-01', '2024-09-30', 50000000.00, 'planning'),
('PRJ005', 'CLI005', 'Platform Learning Management', '2024-07-01', '2025-02-28', 200000000.00, 'planning');

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaction_categories`
--

CREATE TABLE `transaction_categories` (
  `category_id` varchar(20) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `account_id` varchar(20) DEFAULT NULL,
  `type` enum('kredit','debit') DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `transaction_categories`
--

INSERT INTO `transaction_categories` (`category_id`, `name`, `account_id`, `type`, `description`) VALUES
('CAT001', 'Penerimaan Kas', 'ACC001', 'debit', 'Kategori untuk penerimaan kas tunai'),
('CAT002', 'Pembayaran Vendor', 'ACC001', 'kredit', 'Kategori untuk pembayaran kepada vendor'),
('CAT003', 'Transfer Bank Masuk', 'ACC002', 'debit', 'Kategori untuk transfer masuk ke rekening bank'),
('CAT004', 'Transfer Bank Keluar', 'ACC002', 'kredit', 'Kategori untuk transfer keluar dari rekening bank'),
('CAT005', 'Penjualan Jasa', 'ACC007', 'kredit', 'Kategori untuk pendapatan dari penjualan jasa'),
('CAT006', 'Pembayaran Gaji', 'ACC009', 'debit', 'Kategori untuk pembayaran gaji karyawan'),
('CAT007', 'Pembayaran Sewa', 'ACC010', 'debit', 'Kategori untuk pembayaran sewa kantor'),
('CAT008', 'Piutang Pelanggan', 'ACC003', 'debit', 'Kategori untuk pencatatan piutang pelanggan');

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

CREATE TABLE `users` (
  `user_id` varchar(20) NOT NULL,
  `username` varchar(20) DEFAULT NULL,
  `role` varchar(20) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `fullname` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `users`
--

INSERT INTO `users` (`user_id`, `username`, `role`, `password`, `fullname`) VALUES
('USR001', 'admin', 'administrator', 'admin', 'Administrator System'),
('USR002', 'accountant1', 'accountant', 'password', 'Sari Wijaya'),
('USR003', 'manager1', 'manager', 'password', 'Budi Santoso'),
('USR004', 'staff1', 'staff', 'password', 'Andi Pratama');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `accounts`
--
ALTER TABLE `accounts`
  ADD PRIMARY KEY (`account_id`);

--
-- Indeks untuk tabel `clients`
--
ALTER TABLE `clients`
  ADD PRIMARY KEY (`client_id`);

--
-- Indeks untuk tabel `projects`
--
ALTER TABLE `projects`
  ADD PRIMARY KEY (`project_id`),
  ADD KEY `client_id` (`client_id`);

--
-- Indeks untuk tabel `transaction_categories`
--
ALTER TABLE `transaction_categories`
  ADD PRIMARY KEY (`category_id`),
  ADD KEY `account_id` (`account_id`);

--
-- Indeks untuk tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `projects`
--
ALTER TABLE `projects`
  ADD CONSTRAINT `projects_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`);

--
-- Ketidakleluasaan untuk tabel `transaction_categories`
--
ALTER TABLE `transaction_categories`
  ADD CONSTRAINT `transaction_categories_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
