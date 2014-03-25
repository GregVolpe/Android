package com.CS360.GameCenter;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
/**
 * Fragment used to control all operations on Tab 1 (Games)  
 * @author Greg Volpe
 *
 */
public class FragmentTab1 extends Fragment 
{
	private boolean loggedIn=false;
	@Override
	/**
	 * Standard implementation for creating Tab 1 fragment
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		loggedIn =((MainActivity)getActivity()).getStatus();

		View rootView;
		if(loggedIn)
		{
			//This code displays a game page, we want to display a game list it must be changed
			//to show a list.  When a list item is clicked it should open a game page, must be 
			//able to return using the back button
			rootView = inflater.inflate(R.layout.gamelist, container, false);
			GameListFragment gameList = new GameListFragment();
			FragmentTransaction transaction = super.getFragmentManager().beginTransaction();
			transaction.replace(R.id.fragment_container, gameList);
			transaction.addToBackStack(null);
			transaction.commit();
		}     
		else
		{
			rootView = inflater.inflate(R.layout.start_page, container, false);
			TextView version = (TextView)rootView.findViewById(R.id.startpage_appversion);
			String str = null;
			try 
			{
				str = super.getActivity().getPackageManager().getPackageInfo("com.CS360.GameCenter", 0).versionName;
			} catch (NameNotFoundException e) 
			{				
				e.printStackTrace();
			}	    	
			version.setText("Version: "+str);
			//The following code sets up the button on the Fragment Tab 1 and sets a click listener

			Button create = (Button)rootView.findViewById(R.id.startPage_createAccountButton);
			create.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{
					//create a new game data fragment
					FillDataFragment fillData = new FillDataFragment();
					//fragments must be processed with a fragment transaction
					FragmentTransaction transaction = getFragmentManager().beginTransaction();	
					// Replace whatever is in the fragment_container view with this fragment,
					// and add the transaction to the back stack
					transaction.replace(R.id.fragment_container, fillData);	
					// Commit the transaction
					transaction.commit();
					((MainActivity)getActivity()).setStatus(true);
				}
			});

			Button login = (Button)rootView.findViewById(R.id.startPage_loginButton);
			login.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{
					LoginFragment login = new LoginFragment();
					//fragments must be processed with a fragment transaction
					FragmentTransaction transaction = getFragmentManager().beginTransaction();	
					// Replace whatever is in the fragment_container view with this fragment,
					// and add the transaction to the back stack
					transaction.replace(R.id.fragment_container, login);
					transaction.addToBackStack(null);	
					// Commit the transaction
					transaction.commit();
					((MainActivity)getActivity()).setStatus(true);
				}
			});
		}       
		return rootView;	
	}

}

