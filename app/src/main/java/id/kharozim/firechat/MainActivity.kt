package id.kharozim.firechat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.kharozim.firechat.utils.PreferencesHelper

class MainActivity : AppCompatActivity() {

    private val auth by lazy { Firebase.auth }
    private val sharedpref by lazy { PreferencesHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.fragments.first().childFragmentManager.fragments.first()
        if (fragment is HomeFragment) {
            auth.signOut()
            sharedpref.onClear()
        }
        super.onBackPressed()
    }
}