package minesweeper.database;

import minesweeper.model.IUser;

public interface DataAccessObject {
	
	void create(IUser user);
	IUser read(String username);
	void update(IUser user);
	void delete(IUser user);
	boolean contains(IUser user);
}
