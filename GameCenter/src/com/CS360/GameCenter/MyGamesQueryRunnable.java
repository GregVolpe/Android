package com.CS360.GameCenter;

import java.util.ArrayList;
import com.CS360.JavaCore.Account;
import com.CS360.JavaCore.DatabaseAccess;

/**
 * This method is used to run the My Games Tab queries.
 * @author Greg Volpe
 *
 */
public class MyGamesQueryRunnable implements Runnable
{

	private ArrayList<String> stuff = new ArrayList<String>();
	private Account myAccount = new Account();
	private DatabaseAccess DB = new DatabaseAccess();
	private ArrayList<String>columnNames = new ArrayList<String>();
	
	/**
	 * This is the default run method called when the thread is started
	 */
	@Override
	public void run() 
	{
		columnNames.add("GameName");
		stuff = DB.getMyGames(myAccount.getGamerTag(), columnNames);
	}
	/**
	 * This method is used to set the account to the current user's account
	 * @param anAccount
	 */
	public void setAccount(Account anAccount)
	{
		myAccount = anAccount;
	}
	/**
	 * This method is used to return the current list of games owned by the user
	 * @return List of all games owned by the user
	 */
	public ArrayList<String> getMyGames()
	{
		return stuff;
	}


}

