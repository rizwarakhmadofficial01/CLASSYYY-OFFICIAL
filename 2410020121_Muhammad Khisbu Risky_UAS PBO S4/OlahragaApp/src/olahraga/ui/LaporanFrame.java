package olahraga.ui;

import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import olahraga.koneksi.Koneksi;

/**
 * JFrame TEXT - ditulis langsung dengan kode (tanpa GUI Builder / tanpa file .form).
 * Laporan sederhana: jumlah atlet per cabang olahraga, bisa dicetak (fitur opsional no.6).
 */
public class LaporanFrame extends JFrame {

    private static final Color HIJAU_UTAMA = new Color(21, 102, 74);

    private JTable tabelLaporan;
    private DefaultTableModel model;
    private JLabel lblTotal;

    public LaporanFrame() {
        setTitle("Laporan Atlet per Cabang Olahraga - Sport Manager");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        add(buatHeader(), BorderLayout.NORTH);
        add(buatPanelTengah(), BorderLayout.CENTER);
        add(buatPanelBawah(), BorderLayout.SOUTH);

        muatLaporan();
    }

    private JPanel buatHeader() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(HIJAU_UTAMA);

        JLabel judul = new JLabel("  \uD83C\uDFC6  LAPORAN JUMLAH ATLET PER CABANG OLAHRAGA");
        judul.setFont(new Font("Segoe UI", Font.BOLD, 16));
        judul.setForeground(Color.WHITE);
        judul.setBorder(BorderFactory.createEmptyBorder(12, 5, 2, 5));

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy, HH:mm");
        JLabel tanggal = new JLabel("  Dicetak pada: " + sdf.format(new Date()));
        tanggal.setForeground(Color.WHITE);
        tanggal.setBorder(BorderFactory.createEmptyBorder(0, 5, 12, 5));

        panel.add(judul);
        panel.add(tanggal);
        return panel;
    }

    private JScrollPane buatPanelTengah() {
        model = new DefaultTableModel(new Object[]{"No", "Kode", "Nama Cabang", "Kategori", "Jumlah Atlet"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelLaporan = new JTable(model);
        tabelLaporan.setRowHeight(26);
        tabelLaporan.getTableHeader().setBackground(HIJAU_UTAMA);
        tabelLaporan.getTableHeader().setForeground(Color.WHITE);
        tabelLaporan.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        return new JScrollPane(tabelLaporan);
    }

    private JPanel buatPanelBawah() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        lblTotal = new JLabel("Total Atlet: 0");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JButton btnCetak = new JButton("Cetak Laporan");
        btnCetak.setBackground(HIJAU_UTAMA);
        btnCetak.setForeground(Color.WHITE);
        btnCetak.setFocusPainted(false);
        btnCetak.addActionListener(e -> cetakLaporan());

        JButton btnTutup = new JButton("Tutup");
        btnTutup.addActionListener(e -> dispose());

        JPanel panelTombol = new JPanel();
        panelTombol.add(btnCetak);
        panelTombol.add(btnTutup);

        panel.add(lblTotal, BorderLayout.WEST);
        panel.add(panelTombol, BorderLayout.EAST);
        return panel;
    }

    private void muatLaporan() {
        model.setRowCount(0);
        String sql = "SELECT c.kode_cabang, c.nama_cabang, c.kategori, COUNT(a.id_atlet) AS jumlah "
                + "FROM tb_cabang c LEFT JOIN tb_atlet a ON c.id_cabang = a.id_cabang "
                + "GROUP BY c.id_cabang ORDER BY c.nama_cabang";
        int no = 1;
        int total = 0;
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int jumlah = rs.getInt("jumlah");
                total += jumlah;
                model.addRow(new Object[]{
                    no++,
                    rs.getString("kode_cabang"),
                    rs.getString("nama_cabang"),
                    rs.getString("kategori"),
                    jumlah
                });
            }
            lblTotal.setText("Total Atlet: " + total);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat laporan: " + e.getMessage());
        }
    }

    private void cetakLaporan() {
        try {
            boolean berhasil = tabelLaporan.print(JTable.PrintMode.FIT_WIDTH,
                    new java.text.MessageFormat("Laporan Atlet per Cabang Olahraga"),
                    new java.text.MessageFormat("Halaman {0}"));
            if (!berhasil) {
                JOptionPane.showMessageDialog(this, "Pencetakan dibatalkan.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mencetak: " + e.getMessage());
        }
    }
}
