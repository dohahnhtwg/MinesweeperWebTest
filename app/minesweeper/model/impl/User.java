package minesweeper.model.impl;

import minesweeper.model.IField;
import minesweeper.model.IStatistic;
import minesweeper.model.IUser;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.UUID;

public class User implements IUser {
    private String id;
    private String name;
    private byte[] encryptedPassword;
    private byte[] salt;
    private IField playingField;
    private IStatistic statistic;

    public User()   {
        this.id = UUID.randomUUID().toString();
    }

    public User(String name, String password) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        try {
            this.salt = this.generateSalt();
            this.encryptedPassword = generateEncryptedPassword(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException exc) {
            exc.printStackTrace();
        }
        playingField = new Field();
        statistic = new Statistic();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public byte[] getSalt() {
        return salt;
    }

    @Override
    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setPassword(String password) {
        try {
            this.encryptedPassword = generateEncryptedPassword(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public byte[] getEncryptedPassword() {
        return this.encryptedPassword;
    }

    @Override
    public void setEncryptedPassword(byte[] encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    @Override
    public boolean authenticate(String name, String password) {
        try {
            byte[] encryptedAttemptedPassword = generateEncryptedPassword(password);
            return this.name.equals(name) && Arrays.equals(this.encryptedPassword, encryptedAttemptedPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException exc) {
            exc.printStackTrace();
            return false;
        }
    }

    @Override
    public IStatistic getStatistic() {
        return this.statistic;
    }

    @Override
    public void setStatistic(IStatistic statistic) {
        this.statistic = statistic;
    }

    private byte[] generateEncryptedPassword(String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        int derivedKeyLength = 160;
        int iterations = 20000;
        KeySpec spec = new PBEKeySpec(password.toCharArray(), this.salt, iterations, derivedKeyLength);
        String algorithm = "PBKDF2WithHmacSHA1";
        SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);
        return factory.generateSecret(spec).getEncoded();
    }

    private byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return salt;
    }

    public IField getPlayingField() {
        return playingField;
    }

    public void setPlayingField(IField playingField) {
        this.playingField = playingField;
    }
}
