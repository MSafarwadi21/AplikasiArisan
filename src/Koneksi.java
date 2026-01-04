/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplikasiarisan;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author teddy
 */
public class Koneksi {
    private static Connection konek;

    public static Connection getKoneksi() {
        if (konek == null) {
            try {
                // Tentukan lokasi database di folder AppData user (selalu writable)
                String appData = System.getenv("APPDATA"); // C:\Users\NamaUser\AppData\Roaming
                Path appDir = Paths.get(appData, "ArisanBersama");
                Path dbPath = appDir.resolve("data_arisan.db");

                // Buat folder ArisanBersama di AppData jika belum ada
                Files.createDirectories(appDir);

                // Cek apakah file DB sudah ada di AppData
                boolean dbExists = Files.exists(dbPath);

                // Jika belum ada, copy template dari folder aplikasi (bundled)
                if (!dbExists) {
                    // Path template yang ikut di dist (bundled bersama .exe)
                    Path templatePath = Paths.get("data_arisan.db");
                    if (Files.exists(templatePath)) {
                        Files.copy(templatePath, dbPath, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("Template database berhasil dicopy ke AppData");
                    } else {
                        // Jika template tidak ada, biarkan SQLite buat file baru kosong
                        System.out.println("Template tidak ditemukan, akan buat database baru");
                    }
                }

                String url = "jdbc:sqlite:" + dbPath.toString();
                konek = DriverManager.getConnection(url);
                System.out.println("Koneksi database berhasil: " + dbPath);

                // Buat tabel hanya jika database baru dibuat (first run)
                if (!dbExists) {
                    buatTabel();
                }

            } catch (SQLException | IOException e) {
                System.err.println("Gagal koneksi database: " + e.getMessage());
                e.printStackTrace();
                // Optional: tampilkan pesan ke user
                // JOptionPane.showMessageDialog(null, "Gagal koneksi database: " + e.getMessage());
            }
        }
        return konek;
    }

    private static void buatTabel() {
        
        try (Statement stmt = konek.createStatement()) {
            // 1. Tabel Peserta
            String sqlPeserta = "CREATE TABLE IF NOT EXISTS peserta ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "nama TEXT NOT NULL,"
                    + "no_telp TEXT,"
                    + "b1 INTEGER DEFAULT 0, b2 INTEGER DEFAULT 0, b3 INTEGER DEFAULT 0,"
                    + "b4 INTEGER DEFAULT 0, b5 INTEGER DEFAULT 0, b6 INTEGER DEFAULT 0,"
                    + "b7 INTEGER DEFAULT 0, b8 INTEGER DEFAULT 0, b9 INTEGER DEFAULT 0,"
                    + "b10 INTEGER DEFAULT 0, b11 INTEGER DEFAULT 0, b12 INTEGER DEFAULT 0,"
                    + "status_menang INTEGER DEFAULT 0"
                    + ");";
            stmt.execute(sqlPeserta);

            // 2. Tabel Admin
            String sqlAdmin = "CREATE TABLE IF NOT EXISTS admin ("
                    + "username TEXT PRIMARY KEY, "
                    + "password TEXT NOT NULL);";
            stmt.execute(sqlAdmin);

            // 3. Insert admin default jika belum ada
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM admin");
            if (rs.next() && rs.getInt(1) == 0) {
                stmt.execute("INSERT INTO admin (username, password) VALUES ('admin', '123')");
            }

            System.out.println("Tabel berhasil dibuat & admin default disiapkan!");

        } catch (SQLException e) {
            System.err.println("Gagal membuat tabel: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
