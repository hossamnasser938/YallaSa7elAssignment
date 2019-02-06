package com.example.android.yallasa7elassignment.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.yallasa7elassignment.data.YallaSa7elContract.*;

import java.util.ArrayList;

public class YallaSa7elDBHelper extends SQLiteOpenHelper {

    private final String TAG = "YallaSa7elDBHelper";
    private static final String DATABASE_NAME = "yallasa7el.db";
    private static final int DATABASE_VERSION = 1;


    public YallaSa7elDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.d(TAG, "onCreate method executes");
        // Create Space, Users, and SignedInState tables
        createSpaceTable(database);
        createUsersTable(database);
        createSignedInStateTable(database);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Nothing
    }


    public boolean insertSpace( Space space, SQLiteDatabase database ) {
        Log.d(TAG, "insertSpace method executes");

        // Construct the content values object with the given space info
        ContentValues contentValues = new ContentValues();
        contentValues.put(YallaSa7elContract.Space.COLUMN_TITLE, space.getTitle());
        contentValues.put(YallaSa7elContract.Space.COLUMN_DESTINATION, space.getDestination());
        contentValues.put(YallaSa7elContract.Space.COLUMN_ADDRESS, space.getAddress());
        contentValues.put(YallaSa7elContract.Space.COLUMN_NUMBER_OF_ROOMS, space.getNumberOfRooms());
        contentValues.put(YallaSa7elContract.Space.COLUMN_NUMBER_OF_BATHROOMS, space.getNumberOfBathrooms());
        contentValues.put(YallaSa7elContract.Space.COLUMN_BASE_PRICE, space.getBasePrice());
        contentValues.put(YallaSa7elContract.Space.COLUMN_OWNER_NAME, space.getOwnerName());
        contentValues.put(YallaSa7elContract.Space.COLUMN_MOBILE_NUMBER, space.getMobileNumber());

        if ( database.insert(YallaSa7elContract.Space.TABLE_NAME, null, contentValues) != -1 ) {
            Log.d(TAG, "Space inserted successfully");
            // Successful insertion operation
            return true;
        }
        else {
            Log.e(TAG, "Error while inserting a space");
            // Failure insertion operation
            return false;
        }
    }


    /*
    Retrieve spaces that match the given destination [and number of rooms]
     */
    public ArrayList<Space> querySpace(String pDestination, String pNumberOfRooms , SQLiteDatabase database ) {
        Log.d(TAG, "querySpace method executes");

        // Construct the where clause based on the given info
        String where;
        if ( pNumberOfRooms != null ) {
            Log.d(TAG, "numberOfRooms is given");
            // Destination as well as NumberOfRooms
            where = YallaSa7elContract.Space.COLUMN_DESTINATION + "=" + pDestination
                    + " AND " + YallaSa7elContract.Space.COLUMN_NUMBER_OF_ROOMS + "=" +
            pNumberOfRooms;
        }
        else {
            Log.d(TAG, "numberOfRooms is not given");
            // Destination only
            where = YallaSa7elContract.Space.COLUMN_DESTINATION + "=" + pDestination;
        }

        // Get all columns
        Cursor cursor = database.query(YallaSa7elContract.Space.TABLE_NAME, null, where,
                null, null, null, null);

        if( cursor.getCount() == 0 ) {
            Log.d(TAG, "Cursor is empty");
            cursor.close();
            return null;
        }

        Log.d(TAG, "Cursor is not empty");

        // Prepare array list to hold spaces retrieved from cursor
        ArrayList<Space> spaceList = new ArrayList<>();

        // Prepare column indices to retrieving from cursor
        int titleIndex = cursor.getColumnIndex(YallaSa7elContract.Space.COLUMN_TITLE);
        int destinationIndex = cursor.getColumnIndex(YallaSa7elContract.Space.COLUMN_DESTINATION);
        int addressIndex = cursor.getColumnIndex(YallaSa7elContract.Space.COLUMN_ADDRESS);
        int numberOfRoomsIndex = cursor.getColumnIndex(YallaSa7elContract.Space.COLUMN_NUMBER_OF_ROOMS);
        int numberOfBathroomsIndex = cursor.getColumnIndex(YallaSa7elContract.Space.COLUMN_NUMBER_OF_BATHROOMS);
        int basePriceIndex = cursor.getColumnIndex(YallaSa7elContract.Space.COLUMN_BASE_PRICE);
        int ownerNameIndex = cursor.getColumnIndex(YallaSa7elContract.Space.COLUMN_OWNER_NAME);
        int mobileNumberIndex = cursor.getColumnIndex(YallaSa7elContract.Space.COLUMN_MOBILE_NUMBER);

        // Prepare holders for values retrieved from cursor in each iteration
        String title, destination, address, ownerName, mobileNumber;
        int numberOfRooms, numberOfBathrooms, basePrice;
        Space space;

        // Loop over the cursor
        while( cursor.moveToNext() ) {
            Log.d(TAG, "got into while");
            // Retrieve
            title = cursor.getString(titleIndex);
            destination = cursor.getString(destinationIndex);
            address = cursor.getString(addressIndex);
            numberOfRooms = cursor.getInt(numberOfRoomsIndex);
            numberOfBathrooms = cursor.getInt(numberOfBathroomsIndex);
            basePrice = cursor.getInt(basePriceIndex);
            ownerName = cursor.getString(ownerNameIndex);
            mobileNumber = cursor.getString(mobileNumberIndex);

            // Instantiate a Space object
            space = new Space( title, destination, address, numberOfRooms, numberOfBathrooms, basePrice, ownerName, mobileNumber );

            // Add that object to the array list
            spaceList.add(space);
        }

        Log.d(TAG, "Got out of while");

        // Close the cursor and return the list
        cursor.close();
        return spaceList;
    }


    /*
    Insert new user in Users table
     */
    public boolean insertUser(User user, SQLiteDatabase database) {
        Log.d(TAG, "insertUser method executes");

        // Construct content values object with the given user info
        ContentValues contentValues = new ContentValues();
        contentValues.put(Users.COLUMN_USER_NAME, user.getUserName());
        contentValues.put(Users.COLUMN_PASSWORD, user.getPassword());

        if ( database.insert( Users.TABLE_NAME, null, contentValues ) != -1 ) {
            Log.d(TAG, "User inserted successfully");
            // successful insertion operation
            return true;
        }
        else {
            Log.e(TAG, "Error while inserting user");
            // failure insertion operation
            return false;
        }
    }


    /*
    Given a userName and his password check if he exists or not
     */
    public boolean checkIfUserExists( User user, SQLiteDatabase database ) {
        Log.d(TAG, "checkIfUser method executes");

        String userName = user.getUserName();
        String password = user.getPassword();

        // Construct columns array
        String columns[] = { Users.COLUMN_USER_NAME };

        // Construct the where clause
        String where = Users.COLUMN_USER_NAME + "=? AND " + Users.COLUMN_PASSWORD + "=?";

        // Construct the args array
        String args[] = { userName, password };

        Cursor cursor = database.query( Users.TABLE_NAME, columns, where, args, null,
                null, null );

        if ( cursor.getCount() == 1 ) {
            Log.d(TAG, "Cursor has exactly 1 element");
            cursor.close();
            return true;
        }
        else {
            Log.d(TAG, "Cursor has !1 elements ");
            cursor.close();
            return false;
        }
    }


    /*
    Set signed in state value to 1 indicating that a user is signed in
     */
    public boolean setSignedInState(SQLiteDatabase database) {
        Log.d(TAG, "setSignedInState method executes");

        // Construct content values object with state set to 1
        ContentValues contentValues = new ContentValues();
        contentValues.put(SignedInState.COLUMN_STATE, 1);

        if ( database.insert( SignedInState.TABLE_NAME, null, contentValues ) != -1 ) {
            Log.d(TAG, "Successfully set state");
            // successful insertion operation
            return true;
        }
        else {
            Log.e(TAG, "Error while setting state");
            // failure insertion operation
            return false;
        }
    }


    /*
    Clear the signed in state to 0 indicating that the user logged out
     */
    public boolean clearSignedInState(SQLiteDatabase database) {
        // Delete all rows which is exactly one
        if( database.delete(SignedInState.TABLE_NAME, "1", null) == 1 ) {
            Log.d(TAG, "Successfully cleared state");
            // successful deletion operation
            return true;
        }
        else {
            Log.e(TAG, "Error while clearing state");
            // failure deletion operation
            return false;
        }
    }


    private void createSpaceTable(SQLiteDatabase database) {
        // Create table Space SQL statement
        String SQL_CREATE_TABLE_SPACE = "CREATE TABLE" + YallaSa7elContract.Space.TABLE_NAME + "("
                + YallaSa7elContract.Space._ID + YallaSa7elContract.DATA_INTEGER + YallaSa7elContract.CONSTRAINT_PRIMARY_KEY + YallaSa7elContract.CONSTRAINT_AUTOINCREMENT + ", "
                + YallaSa7elContract.Space.COLUMN_TITLE + YallaSa7elContract.DATA_TEXT + YallaSa7elContract.CONSTRAINT_NOT_NULL + ", "
                + YallaSa7elContract.Space.COLUMN_OWNER_NAME + YallaSa7elContract.DATA_TEXT + YallaSa7elContract.CONSTRAINT_NOT_NULL + ", "
                + YallaSa7elContract.Space.COLUMN_MOBILE_NUMBER + YallaSa7elContract.DATA_TEXT + YallaSa7elContract.CONSTRAINT_NOT_NULL + ", "
                + YallaSa7elContract.Space.COLUMN_DESTINATION + YallaSa7elContract.DATA_TEXT + YallaSa7elContract.CONSTRAINT_NOT_NULL + ", "
                + YallaSa7elContract.Space.COLUMN_ADDRESS + YallaSa7elContract.DATA_TEXT + ", "
                + YallaSa7elContract.Space.COLUMN_NUMBER_OF_ROOMS + YallaSa7elContract.DATA_INTEGER + YallaSa7elContract.CONSTRAINT_NOT_NULL + ", "
                + YallaSa7elContract.Space.COLUMN_NUMBER_OF_BATHROOMS + YallaSa7elContract.DATA_INTEGER + YallaSa7elContract.CONSTRAINT_NOT_NULL + ", "
                + YallaSa7elContract.Space.COLUMN_BASE_PRICE + YallaSa7elContract.DATA_INTEGER + YallaSa7elContract.CONSTRAINT_DEFAULT + " \"Not Available\"" + ");";

        // Execute Create table statement
        database.execSQL(SQL_CREATE_TABLE_SPACE);
    }


    private void createUsersTable(SQLiteDatabase database) {
        // Create table Users SQL statement
        String SQL_CREATE_TABLE_USERS = "CREATE TABLE" + Users.TABLE_NAME + "("
                + Users.COLUMN_USER_NAME + YallaSa7elContract.DATA_TEXT + YallaSa7elContract.CONSTRAINT_PRIMARY_KEY + ", "
                + Users.COLUMN_PASSWORD + YallaSa7elContract.DATA_TEXT + YallaSa7elContract.CONSTRAINT_NOT_NULL + ");";

        // Execute Create table statement
        database.execSQL(SQL_CREATE_TABLE_USERS);
    }


    private void createSignedInStateTable(SQLiteDatabase database) {
        // Create table SignedInState SQL statement
        String SQL_CREATE_TABLE_SIGNED_IN_STATE = "CREATE TABLE" + SignedInState.TABLE_NAME + "("
                + SignedInState.COLUMN_STATE + YallaSa7elContract.DATA_INTEGER + YallaSa7elContract.CONSTRAINT_NOT_NULL + ");";

        // Execute Create table statement
        database.execSQL(SQL_CREATE_TABLE_SIGNED_IN_STATE);


    }
}
