package es.uam.dadm.sergiogarcia.projectcards.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import es.uam.dadm.sergiogarcia.projectcards.R
import es.uam.dadm.sergiogarcia.projectcards.databinding.ActivityTitleBinding
import timber.log.Timber

class TitleActivity : AppCompatActivity() {
    lateinit var binding: ActivityTitleBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_title)

        NavigationUI.setupWithNavController(
            binding.navView,
            findNavController(R.id.navHostFragment))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.navHostFragment)
        return NavigationUI.navigateUp(navController, binding.drawerLayout)
    }


}