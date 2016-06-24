package minesweeper.controller.messages.FieldController;

import java.io.Serializable;

/**
 * Created by dohahn on 24.05.2016.
 * Requests to create a new field
 * On receive the FieldController will create a new field and answer with a FieldResponse
 */
public class CreateRequest implements Serializable {

    /**
     * Number of lines for the new field
     */
    private final int lines;

    /**
     * Number of columns for the new field
     */
    private final int columns;

    /**
     * Number of mines for the new field
     */
    private final int nMines;

    public CreateRequest(int lines, int columns, int nMines) {

        this.lines = lines;
        this.columns = columns;
        this.nMines = nMines;
    }

    public int getnMines() {
        return nMines;
    }

    public int getColumns() {
        return columns;
    }

    public int getLines() {
        return lines;
    }
}
