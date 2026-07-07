-- =========================================================
-- Database: db_sportmanager
-- Aplikasi : SportManager - Manajemen Atlet & Cabang Olahraga
-- Import file ini lewat HeidiSQL / phpMyAdmin bawaan Laragon
-- =========================================================

CREATE DATABASE IF NOT EXISTS db_sportmanager
    CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE db_sportmanager;

-- ---------------------------------------------------------
-- Tabel 1: tb_user (tabel user/pengguna untuk login)
-- ---------------------------------------------------------
CREATE TABLE IF NOT EXISTS tb_user (
    id_user      INT AUTO_INCREMENT PRIMARY KEY,
    username     VARCHAR(50)  NOT NULL UNIQUE,
    password     VARCHAR(100) NOT NULL,
    nama_lengkap VARCHAR(100) NOT NULL,
    level        ENUM('Admin','Operator') NOT NULL DEFAULT 'Operator',
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ---------------------------------------------------------
-- Tabel 2: tb_cabang (cabang olahraga)
-- ---------------------------------------------------------
CREATE TABLE IF NOT EXISTS tb_cabang (
    id_cabang    INT AUTO_INCREMENT PRIMARY KEY,
    kode_cabang  VARCHAR(10)  NOT NULL UNIQUE,
    nama_cabang  VARCHAR(100) NOT NULL,
    kategori     ENUM('Individu','Beregu') NOT NULL DEFAULT 'Individu',
    keterangan   VARCHAR(255) DEFAULT NULL
) ENGINE=InnoDB;

-- ---------------------------------------------------------
-- Tabel 3: tb_atlet (berelasi ke tb_cabang => FK id_cabang)
-- ---------------------------------------------------------
CREATE TABLE IF NOT EXISTS tb_atlet (
    id_atlet      INT AUTO_INCREMENT PRIMARY KEY,
    nis_atlet     VARCHAR(20)  NOT NULL UNIQUE,
    nama_atlet    VARCHAR(100) NOT NULL,
    id_cabang     INT NOT NULL,
    jenis_kelamin ENUM('Laki-laki','Perempuan') NOT NULL,
    tanggal_lahir DATE DEFAULT NULL,
    no_punggung   INT DEFAULT NULL,
    prestasi      VARCHAR(150) DEFAULT NULL,
    CONSTRAINT fk_atlet_cabang FOREIGN KEY (id_cabang)
        REFERENCES tb_cabang (id_cabang)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- ---------------------------------------------------------
-- Data awal
-- ---------------------------------------------------------
INSERT INTO tb_user (username, password, nama_lengkap, level) VALUES
('admin', 'admin123', 'Administrator', 'Admin'),
('operator', 'operator123', 'Operator Lapangan', 'Operator');

INSERT INTO tb_cabang (kode_cabang, nama_cabang, kategori, keterangan) VALUES
('SPK-01', 'Sepak Bola', 'Beregu', 'Cabang olahraga tim 11 pemain'),
('BLB-02', 'Bulu Tangkis', 'Individu', 'Cabang olahraga raket'),
('BSK-03', 'Bola Basket', 'Beregu', 'Cabang olahraga tim 5 pemain'),
('RNG-04', 'Renang', 'Individu', 'Cabang olahraga akuatik'),
('ATL-05', 'Atletik', 'Individu', 'Lari, lompat, dan lempar');

INSERT INTO tb_atlet (nis_atlet, nama_atlet, id_cabang, jenis_kelamin, tanggal_lahir, no_punggung, prestasi) VALUES
('ATL-0001', 'Rizky Pratama', 1, 'Laki-laki', '2003-05-12', 10, 'Juara 1 Liga Kampus 2025'),
('ATL-0002', 'Dinda Ayu Lestari', 2, 'Perempuan', '2004-02-20', NULL, 'Juara 2 Provinsi 2024'),
('ATL-0003', 'Fajar Nugroho', 3, 'Laki-laki', '2002-11-08', 23, 'Best Player Regional 2025'),
('ATL-0004', 'Siti Aminah', 4, 'Perempuan', '2005-07-15', NULL, 'Rekor Waktu 100m Gaya Bebas'),
('ATL-0005', 'Bagas Setiawan', 5, 'Laki-laki', '2003-09-30', NULL, 'Juara 1 Lari 400m Kejurda');
