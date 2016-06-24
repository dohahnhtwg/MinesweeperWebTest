package minesweeper.database.couchDB;

import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

/**
 * Created by dohahn on 11.04.2016.
 *
 */
class CouchStatistic extends CouchDbDocument {

    private static final long serialVersionUID = -6774088600239708899L;
    @TypeDiscriminator
    private String id;
    private int gamesPlayed;
    private int gamesWon;
    private long timeSpent;
    private long minTime;

    int getGamesPlayed() {
        return gamesPlayed;
    }

    void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    int getGamesWon() {
        return gamesWon;
    }

    void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    long getTimeSpent() {
        return timeSpent;
    }

    void setTimeSpent(long timeSpent) {
        this.timeSpent = timeSpent;
    }

    long getMinTime() {
        return minTime;
    }

    void setMinTime(long minTime) {
        this.minTime = minTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
