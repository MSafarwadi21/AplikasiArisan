/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package aplikasiarisan;

/**
 *
 * @author teddy
 */
public class AplikasiArisan {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            com.formdev.flatlaf.FlatDarkLaf.setup();
        } catch( Exception ex ) {
            System.err.println( "Gagal pasang tema" );
        }

        // 2. Munculin Frame
        java.awt.EventQueue.invokeLater(() -> {
            LoginFrame lf = new LoginFrame(); 
            lf.setVisible(true); 
            lf.setLocationRelativeTo(null);
        });
    }
    
}
