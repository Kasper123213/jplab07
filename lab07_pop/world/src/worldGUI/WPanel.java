package worldGUI;

import main.MainWorld;
import model.Artifact;
import model.Blank;
import model.Category;
import model.Treasure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class WPanel extends JPanel {
    public static int sectorSize;
    public static int treasuresNumber = 0;
    public static JLabel treasures = new JLabel("Liczba znalezionych skarbow: " + treasuresNumber);
    public static int junksNumber = 0;
    public static JLabel junks = new JLabel("Liczba znalezionych skarbow: " + junksNumber);

    JCheckBox[] boxes = new JCheckBox[6];

    public WPanel() {
        add(treasures);
        add(junks);

        boxes[0] = new JCheckBox("Zloto");
        boxes[1] = new JCheckBox("Srebro");
        boxes[2] = new JCheckBox("Bronz");
        boxes[3] = new JCheckBox("Zelazo");
        boxes[4] = new JCheckBox("Inne");
        boxes[5] = new JCheckBox("Puste");

        for (JCheckBox box : boxes) {
            add(box);

            box.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (JCheckBox box1 : boxes) {
                        if (box1.isSelected() && box1 != box) box1.setSelected(false);
                    }
                }
            });
        }

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (getMousePosition().x >= 20 && getMousePosition().x <= 660
                        && getMousePosition().y >= 60 && getMousePosition().y <= 700) {
                    Point possition = getMousePosition();

                    if (e.getButton() == 1) {
                        for (int i = 0; i < boxes.length; i++) {
                            if (boxes[i].isSelected()) {
                                MainWorld.addArtifact(possition, i);
                            }
                        }
                    } else if (e.getButton() == 2) MainWorld.delete(possition);
                    else if (e.getButton() == 3) MainWorld.addArtifact(possition, -1);


                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        sectorSize = (getWidth() - 30) / 80;
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};


        g.translate(20, 60);

        g.setColor(Color.lightGray);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) continue;
                g.fillRect(i * sectorSize * 10, j * sectorSize * 10, sectorSize * 10, sectorSize * 10);
            }
        }

        g.setColor(Color.black);

        for (int i = 0; i <= 80; i++) {

            g.drawLine(0, sectorSize * i, (int) (sectorSize * 80), sectorSize * i);
            g.drawLine(sectorSize * i, 0, sectorSize * i, sectorSize * 80);


            if (i % 10 != 0 || i == 80) continue;
            g.drawString(letters[i / 10], (int) ((i + 4.5) * sectorSize), sectorSize * 80 + 15);
            g.drawString(String.valueOf(8 - i / 10), -10, (int) ((i + 4.5) * sectorSize));

        }


        for (int x = 0; x < 80; x++) {
            for (int y = 0; y < 80; y++) {
                if (MainWorld.world.get(x).get(y) instanceof Blank) continue;
                else if (MainWorld.world.get(x).get(y) instanceof Treasure) {
                    int item = ((Treasure) MainWorld.world.get(x).get(y)).getCategory().ordinal();
                    if (item == 0) g.setColor(new Color(255, 215, 0));
                    else if (item == 1) g.setColor(new Color(116, 175, 175));
                    else if (item == 2) g.setColor(new Color(165, 42, 42));
                    else if (item == 3) g.setColor(new Color(255, 0, 255));
                    else if (item == 4) g.setColor(new Color(124, 252, 0));
                    else if (item == 5) g.setColor(new Color(0, 0, 0));

                    g.fillRect(x * sectorSize, y * sectorSize, sectorSize, sectorSize);
                } else {
                    g.setColor(new Color(107, 142, 35));
                    g.fillRect(x * sectorSize, y * sectorSize, sectorSize, sectorSize);
                }
            }
        }

    }
}
