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

package minesweeper.controller;

import minesweeper.model.ICell;

import javax.swing.undo.AbstractUndoableEdit;
import java.util.LinkedList;
import java.util.List;

public class RevealFieldCommand extends AbstractUndoableEdit    {

    private List<ICell> cellList;
    private List<Integer> undobuffer;
    private static final long serialVersionUID = 1L;

    public RevealFieldCommand(List<ICell> cellList)    {
        this.cellList = cellList;
        undobuffer = new LinkedList<>();
    }
    
    public void undo()  {
        for(ICell x : cellList)  {
            ((LinkedList<Integer>) undobuffer).push(x.getValue());
            x.setIsRevealed(false);
        }
    }
    
    public void redo()  {
        for(ICell x : cellList)  {
            x.setValue(((LinkedList<Integer>) undobuffer).pollLast());
            x.setIsRevealed(true);
        }
    }
    
    public boolean canUndo() {
        return true;
    }

    public boolean canRedo() {
        return true;
    }
}
