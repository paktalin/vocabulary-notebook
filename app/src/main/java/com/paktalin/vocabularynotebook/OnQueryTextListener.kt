package com.paktalin.vocabularynotebook

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import com.paktalin.vocabularynotebook.firestoreitems.WordItem

class OnQueryTextListener(var recyclerView: RecyclerView) : SearchView.OnQueryTextListener {

    override fun onQueryTextSubmit(query: String): Boolean {
        (recyclerView.adapter as VocabularyAdapter).filter(query)
        return true
    }

    override fun onQueryTextChange(query: String): Boolean {
        (recyclerView.adapter as VocabularyAdapter).filter(query)
        return true
    }
}