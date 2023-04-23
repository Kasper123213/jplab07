package officeGUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OFrame extends JFrame {

    private JPanel contentPane;
    public static OPanel op;


    private final int width = 600;
    private final int height = 600;

    public OFrame() {

        contentPane = new JPanel();
        contentPane.setLayout(null);

        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(width + 10, height + 35));
        setContentPane(contentPane);
        setVisible(true);
        setTitle("Office");


        op = new OPanel();
        op.setBounds(5, 5, width - 13, height - 10);

        contentPane.add(op);

    }
}
