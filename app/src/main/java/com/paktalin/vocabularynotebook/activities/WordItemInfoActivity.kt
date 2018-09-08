package com.paktalin.vocabularynotebook.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.paktalin.vocabularynotebook.R
import com.paktalin.vocabularynotebook.WordItem
import kotlinx.android.synthetic.main.activity_word_info.*

class WordItemInfoActivity: AppCompatActivity() {

    private lateinit var wordItem: WordItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_info)
        wordItem = intent.getSerializableExtra("wordItem") as WordItem
        setData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.word_item_info_menu, menu)
        return true
    }

    private fun setData() {
        tvWord.text = wordItem.pojo!!.word
        tvTranslation.text = wordItem.pojo!!.translation
    }

    companion object { private val TAG = "VN/" + WordItemInfoActivity::class.java.simpleName }
}