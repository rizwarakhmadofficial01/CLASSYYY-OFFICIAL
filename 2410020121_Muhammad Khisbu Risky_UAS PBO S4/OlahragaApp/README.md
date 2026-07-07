# SportManager - Aplikasi Manajemen Atlet & Cabang Olahraga

Aplikasi desktop Java Swing (NetBeans) dengan database MySQL (Laragon) bertema **olahraga**,
dibuat untuk memenuhi tugas: CRUD, 3 tabel berelasi, Login, Menu Utama, Form Data/Tampil,
Form Tambah & Ubah, laporan sederhana, serta kombinasi JFrame visual (GUI Builder) dan
JFrame text (dikoding manual).

## Fitur sesuai instruksi tugas

| No | Instruksi                                                        | Implementasi |
|----|-------------------------------------------------------------------|--------------|
| 1  | CRUD                                                               | Tambah, Ubah, Hapus, Tampil pada `CabangFrame` & `AtletFrame` |
| 2  | 3 tabel, 1 tabel user, sisanya berelasi                            | `tb_user`, `tb_cabang`, `tb_atlet` (FK `id_cabang`) |
| 3  | Halaman Login, Menu Utama, Form Data/Tampil, Form Tambah & Ubah    | `LoginFrame`, `MenuUtama`, `CabangFrame`, `AtletFrame` |
| 4  | Laporan sederhana yang menjelaskan frame/form                      | Lihat bagian "Penjelasan Frame/Form" di bawah |
| 5  | Ada JFrame visual dan JFrame text                                  | Visual: `LoginFrame`, `MenuUtama` (punya file `.form`). Text: `CabangFrame`, `AtletFrame`, `LaporanFrame` (murni kode) |
| 6  | (Opsional) Report                                                  | `LaporanFrame` - jumlah atlet per cabang, bisa dicetak (tombol "Cetak Laporan") |

## Struktur Database (Laragon / MySQL)

- **tb_user** - tabel user/pengguna untuk login (username, password, nama_lengkap, level)
- **tb_cabang** - master cabang olahraga (kode, nama, kategori Individu/Beregu)
- **tb_atlet** - data atlet, **berelasi** ke `tb_cabang` lewat foreign key `id_cabang`

Script database ada di: `database/db_sportmanager.sql`

## Cara Menjalankan (Laragon)

1. Buka **Laragon**, klik **Start All** (Apache & MySQL aktif, indikator hijau).
2. Klik menu **Database** di Laragon (atau buka HeidiSQL/phpMyAdmin bawaan Laragon).
3. Import file `database/db_sportmanager.sql` (klik kanan area kosong -> Load SQL file,
   atau buat database baru lalu jalankan isi file .sql tersebut).
4. Pastikan database `db_sportmanager` beserta 3 tabel & data contoh sudah muncul.

## Cara Membuka Project di NetBeans

1. Buka **NetBeans** -> **File > Open Project** -> pilih folder `OlahragaApp` (folder ini).
2. Tambahkan driver JDBC MySQL (**wajib**, lihat `lib/BACA_INI.txt` untuk caranya).
3. Jika koneksi database Anda berbeda (host/user/password), edit file:
   `src/olahraga/koneksi/Koneksi.java` bagian `HOST`, `USER`, `PASSWORD`.
4. Klik kanan project -> **Clean and Build**, lalu **Run** (F6) atau **Shift+F6**
   untuk menjalankan `Main.java`.

## Login Default

| Username  | Password     | Level     |
|-----------|--------------|-----------|
| admin     | admin123     | Admin     |
| operator  | operator123  | Operator  |

## Struktur Project

```
OlahragaApp/
├─ build.xml, manifest.mf, nbproject/      -> konfigurasi project NetBeans (Ant)
├─ database/db_sportmanager.sql            -> script database untuk Laragon
├─ lib/                                    -> taruh driver mysql-connector-j.jar di sini
└─ src/olahraga/
   ├─ Main.java                            -> entry point aplikasi
   ├─ koneksi/Koneksi.java                 -> helper koneksi JDBC ke MySQL
   └─ ui/
      ├─ LoginFrame.java (+.form)          -> JFrame VISUAL - halaman login
      ├─ MenuUtama.java (+.form)           -> JFrame VISUAL - dashboard/menu utama
      ├─ CabangFrame.java                  -> JFrame TEXT - CRUD data cabang olahraga
      ├─ AtletFrame.java                   -> JFrame TEXT - CRUD data atlet (relasi FK)
      └─ LaporanFrame.java                 -> JFrame TEXT - laporan jumlah atlet per cabang
```

## Penjelasan Frame/Form (untuk laporan tugas poin 4)

- **LoginFrame** (visual, `.form`): frame pertama yang tampil. Mengecek username &
  password ke tabel `tb_user`. Jika valid, membuka `MenuUtama` dan menutup diri sendiri.
- **MenuUtama** (visual, `.form`): dashboard berisi tombol navigasi ke `CabangFrame`,
  `AtletFrame`, `LaporanFrame`, dan tombol Logout kembali ke `LoginFrame`.
- **CabangFrame** (text, dikoding manual dengan `GridBagLayout`/`BorderLayout`): berisi
  form input (kiri) dan `JTable` (kanan) untuk CRUD tabel `tb_cabang`, plus fitur pencarian.
- **AtletFrame** (text, dikoding manual): sama seperti `CabangFrame`, tetapi field
  `Cabang Olahraga` berupa `JComboBox` yang mengambil data dari `tb_cabang` (relasi FK),
  sehingga satu atlet wajib terhubung ke satu cabang olahraga.
- **LaporanFrame** (text, dikoding manual): menampilkan rekap jumlah atlet per cabang
  olahraga menggunakan query `JOIN` + `GROUP BY`, serta tombol untuk mencetak laporan
  langsung ke printer (`JTable.print()`).

## Upload ke GitHub

```bash
cd OlahragaApp
git init
git add .
git commit -m "Initial commit: SportManager - Java Swing CRUD (NetBeans + Laragon MySQL)"
git branch -M main
git remote add origin <URL_REPO_GITHUB_ANDA>
git push -u origin main
```

Folder `build/`, `dist/`, dan `nbproject/private/` sudah otomatis diabaikan lewat `.gitignore`,
jadi repository GitHub tetap bersih dan ringan.
