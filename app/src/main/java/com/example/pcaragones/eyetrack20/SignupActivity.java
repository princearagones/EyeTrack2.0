package com.example.pcaragones.eyetrack20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity {

    EditText txtFirstName;
    EditText txtLastName;
    EditText txtUsername;
    EditText txtPassword;
    EditText txtConfirmPassword;
    EditText txtEmail;
    EditText txtCompanyCode;

    Button btnSignup;
    Button btnCancel;

    AlertDialogManager alert = new AlertDialogManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnSignup = (Button) findViewById(R.id.btnSignup);
        btnCancel = (Button) findViewById(R.id.btnCancelSignUp);

        txtFirstName = (EditText) findViewById(R.id.txtFirstName);
        txtLastName = (EditText) findViewById(R.id.txtLastName);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtCompanyCode = (EditText) findViewById(R.id.txtCompanyCode);



        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = txtFirstName.getText().toString();
                String lastName = txtLastName.getText().toString();
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                String confirmPassword = txtConfirmPassword.getText().toString();
                String email = txtEmail.getText().toString();
                String companyCode = txtCompanyCode.getText().toString();
                String res = "";

                if(firstName.trim().length() <= 0){
                    Toast.makeText(getApplicationContext(), "Please enter First Name ", Toast.LENGTH_LONG).show();
                }else if(lastName.trim().length() <= 0){
                    Toast.makeText(getApplicationContext(), "Please enter Last Name ", Toast.LENGTH_LONG).show();
                }else if(username.trim().length() <= 0){
                    Toast.makeText(getApplicationContext(), "Please enter Username", Toast.LENGTH_LONG).show();
                }else if(password.trim().length() <= 0 || confirmPassword.trim().length() <= 0){
                    Toast.makeText(getApplicationContext(), "Please enter Password", Toast.LENGTH_LONG).show();
                }else if(email.trim().length() <= 0){
                    Toast.makeText(getApplicationContext(), "Please enter Email", Toast.LENGTH_LONG).show();
                }else if(companyCode.trim().length() <= 0){
                    Toast.makeText(getApplicationContext(), "Please enter Company Code", Toast.LENGTH_LONG).show();
                }else if(!password.trim().equals(confirmPassword.trim())){
                    Toast.makeText(getApplicationContext(), "Password not matched ", Toast.LENGTH_LONG).show();
                }else if(!isValidEmailAddress(email.trim())){
                    Toast.makeText(getApplicationContext(), "Invalid email address ", Toast.LENGTH_LONG).show();
                }else if(!isUsernameAvailable(username)){
                    Toast.makeText(getApplicationContext(), "Username already taken ", Toast.LENGTH_LONG).show();
                }else if(getCompanyByCode(companyCode.trim()).equals("")){
                    Toast.makeText(getApplicationContext(), "Invalid Company Code", Toast.LENGTH_LONG).show();
                }else{
                    try {
                        JSONObject company = new JSONObject(getCompanyByCode(companyCode));
                        HttpHandler h = new HttpHandler();
                        String params[] = {"/api/user/add","POST","lastname",lastName,"firstname",firstName,"username",username,"password",password,"companyid",company.getString("ID"),"email",email};
                        res = h.execute(params).get();
                        Log.d("new user",res);
                        if(!res.equals("incorrect")){
                            Toast.makeText(getApplicationContext(), "Registered on " + company.getString("Name")+ ". Please to be verified", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(i);
                        }
                        else Toast.makeText(getApplicationContext(), "Sign up failed, check connection", Toast.LENGTH_LONG).show();
                    }catch (Exception e){

                    }
                }
            }
        });
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    public static boolean isUsernameAvailable(String username){
        HttpHandler h = new HttpHandler();
        String params[] = {"/api/user/username/" + username, "GET"};
        String res = "";
        try{
            res = h.execute(params).get();
        }catch(Exception e){

        }
        if(res.equals("")){
            return true;
        }else{
            return false;
        }
    }
    public static String getCompanyByCode(String companyCode){
        HttpHandler h = new HttpHandler();
        String params[] = {"/api/getOneByCode/" + companyCode, "GET"};
        String res = "";
        try{
            res = h.execute(params).get();
        }catch(Exception e){

        }
        return res;
    }
}

