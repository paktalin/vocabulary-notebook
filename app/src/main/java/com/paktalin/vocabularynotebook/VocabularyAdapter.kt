package com.paktalin.vocabularynotebook

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import com.paktalin.vocabularynotebook.firestoreitems.WordItem
import com.paktalin.vocabularynotebook.ui.EditWordFragment
import com.paktalin.vocabularynotebook.ui.MainActivity
import java.util.*

class VocabularyAdapter(private val wordItems: MutableList<WordItem>, private val activity: Activity) : RecyclerView.Adapter<VocabularyAdapter.ViewHolder>() {

    private lateinit var recyclerView: RecyclerView
    private var sortOrder:Int = 0

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
        holder.itemView.setOnClickListener { showPopupMenu(holder.itemView, position) }
        //todo set click listener to menu
    }

    override fun getItemCount(): Int { return wordItems.size }

    private fun showPopupMenu(v: View, position: Int) {
        val popup = PopupMenu(activity, v)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.word_item_menu, popup.menu)
        popup.setOnMenuItemClickListener {
            if (it.itemId == R.id.option_delete) { deleteWordItem(position) }
            if (it.itemId == R.id.option_edit) { editWordItem(v, wordItems[position]) }
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

    fun addWordItem(newWordItem: WordItem) {
        wordItems.add(0, newWordItem)
        this.sort()
    }

    fun updateWordItem(updatedWordItem: WordItem) {
        val updatedItemId = wordItems.indexOf(updatedWordItem)
        wordItems[updatedItemId] = updatedWordItem
        this.sort()
    }

    private fun sortByTranslation() {
        wordItems.sortWith(Comparator { item1, item2 ->
            item1.pojo.translation.compareTo(item2.pojo.translation) })
    }

    private fun sortByWord() {
        wordItems.sortWith(Comparator { item1, item2 ->
            item1.pojo.word.compareTo(item2.pojo.word) })
    }

    private fun sortByTime() {
        wordItems.sortWith(Comparator { item1, item2 ->
            -item1.pojo.time!!.compareTo(item2.pojo.time)
        })
        wordItems.forEach{ item -> Log.d(TAG, item.pojo.print())}
    }

    fun sort() {
        if (sortOrder == 2) sortOrder = 0
        else sortOrder++

        when(sortOrder) {
            0 -> sortByTime()
            1 -> sortByWord()
            2 -> sortByTranslation()
        }
        this.notifyDataSetChanged()
    }

    @SuppressLint("ResourceType")
    private fun editWordItem(container:View, wordItem: WordItem) {
        //set container id
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            container.id = View.generateViewId()
        } else container.id = 18071999

        // start EditWordFragment
        val wordInfoFragment = EditWordFragment()
        val arguments = Bundle()
        arguments.putSerializable("wordItem", wordItem)
        wordInfoFragment.arguments = arguments
        (activity as MainActivity).supportFragmentManager.beginTransaction().add(container.id, wordInfoFragment).commit()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvWord: TextView = itemView.findViewById(R.id.word)
        val tvTranslation: TextView = itemView.findViewById(R.id.translation)
        val layout: LinearLayout = itemView.findViewById(R.id.layout)
    }

    companion object { private val TAG = "VN/" + VocabularyAdapter::class.java.simpleName }
}