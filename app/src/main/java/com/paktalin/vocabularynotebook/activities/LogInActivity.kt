package com.paktalin.vocabularynotebook.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_log_in.*
import com.paktalin.vocabularynotebook.R
import com.paktalin.vocabularynotebook.UserManager
import com.paktalin.vocabularynotebook.Utils

class LogInActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        mAuth = FirebaseAuth.getInstance()
        btnLogIn!!.setOnClickListener({ logIn() })
        btnSignUp!!.setOnClickListener({ signUp() })
        btnRandomUser!!.setOnClickListener({ createRandomUser() })
    }

    override fun onStart() {
        super.onStart()
        if (mAuth!!.currentUser != null) {
            Log.d(TAG, "there is a logged in user")
            startUserActivity()
        }
    }

    private fun logIn() {
        val email = etEmail!!.text.toString()
        val password = etPassword!!.text.toString()

        if (Utils.fieldsNotEmpty(email, password, "Please, enter email and password", this)) {
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

        if (Utils.fieldsNotEmpty(email, password, "Please, enter email and password", this)) {
            //todo check if the password is good
            // todo verify email
            mAuth!!.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener { _ ->
                        Log.d(TAG, "Successfully signed up a new user")
                        UserManager.addNewUserToDb(mAuth!!.currentUser!!, this)
                    }
                    .addOnFailureListener {
                        Log.d(TAG, "createUserWithEmail:failure", it.fillInStackTrace())
                        Toast.makeText(this@LogInActivity, it.message, Toast.LENGTH_SHORT).show()
                    }
        }
    }

    fun startUserActivity() {
        Log.d(TAG, "Logged in successfully")
        val userActivityIntent = Intent(this@LogInActivity, MainActivity::class.java)
        startActivity(userActivityIntent)
    }

    @SuppressLint("SetTextI18n")
    private fun createRandomUser() {
        etEmail.setText("random@gmail.com")
        etPassword.setText("123456")
        signUp()
    }

    companion object {
        private val TAG = "VN/" + LogInActivity::class.simpleName
    }
}