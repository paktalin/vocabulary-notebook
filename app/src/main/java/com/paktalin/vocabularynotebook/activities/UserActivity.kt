package com.paktalin.vocabularynotebook.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.paktalin.vocabularynotebook.R
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        printUserData()
    }

    override fun onDestroy() {
        super.onDestroy()
        FirebaseAuth.getInstance().signOut()
    }

    @SuppressLint("SetTextI18n")
    private fun printUserData() {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        Log.d(TAG, "retrieved userId: $userId")
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document(userId).get().addOnSuccessListener { task ->
            val email = task.get("email").toString()
            tvUserData.text = "email: $email"
        }
    }

    companion object {
        private val TAG = "VN/" + UserActivity::class.simpleName
    }
}
