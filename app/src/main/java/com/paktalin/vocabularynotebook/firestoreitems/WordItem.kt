package com.paktalin.vocabularynotebook.firestoreitems

import android.util.Log
import com.paktalin.vocabularynotebook.appsetup.ConfiguredFirestore
import com.paktalin.vocabularynotebook.firestoreitems.Vocabulary.Companion.VOCABULARIES
import com.paktalin.vocabularynotebook.firestoreitems.Vocabulary.Companion.WORDS

import java.io.Serializable
import java.util.Date

class WordItem(word: String, translation: String, time: Date?, var id: String, private val vocabularyId: String) : Serializable {
    var pojo: Pojo = Pojo(word, translation, time)

    class Pojo(var word: String, var translation: String, var time:Date?) : Serializable {
        init {
            if (time == null) time = Date(System.currentTimeMillis())
        }
    }

    constructor(pojo: Pojo, id: String, vocabularyId: String)
            : this(pojo.word, pojo.translation, pojo.time, id, vocabularyId)

    fun delete() {
        ConfiguredFirestore.instance.collection(VOCABULARIES).document(vocabularyId)
                .collection(WORDS).document(id).delete()
                .addOnSuccessListener { Log.i(TAG, "Successfully deleted word with id $id") }
                .addOnFailureListener { e -> Log.w(TAG, "deleteWordWithId $id:failure", e.fillInStackTrace()) }
    }

    fun contains(string:String):Boolean {
        return pojo.word.toLowerCase().contains(string) ||
                pojo.translation.toLowerCase().contains(string)
    }

    companion object { private val TAG = "VN/" + WordItem::class.java.simpleName }
}
