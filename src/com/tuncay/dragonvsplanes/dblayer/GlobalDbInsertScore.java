package com.tuncay.dragonvsplanes.dblayer;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class GlobalDbInsertScore extends AsyncTask<String, Void, String >{

	private static final String TAG = GlobalDbInsertScore.class.getSimpleName();
	
	private Context context;
	private String name;
	private double score;
	boolean error = false;
	private HttpResponse response;

	public GlobalDbInsertScore(Context context, String name, double score) {			
		this.context = context;
		this.name = name;
		this.score = score;		
	}

	protected void onPreExecute(){

	}
	@Override
	protected String doInBackground(String... args) {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://tuncayaltinpulluk.com/dragonvsplanes/insert_score.php");
		
		Log.d(TAG, "client and post set");

        try
        {
        	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("name", name));
            nameValuePairs.add(new BasicNameValuePair("score", Double.toString(score)));
            
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            
            if (entity != null){
            	Log.d(TAG, EntityUtils.toString(entity));
            }
            else{
                return "No string.";
            }
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	error = true;
        }
		return name;

	}
	@Override
	protected void onPostExecute(String result){
		if (error){
			String errorText = "Error on updating global scores. Check your internet connection";
			Toast toast = Toast.makeText(context, errorText, Toast.LENGTH_LONG);
			toast.show();
		}
	}

}
