package com.CS360.GameCenter;

import java.util.ArrayList;
import com.CS360.JavaCore.Game;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.Fragment;
import android.app.FragmentTransaction;
/**
 * Fragment to control all Tab 2 operations
 * @author Greg Volpe
 *
 */
public class FragmentTab2 extends Fragment 
{
	ArrayList<Game> games;
	@Override
	/**
	 * Standard implementation operations when creating the view of Tab 2
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.fragmenttab2, container, false);
		games = ((MainActivity)getActivity()).getAllGames();

		ListView leaderList = (ListView) rootView.findViewById(R.id.leaderboardlist);
		final ArrayList<String> list = new ArrayList<String>();    	    
		for (int i = 0; i < games.size(); ++i) 
		{
			list.add(games.get(i).getName());
		}
		final StableArrayAdapter adapter = new StableArrayAdapter(super.getActivity(),
				android.R.layout.simple_list_item_1, list); 		    
		leaderList.setAdapter(adapter);
		leaderList.setOnItemClickListener(new AdapterView.OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) 
			{
				//create a new fragment
				LeaderboardFragment leaderboard = new LeaderboardFragment();
				//create a new bundle
				Bundle bundy = new Bundle();
				//create String with the name of the game at position clicked
				String str = (String) parent.getItemAtPosition(position);
				//put the String in the bundle with the Key of gameName
				bundy.putString("gameName", str);
				bundy.putInt("gameID", position);
				//set fragment arguments to the bundle
				leaderboard.setArguments(bundy);
				//process fragment transaction
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.replace(R.id.fragment_container, leaderboard);
				transaction.addToBackStack(null);
				transaction.commit();   				
			}
		});  	    
		return rootView;
	}
}