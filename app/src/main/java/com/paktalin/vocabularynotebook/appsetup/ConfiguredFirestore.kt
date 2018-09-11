package com.paktalin.vocabularynotebook.appsetup

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

object ConfiguredFirestore {

    val instance: FirebaseFirestore
        get() {
            val firestore = FirebaseFirestore.getInstance()
            val settings = FirebaseFirestoreSettings.Builder()
                    .setTimestampsInSnapshotsEnabled(true)
                    .build()
            firestore.firestoreSettings = settings
            return firestore
        }
}
