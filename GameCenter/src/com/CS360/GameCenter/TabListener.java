package com.CS360.GameCenter;
 
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentTransaction;
 /**
  * Class to implement Tab navigation between pages
  * @author HEX
  *
  */
public class TabListener implements ActionBar.TabListener 
{
 
    Fragment fragment;
    /**
     * Method to listen for a tab
     * @param fragment Fragment to listen
     */
    public TabListener(Fragment fragment) 
    {
        this.fragment = fragment;
    }
 
    @Override
    /**
     * Method to perform operation when tab is selected
     * @param tab Current tab
     * @param ft Fragment Transaction to perform operation
     */
    public void onTabSelected(Tab tab, FragmentTransaction ft) 
    {
        
        ft.replace(R.id.fragment_container, fragment);

    }
    
    @Override
    /**
     * Funtion to control operation when tab is unselected
     * @param tab current tab
     * @param ft Fragment Transaction to perform operation
     */
    public void onTabUnselected(Tab tab, FragmentTransaction ft) 
    {
       
        ft.remove(fragment);
    }
 
    @Override
    /**
     * Function to control operation when a tab is reselected
     * @param tab current tab
     * @param ft Fragment Transaction to control operation
     */
    public void onTabReselected(Tab tab, FragmentTransaction ft) 
    {
    	ft.replace(R.id.fragment_container, fragment);
 
    }
}