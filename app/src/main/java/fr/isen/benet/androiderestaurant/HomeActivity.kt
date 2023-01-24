package fr.isen.benet.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import fr.isen.benet.androiderestaurant.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.entrees.setOnClickListener {
            Toast.makeText(this, "Entrees", Toast.LENGTH_SHORT).show()

            val intent = Intent(this@HomeActivity, SelectionActivity::class.java)

            intent.putExtra("categoryName", "Entrees")
            startActivity(intent)

        }

        binding.plats.setOnClickListener {
            Toast.makeText(this, "Plats", Toast.LENGTH_SHORT).show()

            val intent = Intent(this@HomeActivity, SelectionActivity::class.java)

            intent.putExtra("categoryName", "Plats")
            startActivity(intent)

        }

        binding.desserts.setOnClickListener {
            Toast.makeText(this, "Desserts", Toast.LENGTH_SHORT).show()

            val intent = Intent(this@HomeActivity, SelectionActivity::class.java)

            intent.putExtra("categoryName", "Desserts")
            startActivity(intent)

        }

    }


}