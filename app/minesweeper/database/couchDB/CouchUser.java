package minesweeper.database.couchDB;

import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

/**
 * Created by dohahn on 11.04.2016.
 *
 */
class CouchUser extends CouchDbDocument {

    private static final long serialVersionUID = 5228488178609019609L;
    @TypeDiscriminator
    private String id;
    private String name;
    private byte[] encryptedPassword;
    private byte[] salt;
    private CouchField playingField;
    private CouchStatistic statistic;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    byte[] getEncryptedPassword() {
        return encryptedPassword;
    }

    void setEncryptedPassword(byte[] encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    byte[] getSalt() {
        return salt;
    }

    void setSalt(byte[] salt) {
        this.salt = salt;
    }

    CouchField getPlayingField() {
        return playingField;
    }

    void setPlayingField(CouchField playingField) {
        this.playingField = playingField;
    }

    CouchStatistic getStatistic() {
        return statistic;
    }

    void setStatistic(CouchStatistic statistic) {
        this.statistic = statistic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
