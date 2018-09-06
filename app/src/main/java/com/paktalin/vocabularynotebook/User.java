package com.paktalin.vocabularynotebook;


import com.google.firebase.firestore.DocumentReference;
import java.util.List;

public class User {
    private final static String TAG = "VN/" + User.class.getSimpleName();

    private String email, name;
    private List<DocumentReference> vocabularies;

    public User(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DocumentReference> getVocabularies() {
        return vocabularies;
    }

    public void setVocabularies(List<DocumentReference> vocabularies) {
        this.vocabularies = vocabularies;
    }
}
