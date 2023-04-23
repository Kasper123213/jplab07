package clubGUI;

import Main.MainClub;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class CPanel extends JPanel {
    public static ArrayList<ArrayList> seekers = new ArrayList<>();
    public static ArrayList<String> letters = new ArrayList<>();
    JButton getTask = new JButton("Pros o zlecenie");
    public static int treasuresNumber = 0;
    public static JLabel treasures = new JLabel("Liczba znalezionych skarbow: " + treasuresNumber);
    public static int junksNumber = 0;
    public static JLabel junks = new JLabel("Liczna znalezionych rupieci: " + junksNumber);

    public CPanel() {
        add(treasures);
        add(junks);
        add(getTask);

        letters.addAll(List.of(new String[]{"A", "B", "C", "D", "E", "F", "G", "H"}));

        for (int i = 0; i < 80; i++) {
            seekers.add(new ArrayList<Integer>());
            for (int j = 0; j < 80; j++) {
                seekers.get(i).add(0);
            }
        }

        getTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MainClub.getRequest();
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int sectorSize = getWidth() / 80;


        g.translate(20, 60);

        g.setColor(Color.lightGray);

        for (int i = 0; i < 8; i++) {//rzedy
            for (int j = 0; j < 8; j++) {//kolumny
                if (MainClub.sectors[0].equals(letters.get(i) + (8 - j))
                        || MainClub.sectors[1].equals(letters.get(i) + (8 - j))) {
                    g.setColor(Color.green);
                } else {
                    g.setColor(Color.lightGray);
                    if ((i + j) % 2 == 0) continue;
                }

                g.fillRect(i * sectorSize * 10, j * sectorSize * 10, sectorSize * 10, sectorSize * 10);
            }
        }

        g.setColor(Color.black);

        for (int i = 0; i <= 80; i++) {

            g.drawLine(0, sectorSize * i, (int) (sectorSize * 80), sectorSize * i);
            g.drawLine(sectorSize * i, 0, sectorSize * i, sectorSize * 80);


            if (i % 10 != 0 || i == 80) continue;
            g.drawString(letters.get(i / 10), (int) ((i + 4.5) * sectorSize), sectorSize * 80 + 15);
            g.drawString(String.valueOf(8 - (i / 10)), -10, (int) ((i + 4.5) * sectorSize));
        }


        for (int i = 0; i < 80; i++) {
            for (int j = 0; j < 80; j++) {
                if ((int) seekers.get(i).get(j) != 0) {
                    g.setColor(Color.red);
                    g.fillRect(i * sectorSize, j * sectorSize, sectorSize, sectorSize);
                }
            }
        }
    }
}
