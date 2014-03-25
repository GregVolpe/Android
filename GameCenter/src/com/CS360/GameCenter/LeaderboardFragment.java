package com.CS360.GameCenter;

import java.util.ArrayList;

import com.CS360.JavaCore.Game;
import com.CS360.JavaCore.Leaderboard;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
/**
 * Leaderboard Fragment to control all leaderboard activities
 * @author Greg Volpe
 *
 */
public class LeaderboardFragment extends Fragment 
{
	private String gameName;
	private int gameID;
	private Leaderboard rank;
	private ArrayList<Game> games;
	@Override
	/**
	 * Standard implementation operations
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.leaderboard, container, false);
		games = ((MainActivity)getActivity()).getAllGames();
		Game thisGame = null;
		Bundle bundy = super.getArguments();
		//avoid null bundle
		if(getArguments() != null)
		{
			//set gameNme to bundle key
			gameName = bundy.getString("gameName");
			gameID = bundy.getInt("gameID");
			thisGame = games.get(gameID);
		}

		rank = new Leaderboard(gameName,(gameID+1));
		ArrayList<String> results = new ArrayList<String>();
		results = rank.getTopTen();
		((TextView)rootView.findViewById(R.id.leaderboard_gamename)).setText(thisGame.getName());
		((TextView)rootView.findViewById(R.id.leaderboard_company)).setText(thisGame.getCompany());

		ListView listView = (ListView) rootView.findViewById(R.id.leadboard_ranklist);
		listView.setClickable(false);
		listView.setFocusable(false);

		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < results.size(); ++i) 
		{
			list.add(results.get(i));
		}

		final StableArrayAdapter adapter = new StableArrayAdapter(super.getActivity(),
				android.R.layout.simple_list_item_1, list);

		listView.setAdapter(adapter);
		return rootView;
	}


}