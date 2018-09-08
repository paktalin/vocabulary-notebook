package com.paktalin.vocabularynotebook

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
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
        val wordItem = wordItems[position]
        holder.tvWord.text = wordItem.pojo!!.word
        holder.tvTranslation.text = wordItem.pojo!!.translation

        holder.layout.setOnClickListener { openWordItemInfo(wordItem) }
        holder.tvWord.setOnClickListener { openWordItemInfo(wordItem) }
        holder.tvTranslation.setOnClickListener { openWordItemInfo(wordItem) }
        holder.btnPopupMenu.setOnClickListener { showPopupMenu(holder.btnPopupMenu, position) }
        //todo set click listener to menu
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
            if (it.itemId == R.id.item_delete) {
                deleteWordItem(position)
            }
            true
        }
        popup.show()
    }

    private fun deleteWordItem(position: Int) {
        wordItems[position].delete()
        wordItems.removeAt(position)
        recyclerView.removeViewAt(position)
        this.notifyItemRemoved(position)
        this.notifyItemRangeChanged(position, wordItems.size)
    }

    companion object {
        private val TAG = "VN/" + VocabularyAdapter::class.java.simpleName
    }
}