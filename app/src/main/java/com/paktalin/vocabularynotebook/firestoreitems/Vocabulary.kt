package com.paktalin.vocabularynotebook.firestoreitems

class Vocabulary(var words: MutableList<WordItem>) {
    companion object {
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

    fun sort(index:Int) {
        when(index) {
            SORT_BY_TIME -> sortByTime()
            SORT_BY_WORD -> sortByWord()
            SORT_BY_TRANSLATION -> sortByTranslation()
        }
    }

    private fun sortByTime() { }
    private fun sortByWord() { }
    private fun sortByTranslation() { }

}
