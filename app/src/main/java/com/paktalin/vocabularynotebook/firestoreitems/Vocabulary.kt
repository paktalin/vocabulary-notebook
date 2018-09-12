package com.paktalin.vocabularynotebook.firestoreitems

class Vocabulary {
    var pojo:Pojo
    private lateinit var words:MutableList<WordItem>

    class Pojo(var title:String?) {
        init {
            if (title == null) title = "Untitled vocabulary"
        }
    }

    init {
        pojo = Pojo(null)
    }


}
