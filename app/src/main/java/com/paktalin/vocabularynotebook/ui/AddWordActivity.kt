package com.paktalin.vocabularynotebook.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

import com.paktalin.vocabularynotebook.R
import com.paktalin.vocabularynotebook.Utils
import com.paktalin.vocabularynotebook.WordItem.WordItemPojo
import kotlinx.android.synthetic.main.activity_add_word.*

class AddWordActivity : AppCompatActivity() {
    companion object {
        private val TAG = "VN/" + AddWordActivity::class.simpleName
        private const val VOCABULARIES = "vocabularies"
        private const val WORDS = "words"
    }

    private lateinit var vocabularyId: String
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)
        vocabularyId = intent.getStringExtra("vocabularyId")
        btnSubmitNewWord.setOnClickListener { addWordToDb() }
        btnCancel.setOnClickListener { cancel() }
    }

    private fun addWordToDb() {
        val word = etWord.text.toString()
        val translation = etTranslation.text.toString()
        if (Utils.fieldsNotEmpty(word, translation, "Please, enter word and translation", this)) {
            db.collection(VOCABULARIES).document(vocabularyId)
                    .collection(WORDS).add(WordItemPojo(word, translation)).addOnSuccessListener {
                        Log.i(TAG, "Successfully added a new word $word")
                        clearFields()
                        cancel()
                    }
                    .addOnFailureListener {
                        Log.w(TAG, "addNewWordToDb:failure", it.fillInStackTrace())
                        Toast.makeText(this, "Couldn't add the word", Toast.LENGTH_SHORT).show()
                    }
        }
    }

    private fun cancel() {
        val intentMainActivity = Intent(this, MainActivity::class.java)
        startActivity(intentMainActivity)
    }

    private fun clearFields() {
        etWord.text.clear()
        etTranslation.text.clear()
    }
}
