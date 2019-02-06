package com.example.android.yallasa7elassignment.data;

import android.provider.BaseColumns;

public class YallaSa7elContract {

    public static final String DATA_TEXT = " TEXT";
    public static final String DATA_INTEGER = " INTEGER";

    public static final String CONSTRAINT_PRIMARY_KEY = " PRIMARY KEY";
    public static final String CONSTRAINT_AUTOINCREMENT = " AUTOINCREMENT";
    public static final String CONSTRAINT_NOT_NULL = " NOT NULL";
    public static final String CONSTRAINT_DEFAULT = " DEFAULT";

    /*
    Space table holds Apartments, Chalets, Villas
     */
    public static final class Space implements BaseColumns {

        public static final String TABLE_NAME = " Space";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TITLE = "Title";
        public static final String COLUMN_OWNER_NAME = "Owner_Name";
        public static final String COLUMN_MOBILE_NUMBER = "Mobile_Number";
        public static final String COLUMN_DESTINATION = "Destination";
        public static final String COLUMN_ADDRESS = "Address";
        public static final String COLUMN_NUMBER_OF_ROOMS = "Number_Of_Rooms";
        public static final String COLUMN_NUMBER_OF_BATHROOMS = "Number_Of_Bathrooms";
        public static final String COLUMN_BASE_PRICE = "Base_Price";

    }

    /*
    Users table holds information about users for authentication
     */
    public static final class Users implements BaseColumns{

        public static final String TABLE_NAME = " Users";

        public static final String COLUMN_USER_NAME = "User_Name";  // Unique userName used besides password for authentication
        public static final String COLUMN_PASSWORD = "Password";

    }

    /*
    SignedInState table indicates whether a user is signed in or not
     */
    public static final class SignedInState implements BaseColumns{

        public static final String TABLE_NAME = " Signed_In_State";

        public static final String COLUMN_STATE = "State";

    }

}
