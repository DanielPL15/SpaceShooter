package com.danielpl.spaceshootersample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PopUpWindow : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.activity_pop_up_window)
    }

}