package minesweeper.aview.messages;

import minesweeper.model.IField;

import java.io.Serializable;

/**
 * Created by dohahn on 21.05.2016.
 * A Message which signals that something happened. Contains the new field and currentTime.
 * On receive the tui will print the new file
 */
public class UpdateMessage implements Serializable {

    /**
     * New minesweeper field
     */
    private IField field;

    /**
     * Time of the current game
     */
    private Long currentTime;

    public UpdateMessage(IField field, Long currentTime)   {
        this.currentTime = currentTime;
        this.field = field;
    }

    public IField getField()    { return  field; }

    public Long getCurrentTime()    {
        return currentTime;
    }
}
