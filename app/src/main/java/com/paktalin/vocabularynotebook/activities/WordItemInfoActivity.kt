package com.paktalin.vocabularynotebook.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.item_delete -> {
                wordItem.delete()
                cancel()
            }
            R.id.item_edit -> {
                //todo edit item
            }
        }
        return true
    }

    private fun setData() {
        tvWord.text = wordItem.pojo!!.word
        tvTranslation.text = wordItem.pojo!!.translation
    }

    private fun cancel() {
        val intentMainActivity = Intent(this, MainActivity::class.java)
        startActivity(intentMainActivity)
    }

    companion object { private val TAG = "VN/" + WordItemInfoActivity::class.java.simpleName }
}