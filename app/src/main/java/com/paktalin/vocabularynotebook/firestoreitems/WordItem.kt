package com.paktalin.vocabularynotebook.firestoreitems

import android.util.Log
import com.paktalin.vocabularynotebook.appsetup.ConfiguredFirestore

import java.io.Serializable
import java.util.Date

class WordItem(word: String, translation: String, time: Date?, var id: String, private val vocabularyId: String) : Serializable {
    var pojo: Pojo = Pojo(word, translation, time)

    class Pojo(var word: String, var translation: String, var time:Date?) : Serializable {
        init {
            if (time == null) time = Date(System.currentTimeMillis())
        }

        fun print(): String {
            return "word: $word; translation: $translation; time: $time"
        }
    }

    constructor(pojo: Pojo, id: String, vocabularyId: String)
            : this(pojo.word, pojo.translation, pojo.time, id, vocabularyId)

    fun delete() {
        ConfiguredFirestore.instance.collection("vocabularies").document(vocabularyId)
                .collection("words").document(id).delete()
                .addOnSuccessListener { Log.i(TAG, "Successfully deleted word with id $id") }
                .addOnFailureListener { e -> Log.w(TAG, "deleteWordWithId $id:failure", e.fillInStackTrace()) }
    }

    companion object { private val TAG = "VN/" + WordItem::class.java.simpleName }
}
