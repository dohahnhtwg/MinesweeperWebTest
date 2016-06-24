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

package minesweeper.model;

/**
 * This interface represent a simple cell.
 * @author Dominik Hahn & Pavel Kravetskiy
 *
 */
public interface ICell {

    /**
     * This Method returns a number between -1 and 6. -1 stands for a Mine
     * while 0-6 for the number of Mines around the actual field.
     * @return the Value of the Cell. 
     */
    int getValue();
    
    /** 
     * With this Method the value of a Cell can be changed.
     * @param newValue is the new Value. -1 stands for a Mine and 0-6 for the 
     *        number of Mines around.
     */
    void setValue(int newValue);
    
    /**
     * This Method says whether this cell is revealed or not.
     * @return true if the Cell is Revealed.
     */
    boolean isRevealed();
    
    /**
     * This Method changed the actual is Revealed status of the Cell.
     * @param isRevealed isRev
     */
    void setIsRevealed(boolean isRevealed);
    
    /**
     * Returns a formatted String for a given Cell.
     * @return String representing a Cell.
     */
    String toString();
    
    /**
     * Indicates whether some other Cell is "equal to" this one.
     * @return true if given Cells are equals.
     */
    boolean equals(Object obj);
    
    /**
     * Returns a hash code value for the object.
     * @return a hash code.
     */
    int hashCode();

    /**
     * Get cell id
     * @return int
     */
    String getId();

    /**
     * Set cell id
     * @param id int
     */
    void setId(String id);
}
