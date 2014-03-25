package com.CS360.JavaCore;

import java.util.ArrayList;
/**
 * Simple class to represent a Game object
 * @author Greg Volpe / Cody Cullens
 *
 */
public class Game
{
	private String gameName,gameCompany,gameReleaseDate,
		gameVersion,gameDescription,gameRating,gamePlayed,gameDownloads,gamePrice;
	private int gameID;
	private DatabaseAccess DB;
	private Account myAccount;
	private ArrayList<String> columnList,results;

	/**
	 *  Standard constructor to create a Game object
	 * @param mGameID Game ID of the game
	 */
	public Game(int mGameID)
	{
		DB = new DatabaseAccess();
		gameID = mGameID;
		gameName = null;
		gameCompany = null;
		gameRating = null;
		gameReleaseDate = null;
		gameVersion = null;
		gameDownloads = null;
		gamePlayed = null;
		gameDescription = null;
		gamePrice = null;
		columnList = new ArrayList<String>();
		addColumnNames();
		getDBinfo();
	}
	/**
	 * Return the name of the current Game
	 * @return Game Name
	 */
	public String getName()
	{
		return this.gameName;
	}
	/**
	 * Returns the name of deveolping company for the current game
	 * @return Company Name
	 */
	public String getCompany()
	{
		return this.gameCompany;
	}
	/**
	 * Returns the release date of the game
	 * @return Release Date
	 */
	public String getReleaseDate()
	{
		return this.gameReleaseDate;
	}
	/**
	 * Returns the current version of the game
	 * @return Game Version
	 */
	public String getVersion()
	{
		return this.gameVersion;
	}
	/**
	 * Returns description text of the game
	 * @return Game Description
	 */
	public String getDescription()
	{
		return this.gameDescription;
	}
	/**
	 * Returns the calculated rating of the game based on user input
	 * @return Game rating
	 */
	public String getRating()
	{
		return gameRating;
	}
	/**
	 * Returns the number of times the game has been played
	 * @return Number of Plays
	 */
	public String getGamesPlayed()
	{
		return gamePlayed;
	}
	/**
	 * Returns the number of times the game has been downloaded
	 * @return Number of Downloads
	 */
	public String getNumberDownloaded()
	{
		return gameDownloads;
	}

	/**
	 * Adds the current game to the list of games the User owns
	 * @return Boolean outcome of operation
	 */
	public boolean addToMyAccount()
	{
		String query = null;
		String gameID = null;

		//compose query to get GameID
		query = "SELECT GameID FROM `Games` WHERE GameName = '"+ gameName +"';";
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add("GameID");
		gameID=DB.getData(query, columnNames).get(0);
		//compose query to add data to the database
		query = "Insert into LeaderBoard values ('" + this.myAccount.getGamerTag() +  "', '" + gameID + "', '0', '0', '0');";

		//return indicates error
		return DB.setData(query);
	}
	/**
	 * Returns the user's account object
	 * @return User's account
	 */
	public Account getMyAccount() {
		return myAccount;
	}
	/**
	 * Set the User's account object for the Game object
	 * @param myAccount User's account
	 */
	public void setMyAccount(Account myAccount) {
		this.myAccount = myAccount;
	}
	private void addColumnNames()
	{
		columnList.add("GameName");
		columnList.add("Description");
		columnList.add("ReleaseDate");
		columnList.add("Version");
		columnList.add("Developer");
		columnList.add("DownloadNum");
		columnList.add("Rating");
		columnList.add("Price");
	}
	private void getDBinfo()
	{
		String query="SELECT GameName, Description, ReleaseDate, Version, Developer, DownloadNum, Rating, Price FROM Games where GameID ="+gameID+";";
		results = new ArrayList<String>();
		//Get the game information from the database
		results = DB.getData(query,columnList);
		gameName = results.get(0);
		gameDescription = results.get(1);
		gameReleaseDate = results.get(2);
		gameVersion = results.get(3);
		gameCompany = results.get(4);
		gameDownloads = results.get(5);
		gameRating = results.get(6);
		gamePrice = results.get(7);
	}
}
