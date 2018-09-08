package com.paktalin.vocabularynotebook

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvWord: TextView = itemView.findViewById(R.id.etWord)
    val tvTranslation: TextView = itemView.findViewById(R.id.etTranslation)
    val btnPopupMenu: ImageButton = itemView.findViewById(R.id.btnContextMenu)
    val layout: LinearLayout = itemView.findViewById(R.id.tableLayout)

    private var etWordEmpty = true
    private var etTranslationEmpty = true

    fun showEmptyItem() {
        tvWord.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) { }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) { }
            override fun afterTextChanged(editable: Editable) {
                if (!tvWord.text.isEmpty()) {
                    showCancelButton()
                    etWordEmpty = false
                } else etWordEmpty = true
                if (!etWordEmpty && !etTranslationEmpty)
                    showAddWordButton()
            }
        })
        this.tvTranslation.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) { }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) { }
            override fun afterTextChanged(editable: Editable) {
                if (!tvTranslation.text.isEmpty()) {
                    showCancelButton()
                    etTranslationEmpty = false
                } else etTranslationEmpty = true
                if (!etWordEmpty && !etTranslationEmpty)
                    showAddWordButton()
            }
        })
    }

    private fun showCancelButton() {
        Log.d(TAG, "empty word is focused")
        btnPopupMenu.setImageResource(R.drawable.ic_cancel_icon)
        btnPopupMenu.visibility = View.VISIBLE
        //todo add button click listener
    }

    private fun showAddWordButton() {
        //todo show add word button
    }

    companion object { private val TAG = "VN/" + ViewHolder::class.java.simpleName }
}