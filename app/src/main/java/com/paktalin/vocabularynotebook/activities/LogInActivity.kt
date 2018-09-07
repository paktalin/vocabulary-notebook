package com.paktalin.vocabularynotebook.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast

import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_log_in.*
import com.paktalin.vocabularynotebook.R
import com.paktalin.vocabularynotebook.UserManager

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
                            UserManager.addNewUserToDb(mAuth!!.currentUser!!, this)
                        }
                        else {
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(this@LogInActivity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }
        }
    }

    fun startUserActivity() {
        Log.d(TAG, "Logged in successfully")
        val userActivityIntent = Intent(this@LogInActivity, MainActivity::class.java)
        startActivity(userActivityIntent)
    }

    private fun fieldsNotEmpty(email: String, password: String): Boolean {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this@LogInActivity, "Please, enter email and password", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
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