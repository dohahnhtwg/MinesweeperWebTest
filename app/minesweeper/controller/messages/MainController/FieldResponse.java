package minesweeper.controller.messages.MainController;

import minesweeper.model.IField;

import java.io.Serializable;

/**
 * Created by dohahn on 24.05.2016.
 * Response with the actual Field
 * On receive the MainController will send a UpdateMessage to parent
 */
public class FieldResponse implements Serializable {

    /**
     * The actual field
     */
    private IField field;

    public FieldResponse(IField field)  {
        this.field = field;
    }

    public IField getField()    {
        return field;
    }

}
