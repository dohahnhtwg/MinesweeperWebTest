package minesweeper.controller.impl;

import akka.actor.UntypedActor;
import minesweeper.controller.RevealFieldCommand;
import minesweeper.controller.messages.*;
import minesweeper.controller.messages.FieldController.CreateRequest;
import minesweeper.controller.messages.FieldController.FieldRequest;
import minesweeper.controller.messages.FieldController.NewFieldMessage;
import minesweeper.controller.messages.FieldController.RestartRequest;
import minesweeper.controller.messages.MainController.FieldResponse;
import minesweeper.model.ICell;
import minesweeper.model.IField;
import minesweeper.model.impl.Cell;
import minesweeper.model.impl.Field;

import javax.swing.undo.UndoManager;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

class FieldController extends UntypedActor {

    private IField field;
    private UndoManager undoManager;
    private static final int DEFDIMENS = 9;
    private static final int DEFNMINES = 10;
    private static final int BORDER = 2;
    private static final int THREE_CELL_LEN = 3;

    public FieldController()    {
        field = new Field();
        create(DEFDIMENS, DEFDIMENS, DEFNMINES);
        undoManager = new UndoManager();
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof FieldRequest) {
            getSender().tell(new FieldResponse(field), self());
            return;
        }
        if(message instanceof RestartRequest)   {
            restart();
            getSender().tell(new FieldResponse(field), self());
            return;
        }
        if(message instanceof CreateRequest)    {
            CreateRequest request = (CreateRequest)message;
            create(request.getLines(), request.getColumns(), request.getnMines());
            getSender().tell(new FieldResponse(field), self());
        }
        if(message instanceof RedoRequest)  {
            redo();
            getSender().tell(new FieldResponse(field), self());
        }
        if(message instanceof UndoRequest)  {
            undo();
            getSender().tell(new FieldResponse(field), self());
        }
        if(message instanceof RevealCellRequest)   {
            revealCell((RevealCellRequest)message);
            getSender().tell(new RevealCellResponse(field), self());
        }
        if(message instanceof NewFieldMessage)  {
            this.field = ((NewFieldMessage)message).getField();
        }
        unhandled(message);
    }

    private void restart()   {
        create(field.getLines(), field.getColumns(), field.getnMines());
    }

    private void create(int lines, int columns, int nMines)    {
        field.setIsGameOver(false);
        field.setIsVictory(false);
        field.setLines(lines);
        field.setColumns(columns);
        field.setnMines(nMines);
        ICell[][] field = new ICell[lines + BORDER][columns + BORDER];
        for (int i = 0; i < field.length; i++)   {
            for (int j = 0; j < field[0].length; j ++)    {
                field[i][j] = new Cell(0);
            }
        }
        fill(field, lines, columns, nMines);
        this.field.setPlayingField(field);
    }

    private void revealCell(RevealCellRequest msg) {
        if(field.isGameOver() || field.isVictory()) {
            return;
        }
        if(field.getPlayingField()[msg.getX()][msg.getY()].getValue() == -1) {
            field.getPlayingField()[msg.getX()][msg.getY()].setIsRevealed(true);
            field.setIsGameOver(true);
        } else {
            List<ICell> revealFieldCommandList = new LinkedList<>();
            revealCellHelp(msg.getX(), msg.getY(), revealFieldCommandList);
            field.setIsVictory(checkVictory());
            undoManager.addEdit(new RevealFieldCommand(revealFieldCommandList));
        }
    }

    private void undo() {
        if (undoManager.canUndo()) {
            undoManager.undo();
        }
    }

    private void redo() {
        if (undoManager.canRedo()) {
            undoManager.redo();
        }
    }

    private void fill(ICell[][] field, int lines, int columns, int nMines) {
        Long timeForHash = System.nanoTime();
        int hash = timeForHash.hashCode();
        Random rnd = new Random(hash);
        for (int i = 0; i < nMines; i++)    {
            generateMines(field, lines, columns, rnd);
        }
        generateMinesAround(field);
    }

    private void generateMines(ICell[][] field, int lines, int columns, Random rnd)    {
        int x = rnd.nextInt(lines /*- 1*/) + 1;
        int y = rnd.nextInt(columns /*- 1*/) + 1;
        if (field[x][y].getValue() != -1)    {
            field[x][y].setValue(-1);
        } else  {
            generateMines(field, lines, columns, rnd);
        }
    }

    private void generateMinesAround(ICell[][] field) {
        for (int i = 1; i < field.length - 1; i++)   {
            for (int j = 1; j < field[0].length - 1; j ++)   {
                if (field[i][j].getValue() != -1)    {
                    field[i][j].setValue(nMinesAroundAPoint(field, i, j));
                }
            }
        }
    }

    private int nMinesAroundAPoint(ICell[][] field, int x, int y)    {
        int mines = 0;
        for (int i = 0; i < THREE_CELL_LEN; i++) {
            for (int j = 0; j < THREE_CELL_LEN; j++) {
                if (field[x - 1 + i][y - 1 + j].getValue() == -1)    {
                    mines++;
                }
            }
        }
        return mines;
    }

    private void revealCellHelp(int x, int y, List<ICell> revelalFieldCommandList)  {
        field.getPlayingField()[x][y].setIsRevealed(true);
        ((LinkedList<ICell>) revelalFieldCommandList).push(field.getPlayingField()[x][y]);
        if(field.getPlayingField()[x][y].getValue() <= 0)  {
            List<Point> fieldsAround = getCellsAround(x, y);
            for(Point field : fieldsAround) {
                if(checkCellInField(field) && !this.field.getPlayingField()[field.x][field.y].isRevealed()) {
                    revealCellHelp(field.x, field.y, revelalFieldCommandList);
                }
            }
        }
    }

    private List<Point> getCellsAround(int x, int y) {
        List<Point> fieldsAround = new ArrayList<>();
        fieldsAround.add(new Point(x - 1, y));
        fieldsAround.add(new Point(x + 1, y));
        fieldsAround.add(new Point(x - 1, y - 1));
        fieldsAround.add(new Point(x - 1, y + 1));
        fieldsAround.add(new Point(x + 1, y + 1));
        fieldsAround.add(new Point(x + 1, y - 1));
        fieldsAround.add(new Point(x , y - 1));
        fieldsAround.add(new Point(x , y + 1));
        return fieldsAround;
    }

    private boolean checkCellInField(Point cell)    {
        return (cell.getX() > 0 && cell.getY() > 0)
                && (cell.getX() < field.getPlayingField().length - 1
                && cell.getY() < field.getPlayingField()[(int) cell.getX()].length - 1);
    }

    private boolean checkVictory()  {
        int requirement = field.getLines() * field.getColumns() - field.getnMines();
        int current = 0;

        for (int i = 0; i < field.getPlayingField().length; i++)   {
            for (int j = 0; j < field.getPlayingField()[0].length; j ++)    {
                if(field.getPlayingField()[i][j].isRevealed()) {
                    current++;
                }
            }
        }

        return current == requirement;
    }
}
