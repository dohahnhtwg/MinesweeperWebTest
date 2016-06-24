package minesweeper.controller.messages.MainController;

import java.io.Serializable;

/**
 * Created by dohahn on 21.05.2016.
 * Requests a new game with different size
 * On receive the mainController starts a new game with given size
 */
public class NewSizeRequest implements Serializable {

    /**
     * Number of lines
     */
    private Integer lines;
    /**
     * Number of columns
     */
    private Integer columns;

    /**
     * Number of mines
     */
    private Integer mines;

    public NewSizeRequest(Integer lines, Integer columns, Integer mines)    {
        this.lines = lines;
        this.columns = columns;
        this.mines = mines;
    }

    public Integer getLines()   {
        return lines;
    }

    public Integer getColumns() {
        return columns;
    }

    public Integer getMines()   { return mines; }
}
