package com.paktalin.vocabularynotebook.pojo;

public class WordItemPojo {

    private String word, translation;

    public WordItemPojo(String word, String translation) {
        this.word = word;
        this.translation = translation;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
