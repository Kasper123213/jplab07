package officeGUI;

import main.Office;
import model.Report;

import javax.swing.*;
import java.awt.*;

public class OPanel extends JPanel {
    public static String[] clubs = new String[64];


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int sectorSize = getWidth() / 9;
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};


        for (int i = 0; i <= 8; i++) {

            g.drawLine(sectorSize / 2, (int) (sectorSize * (i + 0.5)), (int) (sectorSize * 8.5), (int) (sectorSize * (i + 0.5)));
            g.drawLine((int) (sectorSize * (i + 0.5)), sectorSize / 2, (int) (sectorSize * (i + 0.5)), (int) (sectorSize * 8.5));

            if (i == 8) break;
            g.drawString(letters[i], (i + 1) * sectorSize, (int) (sectorSize * 8.7));
            g.drawString(String.valueOf(8 - i), 25, (i + 1) * sectorSize);

        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (clubs[j + 8 * i] == null) continue;
                g.drawString(clubs[j + 8 * i], (int) ((j + 0.6) * sectorSize), (int) ((i + 0.7) * sectorSize));
            }
        }
    }
}
