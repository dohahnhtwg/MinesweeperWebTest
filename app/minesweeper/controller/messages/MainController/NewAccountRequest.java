package minesweeper.controller.messages.MainController;

import java.io.Serializable;

/**
 * Created by dohahn on 21.05.2016.
 * Request to create a new user
 * On receive the MainController tries to create the new user and responses with a NewAccountResponse
 */
public class NewAccountRequest implements Serializable {

    /**
     * Name of the new user
     */
    private String username;

    /**
     * Password of the new user
     */
    private String password;

    public NewAccountRequest(String username, String password)  {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
