package com.paktalin.vocabularynotebook

import android.util.Log
import com.paktalin.vocabularynotebook.appsetup.ConfiguredFirestore

import java.io.Serializable

class WordItem(word: String, translation: String, var id: String, private val vocabularyId: String) : Serializable {
    var pojo: Pojo? = null

    class Pojo(var word: String?, var translation: String?) : Serializable

    init {
        this.pojo = Pojo(word, translation)
    }

    constructor(pojo: Pojo, id: String, vocabularyId: String)
            : this(pojo.word!!, pojo.translation!!, id, vocabularyId)

    fun delete() {
        ConfiguredFirestore.instance.collection("vocabularies").document(vocabularyId)
                .collection("words").document(id!!).delete()
                .addOnSuccessListener { Log.i(TAG, "Successfully deleted word with id $id") }
                .addOnFailureListener { e -> Log.w(TAG, "deleteWordWithId $id:failure", e.fillInStackTrace()) }
    }

    companion object { private val TAG = "VN/" + WordItem::class.java.simpleName }
}
