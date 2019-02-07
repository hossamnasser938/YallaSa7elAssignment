package com.example.android.yallasa7elassignment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"

    companion object {
        fun newInstance(welcomeMessage: Int) : HomeFragment{
            val homeFragment = HomeFragment()
            val args = Bundle()
            args.putInt( Constants.WELCOME_MESSAGE_KEY, welcomeMessage )
            homeFragment.arguments = args
            return homeFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate( R.layout.fragment_home, container, false )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Show options menu
        setHasOptionsMenu( true )

        val welcomeMessage = arguments?.getInt( Constants.WELCOME_MESSAGE_KEY )
        center_text.text = getString( welcomeMessage!! )
    }

    interface HomeTransitionInterface{
        fun openFragment(fragment : Fragment)
    }
}