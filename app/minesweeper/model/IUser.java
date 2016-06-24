package minesweeper.model;

public interface IUser {
	/**
	 * Returns the user name
	 * @return user name
	 */
    String getName();
    
    /**
     * Changes the user name
     * @param name new user name
     */
    void setName(String name);
    
    /**
     * Changes the password
     * @param password new passwors
     */
    void setPassword(String password);
    
    /**
     * Returns the encrypted password
     * @return encrypted password
     */
    byte[] getEncryptedPassword();

    /**
     * setEncryptedPassword
     * @param encryptedPassword Make intellij happy
     */
    void setEncryptedPassword(byte[] encryptedPassword);

    /**
     * Authenticates the user
     * @param name Make intellij happy
     * @param password Make intellij happy
     * @return Returns true if password and name is correct
     */
    boolean authenticate(String name, String password);
    
    /**
     * Returns statistic of this user
     * @return bound IStatistic instance
     */
    IStatistic getStatistic();
    
    /**
     * Changes the statistic
     * @param statistic reference to IStatistic class
     */
    void setStatistic(IStatistic statistic);
    
    /**
     * Get the field of this user
     * @return Returns the field of this user
     */
    IField getPlayingField();
    
    /**
     * Changes the field of this user
     * @param playingField Make intellij happy
     */
    void setPlayingField(IField playingField);

    /**
     * Get user id
     * @return int
     */
    String getId();

    /**
     * Set user id
     * @param id int
     */
    void setId(String id);

    /**
     * Get salt
     * @return byte[]
     */
    byte[] getSalt();

    /**
     * Set salt
     * @param salt byte[]
     */
    void setSalt(byte[] salt);
}
