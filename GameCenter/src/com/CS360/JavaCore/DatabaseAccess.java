package com.CS360.JavaCore;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * Class used to access the database that stores user, game, leaderboard
 * information
 * @author Greg Volpe / Cody Cullens
 */
public class DatabaseAccess {
	@SuppressWarnings("unused")
	static private String DBCONNECTION;

	/**
	 * Simple constructor to instantiate a null object
	 */
	public DatabaseAccess() {
		DBCONNECTION = null;

	}

	/**
	 * Method used to get data from the database
	 * @param query query to send to the database
	 * @return interpreted data
	 */
	public ArrayList<String> getData(String query, ArrayList<String> columnNames) {
		//Create a new NVP
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		//Add query to NVP
		nameValuePairs.add(new BasicNameValuePair("QUERY", query));
		// calls Connection
		return Connection(nameValuePairs, columnNames);

	};

	private ArrayList<String> Connection(ArrayList<NameValuePair> NVP,
			ArrayList<String> columnNames) {

		InputStream is = null;

		try {
			//make a new URI
			URI uri = new URI("http://ccullens.com/codycullens/Query.php");
			//Make a new HttpClient
			HttpClient httpclient = new DefaultHttpClient();
			//Create a new HTTP Post
			HttpPost httppost = new HttpPost(uri);
			//Set the HTTP Post entitiy
			httppost.setEntity(new UrlEncodedFormEntity(NVP));
			//Get the response from the server
			HttpResponse response = httpclient.execute(httppost);
			//Get the response entity
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}

		// calls Convert
		return Convert(is, columnNames);
	};

	private ArrayList<String> Convert(InputStream is,
			ArrayList<String> columnNames) {
		String result = "";
		// convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			result = sb.toString();

			// debugging display
			Log.i("log_tag", "Results of the Query: " + result);

		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}

		// calls correct ParseJSON based on the parseCode
		return ParseJSON(result, columnNames);

	};

	private ArrayList<String> ParseJSON(String JSONresult,
			ArrayList<String> columnNames) {

		int columnNumber = columnNames.size();
		ArrayList<String> result = new ArrayList<String>();

		// parse JSON data
		try {
			JSONArray jArray = new JSONArray(JSONresult);
			JSONObject json_data = jArray.getJSONObject(0);

			for (int i = 0; i < columnNumber; i++) {
				result.add(i, json_data.getString(columnNames.get(i)));
			}

		}

		catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());

			ArrayList<String> error = new ArrayList<String>();
			error.add("error");
			error.add("error");
			return error;
		}

		return result;
	}

	/**
	 * Method used to send data to the database
	 * @param data data to send to the database
	 * @return boolean result of indicating successful operation
	 */
	public boolean setData(String data) {
		boolean error = false;
		// send data to be written in the database
		// create name value pair
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		// add query
		nameValuePairs.add(new BasicNameValuePair("QUERY", data));

		// http post
		InputStream is = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://www.ccullens.com/codycullens/writeAccountQuery.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
			error = true;
		}
		return error;
	}
	/**
	 * This method is used to get the users password from the database
	 * @param gamertag The users gamertag
	 * @param columnNames The columns needed from the database
	 * @return parsed results of the query
	 */
	public ArrayList<String> getDBpassword(String gamertag,
			ArrayList<String> columnNames) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		// /////////********This is the where the query goes!!!!!!!!
		nameValuePairs.add(new BasicNameValuePair("QUERY", gamertag));

		// calls Connection
		return Connection_password(nameValuePairs, columnNames);

	};

	private ArrayList<String> Connection_password(ArrayList<NameValuePair> NVP,
			ArrayList<String> columnNames) {

		// http post
		InputStream is = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://ccullens.com/codycullens/passwordQuery.php");
			httppost.setEntity(new UrlEncodedFormEntity(NVP));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
			ArrayList<String> error = new ArrayList<String>();
			error.add("error");
			return error;

			// maybe use toast to display "Connection error"?
		}

		// calls Convert
		return Convert(is, columnNames);
	};

	/*
	 * public ArrayList<String> getTopTen(int gameID, ArrayList<String>
	 * columnNames) { ArrayList<NameValuePair> nameValuePairs = new
	 * ArrayList<NameValuePair>();
	 * 
	 * nameValuePairs.add(new BasicNameValuePair("QUERY",
	 * Integer.toString(gameID)));
	 * 
	 * //http post InputStream is = null; try{ HttpClient httpclient = new
	 * DefaultHttpClient();
	 * httppost = new
	 * HttpPost("http://ccullens.com/codycullens/toptenQuery.php");
	 * httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	 * HttpResponse response = httpclient.execute(httppost); HttpEntity entity =
	 * response.getEntity(); is = entity.getContent(); }catch(Exception e){
	 * Log.e("log_tag", "Error in http connection "+e.toString()); //maybe use
	 * toast to display "Connection error"? } ArrayList<Object> ranks = new
	 * ArrayList<Object>(); ranks.add(new ArrayList<String>()); ranks.add(new
	 * ArrayList<Integer>()); //calls Convert return Convert(is, columnNames);
	 * 
	 * 
	 * 
	 * };
	 */
	/**
	 * This method is used to get the top ten players for a game
	 * @param gameID The game ID of the game
	 * @param columnNames The names of the columns needed from the database
	 * @return Parsed results of the query
	 */
	public ArrayList<ArrayList<String>> getTopTen(int gameID,
			ArrayList<String> columnNames) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("QUERY", Integer
				.toString(gameID)));

		// http post
		InputStream is = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://ccullens.com/codycullens/toptenQuery.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}
		ArrayList<Object> ranks = new ArrayList<Object>();
		ranks.add(new ArrayList<String>());
		ranks.add(new ArrayList<Integer>());
		// calls Convert


		String result = "";
		// convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			result = sb.toString();

			// debugging display
			Log.i("log_tag", "Results of the Query: " + result);

		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}

		// calls correct ParseJSON based on the parseCode

		// this may be the part where we need a new one for each query

		int columnNumber = columnNames.size();

		ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
		ArrayList<JSONObject> topTen = new ArrayList<JSONObject>();

		// parse json data
		try {
			JSONArray jArray = new JSONArray(result);
			int size = jArray.length();
			for (int i = 0; i < size; i++) {
				topTen.add(jArray.getJSONObject(i));
			}
			for (int i = 0; i < size; i++) {
				ArrayList<String> temp = new ArrayList<String>();
				for (int j = 0; j < 2; j++) {

					temp.add(topTen.get(i).getString(columnNames.get(j)));
				}
				results.add(temp);
			}

		}

		catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
			ArrayList<ArrayList<String>> error = new ArrayList<ArrayList<String>>();
			ArrayList<String> temp = new ArrayList<String>();
			temp.add("error");
			error.add(temp);
			return error;
			// maybe use toast to display "Parsing error"?
		}

		return results;
	}

	/**
	 * This method is used to get the games owned by the current user
	 * @param gamerTag The user's GamerTag
	 * @param columnNames The names of the columns needed from the database
	 * @return Parsed query results
	 */
	public ArrayList<String> getMyGames(String gamerTag,
			ArrayList<String> columnNames) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("QUERY", gamerTag));

		// http post
		InputStream is = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://ccullens.com/codycullens/myGamesQuery.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
			// maybe use toast to display "Connection error"?
		}
		ArrayList<Object> ranks = new ArrayList<Object>();
		ranks.add(new ArrayList<String>());
		ranks.add(new ArrayList<Integer>());
		// calls Convert

		String result = "";
		// convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			result = sb.toString();

			// debugging display
			Log.i("log_tag", "Results of the Query: " + result);

		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}

		// calls correct ParseJSON based on the parseCode

		// this may be the part where we need a new one for each query

		int columnNumber = columnNames.size();

		ArrayList<String> results = new ArrayList<String>();
		ArrayList<JSONObject> topTen = new ArrayList<JSONObject>();

		// parse json data
		try {
			JSONArray jArray = new JSONArray(result);
			int size = jArray.length();
			for (int i = 0; i < size; i++) {
				topTen.add(jArray.getJSONObject(i));
			}

			for (int i = 0; i < size; i++) {

				results.add(topTen.get(i).getString(columnNames.get(0)));
			}

		}

		catch (JSONException e) {
			Log.e("log_tag", "MyGames Query " + e.toString());
			Log.e("log_tag", "Error parsing data " + e.toString());
			ArrayList<String> error = new ArrayList<String>();
			error.add("error");

			return error;
			// maybe use toast to display "Parsing error"?
		}

		return results;
	}

	/**
	 * Method used to send data to the database
	 * @param GamerTag User's GamerTag
	 * @param fName User's First Name
	 * @param lName User's Last Name
	 * @param email User's Email
	 * @param Password User's Password
	 * @return Boolean result of the operation
	 */
	public boolean addNewAccount(String GamerTag, String fName, String lName,
			String email, String Password) {
		boolean error = true;

		// create name value pair
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		// add query
		nameValuePairs.add(new BasicNameValuePair("GamerTag", GamerTag));
		nameValuePairs.add(new BasicNameValuePair("fName", fName));
		nameValuePairs.add(new BasicNameValuePair("lName", lName));
		nameValuePairs.add(new BasicNameValuePair("email", email));
		nameValuePairs.add(new BasicNameValuePair("Password", Password));

		// http post
		InputStream is = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://www.ccullens.com/codycullens/writeAccountQuery.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			error = false;
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
			error = true;
		}
		return error;
	}
	/**
	 * This method gets all information about the current user from the database
	 * @param gamertag The gamertag of the current user
	 * @param columnNames the names of the columns needed from the database
	 * @return parsed query results
	 */
	public ArrayList<String> getAccount(String gamertag,
			ArrayList<String> columnNames) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("QUERY", gamertag));
		// calls Connection
		return Connection_Account(nameValuePairs, columnNames);
	};

	private ArrayList<String> Connection_Account(ArrayList<NameValuePair> NVP,
			ArrayList<String> columnNames) {

		// http post
		InputStream is = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://ccullens.com/codycullens/accountQuery.php");
			httppost.setEntity(new UrlEncodedFormEntity(NVP));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
			// maybe use toast to display "Connection error"?
		}

		// calls Convert
		return Convert(is, columnNames);
	};
	/**
	 * This method is used to update an existing account in the database
	 * @param GamerTag The user's gamertag
	 * @param fName The user's First Name
	 * @param lName The user's Last Name
	 * @param email The user's Email Address
	 * @param Password The user's Password
	 * @return boolean result of the operation 
	 */
	public boolean updateAccount(String GamerTag, String fName, String lName,
			String email, String Password) {
		boolean error = true;

		// create name value pair
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		// add query
		nameValuePairs.add(new BasicNameValuePair("GamerTag", GamerTag));
		nameValuePairs.add(new BasicNameValuePair("fName", fName));
		nameValuePairs.add(new BasicNameValuePair("lName", lName));
		nameValuePairs.add(new BasicNameValuePair("email", email));
		nameValuePairs.add(new BasicNameValuePair("Password", Password));

		// http post
		InputStream is = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://www.ccullens.com/codycullens/updateAccountQuery.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			error = false;
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
			error = true;
		}
		ArrayList<String> stuff = Convert(is, new ArrayList<String>());
		return error;
	}

	public boolean uninstall(String GamerTag, int gameID) {
		boolean error = true;


		// create name value pair
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		// add query
		nameValuePairs.add(new BasicNameValuePair("GamerTag", GamerTag));
		nameValuePairs.add(new BasicNameValuePair("gameID", Integer
				.toString(gameID + 1)));

		// http post
		InputStream is = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://www.ccullens.com/codycullens/uninstallQuery.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			error = false;
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
			error = true;
		}
		ArrayList<String> stuff = Convert(is, new ArrayList<String>());
		return error;
	}

	public boolean addGame(String GamerTag, int gameID) {
		boolean error = true;

		// create name value pair
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		// add query
		nameValuePairs.add(new BasicNameValuePair("GamerTag", GamerTag));
		nameValuePairs.add(new BasicNameValuePair("gameID", Integer
				.toString(gameID + 1)));

		// http post
		InputStream is = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://www.ccullens.com/codycullens/addGame.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			error = false;
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
			error = true;
		}
		ArrayList<String> stuff = Convert(is, new ArrayList<String>());
		return error;
	}

	public ArrayList<String> getUserList() {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("QUERY",
				"SELECT GamerTag from UserAccount"));
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add("GamerTag");
		// http post
		InputStream is = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://ccullens.com/codycullens/generalQuery.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
			// maybe use toast to display "Connection error"?
		}
		ArrayList<Object> ranks = new ArrayList<Object>();
		ranks.add(new ArrayList<String>());
		ranks.add(new ArrayList<Integer>());
		// calls Convert

		String result = "";
		// convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			result = sb.toString();

			// debugging display
			Log.i("log_tag", "Results of the Query: " + result);

		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
			// maybe use toast to display "Conversion error"?
		}

		// calls correct ParseJSON based on the parseCode

		// this may be the part where we need a new one for each query

		int columnNumber = columnNames.size();

		ArrayList<String> results = new ArrayList<String>();
		ArrayList<JSONObject> userJSON = new ArrayList<JSONObject>();

		// parse json data
		try {
			JSONArray jArray = new JSONArray(result);
			int size = jArray.length();
			for (int i = 0; i < size; i++) {
				userJSON.add(jArray.getJSONObject(i));
			}

			for (int i = 0; i < size; i++) {

				results.add(userJSON.get(i).getString(columnNames.get(0)));
			}

		}

		catch (JSONException e) {
			Log.e("log_tag", "users query " + e.toString());
			Log.e("log_tag", "Error parsing data " + e.toString());
			ArrayList<String> error = new ArrayList<String>();
			error.add("error");

			return error;
			// maybe use toast to display "Parsing error"?
		}

		return results;
	}

	public ArrayList<String> getGameNames() {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("QUERY",
				"SELECT GameName from Games"));
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add("GameName");
		// http post
		InputStream is = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://ccullens.com/codycullens/generalQuery.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
			// maybe use toast to display "Connection error"?
		}
		ArrayList<Object> ranks = new ArrayList<Object>();
		ranks.add(new ArrayList<String>());
		ranks.add(new ArrayList<Integer>());
		// calls Convert

		String result = "";
		// convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			result = sb.toString();

			// debugging display
			Log.i("log_tag", "Results of the Query: " + result);

		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
			// maybe use toast to display "Conversion error"?
		}

		// calls correct ParseJSON based on the parseCode

		// this may be the part where we need a new one for each query

		int columnNumber = columnNames.size();

		ArrayList<String> results = new ArrayList<String>();
		ArrayList<JSONObject> userJSON = new ArrayList<JSONObject>();

		// parse json data
		try {
			JSONArray jArray = new JSONArray(result);
			int size = jArray.length();
			for (int i = 0; i < size; i++) {
				userJSON.add(jArray.getJSONObject(i));
			}

			for (int i = 0; i < size; i++) {

				results.add(userJSON.get(i).getString(columnNames.get(0)));
			}

		}

		catch (JSONException e) {
			Log.e("log_tag", "users query " + e.toString());
			Log.e("log_tag", "Error parsing data " + e.toString());
			ArrayList<String> error = new ArrayList<String>();
			error.add("error");

			return error;
		}

		return results;
	}

}
