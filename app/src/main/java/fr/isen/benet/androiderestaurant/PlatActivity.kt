package fr.isen.benet.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PlatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plat)

        val categoryName = intent.getStringExtra("Name").toString()

        this.title = categoryName

    }
}