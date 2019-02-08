package com.example.android.yallasa7elassignment

import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.example.android.yallasa7elassignment.data.YallaSa7elDBHelper

class HomeActivity : AppCompatActivity(), HomeFragment.HomeTransitionInterface {

    private val TAG = "HomeActivity"

    private lateinit var dBHelper: YallaSa7elDBHelper
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        dBHelper = YallaSa7elDBHelper( this )
        database = dBHelper.writableDatabase

        if ( dBHelper.checkSignedInState( database ) ) {
            Log.d( TAG, "User already logged" )
            // Navigate to Home fragment
            openFragment( HomeFragment.newInstance( R.string.welcome_back ) )
        }
        else {
            Log.d( TAG, "User needs to log" )
            // Navigate to SignIn Fragment
            openFragment( SignInFragment.newInstance() )
        }

    }

    override fun openFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.home_frame_layout, fragment)
                .commit()
    }
}
