package com.paktalin.vocabularynotebook.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth

import com.paktalin.vocabularynotebook.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true

            if(menuItem.itemId == R.id.logOut) { logOut() }

            drawerLayout!!.closeDrawers()
            true
        }
    }

    private fun logOut() {
        Log.i(TAG, "User logged out")
        FirebaseAuth.getInstance()!!.signOut()
        val intentLogInActivity = Intent(this@MainActivity, LogInActivity::class.java)
        startActivity(intentLogInActivity)
    }

    companion object {
        private val TAG = "VN/" + MainActivity::class.simpleName
    }
}
