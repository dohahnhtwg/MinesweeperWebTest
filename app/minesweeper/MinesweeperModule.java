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

package minesweeper;

import com.google.inject.AbstractModule;
import minesweeper.controller.impl.MainController;
import minesweeper.database.DataAccessObject;
import minesweeper.database.couchDB.CouchDatabase;
import minesweeper.model.IField;
import minesweeper.controller.IMainController;
import minesweeper.model.impl.Field;


public class MinesweeperModule extends AbstractModule {

    @Override
    protected void configure() {
        
        bind(IMainController.class).to(MainController.class);
        bind(IField.class).to(Field.class);
        bind(DataAccessObject.class).to(CouchDatabase.class);
        
    }

}
