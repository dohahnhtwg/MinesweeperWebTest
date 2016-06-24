package minesweeper.database.couchDB;

import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

/**
 * Created by dohahn on 11.04.2016.
 *
 */
class CouchField extends CouchDbDocument {

    private static final long serialVersionUID = 3243503736951751825L;
    @TypeDiscriminator
    private String id;
    private CouchCell[][] playingField;
    private int nMines;
    private int lines;
    private int columns;

    CouchCell[][] getPlayingField() {
        return playingField;
    }

    void setPlayingField(CouchCell[][] playingField) {
        this.playingField = playingField;
    }

    int getnMines() {
        return nMines;
    }

    void setnMines(int nMines) {
        this.nMines = nMines;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
