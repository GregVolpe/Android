package com.CS360.GameCenter;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Fragment to display Help page
 * 
 * @author Greg Volpe
 * 
 */
public class HelpFragment extends Fragment {
	@Override
	/**
	 * Standard Fragment UI implementation
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.help, container, false);
		//create the stack overflow button and attach listener
		ImageView stackOverflow = (ImageView) rootView
				.findViewById(R.id.stackoverflow);
		stackOverflow.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.addCategory(Intent.CATEGORY_BROWSABLE);
				intent.setData(Uri.parse("http://www.stackoverflow.com"));
				startActivity(intent);
			}
		});
		//create the android developer button and attach click listener
		ImageView androidDev = (ImageView) rootView
				.findViewById(R.id.androiddev);
		androidDev.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.addCategory(Intent.CATEGORY_BROWSABLE);
				intent.setData(Uri
						.parse("http://developer.android.com/training/index.html"));
				startActivity(intent);
			}
		});
		//create the Java Doc button and attach the on click listener
		ImageView javadoc = (ImageView) rootView.findViewById(R.id.javalogo);
		javadoc.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.addCategory(Intent.CATEGORY_BROWSABLE);
				intent.setData(Uri
						.parse("http://ccullens.com/codycullens/javadoc/index.html"));
				startActivity(intent);
			}
		});

		return rootView;
	}

}