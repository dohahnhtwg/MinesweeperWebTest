package minesweeper.aview.messages;

import java.io.Serializable;

/**
 * Created by dohahn on 25.05.2016.
 * Make intellij happy
 */
public class TimeResponse implements Serializable {

    private Long currentTime;

    public TimeResponse(Long currentTime)   {
        this.currentTime = currentTime;
    }

    public Long getCurrentTime()    {
        return currentTime;
    }
}
