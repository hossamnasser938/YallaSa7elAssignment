package com.example.android.yallasa7elassignment

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.Toast
import com.example.android.yallasa7elassignment.data.YallaSa7elDBHelper
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"

    private lateinit var dBHelper: YallaSa7elDBHelper
    private lateinit var database: SQLiteDatabase

    companion object {
        fun newInstance(welcomeMessage: Int) : HomeFragment{
            val homeFragment = HomeFragment()
            val args = Bundle()
            args.putInt( Constants.WELCOME_MESSAGE_KEY, welcomeMessage )
            homeFragment.arguments = args
            return homeFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Set the title
        activity?.title = getString( R.string.home )

        // Show menu items
        setHasOptionsMenu( true )

        dBHelper = YallaSa7elDBHelper( activity )
        database = dBHelper.writableDatabase

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate( R.layout.fragment_home, container, false )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val welcomeMessage = arguments?.getInt( Constants.WELCOME_MESSAGE_KEY )
        center_text.text = getString( welcomeMessage!! )
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate( R.menu.home_menu, menu )
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when ( item?.itemId ) {
            R.id.sign_out -> {
                if ( dBHelper.signout( database ) ) {
                    activity?.finishAffinity()
                } else {
                    Toast.makeText( activity, R.string.error_signing_out, Toast.LENGTH_LONG ).show()
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
        val intent = Intent( activity, FindSpaceActivity::class.java )
        startActivity( intent )
    }

    private fun navigateToAddSpace() {
        val intent = Intent( activity, AddSpaceActivity::class.java )
        startActivity( intent )
    }

    interface HomeTransitionInterface{
        fun openFragment(fragment : Fragment)
    }
}