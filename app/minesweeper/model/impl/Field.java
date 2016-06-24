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

package minesweeper.model.impl;

import com.google.inject.Inject;
import minesweeper.model.ICell;
import minesweeper.model.IField;

import java.util.UUID;

public class Field implements IField{

    private static final int BORDER = 2;
    private String fieldID;
    private ICell[][] playingField;
    private int nMines;
    private int lines;
    private int columns;
    private boolean isGameOver = false;
    private boolean isVictory = false;
    
    @Inject
    public Field()  {
        this.fieldID = UUID.randomUUID().toString();
    }

    public Field(int lines, int columns, int nMines)    {
        this.fieldID = UUID.randomUUID().toString();
        setLines(lines);
        setColumns(columns);
        setnMines(nMines);
    }
    public Field(String fieldID, Integer lines, Integer columns, Integer nMines, Boolean isGameOver, Boolean isVictory)  {
        this.fieldID = fieldID;
        setLines(lines);
        setColumns(columns);
        setnMines(nMines);
        setIsGameOver(isGameOver);
        setIsVictory(isVictory);
    }

    @Override
    public String toString()    {
        StringBuilder sb = new StringBuilder("\n");
        sb.append(String.format("%5s\n", "Line\n"));
        for (int i = 1; i <= lines; i++) {
            sb.append(String.format("%5d", i));
            for (int j = 1; j <= columns; j++)  {
                sb.append(playingField[i][j].toString());
            }
            sb.append("\n");
        }
        sb.append("     ");
        for (int j = 1; j <= columns; j++)  {
            sb.append(String.format(" %2d ", j));
        }
        sb.append(String.format("       %s", "Column\n"));
        return sb.toString();
    }

    @Override
    public int getLines()   {
        return lines;
    }

    @Override
    public void setLines(int lines) {
        if(lines < 1)   {
            throw new IllegalArgumentException("Field needs at least 1 line");
        }
        this.lines = lines;
    }

    @Override
    public int getColumns() {
        return columns;
    }

    @Override
    public void setColumns(int columns) {
        if(columns < 1) {
            throw new IllegalArgumentException("Field needs at least 1 column");
        }
        this.columns = columns;
    }

    @Override
    public int getnMines() { return nMines; }

    @Override
    public void setnMines(int nMines) {
        if(nMines < 0)  {
            throw new IllegalArgumentException("Field cant have negative bunvers of mines");
        }
        this.nMines = nMines;
    }

    @Override
    public String getFieldID() {
        return fieldID;
    }

    @Override
    public void setFieldID(String fieldID) {
        this.fieldID = fieldID;
    }

    @Override
    public void setPlayingField(ICell[][] playingField) {
        if(playingField == null)    {
            throw new IllegalArgumentException("Field cant be null");
        }
        validateFieldLines(playingField);
        setLines(playingField.length - BORDER);
        validateFieldColumns(playingField);
        setColumns(playingField[0].length - BORDER);
        setnMines(countMines(playingField));
        this.playingField = playingField;
    }

    private int countMines(ICell[][] playingField) {
        int mines = 0;
        for (int i = 1; i <= lines; i++) {
            for (int j = 1; j <= columns; j++)  {
                if(playingField[i][j].getValue() == -1) {
                    mines ++;
                }
            }
        }
        return mines;
    }

    @Override
    public boolean isVictory() { return isVictory; }

    @Override
    public void setIsVictory(boolean isVictory) { this.isVictory = isVictory; }

    @Override
    public boolean isGameOver() { return isGameOver; }

    @Override
    public void setIsGameOver(boolean isGameOver) { this.isGameOver = isGameOver; }

    @Override
    public ICell[][] getPlayingField() {
        return this.playingField;
    }

    private void validateFieldColumns(ICell[][] playingField) {
        for (ICell[] aPlayingField : playingField) {
            if(aPlayingField.length - BORDER < 1)   {
                throw new IllegalArgumentException("Field needs at least one column and two border columns");
            }
        }
    }

    private void validateFieldLines(ICell[][] playingField) {
        if(playingField.length - BORDER < 1)    {
            throw new IllegalArgumentException("Field needs at least one line and two border lines");
        }
    }
}
