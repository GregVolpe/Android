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
/**
 * Fragment to control all Tab 3 operations
 * @author Greg Volpe
 *
 */
public class FragmentTab3 extends Fragment 
{
	ArrayList<String> gameNames;
	@Override
	/**
	 * Standard implementation operations
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.fragmenttab3, container, false);
		ListView aGameList = (ListView) rootView.findViewById(R.id.mygameslist);
		((MainActivity)getActivity()).startMyGames();
		gameNames = ((MainActivity)getActivity()).getMyGames();

		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < gameNames.size(); ++i) 
		{
			list.add(gameNames.get(i));
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(super.getActivity(),
				android.R.layout.simple_list_item_1, list);

		aGameList.setAdapter(adapter);
		if(!gameNames.get(0).equals("No Games"))
		{
			aGameList.setOnItemClickListener(new AdapterView.OnItemClickListener() 
			{

				@Override
				public void onItemClick(AdapterView<?> parent, final View view,
						int position, long id) 
				{
					//create a new fragment
					GameDataFragment frag = new GameDataFragment();
					//create a new bundle
					Bundle bundy = new Bundle();
					//create String with the name of the game at position clicked
					String str = (String) parent.getItemAtPosition(position);
					//put the String in the bundle with the Key of gameName
					bundy.putString("gameName", str);
					int gameID=((MainActivity)getActivity()).getGameID(str);
					bundy.putInt("gameID", gameID);
					//set fragment arguments to the bundle
					frag.setArguments(bundy);
					//process fragment transaction
					FragmentTransaction transaction = getFragmentManager().beginTransaction();
					transaction.replace(R.id.fragment_container, frag);
					transaction.addToBackStack(null);
					transaction.commit();	    				
				}
			});
		}	   
		return rootView;
	}
	public void onResume()
	{
		super.onResume();
		gameNames = ((MainActivity)getActivity()).getMyGames();
	}

}