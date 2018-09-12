package com.paktalin.vocabularynotebook.firestoreitems

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
