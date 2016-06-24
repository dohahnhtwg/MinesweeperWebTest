package minesweeper.aview.messages;

import java.io.Serializable;

/**
 * Created by dohahn on 21.05.2016.
 * Response of a NewAccountRequest which signals if the new Account was accepted
 * On receive the tui will print the result
 */
public class NewAccountResponse implements Serializable {

    /**
     * True if the new account was accepted
     */
    private boolean success;

    public NewAccountResponse(boolean success)  {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }
}
