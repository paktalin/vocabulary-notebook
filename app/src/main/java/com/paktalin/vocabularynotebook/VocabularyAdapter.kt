package com.paktalin.vocabularynotebook

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import com.paktalin.vocabularynotebook.activities.WordItemInfoActivity

class VocabularyAdapter(private val wordItems: MutableList<WordItem>, private val context: Activity) : RecyclerView.Adapter<ViewHolder>() {

    private lateinit var recyclerView: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.word_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) showEmptyItem(holder)
        else {
            val wordItem = wordItems[position]
            holder.etWord.setText(wordItem.pojo!!.word)
            holder.etTranslation.setText(wordItem.pojo!!.translation)

            holder.layout.setOnClickListener{ openWordItemInfo(wordItem) }
            holder.etWord.setOnClickListener{ openWordItemInfo(wordItem) }
            holder.etTranslation.setOnClickListener{ openWordItemInfo(wordItem) }
            holder.btnPopupMenu.setOnClickListener { showPopupMenu(holder.btnPopupMenu, position) }
            //todo set click listener to menu
        }
    }

    override fun getItemCount(): Int {
        return wordItems.size
    }

    private fun openWordItemInfo(wordItem: WordItem) {
        val intentWordItemInfo = Intent(context, WordItemInfoActivity::class.java)
        intentWordItemInfo.putExtra("wordItem", wordItem)
        context.startActivity(intentWordItemInfo)
    }

    private fun showPopupMenu(v: View, position: Int) {
        val popup = PopupMenu(context, v)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.word_item_menu, popup.menu)
        popup.setOnMenuItemClickListener {
            if (it.itemId == R.id.item_delete) { deleteWordItem(position) }
            true }
        popup.show()
    }

    private fun deleteWordItem(position: Int) {
        wordItems[position].delete()
        wordItems.removeAt(position)
        recyclerView.removeViewAt(position)
        this.notifyItemRemoved(position)
        this.notifyItemRangeChanged(position, wordItems.size)
    }

    private fun showEmptyItem(holder: ViewHolder) {
        holder.btnPopupMenu.isClickable = false
        holder.btnPopupMenu.visibility = View.INVISIBLE
        Utils.setEmptyEditText(holder.etWord, "new word")
        Utils.setEmptyEditText(holder.etTranslation, "translation")

        holder.etWord.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) { }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) { }
            override fun afterTextChanged(editable: Editable) {
                if (!holder.etWord.text.isEmpty()) showCancelButton(holder) }
        })
        holder.etTranslation.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) { }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) { }
            override fun afterTextChanged(editable: Editable) {
                if (!holder.etTranslation.text.isEmpty()) showCancelButton(holder) }
        })
    }

    private fun showCancelButton(holder: ViewHolder) {
        Log.d(TAG, "empty word is focused")
        holder.btnPopupMenu.setImageResource(R.drawable.ic_cancel_icon)
        holder.btnPopupMenu.visibility = View.VISIBLE
    }

    companion object { private val TAG = "VN/" + VocabularyAdapter::class.java.simpleName }

    init { wordItems.add(0, WordItem.createEmpty()) }
}