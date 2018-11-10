package com.paktalin.vocabularynotebook.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.firebase.auth.FirebaseAuth
import com.paktalin.vocabularynotebook.*
import kotlinx.android.synthetic.main.activity_log_in.*

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
        if (mAuth!!.currentUser != null) { startUserActivity() }
    }

    private fun logIn() {
        val email = etEmail!!.text.toString()
        val password = etPassword!!.text.toString()

        if (fieldsNotEmpty(email, password, "Please, enter email and password", this)) {
            showProgressBar()
            mAuth!!.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { hideProgressBar() }
                    .addOnSuccessListener {
                        Log.d(TAG, "Successfully signed in")
                        startUserActivity()
                    }
                    .addOnFailureListener {
                        Log.w(TAG, "signInWithEmail:failure", it)
                        shortToast(this@LogInActivity, getString(R.string.toast_auth_failed))
                    }
        }
    }

    private fun signUp() {
        val email = etEmail!!.text.toString()
        val password = etPassword!!.text.toString()

        if (fieldsNotEmpty(email, password, "Please, enter email and password", this)) {
            //todo check if the password is good
            // todo verify email
            showProgressBar()
            mAuth!!.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { hideProgressBar() }
                    .addOnSuccessListener { _ ->
                        Log.d(TAG, "Successfully signed up a new user")
                        UserManager.addNewUserToDb(mAuth!!.currentUser!!, this)
                    }
                    .addOnFailureListener {
                        Log.d(TAG, "createUserWithEmail:failure", it.fillInStackTrace())
                        shortToast(this@LogInActivity, it.message!!)
                    }
        }
    }

    fun startUserActivity() {
        Log.d(TAG, "Logged in successfully")
        val userActivityIntent = Intent(this@LogInActivity, MainActivity::class.java)
        startActivity(userActivityIntent)
    }

    private fun showProgressBar() { addProgressBar(supportFragmentManager, R.id.container_login) }

    private fun hideProgressBar() { removeProgressBar(supportFragmentManager) }

    @SuppressLint("SetTextI18n")
    private fun createRandomUser() {
        etEmail.setText("random@gmail.com")
        etPassword.setText("123456")
        signUp()
    }

    companion object { private val TAG = "VN/" + LogInActivity::class.simpleName }
}