package com.challengeaccepted.challengeaccepted;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
	
	private EditText mFirstnameEditText;
	private EditText mLastnameEditText;
	private EditText mEmailEditText;
	private EditText mPassEditText;
	private Button mLoginButton;
	private Button mCreateAccountButton;
	
	private static URL baseURL = null;
	
	{
		try {
			baseURL = new URL("http://ec2-54-200-160-108.us-west-2.compute.amazonaws.com:3000/");
			
		} catch(MalformedURLException m) {
			m.printStackTrace();
		}
	}
	
	public static String[] doLogin(String email, String pass) {
		String auth = null;
		String firstName = null;
		String lastName = null;
		
		try {
			System.out.println("Beginning login");
			DefaultHttpClient httpclient = new DefaultHttpClient();
			
			HttpPost httppost = new HttpPost(baseURL + "login");
			
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("email", email));
	        nameValuePairs.add(new BasicNameValuePair("password", pass));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			
			InputStream in = entity.getContent();
			InputStreamReader is = new InputStreamReader(in);
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(is);
			String read = br.readLine();
	
			while(read != null) {
			    // System.out.println(read);
			    sb.append(read);
			    read = br.readLine();
			} 
			
			String json = sb.toString();
			System.out.println("Login response recieved");
			
			JSONObject loginDataJSON = new JSONObject(json);
			
			// possible jsons
			// {"error":"The username and password combination are not valid."}
			// {"id":1,"email":"j@j.j","created_at":"2014-05-24T18:34:57.331Z","updated_at":"2014-05-24T23:22:01.993Z","auth_token":"PoBHyy9aspoy8_DMyzE4","first_name":"Jake","last_name":"Heiser","customer_id":null}
			
			auth = loginDataJSON.getString("auth_token");
			firstName = loginDataJSON.getString("first_name");
			lastName = loginDataJSON.getString("last_name");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException j) {
			j.printStackTrace();
		}
		
		String[] ret = {auth, firstName, lastName};
		return ret;
	}
	
	public static String doSignup(String firstName, String lastName, String email, String pass) {
		String auth = null;
		
		try {
			System.out.println("Beginning signup");
			DefaultHttpClient httpclient = new DefaultHttpClient();
			
			HttpPost httppost = new HttpPost(baseURL + "signup");
			
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
			nameValuePairs.add(new BasicNameValuePair("first_name", firstName));
			nameValuePairs.add(new BasicNameValuePair("last_name", lastName));
	        nameValuePairs.add(new BasicNameValuePair("email", email));
	        nameValuePairs.add(new BasicNameValuePair("password", pass));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			
			InputStream in = entity.getContent();
			InputStreamReader is = new InputStreamReader(in);
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(is);
			String read = br.readLine();
	
			while(read != null) {
			    // System.out.println(read);
			    sb.append(read);
			    read = br.readLine();
			} 
			
			String json = sb.toString();
			System.out.println("Login response recieved");
			
			JSONObject loginDataJSON = new JSONObject(json);
			
			auth = loginDataJSON.getString("auth_token");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException j) {
			j.printStackTrace();
		}
		
		return auth;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		try {
			baseURL = new URL("http://ec2-54-200-160-108.us-west-2.compute.amazonaws.com:3000/login");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		mEmailEditText = (EditText)findViewById(R.id.email_textview);
		mPassEditText = (EditText)findViewById(R.id.password_textview);
		
		mLoginButton = (Button)findViewById(R.id.login_button);
		mLoginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String email = mEmailEditText.getText().toString();
				String pass = mPassEditText.getText().toString();
				String[] response = null;
				
				response = doLogin(email, pass);
				
				// Start MainActivity
				if(response[0] != null) {
					Intent i = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(i);
				}
			}
		});
		
		mCreateAccountButton = (Button)findViewById(R.id.create_button);
		mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String firstName = mEmailEditText.getText().toString();
				String lastName = mEmailEditText.getText().toString();
				String email = mEmailEditText.getText().toString();
				String pass = mPassEditText.getText().toString();
				String response = null;
				
				response = doSignup(firstName, lastName, email, pass);
				
				// Start MainActivity
				if(response != null) {
					Intent i = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(i);
				}
			}
		});
	}
}
