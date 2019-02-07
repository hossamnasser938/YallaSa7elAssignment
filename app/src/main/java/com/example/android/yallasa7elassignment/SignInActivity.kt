package com.example.android.yallasa7elassignment

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.android.yallasa7elassignment.data.User
import com.example.android.yallasa7elassignment.data.YallaSa7elDBHelper
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    private val TAG = "SignInActivity"
    private lateinit var dBHelper: YallaSa7elDBHelper
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        dBHelper = YallaSa7elDBHelper( this )
        database = dBHelper.writableDatabase

        handleSignInUPButton()
    }

    private fun handleSignInUPButton() {
        login_button.setOnClickListener {
            Log.d( TAG, "Login Button clicked" )
            // Hide error text if it is visible
            login_error_text_view.visibility = View.GONE

            // extract user inputs
            val userName = login_username_edit_text.text.toString().trim()
            val password = login_password_edit_text.text.toString().trim()

            // validate inputs
            if ( !validateInputs( userName, password ) ) {
                Log.d( TAG, "Error on validation" )
                return@setOnClickListener
            }

            // Construct user object and sign in/up
            val user = User( userName, password )
            if ( dBHelper.signIn( user, database ) ) {
                Log.d( TAG, "Signed in successfully" )
                // Signed in successfully, give him a welcome back
                navigateToHome( R.string.welcome_back )
            }
            else {
                val signUpResponse = dBHelper.signUp( user, database )
                if ( signUpResponse == null ) {
                    Log.e( TAG, "Error signing up. Says you must sign in" )
                    // Unexpected error
                    login_error_text_view.text = getString( R.string.error_signing_in )
                }
                else {
                    if ( signUpResponse ) {
                        Log.d( TAG, "Signed up successfully" )
                        // Signed up successfully, give him welcome for the first time
                        navigateToHome( R.string.welcome_first_time )

                    }
                    else {
                        Log.e( TAG, "Error signing up" )
                        // Unexpected error
                        login_error_text_view.text = getString( R.string.error_signing_in )
                    }
                }
            }
        }
    }

    private fun validateInputs(userName: String, password: String): Boolean {
        if ( userName.isEmpty() or password.isEmpty() ) {
            login_error_text_view.visibility = View.VISIBLE
            login_error_text_view.text = getString( R.string.empty_email_password )
            return false
        }
        return true
    }

    private fun navigateToHome( welcomeMessage: Int ) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags =  Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra( Constants.WELCOME_MESSAGE_KEY, welcomeMessage )
        startActivity( intent )
    }
}
