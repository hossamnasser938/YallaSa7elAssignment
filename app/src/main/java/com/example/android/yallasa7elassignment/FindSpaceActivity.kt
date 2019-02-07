package com.example.android.yallasa7elassignment

import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.android.yallasa7elassignment.data.YallaSa7elDBHelper
import kotlinx.android.synthetic.main.activity_find_space.*

class FindSpaceActivity : AppCompatActivity() {

    private val TAG = "FindSpaceActivity"
    private lateinit var dBHelper: YallaSa7elDBHelper
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_space)

        dBHelper = YallaSa7elDBHelper( this )
        database = dBHelper.writableDatabase

        handleFindButton()
    }

    private fun handleFindButton() {
        query_find_btn.setOnClickListener {
            query_error_text.visibility = View.GONE

            // Retrieve user inputs
            val pDestination = query_destination_edit_text.text.toString().trim()
            val pRooms = query_rooms_edit_text.text.toString().trim()
            var pRoomsInt: Int? = null

            // Validate user preferred destination
            if ( !validateDestination( pDestination ) ) {
                return@setOnClickListener
            }

            // Validate user preferred number of rooms
            val roomsState: Boolean? = validateRooms( pRooms )

            if( roomsState != null ) {
                if ( roomsState ) {
                    pRoomsInt = pRooms.toInt()
                }
                else {
                    return@setOnClickListener
                }
            }

            // Query the database, create the adapter, and bind it with the list view
            val spaceList = dBHelper.querySpace( pDestination, pRoomsInt, database )
            val adapter = SpaceAdapter( this, spaceList )
            query_list.adapter = adapter

            // Handle empty list
            query_list.emptyView = query_empty_list_text
            query_empty_list_text.text = getString( R.string.empty_list_text )
        }
    }

    private fun validateDestination( destination: String ) : Boolean {
        if ( destination.isEmpty() ) {
            query_error_text.visibility = View.VISIBLE
            query_error_text.text = getString( R.string.empty_destination )
            return false
        }
        return true
    }

    private fun validateRooms( rooms: String ) : Boolean? {
        if ( rooms.isEmpty() ) {
            // return null indicating that the preferred number of rooms is empty
            return null
        }
        // check if user entered a numeric value
        var intRooms = 1
        try {
            intRooms = rooms.toInt()
            // return true indicating that the user entered a number of rooms
            return true
        } catch ( e: NumberFormatException ) {
            query_error_text.visibility = View.VISIBLE
            query_error_text.text = getString( R.string.empty_destination )
            // return false indicating that the user entered a value not a number for preferred number of rooms
            return false
        }
    }
}
