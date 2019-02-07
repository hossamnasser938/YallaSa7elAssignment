package com.example.android.yallasa7elassignment

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.android.yallasa7elassignment.data.YallaSa7elDBHelper
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val TAG = "HomeActivity"
    private lateinit var dBHelper: YallaSa7elDBHelper
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        dBHelper = YallaSa7elDBHelper( this )
        database = dBHelper.writableDatabase

        val intent = intent
        val extras = intent.extras
        if ( extras != null ) {
            Log.d( TAG, "Came from SignIn Activity" )
            // We came from SignIn activity
            val welcomeMessage = extras.get( Constants.WELCOME_MESSAGE_KEY ) as Int
            center_text.text = getString( welcomeMessage )
        }
        else {
            Log.d( TAG, "Did not Come from SignIn Activity" )
            // Either first time or logged before
            if ( dBHelper.checkSignedInState( database ) ) {
                Log.d( TAG, "User already logged" )
                // Welcom user
                center_text.text = getString( R.string.welcome_back )
            }
            else {
                Log.d( TAG, "User needs to log" )
                // Navigate to SignIn Activity
                navigateToSignIn()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate( R.menu.home_menu, menu )
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when ( item?.itemId ) {
            R.id.sign_out -> {
                if ( dBHelper.signout( database ) ) {
                    finishAffinity()
                } else {
                    Toast.makeText( this, R.string.error_signing_out, Toast.LENGTH_LONG ).show()
                }
            }
            R.id.find_space -> {
                navigateToFindSpace()
            }
            R.id.add_space -> {
                navigateToAddSpace()
            }
        }
        return true
    }

    private fun navigateToSignIn() {
        val intent = Intent( this, SignInActivity::class.java )
        intent.flags =  Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity( intent )
    }

    private fun navigateToFindSpace() {
        val intent = Intent( this, FindSpaceActivity::class.java )
        startActivity( intent )
    }

    private fun navigateToAddSpace() {
        val intent = Intent( this, AddSpaceActivity::class.java )
        startActivity( intent )
    }
}
