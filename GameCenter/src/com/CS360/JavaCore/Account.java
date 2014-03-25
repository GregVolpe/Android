package com.CS360.JavaCore;

import java.util.ArrayList;
/**
 * Simple class representing the users Account
 * @author Greg Volpe / Cody Cullens
 *
 */
public class Account 
{
	private String gamerTag, DBpassword, localPassword;
	private String email, fName, lName;
	/**
	 * Basic constructor to generate empty account class
	 */
	public Account()
	{
		//constructor code...
		gamerTag = null;
		DBpassword = null;
		localPassword = null;
		email = null;
		fName = null;
		lName = null;
	}
	/**
	 * Basic constructor to create an Account with data
	 * @param mGamerTag The user's GamerTag
	 * @param mFirst The user's First Name
	 * @param mLast The user's Last Name
	 * @param mEmail The user's Email
	 * @param mPassword the user's local password
	 */
	public Account(String mGamerTag, String mFirst, String mLast, String mEmail, String mPassword)
	{
		gamerTag= mGamerTag;
		fName = mFirst;
		lName = mLast;
		email = mEmail;
		localPassword = mPassword;
	}
	/**
	 * Bulk fill data method to fill in the data after a blank user account is created.
	 * @param first User's first name.
	 * @param last User's last name.
	 * @param eMail User's Email address
	 * @param aGamer User's GamerTag 
	 * @param pass User's local password
	 */
	public void setData(String first, String last, String eMail, String aGamer,
			String pass)
	{
		gamerTag = aGamer;
		localPassword = pass;
		email = eMail;
		fName = first;
		lName = last;
	}
	/**
	 * Method to get the user's password from the database
	 * @return the password from the database
	 */
	public String getDBPassword(String gamerTag, DatabaseAccess DB)
	{
		String pass = null;
		//create ArrayList for desired columns
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add("Password");
		//query database and store the password
		pass = DB.getDBpassword(gamerTag, columnNames).get(0);
		//return the password
		return pass;
	}
	/**
	 * Method to get the password entered locally on the device
	 * @return local password
	 */
	public String getLocalPassword()
	{
		return localPassword;
	}
	/**
	 * Method to set the local GamerTag for the user 
	 * @param tag
	 */
	public void setGamerTag(String tag)
	{
		gamerTag = tag;
	}
	/**
	 * Method to set the local Fist Name for the user
	 * @param tag Local first name
	 */
	public void setfName(String tag)
	{
		fName = tag;
	}
	/**
	 * Method to set the local last name for the user
	 * @param tag local last name
	 */
	public void setlName(String tag)
	{
		lName = tag;
	}
	/**
	 * Method to set the local email address for the user
	 * @param tag
	 */
	public void setEmail(String tag)
	{
		email = tag;
	}
	/**
	 * Overloaded method to check two passwords for match
	 * @param password1 password 1
	 * @param password2 password 2
	 * @return boolean result of verification
	 */
	static public ArrayList<Object> checkPassword(String password1, String password2)
	{
		boolean verified = false;
		String message = "";
		ArrayList<Object> results= new ArrayList<Object>();
		if(password1.equals(password2))
		{
			verified = true;
		}
		if(password1.length() < 5)
		{
			verified = false;
			message +=(" Password must be at least 6 chars\n");
		}
		if(!password1.equals(password2))
		{
			verified = false;
			message +=("Passwords do not match\n");
		}
		results.add(verified);
		results.add(message);
		return results;
	}

	/**
	 * Method to check the local password with password from the database, precondition is that local and database password are both !=null
	 * @return boolean result of verification
	 */
	public boolean checkCredentials()
	{
		boolean verified = false;
		if(!DBpassword.equals(null) & !localPassword.equals(null))
			if(DBpassword==localPassword)
				verified = true;

		return verified;
	}
	/**
	 * @return User's local email address
	 */
	public String getEmail()
	{
		return email;
	}
	/**
	 * @return User's First name
	 */
	public String getFname()
	{
		return fName;
	}
	/**
	 * @return User's last name
	 */
	public String getLname()
	{
		return lName;
	}
	/**
	 * @return User's GamerTag
	 */
	public String getGamerTag()
	{
		return gamerTag;
	}

	/**
	 * @param password User's local password
	 */
	public void setLocalpassword(String password) 
	{
		localPassword = password;
	}








}
