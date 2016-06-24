/* This file is part of Minesweeper.
 * 
 * Minesweeper is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Minesweeper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Minesweeper.  If not, see <http://www.gnu.org/licenses/>.
 */

package minesweeper.controller.impl;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.pattern.Patterns;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import minesweeper.aview.messages.*;
import minesweeper.controller.IMainController;
import minesweeper.controller.messages.*;
import minesweeper.controller.messages.FieldController.CreateRequest;
import minesweeper.controller.messages.FieldController.FieldRequest;
import minesweeper.controller.messages.FieldController.NewFieldMessage;
import minesweeper.controller.messages.FieldController.RestartRequest;
import minesweeper.controller.messages.MainController.*;
import minesweeper.database.DataAccessObject;
import minesweeper.model.IStatistic;
import minesweeper.model.IUser;
import minesweeper.model.impl.User;
import scala.concurrent.Future;
import scala.concurrent.Await;
import akka.util.Timeout;
import scala.concurrent.duration.Duration;


@Singleton
public class MainController extends UntypedActor implements IMainController {

    private ActorRef fieldController;
    private IUser user;
    private IStatistic statistic;
    private boolean isStarted = false;
    private DataAccessObject database;
    private Long elapsedTime = 0L;

    @Inject
    public MainController(DataAccessObject database)  {
        this.database = database;
        if (database.contains(new User("Default", "Default"))) {
            this.user = database.read("Default");
        } else {
            this.user = new User("Default", "Default");
            database.create(this.user);
        }
        this.fieldController = getContext().actorOf(Props.create(FieldController.class), "fieldController");
        fieldController.tell(new NewFieldMessage(this.user.getPlayingField()), self());
        this.statistic = this.user.getStatistic();
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof FinishGameMessage)    {
            finishGame();
            return;
        }
        if(message instanceof LoginRequest) {
            logIn((LoginRequest)message);
            return;
        }
        if(message instanceof NewAccountRequest)    {
            addNewAccount((NewAccountRequest)message);
            return;
        }
        if(message instanceof NewGameRequest)   {
            create();
            return;
        }
        if(message instanceof NewSizeRequest)   {
            NewSizeRequest msg = (NewSizeRequest)message;
            create(msg.getLines(), msg.getColumns(), msg.getMines());
            return;
        }
        if(message instanceof RedoRequest)  {
            redo((RedoRequest)message);
            return;
        }
        if(message instanceof UndoRequest)  {
            undo((UndoRequest)message);
            return;
        }
        if(message instanceof RevealCellRequest)    {
            revealCell((RevealCellRequest)message);
            return;
        }
        if(message instanceof StatisticRequest) {
            getContext().parent().tell(new StatisticResponse(statistic), self());
            return;
        }
        if(message instanceof UpdateRequest)    {
            fieldController.tell(new FieldRequest(), self());
            return;
        }
        if(message instanceof FieldResponse)    {
            FieldResponse response = (FieldResponse)message;
            getContext().parent().tell(new UpdateMessage(response.getField(), getCurrentTime()), self());
            return;
        }
        if(message instanceof RevealCellResponse)  {
            handleRevealCellResponse((RevealCellResponse)message);
            return;
        }
        if(message instanceof TimeResponse) {
            getContext().parent().tell(new TimeResponse(getCurrentTime()), self());
            return;
        }
        unhandled(message);
    }

    private void finishGame() {
        database.update(user);
    }

    private void logIn(LoginRequest msg) {
        IUser userFromDb = database.read(msg.getUsername());
        if(userFromDb == null)    {
            getContext().parent().tell(new LoginResponse(false), self());
        } else {
            user = userFromDb;
            user.authenticate(msg.getUsername(), msg.getPassword());
            fieldController.tell(new NewFieldMessage(userFromDb.getPlayingField()), self());
            statistic = userFromDb.getStatistic();
            getContext().parent().tell(new LoginResponse(true), self());
            fieldController.tell(new FieldRequest(), self());
        }
    }

    private void addNewAccount(NewAccountRequest msg) {
        if (msg.getUsername().isEmpty() || msg.getPassword().isEmpty()) {
            getContext().parent().tell(new NewAccountResponse(false), self());
        } else {
            Timeout timeout = new Timeout(Duration.create(5, "seconds"));
            Future<Object> future = Patterns.ask(fieldController, new CreateRequest(9, 9, 10), timeout);
            FieldResponse result = null;
            try {
                result = (FieldResponse) Await.result(future, timeout.duration());
            } catch (Exception e) {
                e.printStackTrace();
            }
            IUser userForDb = new User(msg.getUsername(), msg.getPassword(), result.getField());
            if(database.contains(userForDb)) {
                getContext().parent().tell(new NewAccountResponse(false), self());
            } else {
                database.create(userForDb);
                getContext().parent().tell(new NewAccountResponse(true), self());
            }
        }
    }

    private void create() {
        fieldController.tell(new RestartRequest(), self());
    }

    private void create(int lines, int columns, int nMines) {
        fieldController.tell(new CreateRequest(lines, columns, nMines), self());
    }

    private void undo(UndoRequest msg) {
        fieldController.tell(msg, self());
    }

    private void redo(RedoRequest msg) {
        fieldController.tell(msg, self());
    }

    private void revealCell(RevealCellRequest msg) {
        if (!isStarted) {
            startTimer();
        }
        fieldController.tell(msg, self());
    }

    private void handleRevealCellResponse(RevealCellResponse response) {
        if(response.getField().isGameOver()) {
            stopTimer();
            statistic.updateStatistic(false, elapsedTime);
        } else {
            if(response.getField().isVictory())  {
                stopTimer();
                statistic.updateStatistic(true, elapsedTime);
            }
        }
        getContext().parent().tell(new UpdateMessage(response.getField(), getCurrentTime()), self());
    }

    private void startTimer() {
        isStarted = true;
        elapsedTime = System.currentTimeMillis();
    }

    private void stopTimer() {
        isStarted = false;
        elapsedTime = System.currentTimeMillis() - elapsedTime;
    }

    private Long getCurrentTime() {
        if (isStarted) {
            return (System.currentTimeMillis() - elapsedTime) / 1000;
        }
        return elapsedTime / 1000;
    }
}
