package com.example.android.yallasa7elassignment

import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.android.yallasa7elassignment.data.YallaSa7elDBHelper

class HomeActivity : AppCompatActivity() {

    private lateinit var dBHelper: YallaSa7elDBHelper
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        dBHelper = YallaSa7elDBHelper( this )
        database = dBHelper.writableDatabase

    }
}
