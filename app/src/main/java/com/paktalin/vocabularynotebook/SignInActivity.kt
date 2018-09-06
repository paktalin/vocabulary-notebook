package com.paktalin.vocabularynotebook

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.signin.SignIn

import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    private var mEmailEt: EditText? = null
    private var mPasswordEt: EditText? = null
    private var mSignInBtn: Button? = null
    private var mSignUpBtn: Button? = null

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        initializeViews()
        mAuth = FirebaseAuth.getInstance()
        mSignInBtn!!.setOnClickListener({ signIn() })
        mSignUpBtn!!.setOnClickListener({ signUp() })
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth!!.currentUser
        if (currentUser != null) startUserActivity()
    }

    private fun initializeViews() {
        mEmailEt = findViewById(R.id.email)
        mPasswordEt = findViewById(R.id.password)
        mSignInBtn = findViewById(R.id.btn_sign_in)
        mSignUpBtn = findViewById(R.id.btn_sign_up)
    }

    private fun signIn() {
        val email = mEmailEt!!.text.toString()
        val password = mPasswordEt!!.text.toString()

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
        val email = mEmailEt!!.text.toString()
        val password = mPasswordEt!!.text.toString()

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