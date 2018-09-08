package com.paktalin.vocabularynotebook

import android.util.Log

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

import java.io.Serializable

class WordItem(word: String?, translation: String?, var id: String?, private val vocabularyId: String?) : Serializable {
    var pojo: WordItemPojo? = null

    class WordItemPojo(var word: String?, var translation: String?) : Serializable

    init {
        this.pojo = WordItemPojo(word, translation)
    }

    fun delete() {
        if (vocabularyId != null) {
            FirebaseFirestore.getInstance().collection("vocabularies").document(vocabularyId)
                    .collection("words").document(id!!).delete()
                    .addOnSuccessListener { Log.i(TAG, "Successfully deleted word with id $id") }
                    .addOnFailureListener { e -> Log.w(TAG, "deleteWordWithId $id:failure", e.fillInStackTrace()) }
        }
    }

    companion object {
        private val TAG = "VN/" + WordItem::class.java.simpleName

        fun createEmpty() : WordItem {
            return WordItem(null, null, null, null)
        }
    }
}
