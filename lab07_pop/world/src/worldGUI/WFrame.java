package worldGUI;


import javax.swing.*;
import java.awt.*;

public class WFrame extends JFrame {

    private JPanel contentPane;
    public static WPanel wp;


    private final int width = 760;
    private final int height = 750;

    public WFrame() {

        contentPane = new JPanel();
        contentPane.setLayout(null);

        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(width + 10, height + 35));
        setContentPane(contentPane);
        setVisible(true);
        setTitle("World");

        wp = new WPanel();
        wp.setBounds(5, 5, width - 13, height - 10);

        contentPane.add(wp);
    }
}
