package es.uam.dadm.sergiogarcia.projectcards.activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import es.uam.dadm.sergiogarcia.projectcards.R


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings,
                    SettingsFragment()
                )
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        PreferenceManager.setDefaultValues(
            this,
            R.xml.root_preferences,
            true
        )
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }

    companion object {
        val MAXIMUM_KEY = "max_number_cards"
        val MAXIMUM_DEFAULT = "20"
        val LOGGED = "isLogged"
        val LOGGED_DEFAULT = false
        val FIREBASE_ENABLED = "firebase"
        val FIREBASE_DEFAULT = true

        fun getMaximumNumberOfCards(context: Context): String? {
            return PreferenceManager
                .getDefaultSharedPreferences(context)
                .getString(
                    MAXIMUM_KEY,
                    MAXIMUM_DEFAULT
                )
        }

        fun setMaximumNumberOfCards(context: Context, max: String) {
            val sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putString(MAXIMUM_KEY, max)
            editor.commit ()
        }

        fun setLogged(context: Context, value: Boolean) {
            val sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putBoolean(LOGGED, value)
            editor.commit()
        }

        fun getLogged(context: Context) : Boolean?{
            return PreferenceManager
                .getDefaultSharedPreferences(context)
                .getBoolean(
                    LOGGED,
                    LOGGED_DEFAULT
                )
        }

        fun getFirebasePreference(context: Context) : Boolean? {
            return PreferenceManager
                .getDefaultSharedPreferences(context)
                .getBoolean(
                    FIREBASE_ENABLED,
                    FIREBASE_DEFAULT
                )
        }
    }
}