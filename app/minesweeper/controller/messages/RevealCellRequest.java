package minesweeper.controller.messages;

import java.io.Serializable;

/**
 * Created by dohahn on 21.05.2016.
 * Requests to reveal given cell
 * On receive the MainController starts the timer if hes not already running and pass the request to FieldControler
 * On receive the FieldController reveals given Cell
 */
public class RevealCellRequest implements Serializable {

    /**
     * x-coordinate
     */
    private Integer x;

    /**
     * y-coordinate
     */
    private Integer y;

    public RevealCellRequest(Integer x, Integer y)  {
        this.x = x;
        this.y = y;
    }

    public Integer getX()   {
        return x;
    }

    public Integer getY()  {
        return y;
    }
}
