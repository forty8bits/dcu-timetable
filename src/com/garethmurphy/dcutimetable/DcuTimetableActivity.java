package com.garethmurphy.dcutimetable;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class DcuTimetableActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Setup the event handler to process details and send the Intent.
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new OnClickListener() {
        	
        	private EditText code = (EditText)findViewById(R.id.cdEntry);   
			private EditText year = (EditText)findViewById(R.id.yrEntry);   
			private RadioButton sem1 = (RadioButton)findViewById(R.id.sem1Btn);
			
			public void onClick(View v) {
				if (code.length() == 0 || year.length() == 0) return;
				
				String url = "http://www.dcu.ie/timetables/feed.php3" 
						   + "?hour=1-28&template=student";
				
				// Add the class code to the URL.
				url += "&prog=" + code.getText();
				
				// The year of study.
				url += "&per=" + year.getText();
				
				if (sem1.isChecked()) {
					url += "&week1=1&week2=12";
				} else {
					url += "&week1=13&week2=24";
				}
				
				// Send out the intent to open the timetable in default browser.
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}
		});
    }
}