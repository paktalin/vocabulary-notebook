package com.paktalin.vocabularynotebook.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

import com.paktalin.vocabularynotebook.R
import kotlinx.android.synthetic.main.activity_main.*
import android.view.WindowManager

class MainActivity : AppCompatActivity() {

    lateinit var vocabularyId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hideKeyboard()
        setUpNavigationView()
        extractVocabularyData()
    }

    private fun logOut() {
        Log.i(TAG, "User logged out")
        FirebaseAuth.getInstance()!!.signOut()
        val intentLogInActivity = Intent(this@MainActivity, LogInActivity::class.java)
        startActivity(intentLogInActivity)
    }

    private fun setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            if(menuItem.itemId == R.id.logOut) { logOut() }
            drawerLayout!!.closeDrawers()
            true
        }
    }

    private fun extractVocabularyData() {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val db = FirebaseFirestore.getInstance()

        val userDocument = db.collection("users").document(userId)

        userDocument.get().addOnSuccessListener { task ->
            val vocabularies: List<DocumentReference> = task.get("vocabularies") as List<DocumentReference>
            //todo represent specific vocabulary instead of the first one
            val vocabulary = db.collection("vocabularies").document(vocabularies[0].id)
            vocabularyId = vocabulary.id
            (supportFragmentManager.findFragmentById(R.id.fragment_vocabulary) as VocabularyFragment)
                    .retrieveWordsData(vocabularyId)
        }
    }

    private fun hideKeyboard() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    companion object {
        private val TAG = "VN/" + MainActivity::class.simpleName
    }
}
