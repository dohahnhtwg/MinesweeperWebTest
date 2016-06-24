package minesweeper.controller.messages.MainController;

import java.io.Serializable;

/**
 * Created by dohahn on 21.05.2016.
 * A message which signals the MainController that the user wants to finish the game
 * On receive the MainController saves the actual context in Database
 */
public class FinishGameMessage implements Serializable {
}
