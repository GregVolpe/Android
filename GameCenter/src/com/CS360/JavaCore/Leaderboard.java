package com.CS360.JavaCore;

import java.util.ArrayList;


/**
 * Class to represent a leaderboard for a specific game
 * @author Greg Volpe
 *
 */
public class Leaderboard 
{
	private String gameName;
	private DatabaseAccess DB= new DatabaseAccess();
	private int gameID;
	/**
	 * Public constructor to create a new Leaderboard object
	 * @param name Game Name
	 */
	public Leaderboard(String name, int mGameID)
	{
		gameName = name;
		gameID = mGameID;
	}
	/**
	 * Method to get the top ten players of the game
	 * @return String array of the top ten players sorted in descending order starting at highest rank at index 0
	 */
	public ArrayList<String> getTopTen()
	{
		//instantiate variables
		ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
		ArrayList<String>columnNames=new ArrayList<String>();
		ArrayList<String> rankList = new ArrayList<String>();
		//add column names needed for database extraction
		columnNames.add("GamerTag");
		columnNames.add("wins");
		columnNames.add("gameName");
		//get top ten players for the game
		results = DB.getTopTen(gameID, columnNames);
		
		//compile the results to the rank list
		for(int i =0;i<results.size();i++)
		{
			String temp = "";
			for(int j=0;j<2;j++)
			{
				temp+=results.get(i).get(j);
				temp+="    ";
			}
			rankList.add(temp);
		}
		return rankList;
	}

}
