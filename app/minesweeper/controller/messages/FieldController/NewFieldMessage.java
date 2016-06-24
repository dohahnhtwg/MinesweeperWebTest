package minesweeper.controller.messages.FieldController;

import minesweeper.model.IField;

import java.io.Serializable;

/**
 * Created by dohahn on 24.05.2016.
 * Message with a new field
 * On receive the FieldController change the actual field with given field
 */
public class NewFieldMessage implements Serializable {

    /**
     * New field
     */
    private IField field;

    public NewFieldMessage(IField field)    {
        this.field = field;
    }

    public IField getField()    {
        return field;
    }
}
