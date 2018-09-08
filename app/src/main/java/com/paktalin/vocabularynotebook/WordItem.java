package com.paktalin.vocabularynotebook;

public class WordItem {
    public static class WordItemPojo {

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

    private String wordItemId;
    private WordItemPojo pojo;

    public WordItem(String word, String translation, String wordItemId) {
        this.pojo = new WordItemPojo(word, translation);
        this.wordItemId = wordItemId;
    }

    public WordItemPojo getPojo() {
        return pojo;
    }

    public void setPojo(WordItemPojo pojo) {
        this.pojo = pojo;
    }
}
