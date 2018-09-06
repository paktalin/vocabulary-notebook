package com.paktalin.vocabularynotebook

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.android.gms.signin.SignIn

import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mAuth = FirebaseAuth.getInstance()
        btnSignIn!!.setOnClickListener({ signIn() })
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
                        if (task.isSuccessful) startUserActivity()
                        else {
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(this@SignInActivity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }
        }

    }

    private fun signUp() {
        val email = etEmail!!.text.toString()
        val password = etPassword!!.text.toString()

        if (fieldsNotEmpty(email, password)) {
            mAuth!!.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) startUserActivity()
                        else {
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(this@SignInActivity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }

        }
    }

    private fun startUserActivity() {
        Log.d(TAG, "Signed in successfully");
        val userActivityIntent = Intent(this@SignInActivity, UserActivity::class.java)
        startActivity(userActivityIntent)
    }

    private fun fieldsNotEmpty(email: String, password: String): Boolean {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this@SignInActivity, "Please, enter email and password", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    companion object {
        private val TAG = "VN/" + SignIn::class.simpleName
    }
}