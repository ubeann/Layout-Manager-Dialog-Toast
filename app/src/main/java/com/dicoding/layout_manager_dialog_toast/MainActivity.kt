package com.dicoding.layout_manager_dialog_toast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    // Variable Setup
    private val title:String = "Mata Kuliah"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup title action bar
        val actionBar = supportActionBar
        actionBar?.title = title

    }
}