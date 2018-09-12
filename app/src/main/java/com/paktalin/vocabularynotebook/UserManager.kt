package com.paktalin.vocabularynotebook

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.paktalin.vocabularynotebook.appsetup.ConfiguredFirestore
import com.paktalin.vocabularynotebook.ui.LogInActivity
import com.paktalin.vocabularynotebook.firestoreitems.UserPojo
import com.paktalin.vocabularynotebook.firestoreitems.Vocabulary
import java.util.*

class UserManager {
    companion object {
        private val TAG = "VN/" + UserManager::class.simpleName

        private fun deleteUser(user: FirebaseUser) {
            user.delete()
                    .addOnSuccessListener { Log.i(TAG, "UserPojo was successfully deleted") }
                    .addOnFailureListener { Log.i(TAG, "deleteUser:failure", it.cause)}
        }

        fun addNewUserToDb(newUser: FirebaseUser, logInActivity: LogInActivity) {
            //todo add condition to writing to the db in Firebase Console (request.auth.uid)
            val db = ConfiguredFirestore.instance
            val user = UserPojo(newUser.email)

            db.collection("vocabularies").add(Vocabulary.Pojo(null))
                    .addOnSuccessListener { firstVocabularyRef ->
                        Log.d(TAG, "VocabularyPojo successfully created: " + firstVocabularyRef.path)
                        user.vocabularies = Collections.singletonList(firstVocabularyRef)

                        db.collection("users").document(newUser.uid).set(user)
                                .addOnCompleteListener({ task ->
                                    if (task.isSuccessful) {
                                        Log.i(TAG, "Successfully added user to the collection")
                                        logInActivity.startUserActivity()
                                    }
                                    else Log.w(TAG, "addUser:failure", task.exception)
                                })
                    }
                    .addOnFailureListener {
                        Log.w(TAG, "Couldn't add user to the database", it.cause)
                        UserManager.deleteUser(newUser)
                    }
        }
    }
}
