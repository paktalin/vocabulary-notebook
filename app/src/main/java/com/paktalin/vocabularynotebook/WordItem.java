package com.paktalin.vocabularynotebook;

import java.io.Serializable;

public class WordItem implements Serializable {
    public static class WordItemPojo implements Serializable{

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

    private String id;
    private WordItemPojo pojo;

    public WordItem(String word, String translation, String id) {
        this.pojo = new WordItemPojo(word, translation);
        this.id = id;
    }

    public WordItemPojo getPojo() {
        return pojo;
    }

    public void setPojo(WordItemPojo pojo) {
        this.pojo = pojo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
