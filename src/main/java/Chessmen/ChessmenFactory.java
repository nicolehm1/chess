package Chessmen;

import java.util.EnumMap;
import java.util.HashMap;

/**
 * Created by Nico on 14.08.2015.
 */
public class ChessmenFactory {
    private static ChessmenFactory ourInstance = new ChessmenFactory();
    private EnumMap<ChessmenEnum, Chessmen> chessmenMap = new EnumMap<>(ChessmenEnum.class);

    public static ChessmenFactory getInstance() {
        return ourInstance;
    }

    private ChessmenFactory() {
    }

    public Chessmen getChessman(ChessmenEnum type) {
        if (chessmenMap.containsKey(type))
            return chessmenMap.get(type);
        else {
            Chessmen chessmen = new Chessmen(type);
            chessmenMap.put(type,chessmen);
            return chessmen;
        }
    }
}
