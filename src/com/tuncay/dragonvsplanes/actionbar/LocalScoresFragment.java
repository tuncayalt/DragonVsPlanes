package com.tuncay.dragonvsplanes.actionbar;


import com.tuncay.dragonvsplanes.R;
import com.tuncay.dragonvsplanes.dblayer.CustomDBAdapter;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

public class LocalScoresFragment extends ListFragment{
	
	private static final String TAG = LocalScoresFragment.class.getSimpleName();
	
	CustomDBAdapter dbAdapter;
	Cursor cur;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
			
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.scores, container, false);   

		FillScores();

		return view;  
    }
	
	@Override
	public void onDestroyView(){
		super.onDestroyView();
		cur.close();
		dbAdapter.close();
	}
	
	private void FillScores() {
		
		dbAdapter = new CustomDBAdapter(this.getActivity().getApplicationContext());
		
		try
		{
			cur = dbAdapter.getScores(); 
			String[] from = {dbAdapter.getNameColumn(), dbAdapter.getScoreColumn()};
			int[] to = {R.id.txtScoreCursorName, R.id.txtScoreCursorScore};
			
			SimpleCursorAdapter adapter = new SimpleCursorAdapter(this.getActivity().getApplicationContext(), 
					R.layout.score_cursor, cur, from, to);
			 
			//ListView list = (ListView)this.findViewById(android.R.id.list);
			
			this.setListAdapter(adapter);
			
		}catch(Exception e){
			Log.e(TAG, e.toString());
		}
		
	}

	public void onClickBtnScores (View view){
		cur.close();
		dbAdapter.close();
		this.getActivity().finish();
	}
}
