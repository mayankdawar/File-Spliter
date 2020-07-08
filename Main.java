package abc ;

import javax.swing.*;
import java.awt.Container;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main extends JFrame implements ActionListener
{
    private JTextField txtFilename;
    private JLabel lblFilename,lblDevice;
    private JButton btnPath, btnSplit, btnExit;
    private JPanel pnlTop, pnlCenter, pnlBottom;
    private JComboBox cbSize;
    private fileSplit split = new fileSplit();
    private File file = null;
    private String[] strSize = {"1.44 Floppy disk","500mb Pen drive","700mb Compact Disk drive"};
    private int nValidsize = split.getMax()*1024;

    public Main()
    {
        super(" File Splitter ");
        Container c = getContentPane();

        c.setBackground(Color.BLACK);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });

        lblFilename = new JLabel("Filename:");
        lblFilename.setForeground(Color.white);
        lblFilename.setBounds(3,15,59,25);
        txtFilename = new JTextField();
        txtFilename.setEditable(false);
        txtFilename.setBackground(Color.white);

        btnPath = new JButton("...");
        btnPath.setBackground(Color.BLACK);
        btnPath.addActionListener(this);
        btnPath.setForeground(Color.WHITE);
        btnSplit = new JButton("Split");
        btnSplit.setBackground(Color.BLACK);
        btnSplit.setForeground(Color.WHITE);
        btnSplit.addActionListener(this);
        btnExit = new JButton("Exit");
        btnExit.setBackground(Color.BLACK);
        btnExit.setForeground(Color.WHITE);
        btnExit.addActionListener(this);

        lblDevice = new JLabel("Select Device: ");
        lblDevice.setForeground(Color.WHITE);

        cbSize = new JComboBox(strSize);
        cbSize.setBackground(Color.BLACK);
        cbSize.setForeground(Color.WHITE);
        cbSize.addActionListener(this);

        txtFilename.setBounds(63,15,335,25);
        btnPath.setBounds(398,15,25,25);

        pnlTop = new JPanel();
        pnlTop.setLayout(null);
        pnlTop.add(lblFilename);
        pnlTop.add(txtFilename);
        pnlTop.add(btnPath);
        pnlTop.setBackground(Color.GREEN);

        pnlCenter = new JPanel();
        pnlCenter.setBackground(Color.RED);

        pnlBottom = new JPanel();
        pnlBottom.add(lblDevice);
        pnlBottom.add(cbSize);
        pnlBottom.add(btnSplit);
        pnlBottom.add(btnExit);
        pnlBottom.setBackground(Color.BLACK);

        c.setLayout(null);
        pnlTop.setBounds(5,15,500,50);
        pnlCenter.setBounds(5,80,437,264);
        pnlBottom.setBounds(80,380,350,50);
        c.add(pnlTop);
        c.add(pnlCenter);
        c.add(pnlBottom);
        setContentPane(c);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==btnPath)
        {
            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                file = fc.getSelectedFile();
                txtFilename.setText(file.getPath());
            }
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        else if(e.getSource()==cbSize)
        {
            int nIndex = cbSize.getSelectedIndex();

            switch(nIndex)
            {
                case 0: split.setMax(1400); break;
                case 1: split.setMax(500000); break;
                case 2: split.setMax(700000); break;
            }
            nValidsize = split.getMax()*1024;
        }
        else if(e.getSource()==btnSplit)
        {
            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            if(file != null) // if there is a selected file
                if(file.length()<nValidsize)
                    JOptionPane.showMessageDialog(null, "The file does not need to split (it is small enough to store in the selected device)\nPlease select a file to split by pressing the button beside the text box above", "Error",JOptionPane.ERROR_MESSAGE);
                else if(split.split(file))
                    JOptionPane.showMessageDialog(null, "File split successful at C:\\"+file.getName()+"\\\nto merge the file just double click \""+file.getName().substring(0, file.getName().length()-4)+".bat\"", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(null, "There is an error occurs during file split time", "Error", JOptionPane.ERROR_MESSAGE);
            else
                JOptionPane.showMessageDialog(null, "Please select a file to split by pressing the button beside the text box above", "Error",JOptionPane.ERROR_MESSAGE);
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        else if(e.getSource()==btnExit)
        {
            System.exit(0);
        }
    }

    public static void main(String args[])
    {
        Main a = new Main();
        a.setResizable(false);
        a.setSize(460, 480);
        a.setVisible(true);
    }
}
