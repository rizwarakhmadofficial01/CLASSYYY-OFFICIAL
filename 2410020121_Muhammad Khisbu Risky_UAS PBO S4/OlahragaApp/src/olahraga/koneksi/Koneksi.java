package olahraga.koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 * Helper koneksi database MySQL (Laragon).
 * Pastikan service MySQL di Laragon sudah "Start" sebelum menjalankan aplikasi.
 */
public class Koneksi {

    // ==== KONFIGURASI DATABASE (sesuaikan jika perlu) ====
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DB_NAME = "db_sportmanager";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // default Laragon: kosong

    private static final String URL =
            "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME
            + "?useSSL=false&serverTimezone=Asia/Makassar&allowPublicKeyRetrieval=true";

    private static Connection conn = null;

    public static Connection getKoneksi() {
        if (conn == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null,
                        "Driver JDBC MySQL tidak ditemukan.\n"
                        + "Tambahkan file mysql-connector-j.jar ke folder lib/ project ini,\n"
                        + "lalu klik kanan project -> Properties -> Libraries -> Add JAR/Folder.",
                        "Driver Tidak Ditemukan", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Gagal terhubung ke database.\n"
                        + "Pastikan Laragon sudah di-Start (Apache & MySQL) dan database '"
                        + DB_NAME + "' sudah di-import.\n\nDetail error: " + e.getMessage(),
                        "Koneksi Gagal", JOptionPane.ERROR_MESSAGE);
            }
        }
        return conn;
    }
}
