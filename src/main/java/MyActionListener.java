import Chessmen.ChessmenEnum;
import Chessmen.ChessmenFactory;
import Chessmen.Sounds;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by Nico on 14.08.2015.
 */
class MyActionListener implements ActionListener {
    private JFileChooser fc;
    private JButton button;
    private JTextArea textArea;
    private ChessmenEnum chessmen;

    public MyActionListener(JFileChooser fc, JButton button, JTextArea textArea, ChessmenEnum chessmen) {
        this.fc = fc;
        this.button = button;
        this.textArea = textArea;
        this.chessmen = chessmen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int returnVal = fc.showOpenDialog(button);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            Sounds sound = new Sounds(file);
            ChessmenFactory.getInstance().getChessman(chessmen).setSound(sound);
            try {
                textArea.insert(file.getCanonicalPath(), 0);
                MyProperties.getInstance().put(chessmen.toString(), file.getCanonicalPath());
                MyProperties.getInstance().saveProperties();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
