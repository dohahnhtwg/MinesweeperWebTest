package minesweeper.controller.messages;

import minesweeper.model.IField;

import java.io.Serializable;

/**
 * Created by dohahn on 24.05.2016.
 * Response of a RevealCellRequest with the new field
 */
public class RevealCellResponse implements Serializable {

    /**
     * Field with revealed cell
     */
    private IField field;

    public RevealCellResponse(IField field) {
        this.field = field;
    }

    public IField getField() {
        return field;
    }
}
