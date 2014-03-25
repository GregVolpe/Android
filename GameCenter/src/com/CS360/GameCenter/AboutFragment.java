package com.CS360.GameCenter;
 
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 
/**
 * This fragment is used to display the About Us layout
 * @author Greg Volpe
 *
 */
public class AboutFragment extends Fragment 
{
	/**
	 *Default on Create View used to inflate and display the resource 
	 *
	 */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) 
    {
    	//set view to the about layout
        View rootView = inflater.inflate(R.layout.about, container, false);
        return rootView;
    }
}