package com.paktalin.vocabularynotebook

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.paktalin.vocabularynotebook.activities.WordItemInfoActivity

class VocabularyAdapter(private val wordItems: MutableList<WordItem>,
                        private val context: Context) : RecyclerView.Adapter<VocabularyAdapter.ViewHolder>() {

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
        holder.tvWord.text = wordItem.pojo.word
        holder.tvTranslation.text = wordItem.pojo.translation
        holder.itemView.setOnClickListener { openWordItemInfo(wordItem) }
        holder.itemView.setOnLongClickListener { deleteWordItem(position);true }
    }

    override fun getItemCount(): Int {
        return wordItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvWord: TextView = itemView.findViewById(R.id.tvWord)
        val tvTranslation: TextView = itemView.findViewById(R.id.tvTranslation)
    }

    private fun openWordItemInfo(wordItem: WordItem) {
        val intentWordItemInfo = Intent(context, WordItemInfoActivity::class.java)
        intentWordItemInfo.putExtra("wordItem", wordItem)
        context.startActivity(intentWordItemInfo)
    }

    private fun deleteWordItem(position: Int) {
        wordItems[position].delete()
        wordItems.removeAt(position)
        recyclerView.removeViewAt(position)
        this.notifyItemRemoved(position)
        this.notifyItemRangeChanged(position, wordItems.size)
    }

    companion object { private val TAG = "VN/" + VocabularyAdapter::class.java.simpleName }
}