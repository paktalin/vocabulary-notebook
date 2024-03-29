package com.paktalin.vocabularynotebook.firestoreitems

class Vocabulary(words: MutableList<WordItem>) {
    companion object {
        private val TAG = "VN/" + Vocabulary::class.java.simpleName

        private const val SORT_BY_TIME = 0
        private const val SORT_BY_WORD = 1
        private const val SORT_BY_TRANSLATION = 2

        const val WORDS = "words"
        const val VOCABULARIES = "vocabularies"
    }

    var pojo:Pojo
    private var words: MutableList<WordItem>

    class Pojo(var title:String?) {
        init {
            if (title == null) title = "Untitled vocabulary"
        }
    }

    init {
        this.pojo = Pojo(null)
        this.words = words
    }

    fun sort(sortOrder:Int) {
        when(sortOrder) {
            SORT_BY_TIME -> sortByTime()
            SORT_BY_WORD -> sortByWord()
            SORT_BY_TRANSLATION -> sortByTranslation()
        }
    }

    fun deleteWord(position:Int) {
        words[position].delete() // delete word from the database
        words.removeAt(position) // delete word from the list
    }

    fun addWord(newWord:WordItem) {
        words.add(0, newWord)
    }

    fun addWords(newWords:MutableList<WordItem>) {
        words.addAll(newWords)
    }

    fun addWordsFittingQuery(newWords:MutableList<WordItem>, query:String) {
        for (newWord in newWords) {
            if (newWord.contains(query))
                this.addWord(newWord)
        }
    }

    fun updateWord(updatedWord:WordItem) {
        val updatedItemIndex = words.indexOf(updatedWord)
        words[updatedItemIndex] = updatedWord
    }

    fun getAt(position: Int):WordItem {
        return words[position]
    }

    fun get():MutableList<WordItem> { return words }

    fun size():Int { return words.size }

    fun clear() { words.clear() }

    private fun sortByTime() {
        words.sortWith(Comparator { item1, item2 ->
            -item1.pojo.time!!.compareTo(item2.pojo.time) })
    }
    private fun sortByWord() {
        words.sortWith(Comparator { item1, item2 ->
            item1.pojo.word.compareTo(item2.pojo.word) })
    }
    private fun sortByTranslation() {
        words.sortWith(Comparator { item1, item2 ->
            item1.pojo.translation.compareTo(item2.pojo.translation) })
    }
}