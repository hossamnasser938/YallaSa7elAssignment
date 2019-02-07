package com.example.android.yallasa7elassignment

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.android.yallasa7elassignment.data.YallaSa7elDBHelper
import kotlinx.android.synthetic.main.activity_home.*

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

    private fun navigateToFindSpace() {
        val intent = Intent( this, FindSpaceActivity::class.java )
        startActivity( intent )
    }

    private fun navigateToAddSpace() {
        val intent = Intent( this, AddSpaceActivity::class.java )
        startActivity( intent )
    }

    override fun openFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.home_frame_layout, fragment)
                .commit()
    }
}
