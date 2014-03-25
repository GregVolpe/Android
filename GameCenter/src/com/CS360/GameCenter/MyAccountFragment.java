package com.CS360.GameCenter;
 
import com.CS360.JavaCore.Account;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
 /**
  * Fragment to control the operations on My Account page
  * @author Greg Volpe
  *
  */
public class MyAccountFragment extends Fragment 
{
	private View rootView;
	private Account account;

    @Override
    /**
     * Standard implementation of the Fragments UI
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) 
    {
        rootView = inflater.inflate(R.layout.myaccount, container, false);
        account = ((MainActivity) getActivity()).getAccount();
        
        ((TextView)rootView.findViewById(R.id.myaccount_gamertag)).setText(account.getGamerTag());
        ((TextView)rootView.findViewById(R.id.myaccount_fnamefield)).setText(account.getFname());
        ((TextView)rootView.findViewById(R.id.myaccount_lnamefield)).setText(account.getLname());
        ((TextView)rootView.findViewById(R.id.myaccount_emailfield)).setText(account.getEmail());
        
        Button update = (Button)rootView.findViewById(R.id.game_install);
        update.setOnClickListener(new View.OnClickListener()
        {
        	public void onClick(View v)
        	{
        		Bundle bundy = new Bundle();
        		bundy.putString("fname", account.getFname());
        		bundy.putString("lname", account.getLname());
        		bundy.putString("email", account.getEmail());
        		bundy.putString("gamertag", account.getGamerTag());
        		bundy.putBoolean("isUpdate", true);
        		bundy.putString("error", "update");

        		FragmentTransaction transaction = getFragmentManager().beginTransaction();

        		FillDataFragment frag = new FillDataFragment();
        		frag.setArguments(bundy);
        		transaction.replace(R.id.fragment_container, frag);
        		transaction.addToBackStack(null);
        		transaction.commit();
        		
        	}
        });
        
        return rootView;
    }

}

