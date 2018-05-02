package com.example.pcaragones.eyetrack20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.app.ActionBar;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    // Email, password edittext
    EditText txtUsername, txtPassword;

    // login button
    Button btnLogin;
    Button btnSignup;

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    SessionManager session;// Email, password edittext


    private static final String BASE_URL = "http://192.168.254.108:8000";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("EyeGuard");
//        getActionBar().setIcon(R.drawable.launcher);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("EyeTrack");
//        actionBar.setIcon(R.drawable.launcher);
        // Session Manager
        session = new SessionManager(getApplicationContext());

        // Email, Password input text
        txtUsername = (EditText) findViewById(R.id.editText);
        txtPassword = (EditText) findViewById(R.id.editText3);

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();


        // Login button
        btnLogin = (Button) findViewById(R.id.button);
        btnSignup = (Button) findViewById(R.id.signUpLogin);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(i);
            }
        });


        // Login button click event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get username, password from EditText
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                // Check if username, password is filled
                if(username.trim().length() > 0 && password.trim().length() > 0){
                    Toast.makeText(getApplicationContext(), "Logging in...", Toast.LENGTH_LONG).show();
                    String response = null;

                    HttpHandler h = new HttpHandler();
                    String params[] = {"/api/android/login","POST","username",username,"password",password};
                    try{
                        response = h.execute(params).get();

                    }catch (Exception e) {
                        Log.e("Error","Exception: " + e);
                    }
                    if(response=="incorrect"){
                        // username / password doesn't match
                        alert.showAlertDialog(LoginActivity.this, "Login failed..", "Username/Password is incorrect", false);
                    }
                    if(response!=null){
                        JSONObject res = null;
                        try{
                            res = new JSONObject(response);
                            // Creating user login session
                            // For testing i am stroing name, email as follow
                            // Use user real data

                            String params1[] = {"/api/getOneByID/" + res.getString("CompanyID"), "GET"};
                            HttpHandler h1 = new HttpHandler();
                            String response1 = h1.execute(params1).get();
                            JSONObject res1 = new JSONObject(response1);
                            String companyName = res1.getString("Name");
                            if(res.getString("IsVerified").equals("0")){
                                Toast.makeText(getApplicationContext(), "Please wait to be verified", Toast.LENGTH_LONG).show();
                            }else {
                                session.createLoginSession(res.getString("FirstName") + " " + res.getString("LastName"), res.getString("CompanyID"), companyName, res.getString("ID"));
                                Toast.makeText(getApplicationContext(), "Logging In", Toast.LENGTH_LONG).show();
                                //Staring MainActivity
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }catch (Exception e) {
                            Log.e("Error","Exception: " + e);
                        }
                    }else{
                        // username / password doesn't match
                        alert.showAlertDialog(LoginActivity.this, "Login failed..", "Username/Password is incorrect", false);

                    }
                }else{
                    // user didn't entered username or password
                    // Show alert asking him to enter the details
                    alert.showAlertDialog(LoginActivity.this, "Login failed..", "Please enter username and password", false);
                }

            }
        });
    }
}
