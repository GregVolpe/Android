package com.CS360.GameCenter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.app.Fragment;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This fragment is used to display the About Us layout
 * @author Greg Volpe
 *
 */
public class ChangeLogFragment extends Fragment 
{
	/**
	 *Default on Create View used to inflate and display the resource 
	 *
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{


		View rootView = inflater.inflate(R.layout.changelog, container, false);

		TextView log = (TextView)rootView.findViewById(R.id.changelog_text);
		String logData = "";

		AssetManager assetManager = getActivity().getAssets();
		try {
			InputStream input = assetManager.open("ChangeLog.txt");
			InputStreamReader reader = new InputStreamReader(input);
			int data = 0;

			while((data = reader.read())!= -1)
			{
				logData+=(char)data;
			}
			input.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(MainActivity.getActivity(), "File Error", Toast.LENGTH_LONG).show();
		}
		log.setText(logData);

		return rootView;
	}


}