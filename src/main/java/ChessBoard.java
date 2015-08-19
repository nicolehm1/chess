import Chessmen.ChessmenEnum;
import Chessmen.ChessmenFactory;
import Chessmen.Sounds;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.Timer;

/**
 * Created by Nico on 12.08.2015.
 */
public class ChessBoard extends JPanel {

    private final static int rowSize = 8, columnSize = 16;
    Timer timer = new Timer();
    int loopTime = 250;
    private EnumMap<ChessmenEnum, BufferedImage> images = new EnumMap<>(ChessmenEnum.class);
    private ChessmenEnum[][] pos = new ChessmenEnum[rowSize][columnSize];

    public ChessBoard() {
        super();
        loadResources();

        for (ChessmenEnum[] po : pos) Arrays.fill(po, ChessmenEnum.free);
        placeStandardChess();
    }

    public void saveChessmen(File file) {
        ObjectOutputStream outputStream;
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(pos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadChessmen(File file) {
        ObjectInputStream inputStream;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(file));

            pos = (ChessmenEnum[][]) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        this.repaint();
    }

    public void move(int fromX, int fromY, int toX, int toY) {
        if (!this.getBounds().contains(fromX, fromY) || !this.getBounds().contains(toX, toY))
            return;
        ChessmenEnum tmp;
        tmp = getChessmenAt(fromX, fromY);
        setChessmenAt(fromX, fromY, ChessmenEnum.free);

        setChessmenAt(toX, toY, tmp);
        this.repaint();
    }

    public void setChessmenAt(int x, int y, ChessmenEnum chessmen) {
        pos[mapCordToPosY(y)][mapCordToPosX(x)] = chessmen;
    }

    public ChessmenEnum getChessmenAt(int x, int y) {
        int posY = mapCordToPosY(y);
        int posX = mapCordToPosX(x);

        if (posX > columnSize || posX < 0 || posY > rowSize || posY < 0)
            return ChessmenEnum.outside;

        return pos[posY][posX];
    }

    public int mapCordToPosX(int cord) {
        return cord / (this.getWidth() / columnSize);
    }

    public int mapCordToPosY(int cord) {
        return cord / (this.getHeight() / rowSize);
    }

    public void placeFigure(ChessmenEnum figure, int x, int y) {
        pos[y][x] = figure;
    }

    private void placeStandardChess() {
        Arrays.fill(pos[6], ChessmenEnum.wPawn);
        Arrays.fill(pos[1], ChessmenEnum.bPawn);
        pos[7][0] = ChessmenEnum.wRook;
        pos[7][1] = ChessmenEnum.wKnight;
        pos[7][2] = ChessmenEnum.wBishop;
        pos[7][3] = ChessmenEnum.wQueen;
        pos[7][4] = ChessmenEnum.wKing;
        pos[7][5] = ChessmenEnum.wBishop;
        pos[7][6] = ChessmenEnum.wKnight;
        pos[7][7] = ChessmenEnum.wRook;

        pos[0][0] = ChessmenEnum.bRook;
        pos[0][1] = ChessmenEnum.bKnight;
        pos[0][2] = ChessmenEnum.bBishop;
        pos[0][3] = ChessmenEnum.bQueen;
        pos[0][4] = ChessmenEnum.bKing;
        pos[0][5] = ChessmenEnum.bBishop;
        pos[0][6] = ChessmenEnum.bKnight;
        pos[0][7] = ChessmenEnum.bRook;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(images.get(ChessmenEnum.chessBoard), 0, 0, this.getWidth(), this.getHeight(), null);

        drawFigures(g);
    }

    private void drawFigures(Graphics g) {
        int width8 = this.getWidth() / columnSize;
        int height8 = this.getHeight() / rowSize;

        for (int i = 0; i < pos.length; i++) {
            for (int j = 0; j < pos[i].length; j++) {
                if (pos[i][j] != ChessmenEnum.free) {

                    g.drawImage(ChessmenFactory.getInstance().getChessman(pos[i][j]).getImage(), j * width8 + width8 / columnSize, i * height8 + height8 / rowSize, this.getWidth() / (columnSize + 2), this.getHeight() / (rowSize + 2), null);
                }
            }
        }
    }

    private void loadResources() {
        for (ChessmenEnum str : ChessmenEnum.values()) {
            loadImage(str);
        }

        for (ChessmenEnum str : ChessmenEnum.values()) {
            if (MyProperties.getInstance().containsKey(str.toString())) {
                String fileName = MyProperties.getInstance().getProperty(str.toString());
                Sounds sound = new Sounds(fileName);
                ChessmenFactory.getInstance().getChessman(str).setSound(sound);
            }
        }
    }

    private void loadImage(ChessmenEnum imageName) {
        BufferedImage myPicture = null;
        File file = new File("C:\\Users\\nico\\Desktop\\chess\\src\\main\\resources\\" + imageName + ".png");
        try {
            myPicture = ImageIO.read(file);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Couldn't find the image Resource for " + file.getAbsolutePath());
        }

        images.put(imageName, myPicture);
        ChessmenFactory.getInstance().getChessman(imageName).setImage(myPicture);
    }

    public List<Pair<Integer, Integer>> getMoves(int x, int y) {
        List<Pair<Integer, Integer>> possibleMoves = new ArrayList<>();
        List<ChessmenEnum> allMoves = new ArrayList<>();

        ChessmenEnum thisChessman = getChessmenAt(x, y);

        switch (thisChessman) {
            case bKing:
            case wKing:
                for (int i = x - 1; x <= x + 1; x++)
                    for (int j = y - 1; y <= y + 1; y++)
                        allMoves.add(getChessmenAt(i, j));
                break;
            case bQueen:
            case wQueen:
                for (int i = x + 1; i < columnSize; i++) {
                    allMoves.add(getChessmenAt(i, y));
                    if (getChessmenAt(i, y) != ChessmenEnum.free)
                        break;
                }
                for (int i = x - 1; i >= 0; i--) {
                    allMoves.add(getChessmenAt(i, y));
                    if (getChessmenAt(i, y) != ChessmenEnum.free)
                        break;
                }

                for (int i = y + 1; i < rowSize; i++) {
                    allMoves.add(getChessmenAt(i, y));
                    if (getChessmenAt(i, y) != ChessmenEnum.free)
                        break;
                }
                for (int i = y - 1; i >= 0; i--) {
                    allMoves.add(getChessmenAt(x, i));
                    if (getChessmenAt(x, i) != ChessmenEnum.free)
                        break;
                }
                break;
            case bBishop:
            case wBishop:
                break;
            case bKnight:
            case wKnight:
                break;
            case bRook:
            case wRook:
                for (int i = x + 1; i < columnSize; i++) {
                    allMoves.add(getChessmenAt(i, y));
                    if (getChessmenAt(i, y) != ChessmenEnum.free)
                        break;
                }
                for (int i = x - 1; i >= 0; i--) {
                    allMoves.add(getChessmenAt(i, y));
                    if (getChessmenAt(i, y) != ChessmenEnum.free)
                        break;
                }

                for (int i = y + 1; i < rowSize; i++) {
                    allMoves.add(getChessmenAt(i, y));
                    if (getChessmenAt(i, y) != ChessmenEnum.free)
                        break;
                }
                for (int i = y - 1; i >= 0; i--) {
                    allMoves.add(getChessmenAt(x, i));
                    if (getChessmenAt(x, i) != ChessmenEnum.free)
                        break;
                }
                break;
            case bPawn:
            case wPawn:
                break;
            case free:
                break;
        }

        return possibleMoves;
    }

    public void playSoundColumn(int column) {
        for (int j = 0; j < rowSize; j++) {
            ChessmenFactory.getInstance().getChessman(pos[j][column]).playSound();
        }
    }

    public void startSoundLoop() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            int column = 0;

            public void run() {
                playSoundColumn(column++);
                column %= columnSize;
            }
        }, 0, loopTime);
    }

    public void stopSoundLoop() {
        timer.cancel();
    }
}
