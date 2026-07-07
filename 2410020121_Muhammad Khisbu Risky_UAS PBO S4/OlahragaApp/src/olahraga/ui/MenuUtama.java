package olahraga.ui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * JFrame VISUAL - dirancang menggunakan GUI Builder (Design view) NetBeans.
 * Form pendamping: MenuUtama.form
 */
public class MenuUtama extends javax.swing.JFrame {

    private JLabel lblWelcome;
    private JLabel lblSubtitle;
    private JButton btnDataCabang;
    private JButton btnDataAtlet;
    private JButton btnLaporan;
    private JButton btnLogout;

    private final String namaLengkap;
    private final String level;

    public MenuUtama(String namaLengkap, String level) {
        this.namaLengkap = namaLengkap;
        this.level = level;
        initComponents();
        lblSubtitle.setText("Selamat datang, " + namaLengkap + " (" + level + ")");
        setLocationRelativeTo(null);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code (GUI Builder)">
    private void initComponents() {
        lblWelcome = new JLabel();
        lblSubtitle = new JLabel();
        btnDataCabang = new JButton();
        btnDataAtlet = new JButton();
        btnLaporan = new JButton();
        btnLogout = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sport Manager - Menu Utama");

        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblWelcome.setForeground(new Color(21, 102, 74));
        lblWelcome.setText("SPORT MANAGER");

        lblSubtitle.setText("Selamat datang");

        btnDataCabang.setText("Data Cabang Olahraga");
        btnDataCabang.addActionListener(this::btnDataCabangActionPerformed);

        btnDataAtlet.setText("Data Atlet");
        btnDataAtlet.addActionListener(this::btnDataAtletActionPerformed);

        btnLaporan.setText("Laporan Atlet per Cabang");
        btnLaporan.addActionListener(this::btnLaporanActionPerformed);

        btnLogout.setBackground(new Color(192, 61, 61));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setText("Logout");
        btnLogout.addActionListener(this::btnLogoutActionPerformed);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblWelcome)
                    .addComponent(lblSubtitle)
                    .addComponent(btnDataCabang, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDataAtlet, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLaporan, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLogout, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblWelcome)
                .addGap(8, 8, 8)
                .addComponent(lblSubtitle)
                .addGap(25, 25, 25)
                .addComponent(btnDataCabang, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnDataAtlet, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnLaporan, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(btnLogout, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }
    // </editor-fold>

    private void btnDataCabangActionPerformed(java.awt.event.ActionEvent evt) {
        new CabangFrame().setVisible(true);
    }

    private void btnDataAtletActionPerformed(java.awt.event.ActionEvent evt) {
        new AtletFrame().setVisible(true);
    }

    private void btnLaporanActionPerformed(java.awt.event.ActionEvent evt) {
        new LaporanFrame().setVisible(true);
    }

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {
        int pilih = JOptionPane.showConfirmDialog(this, "Yakin ingin logout?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (pilih == JOptionPane.YES_OPTION) {
            new LoginFrame().setVisible(true);
            this.dispose();
        }
    }
}
