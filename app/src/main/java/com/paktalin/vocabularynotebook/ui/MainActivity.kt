package com.paktalin.vocabularynotebook.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference

import com.paktalin.vocabularynotebook.R
import kotlinx.android.synthetic.main.activity_main.*
import android.view.WindowManager
import android.app.Activity
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.paktalin.vocabularynotebook.VocabularyAdapter
import com.paktalin.vocabularynotebook.appsetup.ConfiguredFirestore
import kotlinx.android.synthetic.main.fragment_vocabulary.*
import android.support.v4.view.MenuItemCompat.getActionView
import android.content.Context.SEARCH_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.app.SearchManager
import android.content.Context
import android.widget.SearchView


class MainActivity : AppCompatActivity() {

    lateinit var vocabularyId: String
    lateinit var vocabularyFragment: VocabularyFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hideKeyboard()
        setUpNavigationView()
        extractVocabularyData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu!!.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.sort)
            (recyclerView.adapter as VocabularyAdapter).updateSortOrder()
        return super.onOptionsItemSelected(item)
    }

    private fun logOut() {
        Log.i(TAG, "User logged out")
        FirebaseAuth.getInstance()!!.signOut()
        val intentLogInActivity = Intent(this@MainActivity, LogInActivity::class.java)
        startActivity(intentLogInActivity)
    }

    private fun setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            if(menuItem.itemId == R.id.logOut) { logOut() }
            drawerLayout!!.closeDrawers()
            true
        }
    }

    private fun extractVocabularyData() {
        val userId = FirebaseAuth.getInstance()!!.currentUser!!.uid
        val db = ConfiguredFirestore.instance

        val userDocument = db.collection("users").document(userId)
        showProgressBar()
        userDocument.get()
                .addOnSuccessListener { task ->
                    hideProgressBar()
                    //todo move Firestore logic and collections names to a separate class
                    if (task.get("vocabularies") != null) {
                        val vocabularies: List<DocumentReference> = task.get("vocabularies") as List<DocumentReference>
                        val vocabulary = db.collection("vocabularies").document(vocabularies[0].id)
                        vocabularyId = vocabulary.id

                        // start VocabularyFragment
                        vocabularyFragment = VocabularyFragment()
                        val arguments = Bundle()
                        arguments.putString("vocabularyId", vocabularyId)
                        vocabularyFragment.arguments = arguments
                        supportFragmentManager.beginTransaction().add(R.id.fragment_container, vocabularyFragment)
                                .commitNowAllowingStateLoss()
                    } else {
                        Log.w(TAG, "There's no collection \"vocabularies\"")
                        showToastNoWords() }
        }
    }

    private fun hideKeyboard() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    fun hideKeyboardNotFromActivity(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) { view = View(activity) }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showProgressBar() { progress.visibility = View.VISIBLE }

    fun hideProgressBar() { progress.visibility = View.GONE }

    fun showToastNoWords() {
        Toast.makeText(this@MainActivity,
                "You don't have any words yet. Add your fist one!", Toast.LENGTH_SHORT).show()
    }

    fun removeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss()
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    companion object { private val TAG = "VN/" + MainActivity::class.simpleName }
}