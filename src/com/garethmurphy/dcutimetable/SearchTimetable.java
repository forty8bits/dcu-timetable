
package com.garethmurphy.dcutimetable;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class SearchTimetable extends Activity {
	
	private String filename = null;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_timetable);
        
    
     // Setup the event handler to process details and send the Intent.
        final Button btn = (Button)findViewById(R.id.button1);
        final EditText classCode = (EditText)findViewById(R.id.cdEntry); 
        
       // This lump of code disables the keyboard from popping up on launch 
        
        classCode.setInputType(InputType.TYPE_NULL);
    
        classCode.setOnTouchListener(new View.OnTouchListener() {
            
            public boolean onTouch(View v, MotionEvent event) {
        	
        	classCode.setInputType(InputType.TYPE_CLASS_TEXT);
        	classCode.onTouchEvent(event); 
        	
            return true;
            
            } 
            
        });
        
        
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
    				
        	int semester;
			if (sem1.isChecked()) {
				url += "&week1=1&week2=12";
				semester = 1;
			} else {
				
				// Displayed on DCU website, these are the proper weeks.
				url += "&week1=20&week2=31";
				semester = 2;
			}
			
			try {
				URL fullUrl = new URL(url);
				
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(fullUrl.openStream())
				);
				
				String html = "";
				String htmlLine = null;
				while ((htmlLine = reader.readLine()) != null) {
					html += htmlLine;
				}
				
				filename = code.getText().toString() + 
						year.getText().toString() + 
						semester + ".html";
				
				FileOutputStream localCopy = openFileOutput(filename,
						Context.MODE_PRIVATE
				);
				
				localCopy.write(html.getBytes());
				localCopy.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
    				
        	// Send out the intent to open the timetable in default browser.
        	Intent i = new Intent(Intent.ACTION_VIEW);
        	i.setData(Uri.parse(url));
        	startActivity(i);
            }
            
        });
    
    }

}
