package com.paktalin.vocabularynotebook.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.paktalin.vocabularynotebook.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout!!.closeDrawers()
            true
        }
    }

    companion object {
        private val TAG = "VN/" + MainActivity::class.simpleName
    }
}
