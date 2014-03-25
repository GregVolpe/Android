package com.CS360.GameCenter;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Fragment to control all Tab 3 operations
 * @author Greg Volpe
 *
 */
public class FragmentTab4 extends Fragment 
{
	ArrayList<String> userList;
	@Override
	/**
	 * Standard implementation operations
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.fragmenttab4, container, false);

		ListView users = (ListView) rootView.findViewById(R.id.userlist);
		userList = ((MainActivity)getActivity()).getUserList();

		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < userList.size(); ++i) 
		{
			list.add(userList.get(i));
		}

		final StableArrayAdapter adapter = new StableArrayAdapter(super.getActivity(),
				android.R.layout.simple_list_item_1, list);

		users.setAdapter(adapter);
		return rootView;
	}
	/**
	 * This method is an overload of the defaul on resume method used to refresh the My Games tab
	 */
	public void onResume()
	{
		super.onResume();
		userList = ((MainActivity)getActivity()).getMyGames();
	}

}