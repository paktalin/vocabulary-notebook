package com.paktalin.vocabularynotebook.firestoreitems

class Vocabulary(var words: MutableList<WordItem>) {
    companion object {
        private val TAG = "VN/" + Vocabulary::class.java.simpleName

        private const val SORT_BY_TIME = 0
        private const val SORT_BY_WORD = 1
        private const val SORT_BY_TRANSLATION = 2
    }

    var pojo:Pojo

    class Pojo(var title:String?) {
        init {
            if (title == null) title = "Untitled vocabulary"
        }
    }

    init {
        pojo = Pojo(null)
    }

    fun sort(sortOrder:Int) {
        when(sortOrder) {
            SORT_BY_TIME -> sortByTime()
            SORT_BY_WORD -> sortByWord()
            SORT_BY_TRANSLATION -> sortByTranslation()
        }
    }

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
