package com.example.android.yallasa7elassignment

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.yallasa7elassignment.data.User
import com.example.android.yallasa7elassignment.data.YallaSa7elDBHelper
import kotlinx.android.synthetic.main.fragment_signin.*

class SignInFragment : Fragment() {

    private val TAG = "SignInFragment"

    private lateinit var dBHelper: YallaSa7elDBHelper
    private lateinit var database: SQLiteDatabase

    private lateinit var transition: HomeFragment.HomeTransitionInterface

    companion object {
        fun newInstance() : SignInFragment{
            val signinFragment = SignInFragment()
            return signinFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Set the title
        activity?.title = getString( R.string.sign_in_up )

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate( R.layout.fragment_signin, container, false )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        transition = activity as HomeFragment.HomeTransitionInterface

        dBHelper = YallaSa7elDBHelper( activity )
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
                // Signed in successfully, navigate to Home fragment
                transition.openFragment( HomeFragment.newInstance( R.string.welcome_back ) )
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
                        // Signed up successfully, navigate to Home fragment
                        transition.openFragment( HomeFragment.newInstance( R.string.welcome_first_time ) )
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

}