package com.garethmurphy.dcutimetable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DcuTimetableActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button myTimetable = (Button) findViewById(R.id.myTimetable);
        Button searchTimetables = (Button) findViewById(R.id.searchTimetables);

        myTimetable.setOnClickListener(new OnClickListener() {	
            public void onClick(View v) {
        	Intent myIntent = new Intent(v.getContext(), MyTimetable.class);
                startActivityForResult(myIntent, 0);
            }
        });
        
        searchTimetables.setOnClickListener(new OnClickListener() {	
            public void onClick(View v) {
        	Intent myIntent = new Intent(v.getContext(), SearchTimetable.class);
                startActivityForResult(myIntent, 0);
            }
        });
        
    }
    
}
