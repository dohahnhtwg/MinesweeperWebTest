package minesweeper.aview.messages;

import java.io.Serializable;

/**
 * Created by dohahn on 21.05.2016.
 * Response of a LoginRequest which signal if credentials was accepted
 * On receive the tui will print the result
 */
public class LoginResponse implements Serializable {

    /**
     * True if the credentials was accepted
     */
    private boolean success;

    public LoginResponse(boolean success)  {
        this.success = success;
    }

    public boolean isSuccess()  {
        return success;
    }
}
