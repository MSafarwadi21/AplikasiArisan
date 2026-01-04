/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplikasiarisan;
import java.awt.*;
import java.util.List;
import javax.swing.JPanel;
/**
 *
 * @author teddy
 */
public class PanelRoda extends JPanel {
    private List<String> listNama;
    private double sudutSekarang = 0;

    public PanelRoda(List<String> listNama) {
        this.listNama = listNama;
        setPreferredSize(new Dimension(300, 300));
        setBackground(Color.WHITE);
    }

    public void setSudut(double sudut) {
        this.sudutSekarang = sudut;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int d = Math.min(getWidth(), getHeight()) - 40;
        int x = (getWidth() - d) / 2;
        int y = (getHeight() - d) / 2;

        int n = listNama.size();
        double sudutPerBagian = 360.0 / n;

        for (int i = 0; i < n; i++) {
            g2.setColor(Color.getHSBColor((float) i / n, 0.7f, 0.9f));
            g2.fillArc(x, y, d, d, (int) (sudutSekarang + (i * sudutPerBagian)), (int) sudutPerBagian);
            
            // Gambar Nama
            g2.setColor(Color.BLACK);
            double rad = Math.toRadians(sudutSekarang + (i * sudutPerBagian) + (sudutPerBagian / 2));
            int tx = (int) (getWidth() / 2 + Math.cos(rad) * (d / 3));
            int ty = (int) (getHeight() / 2 - Math.sin(rad) * (d / 3));
            g2.drawString(listNama.get(i), tx, ty);
        }

        // Gambar Jarum Penunjuk
        g2.setColor(Color.RED);
        int[] px = {getWidth() / 2 - 10, getWidth() / 2 + 10, getWidth() / 2};
        int[] py = {y - 10, y - 10, y + 10};
        g2.fillPolygon(px, py, 3);
    }
}