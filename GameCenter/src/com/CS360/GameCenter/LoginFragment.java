package com.CS360.GameCenter;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.CS360.JavaCore.Account;
import com.CS360.JavaCore.DatabaseAccess;
/**
 * Fragment to control and display Login UI
 * @author Greg Volpe
 *
 */
public class LoginFragment extends Fragment {
	private View rootView;
	private Account account;
	int count =0;
	private ProgressBar loading;

	@Override
	/**
	 * Standard construction of UI elements
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		rootView = inflater.inflate(R.layout.login_page, container, false);
		loading = (ProgressBar)rootView.findViewById(R.id.progressBar2);
		loading.setVisibility(ProgressBar.INVISIBLE);
		ProgressBar tries = (ProgressBar)rootView.findViewById(R.id.progressBar1);
		if(this.getArguments() !=null)
		{
			Bundle bundy = this.getArguments();
			count = bundy.getInt("count");
			int progress  = tries.getMax()-(count*25);
			tries.setProgress(progress);
			//this loop exits the app if 4 unsuccessful attempts
			if (count == 4)
			{
				((MainActivity)getActivity()).finish();
			}
		}
		else
			tries.setProgress(tries.getMax());

		account = ((MainActivity)getActivity()).getAccount();
		final MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.login);
		final MediaPlayer mpDenied = MediaPlayer.create(getActivity(), R.raw.denied);

		if(!account.getGamerTag().equals(null) & !account.getGamerTag().equals(""))
		{
			((EditText)rootView.findViewById(R.id.login_gamerTagInput)).setText(account.getGamerTag());
		}
		Button submit = (Button)rootView.findViewById(R.id.login_loginButton);
		submit.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				loading.setVisibility(ProgressBar.VISIBLE);
				String password = ((EditText)rootView.findViewById(R.id.login_passwordInput)).getText().toString();
				String gamerTag = ((EditText)rootView.findViewById(R.id.login_gamerTagInput)).getText().toString();
				String databasePass = account.getDBPassword(gamerTag, ((MainActivity)getActivity()).getAccess());
				FragmentTransaction transaction = getFragmentManager().beginTransaction();

				ArrayList<Object> results = Account.checkPassword(password, databasePass);
				boolean verified = (Boolean) results.get(0);
				String message = (String) results.get(1);

				if(databasePass.equals(null)| !verified)
				{
					mpDenied.start();
					Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
					LoginFragment frag = new LoginFragment();
					Bundle bundy = new Bundle();
					++count;
					bundy.putInt("count", count);
					frag.setArguments(bundy);
					transaction.replace(R.id.fragment_container, frag);
					transaction.commit();	
				}
				
				else
				{
					mp.start();
					DatabaseAccess DB = new DatabaseAccess();
					ArrayList<String> columnNames = new ArrayList<String>();
					columnNames.add("fName");
					columnNames.add("lName");
					columnNames.add("email");
					ArrayList<String> accountInfo = new ArrayList<String>();
					accountInfo = DB.getAccount(gamerTag, columnNames);
					account.setData(accountInfo.get(0), accountInfo.get(1), accountInfo.get(2), gamerTag, password);
					((MainActivity)getActivity()).saveAccount();
					((MainActivity)getActivity()).startMyGames();

					GameListFragment gamelist = new GameListFragment();

					transaction.replace(R.id.fragment_container, gamelist);


					((MainActivity)getActivity()).getActionBar().show();
					((MainActivity)getActivity()).setStatus(true);

					InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE); 
					inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),      
							InputMethodManager.HIDE_NOT_ALWAYS);
					transaction.commit();
				}

			}
		});

		return rootView;
	}


}