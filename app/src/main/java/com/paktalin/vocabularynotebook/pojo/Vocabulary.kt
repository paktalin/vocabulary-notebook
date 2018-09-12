package com.paktalin.vocabularynotebook.pojo

class Vocabulary {
    var pojo:Pojo

    class Pojo(var title:String?) {
        init {
            if (title == null) title = "Untitled vocabulary"
        }
    }

    init {
        pojo = Pojo(null)
    }
}
