package com.paktalin.vocabularynotebook

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class VocabularyAdapter(private val wordItems: List<WordItem>) : RecyclerView.Adapter<VocabularyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.word_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = wordItems[position]
        holder.tvWord.text = item.pojo.word
        holder.tvTranslation.text = item.pojo.translation
    }

    override fun getItemCount(): Int {
        return wordItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvWord: TextView = itemView.findViewById(R.id.tvWord)
        var tvTranslation: TextView = itemView.findViewById(R.id.tvTranslation)
    }

    companion object { private val TAG = "VN/" + VocabularyAdapter::class.java.simpleName }
}