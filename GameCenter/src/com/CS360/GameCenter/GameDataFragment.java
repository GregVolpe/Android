package com.CS360.GameCenter;

import java.io.File;

import android.app.DownloadManager;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.CS360.JavaCore.Account;
import com.CS360.JavaCore.DatabaseAccess;
import com.CS360.JavaCore.Game;
/**
 * Fragment to control all operations on the Game Data Page
 * @author Greg Volpe
 *
 */
public class GameDataFragment extends Fragment 
{
	private String gameName;
	int gameID;
	private DatabaseAccess DB;
	boolean inMyGames = false;
	private Account myAccount;

	@Override
	/**
	 * Standard implementation of Game Data Page UI
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		myAccount = ((MainActivity)getActivity()).getAccount();
		DB = new DatabaseAccess();
		//create a new bundle
		Bundle bundy = super.getArguments();
		//avoid null bunlde
		if(getArguments() != null)
		{
			//set gameNme to bundle key
			gameName = bundy.getString("gameName");
			gameID = bundy.getInt("gameID");
			inMyGames = ((MainActivity)getActivity()).checkMyGames(gameName);
		}
		//declare the default view for the page
		View rootView = inflater.inflate(R.layout.game_data_page, container, false);
		Button install = (Button)rootView.findViewById(R.id.game_install);
		//check if installed
		if(inMyGames)
			install.setText("Uninstall");
		else
			install.setText("Install");
		//get the game
		Game theGame = ((MainActivity)getActivity()).getGame(gameID);
		//set the game details
		TextView name = (TextView)rootView.findViewById(R.id.game_gameName);
		name.setText(theGame.getName());
		TextView description = (TextView)rootView.findViewById(R.id.game_descriptionField);
		description.setText(theGame.getDescription());
		TextView releaseDate = (TextView)rootView.findViewById(R.id.game_releaseDateField);
		releaseDate.setText(theGame.getReleaseDate());
		TextView version = (TextView)rootView.findViewById(R.id.game_versionField);
		version.setText(theGame.getVersion());
		TextView developer = (TextView)rootView.findViewById(R.id.game_company);
		developer.setText(theGame.getCompany());
		TextView downloadNum = (TextView)rootView.findViewById(R.id.game_downloadsField);
		downloadNum.setText(theGame.getNumberDownloaded());
		TextView rating = (TextView)rootView.findViewById(R.id.game_ratingNumber);
		rating.setText(theGame.getRating());
		String ratingNumber = rating.getText().toString();
		//Rating bar creation
		RatingBar newbar = (RatingBar)rootView.findViewById(R.id.game_rating);
		//set step size to 1/2 star
		newbar.setRating(Float.valueOf(ratingNumber));

		newbar.setClickable(false);
		newbar.setFocusable(false);


		//The following code will set the description field on the game tab
		//so that if the text exceeds the amount of space the user can scroll
		//rootView is the view of the layout that must be manipulated
		description.setMovementMethod(new ScrollingMovementMethod());

		install.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				
				if(inMyGames)
				{
					DB.uninstall(myAccount.getGamerTag(), gameID);
					boolean deleted = false;
					//Attempt to delete file from phone
					//will not work on AVD
					try{
						String selectedFilePath = ("/storage/sdcard0/Download/"+gameName+".apk");
						File file = new File(selectedFilePath);
						deleted = file.delete();
					}catch(Exception e)
					{
						Log.i("Delete Error", "Error Deleteing Game APK");
						deleted = true;
					}
					
					if(deleted)
					{
						Toast.makeText(getActivity(), "Uninstalling...", Toast.LENGTH_LONG).show();
					}

				}
				if(!inMyGames)
				{
					DB.addGame(myAccount.getGamerTag(), gameID);
					Toast.makeText(getActivity(), "Installing...", Toast.LENGTH_LONG).show();
					inMyGames = true;
					
					//attempt to download apk file to the phone
					//will not work on AVD
					try{
						//Attempt to download an apk file
						String url = "http://ccullens.com/codycullens/apk/Adobe%20Flash%20Player%2011.1.apk";
						String fileName = gameName+".apk";
						DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
						request.setDescription("Game Center Download");
						request.setTitle(gameName);
						// in order for this if to run, you must use the android 3.2 to compile your app
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
							request.allowScanningByMediaScanner();
							request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
						}
						request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

						// get download service and enqueue file
						DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
						manager.enqueue(request);
					}
					catch(Exception e)
					{
						Log.i("Download", "Error Downloading APK");
					}
				}
				((MainActivity)getActivity()).startMyGames();
			}
		});

		return rootView;
	}
	/**
	 * Overload of the default on resume method.  This will refresh the game data page and set the install button accordingly.
	 */
	public void onResume()
	{
		super.onResume();
		View v = this.getView();
		v.findViewById(R.id.game_install);
		Button install = (Button)v.findViewById(R.id.game_install);
		boolean installed = ((MainActivity)getActivity()).checkMyGames(gameName);
		if(installed)
			install.setText("Uninstall");
		else
			install.setText("Install");


	}


}