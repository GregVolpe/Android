package com.CS360.GameCenter;

import java.util.ArrayList;
import com.CS360.JavaCore.DatabaseAccess;
import com.CS360.JavaCore.Game;

/**
 * This application carries out the All Games Queries needed to fill the All Games list
 * @author Greg Volpe
 *
 */
public class AllGamesQueryRunnable implements Runnable
{
	private ArrayList<Game> games = new ArrayList<Game>();
	private boolean finished = false;
	private DatabaseAccess DB = new DatabaseAccess();
	private ArrayList<String>num = new ArrayList<String>();
	private ArrayList<String>columnNames = new ArrayList<String>();
	private int count;
	
	/**
	 * This is the default run method for the runnable query.  It builds new game objects and fills all data from the database.
	 */
	@Override
	public void run() 
	{
		count = 0;
		columnNames.add("COUNT(*)");
		num = DB.getData("SELECT COUNT(*) FROM Games", columnNames);
		if(!num.get(0).equals("error"))
		{
			count = Integer.parseInt(num.get(0));
	        for(int i = 1; i<=count; ++i)
	        {
	        	games.add(new Game(i));
	        	
	        }
	        finished = true;
		}

	}
	/**
	 * This method gets all games currently in the list
	 * @return List for Games
	 */
	public ArrayList<Game> getGames()
	{
		return games;
	}
	/**
	 * This method is used to get the number of games that are currently in the list
	 * @return Integer number of games
	 */
	public int getNumberOfGames()
	{
		return this.count;
	}
	/**
	 * This method will return the status of the queries being run.  If all queries are finished, it will return true
	 * @return Whether or not the runnable is finished.
	 */
	public boolean isFinished()
	{
		return finished;
	}

}
