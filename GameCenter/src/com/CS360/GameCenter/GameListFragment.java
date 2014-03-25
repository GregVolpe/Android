package com.CS360.GameCenter;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.CS360.JavaCore.Game;

/**
 * Fragment to control all operations on Game List UI
 * 
 * @author Greg Volpe
 * 
 */
public class GameListFragment extends Fragment {

	private ArrayList<Game> games;
	private ArrayAdapter<String> adapter;
	private int count;

	@Override
	/**
	 * Standard implementation of Fragment UI
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.gamelist, container, false);
		ListView aGameList = (ListView) rootView.findViewById(R.id.gamelist);
		games = ((MainActivity) getActivity()).getAllGames();
		ArrayList<String> list = new ArrayList<String>();
		count = games.size();
		
		for (int i = 0; i < count; ++i) {
			list.add(games.get(i).getName());
		}

		adapter = new ArrayAdapter<String>(super.getActivity(),
				android.R.layout.simple_list_item_1, list);

		aGameList.setAdapter(adapter);

		aGameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				// create a new fragment
				GameDataFragment newGame = new GameDataFragment();

				// create a new bundle
				Bundle bundy = new Bundle();
				// create String with the name of the game at position clicked
				String str = (String) parent.getItemAtPosition(position);
				// put the String in the bundle with the Key of gameName
				bundy.putString("gameName", str);
				bundy.putInt("gameID", (position));

				// set fragment arguments to the bundle
				newGame.setArguments(bundy);
				// process fragment transaction
				FragmentTransaction transaction = getFragmentManager()
						.beginTransaction();
				transaction.replace(R.id.fragment_container, newGame);
				transaction.addToBackStack(null);
				transaction.commit();

			}
		});
		//if the games are not finished running, get the new game and check again
		while (!((MainActivity) getActivity()).isGamesFinished()) 
		{
			games = ((MainActivity) getActivity()).getAllGames();
			int temp = games.size();
			if (count != temp) {
				for (int i = count; i < temp; ++i) {
					list.add(games.get(i).getName());
				}
				count = temp;
				adapter.notifyDataSetChanged();
			}
		}
		return rootView;
	}

	/**
	 * This is an overloaded method that refreshes the game list from the
	 * database
	 */
	public void onResume() {
		super.onResume();
		games = ((MainActivity) getActivity()).getAllGames();
	}

}
