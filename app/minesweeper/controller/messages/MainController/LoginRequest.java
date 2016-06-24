package minesweeper.controller.messages.MainController;

import java.io.Serializable;

/**
 * Created by dohahn on 21.05.2016.
 * Request for a Login with given credentials
 * On receive the MainController will check the credentials and answer with LoginResponse
 */
public class LoginRequest implements Serializable {

    /**
     * Name of user
     */
    private String username;

    /**
     * Password of user
     */
    private String password;

    public LoginRequest(String username, String password)   {
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
