package com.challengeaccepted.challengeaccepted;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends Activity {
	
	private Button mLoginButton;
	private Button mCreateAccountButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		mLoginButton = (Button)findViewById(R.id.login_button);
		mLoginButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Start MainActivity
				Intent i = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(i);
			}
		});
	}
}
