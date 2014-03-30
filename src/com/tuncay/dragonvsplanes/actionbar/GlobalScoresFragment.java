package com.tuncay.dragonvsplanes.actionbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tuncay.dragonvsplanes.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class GlobalScoresFragment extends ListFragment{

	private static final String TAG = GlobalScoresFragment.class.getSimpleName();

	private String jsonResult;
	private String url = "http://tuncayaltinpulluk.com/dragonvsplanes/get_100_scores.php";
	ProgressDialog progress;
	boolean error;
	Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.scores, container, false);
		context = this.getActivity();
		FillScores();
		
		return view;    
	}

	// Async Task to access the web
	private class JsonReadTask extends AsyncTask<String, Void, String> {
		
		ProgressDialog progress;
		
		@Override
		protected void onPreExecute() {
	        Log.d(TAG, " pre execute async");
	        progress = new ProgressDialog(getActivity());	 
	        progress.setIndeterminate(true);
	        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progress.setMessage("Getting global scores...");
	        progress.show();
	    }
		
		@Override
		protected String doInBackground(String... params) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(params[0]);
			try {
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				
				jsonResult = inputStreamToString(
						entity.getContent()).toString();
				
				if (jsonResult != null){
	            	Log.d(TAG, jsonResult);
	            }
	            else{
	                return "No string.";
	            }
			}
			catch (ClientProtocolException e) {
				e.printStackTrace();
				error = true;
			} catch (IOException e) {
				e.printStackTrace();
				error = true;
			}catch (Exception e) {
				e.printStackTrace();
				error = true;
			}
			return null;
		}

		private StringBuilder inputStreamToString(InputStream is) {
			String rLine = "";
			StringBuilder answer = new StringBuilder();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));

			try {
				while ((rLine = rd.readLine()) != null) {
					answer.append(rLine);
				}
			}

			catch (IOException e) {
				e.printStackTrace();
			}catch (Exception e){
				e.printStackTrace();
			}
			return answer;
		}

		@Override
		protected void onPostExecute(String result) {
			if (error){
				String errorText = "Error on getting global scores. Check your internet connection";
				Toast toast = Toast.makeText(context, errorText, Toast.LENGTH_LONG);
				toast.show();
			}else{
				ListDrawer();
			}
			progress.dismiss();
		}
	}// end async task

	public void FillScores() {
		JsonReadTask task = new JsonReadTask();
		// passes values for the urls string array
		task.execute(new String[] { url });
	}

	// build hash set for list view
	public void ListDrawer() {
		List<Map<String, String>> scoreList = new ArrayList<Map<String, String>>();

		try {
			JSONObject jsonResponse = new JSONObject(jsonResult);
			JSONArray jsonMainNode = jsonResponse.optJSONArray("scorerecords");

			for (int i = 0; i < jsonMainNode.length(); i++) {
				JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
				String name = jsonChildNode.optString("name");
				String score = jsonChildNode.optString("score");	
				
				HashMap<String, String> nameScore = new HashMap<String, String>();
				nameScore.put("number", String.valueOf(i + 1));
				nameScore.put("name", name);
				nameScore.put("score", score);
				
				scoreList.add(nameScore);
				Log.d(TAG, name + " " + score);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		String[] from = {"number", "name", "score"};
		int[] to = {R.id.txtScoreCursorNumber, R.id.txtScoreCursorName, R.id.txtScoreCursorScore};
		
		SimpleAdapter simpleAdapter = new SimpleAdapter(this.getActivity(), scoreList, 
				R.layout.score_cursor, from, to);

		this.setListAdapter(simpleAdapter);
	}
}