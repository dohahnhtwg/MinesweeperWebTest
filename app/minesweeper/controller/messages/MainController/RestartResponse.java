package minesweeper.controller.messages.MainController;

import minesweeper.model.IField;

import java.io.Serializable;

/**
 * Created by dohahn on 24.05.2016.
 * Response of a RestartRequest with the new field
 */
public class RestartResponse implements Serializable {

    /**
     * The field after restart
     */
    private IField field;

    public RestartResponse(IField field)    {
        this.field = field;
    }

    public IField getField()    {
        return field;
    }
}
