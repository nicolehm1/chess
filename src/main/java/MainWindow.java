import Chessmen.ChessmenEnum;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Properties;

/**
 * Created by Nico on 12.08.2015.
 */
public class MainWindow {
    public JFileChooser fc;
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JPanel rawPane;
    private ChessBoard chessBoard;
    private JButton button1;
    private JTextArea formattedTextField1;
    private JTextArea formattedTextField2;
    private JTextArea formattedTextField3;
    private JTextArea formattedTextField4;
    private JTextArea formattedTextField5;
    private JTextArea formattedTextField6;
    private JTextArea formattedTextField7;
    private JTextArea formattedTextField8;
    private JTextArea formattedTextField9;
    private JTextArea formattedTextField10;
    private JTextArea formattedTextField11;
    private JTextArea formattedTextField12;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton button9;
    private JButton button10;
    private JButton button11;
    private JButton button12;
    private JButton loopButton;
    private JTextField timer;
    private JButton saveButton;
    private JButton loadButton;

    public MainWindow() {
        fc = new JFileChooser(new File("."), null);
        //fc.setCurrentDirectory(new File("."));

        button1.addActionListener(new MyActionListener(fc, button1, formattedTextField1, ChessmenEnum.bKing));
        button2.addActionListener(new MyActionListener(fc, button2, formattedTextField2, ChessmenEnum.bQueen));
        button3.addActionListener(new MyActionListener(fc, button3, formattedTextField3, ChessmenEnum.bBishop));
        button4.addActionListener(new MyActionListener(fc, button4, formattedTextField4, ChessmenEnum.bKnight));
        button5.addActionListener(new MyActionListener(fc, button5, formattedTextField5, ChessmenEnum.bRook));
        button6.addActionListener(new MyActionListener(fc, button6, formattedTextField6, ChessmenEnum.bPawn));
        button7.addActionListener(new MyActionListener(fc, button7, formattedTextField7, ChessmenEnum.wKing));
        button8.addActionListener(new MyActionListener(fc, button8, formattedTextField8, ChessmenEnum.wQueen));
        button9.addActionListener(new MyActionListener(fc, button9, formattedTextField9, ChessmenEnum.wBishop));
        button10.addActionListener(new MyActionListener(fc, button10, formattedTextField10, ChessmenEnum.wKnight));
        button11.addActionListener(new MyActionListener(fc, button11, formattedTextField11, ChessmenEnum.wRook));
        button12.addActionListener(new MyActionListener(fc, button12, formattedTextField12, ChessmenEnum.wPawn));

        Properties prob = MyProperties.getInstance();

        if (prob.containsKey(ChessmenEnum.bKing.toString()))
            formattedTextField1.insert(prob.getProperty(ChessmenEnum.bKing.toString()), 0);
        if (prob.containsKey(ChessmenEnum.bQueen.toString()))
            formattedTextField2.insert(prob.getProperty(ChessmenEnum.bQueen.toString()), 0);
        if (prob.containsKey(ChessmenEnum.bBishop.toString()))
            formattedTextField3.insert(prob.getProperty(ChessmenEnum.bBishop.toString()), 0);
        if (prob.containsKey(ChessmenEnum.bKnight.toString()))
            formattedTextField4.insert(prob.getProperty(ChessmenEnum.bKnight.toString()), 0);
        if (prob.containsKey(ChessmenEnum.bRook.toString()))
            formattedTextField5.insert(prob.getProperty(ChessmenEnum.bRook.toString()), 0);
        if (prob.containsKey(ChessmenEnum.bPawn.toString()))
            formattedTextField6.insert(prob.getProperty(ChessmenEnum.bPawn.toString()), 0);
        if (prob.containsKey(ChessmenEnum.wKing.toString()))
            formattedTextField7.insert(prob.getProperty(ChessmenEnum.wKing.toString()), 0);
        if (prob.containsKey(ChessmenEnum.wQueen.toString()))
            formattedTextField8.insert(prob.getProperty(ChessmenEnum.wQueen.toString()), 0);
        if (prob.containsKey(ChessmenEnum.wBishop.toString()))
            formattedTextField9.insert(prob.getProperty(ChessmenEnum.wBishop.toString()), 0);
        if (prob.containsKey(ChessmenEnum.wKnight.toString()))
            formattedTextField10.insert(prob.getProperty(ChessmenEnum.wKnight.toString()), 0);
        if (prob.containsKey(ChessmenEnum.wRook.toString()))
            formattedTextField11.insert(prob.getProperty(ChessmenEnum.wRook.toString()), 0);
        if (prob.containsKey(ChessmenEnum.wPawn.toString()))
            formattedTextField12.insert(prob.getProperty(ChessmenEnum.wPawn.toString()), 0);
        loopButton.addActionListener(new ActionListener() {
            boolean loop = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (loop) {
                    loop = false;
                    chessBoard.loopTime = Integer.parseInt(timer.getText());
                    chessBoard.startSoundLoop();
                } else {
                    loop = true;
                    chessBoard.stopSoundLoop();
                }
            }
        });

        JFileChooser fcSaveLoad = new JFileChooser(new File("."));

        saveButton.addActionListener(new ActionListener() {
            JFileChooser fc = fcSaveLoad;

            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(saveButton);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    chessBoard.saveChessmen(file);
                }
            }
        });
        loadButton.addActionListener(new ActionListener() {
            JFileChooser fc = fcSaveLoad;

            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(saveButton);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    chessBoard.loadChessmen(file);
                }
            }
        });
    }

    public void start() {
        JFrame frame = new JFrame("MainWindow");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        chessBoard.addMouseListener(new ChessBoardListener(chessBoard));
    }

    private void createUIComponents() {
    }
}
