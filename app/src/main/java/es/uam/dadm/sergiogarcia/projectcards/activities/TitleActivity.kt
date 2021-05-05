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

        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val reference = database.getReference("mensaje")
        reference.setValue("hola desde cards")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                Toast.makeText(
                    baseContext,
                    "${p0.value.toString()}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        NavigationUI.setupWithNavController(
            binding.navView,
            findNavController(R.id.navHostFragment))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.navHostFragment)
        return NavigationUI.navigateUp(navController, binding.drawerLayout)
    }

    override fun onPause() {
        super.onPause()
        Timber.i("OnPause")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("OnResume")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("OnStop")
    }

    override fun onStart() {
        super.onStart()
        Timber.i("onStart")
    }



}