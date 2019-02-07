package com.example.android.yallasa7elassignment

import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.android.yallasa7elassignment.data.Space
import com.example.android.yallasa7elassignment.data.YallaSa7elDBHelper
import kotlinx.android.synthetic.main.activity_add_space.*

class AddSpaceActivity : AppCompatActivity() {

    private val TAG = "AddSpaceActivity"
    private lateinit var dBHelper: YallaSa7elDBHelper
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_space)

        dBHelper = YallaSa7elDBHelper( this )
        database = dBHelper.writableDatabase

        handleAddSpaceButton()
    }

    private fun handleAddSpaceButton() {
        add_space_btn.setOnClickListener {
            add_error_text.visibility = View.GONE

            val title = add_title_edit_text.text.toString().trim()
            val destination = add_destination_edit_text.text.toString().trim()
            val address = add_address_edit_text.text.toString().trim()
            val rooms = add_rooms_edit_text.text.toString().trim()
            val bathrooms = add_bathrooms_edit_text.text.toString().trim()
            val basePrice = add_baseprice_edit_text.text.toString().trim()
            val ownerName = add_ownername_edit_text.text.toString().trim()
            val ownerPhone = add_ownerphone_edit_text.text.toString().trim()

            if ( !validateInputs( title, destination, address, rooms, bathrooms, basePrice, ownerName, ownerPhone ) ) {
                return@setOnClickListener
            }

            var roomsInt: Int
            var bathroomsInt: Int
            var basePriceInt: Int

            try {
                roomsInt = rooms.toInt()
                bathroomsInt = bathrooms.toInt()
                basePriceInt = basePrice.toInt()
            } catch ( e: NumberFormatException ) {
                add_error_text.visibility = View.VISIBLE
                add_error_text.text = getString(R.string.not_numeric_add_fields)
                return@setOnClickListener
            }

            val spaceObj = Space( title, destination, address, roomsInt, bathroomsInt, basePriceInt, ownerName, ownerPhone )

            if ( dBHelper.insertSpace( spaceObj, database ) ) {
                Toast.makeText( this, R.string.space_inserted, Toast.LENGTH_LONG ).show()
            }
            else {
                add_error_text.visibility = View.VISIBLE
                add_error_text.text = getString(R.string.error_inserting_space)
            }
        }
    }

    private fun validateInputs(title: String, destination: String, address: String, rooms: String, bathrooms: String, basePrice: String, ownerName: String, ownerMobileNumber: String) : Boolean {
        if( title.isEmpty() or destination.isEmpty()  or rooms.isEmpty() or bathrooms.isEmpty() or basePrice.isEmpty() or ownerName.isEmpty() or ownerMobileNumber.isEmpty() ) {
            add_error_text.visibility = View.VISIBLE
            add_error_text.text = getString(R.string.empty_add_field)
            return false
        }
        return true

    }
}
