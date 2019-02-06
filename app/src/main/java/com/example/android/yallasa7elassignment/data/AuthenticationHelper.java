package com.example.android.yallasa7elassignment.data;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AuthenticationHelper {

    private final String TAG = "AuthenticationHelper";
    private YallaSa7elDBHelper dBHelper;
    private SQLiteDatabase database;


    AuthenticationHelper( YallaSa7elDBHelper dBHelper, SQLiteDatabase database ) {
        this.dBHelper = dBHelper;
        this.database = database;
    }


    public boolean signIn( User user ) {
        Log.d(TAG, "signIn method executes");
        return dBHelper.checkIfUserExists( user, database );
    }


    public Boolean signUp( User user ) {
        Log.d(TAG, "signUp method executes");
        boolean userExists = dBHelper.checkIfUserExists( user, database );

        if( userExists ) {
            Log.d(TAG, "User already exists");
            // return null indicating that the user already has an account
            return null;
        }

        Log.d(TAG, "User does not exist -> insert and set state");
        // Insert user and set the signed in state
        return dBHelper.insertUser( user, database ) && dBHelper.setSignedInState(database);
    }


    public boolean signout() {
        Log.d(TAG, "signOut method executes");
        return dBHelper.clearSignedInState( database );
    }
}
