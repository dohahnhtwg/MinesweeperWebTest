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

    private String user;

    public LoginResponse(boolean success, String user)  {
        this.success = success;
        this.user = user;
    }

    public boolean isSuccess()  {
        return success;
    }

    public String getUser() {
        return user;
    }
}
