package minesweeper.aview.messages;

import java.io.Serializable;

/**
 * Created by dohahn on 21.05.2016.
 * A Message with tui input
 * On receive the tui will handle the new input
 */
public class InputMessage implements Serializable {

    /**
     * Input to proceed
     */
    private String input;

    public InputMessage(String input)  {
        this.input = input;
    }

    public String getInput()    {
        return  input;
    }
}
