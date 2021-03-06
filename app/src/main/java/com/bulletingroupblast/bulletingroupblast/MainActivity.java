/**
 * Copyright © 2015 Ruben Piatnitsky
 * This program is released under the "MIT license".
 * Please see the file LICENSE in this distribution for
 * license terms.
 *
 * @Author Ruben Piatnitsky
 */

package com.bulletingroupblast.bulletingroupblast;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.app.Activity;
import android.widget.ProgressBar;

import com.bulletingroupblast.bulletingroupblast.Entities.User;


public class MainActivity extends Activity {

    private User mUserAccount;
    private DatabaseHandler bgb_DB;
    private Intent intentNewUser;
    private Intent intentSignIn;
    private Intent intentUserLanding;
    public final static String USERID_MESSAGE = "com.BulletinGroupBlast.BulletinGroupBlast.UserId";
    private ProgressBar mProgress;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();
    public final static String SESSION_DATA = "SessionDataFile";
    public GlobalState gs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgress = (ProgressBar) findViewById(R.id.progressBar);


        // Start lengthy operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (mProgressStatus < 100) {
                    mProgressStatus += checkLoginData();   // This checks if there is local data available

                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            mProgress.setProgress(mProgressStatus);
                        }
                    });
                }
                Log.i("My Tag","The Data Check is complete!!");
            }
        }).start();

        gs = new GlobalState();
        gs.createTestData();
    }

    /**
     * When the activity resumes from another activity
     */
    @Override
    public void onResume() {
        super.onResume();  // Call the superclass method

        /*TODO: Log the user out*/
        checkLoginData();
    }

    /** Check if a User exists and is set to AutoLogin
     */
    private int checkLoginData() {
        int dataAvailable = LoadLocalData();  //Check for local Data

        try {
            switch (dataAvailable) {
                case -1:
                    goToCreateNewUserAccount(); // Data Error
                    break;
                case 1:
                    GlobalState gs = (GlobalState) getApplicationContext();
                    gs.createTestData();

                    goToUserLanding();          // Log user in automatically
                    break;
                default:
                    goToUserLogin();            // Go to Login page none
                    break;
            }

        } catch (Exception e) {
            /*TODO: Error Log entry*/
        }

        return 10;
    }

    /**
     * Checks to see if any local data on the device and loads it if there is
    * @return int -1 is Fail, 0 is No Local Data, 1 is Data Available and loaded
    */
    private int LoadLocalData() {
        int result = 0;

        try {
            /*TODO: Check the local data stored on device*/
            /*TODO: Load User if one exists locally*/
            mUserAccount = new User("John.Doe@gmail.com","password","John", "Doe");
            mUserAccount.setAutoLogin(false);

            setUserSessionVariable();

//            result = 0;
            result = 1;
        } catch (Exception e) {
            /*TODO: Write to Error Log*/
            result = -1;
        }

        return result;
    }

    /**
     *  Opens the User Landing activity for the user
     */
    private void goToUserLanding() {
        Intent intentUserLanding = new Intent(this, com.bulletingroupblast.bulletingroupblast.UserLandingActivity.class);     // Intent is for switching to a different activity
        intentUserLanding.putExtra(USERID_MESSAGE, mUserAccount.getId());        // Adds the text value to the intent
        super.onResume();
        startActivity(intentUserLanding);
        //message = "SUCCESS - User " + newUser.getEmail() + " Created and Saved!";
    }

    /**
     *  Checks if the user needs to enter credentials or not nd redirects them to login or landing page
     */
    private void goToUserLogin() {
        if (mUserAccount.getAutoLogin()) {
            goToUserLanding();  // Send the user to the landing page
        } else {
            // Send the user to the login page
            Intent intentSignIn = new Intent(this, com.bulletingroupblast.bulletingroupblast.LoginActivity.class);
            startActivity(intentSignIn);
        }
    }

    /**
     *  Redirecting to create a new user account activity
     */
    private void goToCreateNewUserAccount() {
        // Send the user to create a new user account
        Intent intentNewUser = new Intent(this, com.bulletingroupblast.bulletingroupblast.CreateUserAccountActivity.class);
        startActivity(intentNewUser);
    }


    /** Sets the User variable in the shared preferences
     *
     */
    public void setUserSessionVariable() {

        SharedPreferences settings = getSharedPreferences(SESSION_DATA, 0); // Shared Pref File
        SharedPreferences.Editor editor = settings.edit();  // Edit the file
        editor.putInt("userId", mUserAccount.getId());      // Set the User Id
        editor.commit();                                    // Commit the edits!

    }

    public GlobalState getGlobalState() {
        return gs;
    }
}
