package com.example.pcaragones.eyetrack20;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by pcaragones on 7/7/17.
 */

class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Company name (make variable public to access from outside)
    public static final String KEY_COMPANY_NAME = "companyName";

    // Company id (make variable public to access from outside)
    public static final String KEY_COMPANY_ID = "companyID";

    // Company id (make variable public to access from outside)
    public static final String KEY_TIMEIN = "timeIn";


    public static final String KEY_ID = "id";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name, String companyID, String companyName, String id){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);


        editor.putBoolean(KEY_TIMEIN, false);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing company name in pref
        editor.putString(KEY_COMPANY_NAME, companyName);

        // Storing company id in pref
        editor.putString(KEY_COMPANY_ID, companyID);

        // Storing userid in pref
        editor.putString(KEY_ID, id);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user company
        user.put(KEY_COMPANY_NAME, pref.getString(KEY_COMPANY_NAME, null));

        // user company
        user.put(KEY_COMPANY_ID, pref.getString(KEY_COMPANY_ID, null));


        user.put(KEY_ID, String.valueOf(pref.getBoolean(KEY_TIMEIN, false)));

        // user id
        user.put(KEY_ID, pref.getString(KEY_ID, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public boolean isTimedIn(){
        return pref.getBoolean(KEY_TIMEIN, false);
    }
}
