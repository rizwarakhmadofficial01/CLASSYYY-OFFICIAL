package olahraga.ui;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import olahraga.koneksi.Koneksi;

/**
 * JFrame VISUAL - dirancang menggunakan GUI Builder (Design view) NetBeans.
 * Form pendamping: LoginFrame.form
 */
public class LoginFrame extends javax.swing.JFrame {

    private JLabel jLabelTitle;
    private JLabel jLabelUser;
    private JLabel jLabelPass;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnKeluar;

    public LoginFrame() {
        initComponents();
        setLocationRelativeTo(null);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code (GUI Builder)">
    private void initComponents() {

        jLabelTitle = new JLabel();
        jLabelUser = new JLabel();
        jLabelPass = new JLabel();
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        btnLogin = new JButton();
        btnKeluar = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login - Sport Manager");
        setResizable(false);

        jLabelTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        jLabelTitle.setForeground(new Color(21, 102, 74));
        jLabelTitle.setText("SPORT MANAGER - LOGIN");

        jLabelUser.setText("Username");
        jLabelPass.setText("Password");

        btnLogin.setBackground(new Color(31, 160, 74));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setText("LOGIN");
        btnLogin.addActionListener(this::btnLoginActionPerformed);

        btnKeluar.setText("KELUAR");
        btnKeluar.addActionListener(this::btnKeluarActionPerformed);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTitle)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelUser)
                            .addComponent(jLabelPass))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(btnKeluar, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabelTitle)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelUser)
                    .addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPass)
                    .addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogin)
                    .addComponent(btnKeluar))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pack();
    }
    // </editor-fold>

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan Password wajib diisi!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection conn = Koneksi.getKoneksi();
        if (conn == null) {
            return;
        }

        String sql = "SELECT * FROM tb_user WHERE username = ? AND password = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String namaLengkap = rs.getString("nama_lengkap");
                String level = rs.getString("level");
                JOptionPane.showMessageDialog(this, "Login berhasil. Selamat datang, " + namaLengkap + "!",
                        "Berhasil", JOptionPane.INFORMATION_MESSAGE);
                new MenuUtama(namaLengkap, level).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username atau Password salah!",
                        "Login Gagal", JOptionPane.ERROR_MESSAGE);
                txtPassword.setText("");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {
        int pilih = JOptionPane.showConfirmDialog(this, "Yakin ingin keluar dari aplikasi?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (pilih == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
