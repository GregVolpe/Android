package com.CS360.GameCenter;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.CS360.JavaCore.Account;
import com.CS360.JavaCore.DatabaseAccess;
import com.CS360.JavaCore.Game;
/**
 * Main Application Activity
 * @author Greg Volpe
 *
 */
public class MainActivity extends Activity 
{
	// Declare Activity Variables
	private ActionBar.Tab Tab1, Tab2, Tab3, Tab4;
	private ActionBar actionBar;
	private Fragment fragmentTab1 = new FragmentTab1();
	private Fragment fragmentTab2 = new FragmentTab2();
	private Fragment fragmentTab3 = new FragmentTab3();
	private Fragment fragmentTab4 = new FragmentTab4();
	public boolean LoggedIn;
	private DatabaseAccess DB = new DatabaseAccess();
	private Account account = new Account();
	private static final String PREFS_NAME = "MyPrefsFile";
	//private ArrayList<Game> games = new ArrayList<Game>();
	private MyGamesQueryRunnable myGamesRunnable = new MyGamesQueryRunnable();
	private AllGamesQueryRunnable allGamesRun = new AllGamesQueryRunnable();

	@Override
	/**
	 * Standard UI implementation
	 */
	public void onCreate(Bundle savedInstanceState) 
	{



		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StrictMode.enableDefaults();
		if(!isNetworkAvailable())
		{
			Toast.makeText(this, "Internet Required...", Toast.LENGTH_SHORT).show();
			super.finish();
		}
		else
		{
			Thread allGames = new Thread(allGamesRun);
			allGames.setPriority(Thread.MAX_PRIORITY);
			allGames.start();

			loadAccount();
			setTabs();
		}

	}

	/**
	 * Standard Menu Creation
	 */
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}
	/**
	 * Menu selection Controls
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.logout:
			saveAccount();
			logout
			();
			return true;
		case R.id.about:
			displayAbout();
			return true;
		case R.id.myaccount:
			displayMyAccount();
			return true;
		case R.id.help:
			displayHelp();
			return true;
		case R.id.changelog:
			displayChangeLog();
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void setTabs()
	{
		actionBar = getActionBar();

		// Create Actionbar Tabs
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set Tab Icon and Titles
		// located in res/drawable-hdpi
		//Tab4 = actionBar.newTab().setText("Menu");
		Tab1 = actionBar.newTab().setText("Games");
		Tab2 = actionBar.newTab().setText("LeaderBoard");
		Tab3 = actionBar.newTab().setText("My Games");
		Tab4 = actionBar.newTab().setText("Users");

		// Set Tab Listeners
		//Tab4.setTabListener(new TabListener(loginFragment));
		Tab1.setTabListener(new TabListener(fragmentTab1));
		Tab2.setTabListener(new TabListener(fragmentTab2));
		Tab3.setTabListener(new TabListener(fragmentTab3));
		Tab4.setTabListener(new TabListener(fragmentTab4));

		// Add tabs to actionbar
		// actionBar.addTab(Tab4);
		actionBar.addTab(Tab1);
		actionBar.addTab(Tab2);
		actionBar.addTab(Tab3);
		actionBar.addTab(Tab4);
		if(!getStatus())
		{
			actionBar.hide();
		}

	}
	/**
	 * Function to return the login status of user's account
	 * @return Login status
	 */
	public boolean getStatus()
	{
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		return settings.getBoolean("LoggedIn", false);
	}
	/**
	 * Function to save the account information to the device for local login.  Makes it possible to access the application without internet access
	 * while retaining the login/logout capabilities
	 */
	public void saveAccount()
	{
		SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("fname", account.getFname());
		editor.putString("lname", account.getLname());
		editor.putString("email", account.getEmail());
		editor.putString("gamerTag", account.getGamerTag());
		editor.putString("password", account.getLocalPassword());
		editor.commit();
		Toast.makeText(this, "Saving Account...", Toast.LENGTH_SHORT).show();

	}
	/**
	 * This method is used to load Account information into the main Activity
	 */
	public void loadAccount()
	{
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		account.setGamerTag(settings.getString("gamerTag", ""));
		account.setfName(settings.getString("fname", ""));
		account.setlName(settings.getString("lname", ""));
		account.setEmail(settings.getString("email", ""));
		account.setLocalpassword(settings.getString("password", ""));
		this.setStatus(settings.getBoolean("LoggedIn", false));
		//this.startMyGames();
	}
	/**
	 * Set login status of the account on the local device
	 * @param bool
	 */
	public void setStatus(boolean bool)
	{
		SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("LoggedIn", bool);
		editor.commit();
	}
	/**
	 * This method is used to logout of the application and automatically finish the main activity
	 */
	private void logout()
	{
		MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.logout);
		mp.start();
		setStatus(false);
		Toast.makeText(this, "Application Loggin Out and Exiting", Toast.LENGTH_SHORT).show();
		this.finish();

	}

	private void displayAbout()
	{
		AboutFragment fragment = new AboutFragment();
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.fragment_container, fragment);
		transaction.addToBackStack(null);
		transaction.commit();

	}
	private void displayHelp()
	{
		HelpFragment fragment = new HelpFragment();
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.fragment_container, fragment);
		transaction.addToBackStack(null);
		transaction.commit();

	}
	private void displayMyAccount()
	{


		MyAccountFragment fragment = new MyAccountFragment();
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.fragment_container, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}
	private void displayChangeLog()
	{

		ChangeLogFragment fragment = new ChangeLogFragment();
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.fragment_container, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}
	/**
	 * Returns the User's account
	 * @return User's account
	 */
	public Account getAccount()
	{
		return account;
	}
	/**
	 * This method is used to return all games owned by the user
	 * @return List of games owned by the user
	 */
	public ArrayList<String> getMyGames()
	{
		ArrayList<String> myGames = myGamesRunnable.getMyGames();
		if(!myGames.get(0).equals("error"))
			return myGames;
		else
		{
			ArrayList<String>error = new ArrayList<String>();
			error.add("No Games");
			return error;
		}
	}
	/**
	 * This method is used to check if a game is in the users list of owned games
	 * @param gameName The name of the game you want to check
	 * @return boolean value of the resulting check
	 */
	public boolean checkMyGames(String gameName)
	{
		boolean inMyGames = false;
		ArrayList<String> myGames = getMyGames();
		for(int i=0;i<myGames.size();i++)
		{
			if(gameName.equals(myGames.get(i)))
			{
				inMyGames = true;
			}
		}

		return inMyGames;

	}
	/**
	 * This method is used to return all game objects from the queries being run
	 * @return List of game objects
	 */
	public ArrayList<Game> getAllGames()
	{
		return allGamesRun.getGames();
	}
	/**
	 * This method returns a specific game from the All Games runnable
	 * @param index The ID of the game you want to get
	 * @return The game
	 */
	public Game getGame(int index)
	{
		return allGamesRun.getGames().get(index);
	}
	/**
	 * This returns the Database Access object from the Main Activity
	 * @return The Main Activity Database Access Object
	 */
	public DatabaseAccess getAccess()
	{
		return DB;
	}
	/**
	 * This method return the context on the main activity
	 * @return ContextWrapper of the Main Activity
	 */
	public static ContextWrapper getActivity() 
	{
		return getActivity();
	}
	/**
	 * This method is used to start the My Games tab runnable to retrieve the list of games from the database
	 */
	public void startMyGames()
	{
		myGamesRunnable.setAccount(account);
		Thread t = new Thread(myGamesRunnable);
		t.start();
	}
	/**
	 * This method is an overloaded function of Activity.  When resuming the activity this will start the My Games Queries to update the list
	 */
	public void onResume()
	{
		super.onResume();
		startMyGames();

	}
	/**
	 * This method is used to return the game ID of a specific game
	 * @param gameName The game name you want to find
	 * @return the game ID 
	 */
	public int getGameID(String gameName)
	{
		ArrayList<Game>allGames = getAllGames();
		for(int i =0;i<allGames.size();i++)
		{
			if(gameName.equals(allGames.get(i).getName()))
			{
				return i;
			}
		}
		return -1;
	}
	/**
	 * This method will return a list of users from the databases
	 * @return List of user names
	 */
	public ArrayList<String> getUserList()
	{
		ArrayList<String>users =null;
		users = DB.getUserList();

		return users;		
	}
	/**
	 * This method is used to return the status of the all games runnable
	 * @return boolean result of the operation
	 */
	public boolean isGamesFinished()
	{
		return allGamesRun.isFinished();
	}
	/**
	 * This method is used to get the all games runnable that is executing query calls to the database
	 * @return The All Games Query Runnable
	 */
	public AllGamesQueryRunnable getAllGamesRun()
	{
		return allGamesRun;
	}
	/**
	 * This method is used to check for network availability.  It will check both mobile and WiFi data connections
	 * @return boolean result of the operation
	 */
	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager 
		= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

}
