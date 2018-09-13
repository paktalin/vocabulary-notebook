package com.paktalin.vocabularynotebook

import android.support.v7.widget.SearchView

class OnQueryTextListener(var adapter: VocabularyAdapter) : SearchView.OnQueryTextListener {

    override fun onQueryTextSubmit(query: String): Boolean {
        adapter.filter(query)
        return true
    }

    override fun onQueryTextChange(query: String): Boolean {
        adapter.filter(query)
        return true
    }
}