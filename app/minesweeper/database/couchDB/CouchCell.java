package minesweeper.database.couchDB;

import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

/**
 * Created by dohahn on 11.04.2016.
 *
 */
class CouchCell extends CouchDbDocument {


    private static final long serialVersionUID = 7652109675374624746L;
    @TypeDiscriminator
    private String id;
    private int value;
    private boolean isRevealed = false;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    boolean isRevealed() {
        return isRevealed;
    }

    void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
