package com.challengeaccepted.challengeaccepted;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {
	
	boolean mLoggedIn = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if (savedInstanceState != null) {
        	String authToken = savedInstanceState.getString("authToken");
        	if(authToken == null) {
        		Intent i = new Intent(MainActivity.this, LoginActivity.class);
        		startActivity(i);
        	} else {
        		
        	}
    	}
    	
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}