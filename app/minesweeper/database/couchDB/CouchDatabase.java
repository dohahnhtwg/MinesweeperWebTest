package minesweeper.database.couchDB;

import com.google.inject.Inject;
import minesweeper.database.DataAccessObject;
import minesweeper.model.ICell;
import minesweeper.model.IField;
import minesweeper.model.IStatistic;
import minesweeper.model.IUser;
import minesweeper.model.impl.Cell;
import minesweeper.model.impl.Field;
import minesweeper.model.impl.Statistic;
import minesweeper.model.impl.User;
import org.ektorp.*;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import play.Logger;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;


public class CouchDatabase implements DataAccessObject {

    private CouchDbConnector db = null;
    private static final int BORDER = 2;

    @Inject
    public CouchDatabase()  {
        HttpClient client = null;
        try {
            client = new StdHttpClient.Builder().url("http://lenny2.in.htwg-konstanz.de:5984").build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        CouchDbInstance dbInstance = new StdCouchDbInstance(client);
        db = dbInstance.createConnector("minesweeper_db_SS2016", true);
    }

    @Override
    public void create(IUser user) {
        CouchUser couchUser = copyUser(user);
        db.create(user.getId(), couchUser);
        System.out.println(couchUser.getRevision());
    }

    @Override
    public IUser read(String username) {
        for(CouchUser u : getAllUsers())    {
            if(u.getName().equals(username))    {
                return copyUser(u);
            }
        }
        return null;
    }

    @Override
    public void update(IUser user) {
        db.update(copyUser(user));
    }

    @Override
    public void delete(IUser user) {
        db.delete(copyUser(getUserById(user.getId())));
    }

    @Override
    public boolean contains(IUser user) {
        IUser dbUser = read(user.getName());
        return dbUser != null;
    }

    private IUser copyUser(CouchUser couchUser)  {
        IUser user = new User();
        user.setId(couchUser.getId());
        user.setEncryptedPassword(couchUser.getEncryptedPassword());
        user.setName(couchUser.getName());
        user.setSalt(couchUser.getSalt());
        user.setStatistic(copyStatistic(couchUser.getStatistic()));
        user.setPlayingField(copyField(couchUser.getPlayingField()));
        return user;
    }

    private IStatistic copyStatistic(CouchStatistic couchStatistic) {
        IStatistic statistic = new Statistic();
        statistic.setId(couchStatistic.getId());
        statistic.setGamesPlayed(couchStatistic.getGamesPlayed());
        statistic.setGamesWon(couchStatistic.getGamesWon());
        statistic.setMinTime(couchStatistic.getMinTime());
        statistic.setTimeSpent(couchStatistic.getTimeSpent());
        return statistic;
    }

    private IField copyField(CouchField couchField) {
        IField playingField = new Field();
        playingField.setFieldID(couchField.getId());
        playingField.setColumns(couchField.getColumns());
        playingField.setLines(couchField.getColumns());
        playingField.setnMines(couchField.getnMines());

        ICell[][] field = new ICell[couchField.getColumns() + BORDER][couchField.getLines() + BORDER];
        for(int i=0; i < couchField.getColumns() + BORDER; i++)   {
            for(int j=0; j < couchField.getLines() + BORDER; j++)  {
                field[i][j] = copyCell(couchField.getPlayingField()[i][j]);
            }
        }
        playingField.setPlayingField(field);
        return playingField;
    }

    private CouchStatistic copyStatistic(IStatistic statistic) {
        CouchStatistic couchStatistic = new CouchStatistic();
        couchStatistic.setId(statistic.getId());
        couchStatistic.setGamesPlayed(statistic.getGamesPlayed());
        couchStatistic.setGamesWon(statistic.getGamesWon());
        couchStatistic.setMinTime(statistic.getMinTime());
        couchStatistic.setTimeSpent(statistic.getTimeSpent());
        return couchStatistic;
    }

    private ICell copyCell(CouchCell couchCell) {
        ICell cell = new Cell(couchCell.getValue());
        cell.setId(couchCell.getId());
        cell.setIsRevealed(couchCell.isRevealed());
        return cell;
    }

    private CouchUser copyUser(IUser user)  {
        CouchUser couchUser = getUserById(user.getId());
        if(couchUser == null) {
            couchUser = new CouchUser();
        }
        couchUser.setId(user.getId());
        couchUser.setEncryptedPassword(user.getEncryptedPassword());
        couchUser.setSalt(user.getSalt());
        couchUser.setName(user.getName());
        couchUser.setStatistic(copyStatistic(user.getStatistic()));
        couchUser.setPlayingField(copyField(user.getPlayingField()));
        return couchUser;
    }

    private CouchField copyField(IField playingField) {
        CouchField couchField = new CouchField();
        couchField.setId(playingField.getFieldID());
        couchField.setColumns(playingField.getColumns());
        couchField.setLines(playingField.getLines());
        couchField.setnMines(playingField.getnMines());

        CouchCell[][] field = new CouchCell[playingField.getColumns() + BORDER][playingField.getLines() + BORDER];
        for(int i=0; i < couchField.getColumns() + BORDER; i++)   {
            for(int j=0; j < couchField.getLines() + BORDER; j++)  {
                field[i][j] = copyCell(playingField.getPlayingField()[i][j]);
            }
        }
        couchField.setPlayingField(field);
        return couchField;
    }

    private CouchCell copyCell(ICell cell) {
        CouchCell couchCell = new CouchCell();
        couchCell.setId(cell.getId());
        couchCell.setValue(cell.getValue());
        couchCell.setRevealed(cell.isRevealed());
        return couchCell;
    }

    private List<CouchUser> getAllUsers()   {
        List<CouchUser> users = new ArrayList<>();

        ViewQuery query = new ViewQuery().allDocs();
        ViewResult vr = db.queryView(query);

        for (ViewResult.Row r : vr.getRows()) {
            users.add(getUserById(r.getId()));
        }
        return users;
    }

    public CouchUser getUserById(String id) {
        CouchUser user = db.find(CouchUser.class, id);
        if (user == null) {
            return null;
        }
        return user;
    }
}
