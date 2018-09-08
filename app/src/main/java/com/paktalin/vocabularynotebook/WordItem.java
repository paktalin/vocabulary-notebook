package com.paktalin.vocabularynotebook;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

public class WordItem implements Serializable {
    private static final String TAG = "VN/" + WordItem.class.getSimpleName();

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
    private String vocabularyId;
    private WordItemPojo pojo;

    public WordItem(String word, String translation, String id, String vocabularyId) {
        this.pojo = new WordItemPojo(word, translation);
        this.vocabularyId = vocabularyId;
        this.id = id;
    }

    public void delete() {
        FirebaseFirestore.getInstance().collection("vocabularies").document(vocabularyId)
                .collection("words").document(id).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i(TAG, "Successfully deleted word with id " + id);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "deleteWordWithId " + id + ":failure", e.fillInStackTrace());
            }
        });
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
