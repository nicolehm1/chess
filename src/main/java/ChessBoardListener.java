import Chessmen.ChessmenEnum;
import Chessmen.ChessmenFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Nico on 12.08.2015.
 */
public class ChessBoardListener implements MouseListener {

    ChessBoard chessBoard;
    private int lastX;
    private int lastY;

    public ChessBoardListener(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == 3)
            showMenu(e);
        else if (e.getButton() == 1) {
            ChessmenFactory.getInstance().getChessman(chessBoard.getChessmenAt(e.getX(), e.getY())).playSound();
        } else if (e.getButton() == 2) {
            chessBoard.playSoundColumn(chessBoard.mapCordToPosX(e.getX()));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        lastX = e.getX();
        lastY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        chessBoard.move(lastX, lastY, e.getX(), e.getY());
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void showMenu(MouseEvent event) {
        JPopupMenu popup;

        //...where the GUI is constructed:
        //Create the popup menu.
        popup = new JPopupMenu();
        JMenuItem menuItem;

        for (ChessmenEnum c : ChessmenEnum.values()) {
            if (c != ChessmenEnum.chessBoard && c != ChessmenEnum.outside) {
                menuItem = new JMenuItem(c.toString());
                menuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        chessBoard.setChessmenAt(event.getX(), event.getY(), c);
                        chessBoard.repaint();
                    }
                });
                popup.add(menuItem);
            }
        }

        popup.show(event.getComponent(), event.getX(), event.getY());
    }
}
