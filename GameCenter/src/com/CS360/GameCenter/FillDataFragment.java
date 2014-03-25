package com.CS360.GameCenter;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.CS360.JavaCore.Account;
import com.CS360.JavaCore.DatabaseAccess;
/**
 * Fragment used to display and operate the new user creation page
 * @author Greg Volpe
 *
 */
public class FillDataFragment extends Fragment 
{
	private String fname, lname, email, gamertag;
	private String password1,password2;
	private View rootView;
	private Account newAccount;
	private DatabaseAccess DB = new DatabaseAccess();
	boolean isUpdate = false;
	private ArrayList<String> columnNames = new ArrayList<String>();
	private String error;

	@Override
	/**
	 * Standard on creation method
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		columnNames.add("Password");
		//get the rootview and change settings of checkboxes / submit buttons
		rootView = inflater.inflate(R.layout.fill_data, container, false);
		((CheckBox)rootView.findViewById(R.id.filldata_emailcheck)).setClickable(false);
		((CheckBox)rootView.findViewById(R.id.filldata_password1)).setClickable(false);
		((CheckBox)rootView.findViewById(R.id.filldata_password2)).setClickable(false);
		((Button)rootView.findViewById(R.id.submitButton)).setClickable(false);
		((Button)rootView.findViewById(R.id.submitButton)).setFocusable(false);
		((Button)rootView.findViewById(R.id.submitButton)).setVisibility(Button.INVISIBLE);

		//check for bundled arguments
		if(getArguments() !=null)
		{
			fname = getArguments().getString("fname");
			lname = getArguments().getString("lname");
			email = getArguments().getString("email");
			gamertag = getArguments().getString("gamertag");
			error = getArguments().getString("error");
			isUpdate = getArguments().getBoolean("isUpdate");
			((EditText)rootView.findViewById(R.id.fnameInput)).setText(fname);
			((EditText)rootView.findViewById(R.id.lnameInput)).setText(lname);
			((EditText)rootView.findViewById(R.id.emailInput)).setText(email);
			EditText gamer =((EditText)rootView.findViewById(R.id.gamertagInput));
			//gamertag should not be editable for accounts that have already been created
			if(isUpdate)
			{
				gamer.setText(gamertag);
				gamer.setClickable(false);
				gamer.setFocusable(false);

			}
			if(error.equals("fname"))
			{
				((TextView)rootView.findViewById(R.id.fnamelabel)).setTextColor(Color.RED);
			}
			if(error.equals("lname"))
			{
				((TextView)rootView.findViewById(R.id.lnamelabel)).setTextColor(Color.RED);
			}
			if(error.equals("email"))
			{
				((TextView)rootView.findViewById(R.id.emaillabel)).setTextColor(Color.RED);
			}

			//change the color of gamertag if error is true
			if(error.equals("GAMERTAG_USED")||error.equals("gamertag"))
			{
				((TextView)rootView.findViewById(R.id.gamerTagLabel)).setTextColor(Color.RED);
			}
			else
				if(error.equals("update"))
				{
					((TextView)rootView.findViewById(R.id.fill_warning)).setVisibility(TextView.INVISIBLE);
				}

		}
		//get the users account
		newAccount = ((MainActivity) getActivity()).getAccount();

		//Email checkbox
		EditText emailInput = ((EditText)rootView.findViewById(R.id.emailInput));
		emailInput.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) 
			{
				String tag = s.toString();
				if(tag.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
				{
					((CheckBox)rootView.findViewById(R.id.filldata_emailcheck)).setChecked(true);
					//((Button)rootView.findViewById(R.id.submitButton)).setClickable(true);
				}

				else
				{
					((CheckBox)rootView.findViewById(R.id.filldata_emailcheck)).setChecked(false);
					((Button)rootView.findViewById(R.id.submitButton)).setClickable(false);
					((Button)rootView.findViewById(R.id.submitButton)).setFocusable(false);
				}

			} 

		});

		//password 1 checkbox
		EditText password1 = ((EditText)rootView.findViewById(R.id.passwordInput));
		password1.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) 
			{
				String tag = s.toString();
				if(tag.length() > 5)
				{
					((CheckBox)rootView.findViewById(R.id.filldata_password1)).setChecked(true);
					//((Button)rootView.findViewById(R.id.submitButton)).setClickable(true);
				}

				else
				{
					((CheckBox)rootView.findViewById(R.id.filldata_password1)).setChecked(false);
					((Button)rootView.findViewById(R.id.submitButton)).setClickable(false);
					((Button)rootView.findViewById(R.id.submitButton)).setFocusable(false);
				}

			} 

		});
		//password 2 checkbox
		EditText password2 = ((EditText)rootView.findViewById(R.id.passwordInput2));
		password2.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) 
			{
				String tag = s.toString();
				String pass1 = ((EditText)rootView.findViewById(R.id.passwordInput)).getText().toString();
				if(tag.equals(pass1) & !tag.equals(null))
				{
					((CheckBox)rootView.findViewById(R.id.filldata_password2)).setChecked(true);
					((Button)rootView.findViewById(R.id.submitButton)).setClickable(true);
					((Button)rootView.findViewById(R.id.submitButton)).setFocusable(true);
					((Button)rootView.findViewById(R.id.submitButton)).setVisibility(Button.VISIBLE);
				}

				else
				{
					((CheckBox)rootView.findViewById(R.id.filldata_password2)).setChecked(false);
					((Button)rootView.findViewById(R.id.submitButton)).setClickable(false);
					((Button)rootView.findViewById(R.id.submitButton)).setFocusable(false);
				}

			} 

		});
		//submit button
		Button submit = (Button)rootView.findViewById(R.id.submitButton);
		submit.setOnClickListener(new View.OnClickListener()
		{

			@SuppressLint("NewApi")
			public void onClick(View v)
			{
				String error =null;
				FragmentTransaction transaction = ((MainActivity)getActivity()).getFragmentManager().beginTransaction();
				//get data inputs
				fname = ((EditText)rootView.findViewById(R.id.fnameInput)).getText().toString();
				lname = ((EditText)rootView.findViewById(R.id.lnameInput)).getText().toString();
				email = ((EditText)rootView.findViewById(R.id.emailInput)).getText().toString();
				gamertag = ((EditText)rootView.findViewById(R.id.gamertagInput)).getText().toString();
				String password1 = ((EditText)rootView.findViewById(R.id.passwordInput)).getText().toString();
				String password2 = ((EditText)rootView.findViewById(R.id.passwordInput2)).getText().toString();

				ArrayList<String>result = DB.getDBpassword(gamertag, columnNames);
				boolean gamerUsed = true;
				if(result.get(0).equals("error"))
					gamerUsed = false;
				//check to see if account is being updated
				if(gamerUsed & !isUpdate)
				{
					Bundle bundy = new Bundle();
					bundy.putString("fname", fname);
					bundy.putString("lname",lname);
					bundy.putString("email", email);
					bundy.putString("error", "GAMERTAG_USED");
					Toast.makeText(getActivity(), "GamerTag is used", Toast.LENGTH_LONG).show();
					FillDataFragment frag = new FillDataFragment();
					frag.setArguments(bundy);
					//replace fragment with clean data
					transaction.replace(R.id.fragment_container, frag);
					transaction.commit();

				}
				else
					//all field checking
					if(!password1.equals(password2) || password1.equals("") || password2.equals("") || fname.equals("")||lname.equals("")||email.equals("")
							|| gamertag.equals("") ||password1.equals(""))
					{
						if(fname.equals(""))
						{
							error = "fname";
						}
						else if(lname.equals(""))
						{
							error = "lname";
						}
						else if(email.equals(""))
						{
							error = "email";
						}
						else if(gamertag.equals(""))
						{
							error = "gamertag";
						}
						//if password bad, bundle good account items
						Bundle bundy = new Bundle();
						bundy.putString("fname", fname);
						bundy.putString("lname",lname);
						bundy.putString("email", email);
						bundy.putString("gamertag",gamertag);
						bundy.putString("error", error);
						//output error message
						Toast.makeText(getActivity(), "Error Creating Account", Toast.LENGTH_SHORT).show();
						FillDataFragment frag = new FillDataFragment();
						frag.setArguments(bundy);
						//replace fragment with clean data
						transaction.replace(R.id.fragment_container, frag);
						transaction.commit();

					}
					else
						if(isUpdate)
						{
							//set data in the account
							DB.updateAccount(gamertag, fname, lname, email, password1);
							newAccount.setData(fname, lname, email, gamertag, password1);
							((MainActivity)getActivity()).saveAccount();

							//plays a data transfer sound signifying account has been created
							MediaPlayer mp = MediaPlayer.create(getActivity().getBaseContext(), R.raw.transfercomplete);
							mp.start();

							//builds a notification to display in the notification tray
							NotificationCompat.Builder mBuilder =
									new NotificationCompat.Builder(getActivity())
							.setSmallIcon(R.drawable.ic_launcher)
							.setContentTitle("Welcome "+ gamertag+"!!")
							.setContentText("Your account has been created");

							int mNotificationId = 001;
							// Gets an instance of the NotificationManager service
							NotificationManager mNotifyMgr = 
									(NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
							// Builds the notification and issues it.
							mNotifyMgr.notify(mNotificationId, mBuilder.build());

							//new game list fragment to dipsplay list of games
							GameListFragment games = new GameListFragment();
							transaction.replace(R.id.fragment_container, games );
							transaction.commit();

							//set the tabs visible
							((MainActivity)getActivity()).getActionBar().show();
							((MainActivity)getActivity()).setStatus(true);
							//close the keyboard
							InputMethodManager inputManager = (InputMethodManager)            
									getActivity().getSystemService(Context.INPUT_METHOD_SERVICE); 
							inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),      
									InputMethodManager.HIDE_NOT_ALWAYS);
						}
						else
						{
							//set data in the account
							DB.addNewAccount(gamertag, fname, lname, email, password1);
							newAccount.setData(fname, lname, email, gamertag, password1);
							((MainActivity)getActivity()).saveAccount();
							((MainActivity)getActivity()).setStatus(true);

							//plays a data transfer sound signifying account has been created
							MediaPlayer mp = MediaPlayer.create(getActivity().getBaseContext(), R.raw.transfercomplete);
							mp.start();

							//builds a notification to display in the notification tray
							NotificationCompat.Builder mBuilder =
									new NotificationCompat.Builder(getActivity())
							.setSmallIcon(R.drawable.ic_launcher)
							.setContentTitle("Welcome "+ gamertag+"!!")
							.setContentText("Your account has been created");

							int mNotificationId = 001;
							// Gets an instance of the NotificationManager service
							NotificationManager mNotifyMgr = 
									(NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
							// Builds the notification and issues it.
							mNotifyMgr.notify(mNotificationId, mBuilder.build());

							//new game list fragment to dipsplay list of games
							GameListFragment games = new GameListFragment();
							transaction.replace(R.id.fragment_container, games );
							transaction.commit();

							//set the tabs visible
							((MainActivity)getActivity()).getActionBar().show();
							((MainActivity)getActivity()).setStatus(true);
							//close the keyboard
							InputMethodManager inputManager = (InputMethodManager)            
									getActivity().getSystemService(Context.INPUT_METHOD_SERVICE); 
							inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),      
									InputMethodManager.HIDE_NOT_ALWAYS);
						}
			}

		});

		return rootView;
	}

}