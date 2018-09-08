package com.paktalin.vocabularynotebook

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.paktalin.vocabularynotebook.activities.WordItemInfoActivity

class VocabularyAdapter(private val wordItems: MutableList<WordItem>, private val context: Activity) : RecyclerView.Adapter<VocabularyAdapter.ViewHolder>() {

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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val etWord: EditText = itemView.findViewById(R.id.etWord)
        val etTranslation: EditText = itemView.findViewById(R.id.etTranslation)
        val btnPopupMenu: ImageButton = itemView.findViewById(R.id.btnContextMenu)
        val layout: LinearLayout = itemView.findViewById(R.id.tableLayout)
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
        holder.etWord.setOnFocusChangeListener({ _, focus ->
            if (focus) showCancelButton()
        })
        holder.etTranslation.setOnFocusChangeListener({ _, focus ->
            if (focus) showCancelButton()
        })
    }

    private fun showCancelButton() {
        Log.d(TAG, "empty word is focused")
    }

    companion object { private val TAG = "VN/" + VocabularyAdapter::class.java.simpleName }

    init { wordItems.add(0, WordItem.createEmpty()) }
}