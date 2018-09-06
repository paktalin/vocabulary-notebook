package com.paktalin.vocabularynotebook.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.android.gms.signin.SignIn

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_log_in.*
import com.google.firebase.firestore.FirebaseFirestore
import com.paktalin.vocabularynotebook.R
import com.paktalin.vocabularynotebook.User

class LogInActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        mAuth = FirebaseAuth.getInstance()
        btnLogIn!!.setOnClickListener({ signIn() })
        btnSignUp!!.setOnClickListener({ signUp() })
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth!!.currentUser
        if (currentUser != null) startUserActivity()
    }

    private fun signIn() {
        val email = etEmail!!.text.toString()
        val password = etPassword!!.text.toString()

        if (fieldsNotEmpty(email, password)) {
            mAuth!!.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "Successfully signed in")
                            startUserActivity()
                        }
                        else {
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(this@LogInActivity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }
        }
    }

    private fun signUp() {
        val email = etEmail!!.text.toString()
        val password = etPassword!!.text.toString()

        if (fieldsNotEmpty(email, password)) {
            //todo check if the password is good
            // todo verify email
            mAuth!!.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "Successfully signed up a new user")
                            addNewUserToDb(mAuth!!.currentUser!!)
                            startUserActivity()
                        }
                        else {
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(this@LogInActivity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }
        }
    }

    private fun startUserActivity() {
        Log.d(TAG, "Signed in successfully")
        val userActivityIntent = Intent(this@LogInActivity, UserActivity::class.java)
        startActivity(userActivityIntent)
    }

    private fun fieldsNotEmpty(email: String, password: String): Boolean {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this@LogInActivity, "Please, enter email and password", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun addNewUserToDb(newUser: FirebaseUser) {
        //todo add condition to writing to the db in Firebase Console (request.auth.uid)
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(newUser.uid).set(User(newUser.email))
                .addOnCompleteListener({ task ->
                    if (task.isSuccessful) Log.i(TAG, "Successfully added user to the collection")
                    else Log.w(TAG, "addUser:failure", task.exception)
                })
    }

    companion object {
        private val TAG = "VN/" + SignIn::class.simpleName
    }
}