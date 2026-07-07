package olahraga.ui;

import java.awt.*;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import olahraga.koneksi.Koneksi;

/**
 * JFrame TEXT - ditulis langsung dengan kode (tanpa GUI Builder / tanpa file .form).
 * CRUD lengkap untuk tabel tb_atlet, berelasi (FK id_cabang) ke tb_cabang.
 */
public class AtletFrame extends JFrame {

    private static final Color HIJAU_UTAMA = new Color(21, 102, 74);
    private static final Color HIJAU_MUDA = new Color(230, 245, 236);

    private JTextField txtNis, txtNama, txtTglLahir, txtNoPunggung, txtPrestasi, txtCari;
    private JComboBox<String> cboCabang, cboJenisKelamin;
    private JTable tabel;
    private DefaultTableModel model;
    private int idTerpilih = -1;
    private final Map<String, Integer> mapCabang = new LinkedHashMap<>(); // "Nama Cabang" -> id_cabang

    public AtletFrame() {
        setTitle("Data Atlet - Sport Manager");
        setSize(1000, 580);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        add(buatHeader(), BorderLayout.NORTH);
        add(buatPanelForm(), BorderLayout.WEST);
        add(buatPanelTabel(), BorderLayout.CENTER);

        muatCabang();
        muatData("");
    }

    private JPanel buatHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(HIJAU_UTAMA);
        panel.setPreferredSize(new Dimension(100, 55));
        JLabel judul = new JLabel("  \uD83C\uDFC3  DATA ATLET");
        judul.setFont(new Font("Segoe UI", Font.BOLD, 18));
        judul.setForeground(Color.WHITE);
        panel.add(judul, BorderLayout.WEST);
        return panel;
    }

    private JPanel buatPanelForm() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 100));
        panel.setBackground(HIJAU_MUDA);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagLayout gbl = new GridBagLayout();
        panel.setLayout(gbl);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 4, 5, 4);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        int y = 0;

        JLabel lblForm = new JLabel("Form Data Atlet");
        lblForm.setFont(new Font("Segoe UI", Font.BOLD, 14));
        c.gridy = y++; panel.add(lblForm, c);

        c.gridy = y++; panel.add(new JLabel("NIS Atlet"), c);
        txtNis = new JTextField();
        c.gridy = y++; panel.add(txtNis, c);

        c.gridy = y++; panel.add(new JLabel("Nama Atlet"), c);
        txtNama = new JTextField();
        c.gridy = y++; panel.add(txtNama, c);

        c.gridy = y++; panel.add(new JLabel("Cabang Olahraga"), c);
        cboCabang = new JComboBox<>();
        c.gridy = y++; panel.add(cboCabang, c);

        c.gridy = y++; panel.add(new JLabel("Jenis Kelamin"), c);
        cboJenisKelamin = new JComboBox<>(new String[]{"Laki-laki", "Perempuan"});
        c.gridy = y++; panel.add(cboJenisKelamin, c);

        c.gridy = y++; panel.add(new JLabel("Tanggal Lahir (yyyy-MM-dd)"), c);
        txtTglLahir = new JTextField();
        c.gridy = y++; panel.add(txtTglLahir, c);

        c.gridy = y++; panel.add(new JLabel("No. Punggung"), c);
        txtNoPunggung = new JTextField();
        c.gridy = y++; panel.add(txtNoPunggung, c);

        c.gridy = y++; panel.add(new JLabel("Prestasi"), c);
        txtPrestasi = new JTextField();
        c.gridy = y++; panel.add(txtPrestasi, c);

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
        c.gridy = y;
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
        panelCari.add(new JLabel("Cari Nama Atlet: "), BorderLayout.WEST);
        panelCari.add(txtCari, BorderLayout.CENTER);
        panelCari.add(btnCari, BorderLayout.EAST);
        btnCari.addActionListener(e -> muatData(txtCari.getText().trim()));

        model = new DefaultTableModel(
                new Object[]{"ID", "NIS", "Nama Atlet", "Cabang", "JK", "Tgl Lahir", "No. Punggung", "Prestasi"}, 0) {
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
                txtNis.setText(model.getValueAt(baris, 1).toString());
                txtNama.setText(model.getValueAt(baris, 2).toString());
                cboCabang.setSelectedItem(model.getValueAt(baris, 3).toString());
                cboJenisKelamin.setSelectedItem(model.getValueAt(baris, 4).toString());
                txtTglLahir.setText(model.getValueAt(baris, 5) == null ? "" : model.getValueAt(baris, 5).toString());
                txtNoPunggung.setText(model.getValueAt(baris, 6) == null ? "" : model.getValueAt(baris, 6).toString());
                txtPrestasi.setText(model.getValueAt(baris, 7) == null ? "" : model.getValueAt(baris, 7).toString());
            }
        });

        panel.add(panelCari, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabel), BorderLayout.CENTER);
        return panel;
    }

    private void muatCabang() {
        cboCabang.removeAllItems();
        mapCabang.clear();
        String sql = "SELECT id_cabang, nama_cabang FROM tb_cabang ORDER BY nama_cabang";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String nama = rs.getString("nama_cabang");
                mapCabang.put(nama, rs.getInt("id_cabang"));
                cboCabang.addItem(nama);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data cabang: " + e.getMessage());
        }
    }

    private void muatData(String kataKunci) {
        model.setRowCount(0);
        String sql = "SELECT a.*, c.nama_cabang FROM tb_atlet a "
                + "JOIN tb_cabang c ON a.id_cabang = c.id_cabang "
                + "WHERE a.nama_atlet LIKE ? ORDER BY a.id_atlet";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + kataKunci + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_atlet"),
                    rs.getString("nis_atlet"),
                    rs.getString("nama_atlet"),
                    rs.getString("nama_cabang"),
                    rs.getString("jenis_kelamin"),
                    rs.getDate("tanggal_lahir"),
                    rs.getObject("no_punggung"),
                    rs.getString("prestasi")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }
    }

    private void simpanData() {
        if (txtNis.getText().isEmpty() || txtNama.getText().isEmpty() || cboCabang.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "NIS, Nama Atlet, dan Cabang wajib diisi!");
            return;
        }
        String sql = "INSERT INTO tb_atlet (nis_atlet, nama_atlet, id_cabang, jenis_kelamin, tanggal_lahir, no_punggung, prestasi) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            isiPreparedStatement(ps);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data atlet berhasil ditambahkan.");
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
        String sql = "UPDATE tb_atlet SET nis_atlet=?, nama_atlet=?, id_cabang=?, jenis_kelamin=?, "
                + "tanggal_lahir=?, no_punggung=?, prestasi=? WHERE id_atlet=?";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            isiPreparedStatement(ps);
            ps.setInt(8, idTerpilih);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data atlet berhasil diubah.");
            bersihkanForm();
            muatData("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mengubah data: " + e.getMessage());
        }
    }

    private void isiPreparedStatement(PreparedStatement ps) throws SQLException {
        ps.setString(1, txtNis.getText().trim());
        ps.setString(2, txtNama.getText().trim());
        Integer idCabang = mapCabang.get(cboCabang.getSelectedItem().toString());
        ps.setInt(3, idCabang == null ? 0 : idCabang);
        ps.setString(4, cboJenisKelamin.getSelectedItem().toString());
        String tgl = txtTglLahir.getText().trim();
        if (tgl.isEmpty()) {
            ps.setNull(5, Types.DATE);
        } else {
            ps.setDate(5, Date.valueOf(tgl));
        }
        String noPunggung = txtNoPunggung.getText().trim();
        if (noPunggung.isEmpty()) {
            ps.setNull(6, Types.INTEGER);
        } else {
            ps.setInt(6, Integer.parseInt(noPunggung));
        }
        ps.setString(7, txtPrestasi.getText().trim());
    }

    private void hapusData() {
        if (idTerpilih == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data pada tabel terlebih dahulu!");
            return;
        }
        int pilih = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (pilih != JOptionPane.YES_OPTION) {
            return;
        }
        String sql = "DELETE FROM tb_atlet WHERE id_atlet=?";
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
        txtNis.setText("");
        txtNama.setText("");
        txtTglLahir.setText("");
        txtNoPunggung.setText("");
        txtPrestasi.setText("");
        if (cboCabang.getItemCount() > 0) {
            cboCabang.setSelectedIndex(0);
        }
        cboJenisKelamin.setSelectedIndex(0);
        tabel.clearSelection();
    }
}
