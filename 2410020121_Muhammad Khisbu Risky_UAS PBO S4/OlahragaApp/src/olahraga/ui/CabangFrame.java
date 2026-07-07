package olahraga.ui;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import olahraga.koneksi.Koneksi;

/**
 * JFrame TEXT - ditulis langsung dengan kode (tanpa GUI Builder / tanpa file .form).
 * CRUD lengkap untuk tabel tb_cabang (Cabang Olahraga).
 */
public class CabangFrame extends JFrame {

    private static final Color HIJAU_UTAMA = new Color(21, 102, 74);
    private static final Color HIJAU_MUDA = new Color(230, 245, 236);

    private JTextField txtKode, txtNama, txtKeterangan, txtCari;
    private JComboBox<String> cboKategori;
    private JTable tabel;
    private DefaultTableModel model;
    private int idTerpilih = -1;

    public CabangFrame() {
        setTitle("Data Cabang Olahraga - Sport Manager");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        add(buatHeader(), BorderLayout.NORTH);
        add(buatPanelForm(), BorderLayout.WEST);
        add(buatPanelTabel(), BorderLayout.CENTER);

        muatData("");
    }

    private JPanel buatHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(HIJAU_UTAMA);
        panel.setPreferredSize(new Dimension(100, 55));
        JLabel judul = new JLabel("  \u26BD  DATA CABANG OLAHRAGA");
        judul.setFont(new Font("Segoe UI", Font.BOLD, 18));
        judul.setForeground(Color.WHITE);
        panel.add(judul, BorderLayout.WEST);
        return panel;
    }

    private JPanel buatPanelForm() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(280, 100));
        panel.setBackground(HIJAU_MUDA);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagLayout gbl = new GridBagLayout();
        panel.setLayout(gbl);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 4, 6, 4);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;

        JLabel lblForm = new JLabel("Form Cabang Olahraga");
        lblForm.setFont(new Font("Segoe UI", Font.BOLD, 14));
        c.gridy = 0;
        panel.add(lblForm, c);

        c.gridy = 1; panel.add(new JLabel("Kode Cabang"), c);
        txtKode = new JTextField();
        c.gridy = 2; panel.add(txtKode, c);

        c.gridy = 3; panel.add(new JLabel("Nama Cabang"), c);
        txtNama = new JTextField();
        c.gridy = 4; panel.add(txtNama, c);

        c.gridy = 5; panel.add(new JLabel("Kategori"), c);
        cboKategori = new JComboBox<>(new String[]{"Individu", "Beregu"});
        c.gridy = 6; panel.add(cboKategori, c);

        c.gridy = 7; panel.add(new JLabel("Keterangan"), c);
        txtKeterangan = new JTextField();
        c.gridy = 8; panel.add(txtKeterangan, c);

        JPanel tombol = new JPanel(new GridLayout(2, 2, 5, 5));
        tombol.setOpaque(false);
        JButton btnTambah = buatTombol("Tambah", new Color(31, 160, 74));
        JButton btnUbah = buatTombol("Ubah", new Color(41, 128, 185));
        JButton btnHapus = buatTombol("Hapus", new Color(192, 57, 43));
        JButton btnBersih = buatTombol("Bersihkan", new Color(127, 140, 141));
        tombol.add(btnTambah);
        tombol.add(btnUbah);
        tombol.add(btnHapus);
        tombol.add(btnBersih);
        c.gridy = 9;
        c.insets = new Insets(15, 4, 4, 4);
        panel.add(tombol, c);

        btnTambah.addActionListener(e -> simpanData());
        btnUbah.addActionListener(e -> ubahData());
        btnHapus.addActionListener(e -> hapusData());
        btnBersih.addActionListener(e -> bersihkanForm());

        return panel;
    }

    private JButton buatTombol(String teks, Color warna) {
        JButton b = new JButton(teks);
        b.setBackground(warna);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        return b;
    }

    private JPanel buatPanelTabel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelCari = new JPanel(new BorderLayout(5, 5));
        txtCari = new JTextField();
        JButton btnCari = buatTombol("Cari", HIJAU_UTAMA);
        panelCari.add(new JLabel("Cari Nama Cabang: "), BorderLayout.WEST);
        panelCari.add(txtCari, BorderLayout.CENTER);
        panelCari.add(btnCari, BorderLayout.EAST);
        btnCari.addActionListener(e -> muatData(txtCari.getText().trim()));

        model = new DefaultTableModel(
                new Object[]{"ID", "Kode", "Nama Cabang", "Kategori", "Keterangan"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabel = new JTable(model);
        tabel.setRowHeight(24);
        tabel.getTableHeader().setBackground(HIJAU_UTAMA);
        tabel.getTableHeader().setForeground(Color.WHITE);
        tabel.getColumnModel().getColumn(0).setMaxWidth(0);
        tabel.getColumnModel().getColumn(0).setMinWidth(0);
        tabel.getColumnModel().getColumn(0).setPreferredWidth(0);

        tabel.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabel.getSelectedRow() != -1) {
                int baris = tabel.getSelectedRow();
                idTerpilih = Integer.parseInt(model.getValueAt(baris, 0).toString());
                txtKode.setText(model.getValueAt(baris, 1).toString());
                txtNama.setText(model.getValueAt(baris, 2).toString());
                cboKategori.setSelectedItem(model.getValueAt(baris, 3).toString());
                txtKeterangan.setText(model.getValueAt(baris, 4) == null ? "" : model.getValueAt(baris, 4).toString());
            }
        });

        panel.add(panelCari, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabel), BorderLayout.CENTER);
        return panel;
    }

    private void muatData(String kataKunci) {
        model.setRowCount(0);
        String sql = "SELECT * FROM tb_cabang WHERE nama_cabang LIKE ? ORDER BY id_cabang";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + kataKunci + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_cabang"),
                    rs.getString("kode_cabang"),
                    rs.getString("nama_cabang"),
                    rs.getString("kategori"),
                    rs.getString("keterangan")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }
    }

    private void simpanData() {
        if (txtKode.getText().isEmpty() || txtNama.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Kode dan Nama Cabang wajib diisi!");
            return;
        }
        String sql = "INSERT INTO tb_cabang (kode_cabang, nama_cabang, kategori, keterangan) VALUES (?, ?, ?, ?)";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, txtKode.getText().trim());
            ps.setString(2, txtNama.getText().trim());
            ps.setString(3, cboKategori.getSelectedItem().toString());
            ps.setString(4, txtKeterangan.getText().trim());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan.");
            bersihkanForm();
            muatData("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + e.getMessage());
        }
    }

    private void ubahData() {
        if (idTerpilih == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data pada tabel terlebih dahulu!");
            return;
        }
        String sql = "UPDATE tb_cabang SET kode_cabang=?, nama_cabang=?, kategori=?, keterangan=? WHERE id_cabang=?";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, txtKode.getText().trim());
            ps.setString(2, txtNama.getText().trim());
            ps.setString(3, cboKategori.getSelectedItem().toString());
            ps.setString(4, txtKeterangan.getText().trim());
            ps.setInt(5, idTerpilih);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil diubah.");
            bersihkanForm();
            muatData("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mengubah data: " + e.getMessage());
        }
    }

    private void hapusData() {
        if (idTerpilih == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data pada tabel terlebih dahulu!");
            return;
        }
        int pilih = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?\n"
                + "Data atlet pada cabang ini juga akan ikut terhapus.",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (pilih != JOptionPane.YES_OPTION) {
            return;
        }
        String sql = "DELETE FROM tb_cabang WHERE id_cabang=?";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTerpilih);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
            bersihkanForm();
            muatData("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + e.getMessage());
        }
    }

    private void bersihkanForm() {
        idTerpilih = -1;
        txtKode.setText("");
        txtNama.setText("");
        txtKeterangan.setText("");
        cboKategori.setSelectedIndex(0);
        tabel.clearSelection();
    }
}
