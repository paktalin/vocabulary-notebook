package com.paktalin.vocabularynotebook

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val etWord: EditText = itemView.findViewById(R.id.etWord)
    val etTranslation: EditText = itemView.findViewById(R.id.etTranslation)
    val btnPopupMenu: ImageButton = itemView.findViewById(R.id.btnContextMenu)
    val layout: LinearLayout = itemView.findViewById(R.id.tableLayout)


    fun showEmptyItem() {
        btnPopupMenu.isClickable = false
        btnPopupMenu.visibility = View.INVISIBLE
        Utils.setEmptyEditText(etWord, "new word")
        Utils.setEmptyEditText(etTranslation, "translation")

        etWord.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) { }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) { }
            override fun afterTextChanged(editable: Editable) {
                if (!etWord.text.isEmpty()) showCancelButton() }
        })
        this.etTranslation.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) { }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) { }
            override fun afterTextChanged(editable: Editable) {
                if (!etTranslation.text.isEmpty()) showCancelButton() }
        })
    }

    private fun showCancelButton() {
        Log.d(TAG, "empty word is focused")
        btnPopupMenu.setImageResource(R.drawable.ic_cancel_icon)
        btnPopupMenu.visibility = View.VISIBLE
        //todo add button click listener
    }

    companion object { private val TAG = "VN/" + ViewHolder::class.java.simpleName }
}