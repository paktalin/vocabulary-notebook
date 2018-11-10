package com.paktalin.vocabularynotebook

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import com.paktalin.vocabularynotebook.firestoreitems.Vocabulary
import com.paktalin.vocabularynotebook.firestoreitems.WordItem
import com.paktalin.vocabularynotebook.ui.EditWordFragment
import com.paktalin.vocabularynotebook.ui.MainActivity
import kotlinx.android.synthetic.main.word_item.view.*

class VocabularyAdapter(private val displayedVocabulary: Vocabulary, private val mainActivity: MainActivity) : RecyclerView.Adapter<VocabularyAdapter.ViewHolder>() {

    private lateinit var recyclerView: RecyclerView

    private var sortOrder: Int = 0
        set(value) {
            field = value; sort()
        }

    private var vocabularyCopy: MutableList<WordItem> = mutableListOf() // stores all the words loaded from the db

    init {
        vocabularyCopy.addAll(displayedVocabulary.get())
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
        mainActivity.searchView.setOnQueryTextListener(OnQueryTextListener(this@VocabularyAdapter))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.word_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wordItem = displayedVocabulary.getAt(position)
        holder.tvWord.text = wordItem.pojo.word
        holder.tvTranslation.text = wordItem.pojo.translation
        holder.itemView.setOnClickListener { showPopupMenu(holder.itemView, position) }
        //todo set click listener to menu
    }

    override fun getItemCount(): Int {
        return displayedVocabulary.size()
    }

    private fun showPopupMenu(v: View, position: Int) {
        val popup = PopupMenu(mainActivity, v)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.word_item_menu, popup.menu)
        popup.setOnMenuItemClickListener {
            if (it.itemId == R.id.option_delete) { deleteWord(position) }
            if (it.itemId == R.id.option_edit) { editWord(v, displayedVocabulary.getAt(position)) }
            true
        }
        popup.show()
    }

    private fun deleteWord(position: Int) {
        displayedVocabulary.deleteWord(position)
        // update recyclerView
        recyclerView.removeViewAt(position)
        this.notifyItemRemoved(position)
        this.notifyItemRangeChanged(position, displayedVocabulary.size())
    }

    fun addWord(newWord: WordItem) {
        displayedVocabulary.addWord(newWord)
        this.sort()
    }

    fun updateWord(updatedWord: WordItem) {
        displayedVocabulary.updateWord(updatedWord)
        this.sort()
    }

    fun updateSortOrder() {
        if (sortOrder == 2) sortOrder = 0
        else sortOrder++
    }

    private fun sort() {
        displayedVocabulary.sort(sortOrder)
        this.notifyDataSetChanged()
    }

    @SuppressLint("ResourceType")
    private fun editWord(container: View, wordItem: WordItem) {
        //set container id
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            container.id = View.generateViewId()
        } else container.id = 18071999

        // start EditWordFragment
        val arguments = Bundle()
        arguments.putSerializable("wordItem", wordItem)
        addFragment(mainActivity.supportFragmentManager, EditWordFragment(), container.id, arguments)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvWord: TextView = itemView.word
        val tvTranslation: TextView = itemView.translation
        val layout: LinearLayout = itemView.layout
    }

    fun filter(query: String) {
        displayedVocabulary.clear()
        if (query.isEmpty())
            displayedVocabulary.addWords(vocabularyCopy)
        else
            displayedVocabulary.addWordsFittingQuery(vocabularyCopy, query.toLowerCase())
        notifyDataSetChanged()
    }

    companion object {
        private val TAG = "VN/" + VocabularyAdapter::class.java.simpleName
    }
}