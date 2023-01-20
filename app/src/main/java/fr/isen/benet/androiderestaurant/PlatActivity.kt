package fr.isen.benet.androiderestaurant



import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

import fr.isen.benet.androiderestaurant.databinding.ActivityPlatBinding



class PlatActivity : AppCompatActivity() {

    lateinit var plat : RepasAffiche

    private lateinit var binding : ActivityPlatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.plat  =
            (intent.extras!!.getBinder("Plat") as ObjectWrapperForBinder?)!!.data as RepasAffiche

        this.title ="DroidRestaurant"

       // binding.nomPlat.text = categoryName

       this.displayImage()

    }

    fun displayImage(){

        var image = this.plat.images.filterNotNull().first()

        if (image =="") {
            image = R.drawable.image_accueil.toString()
        }

        val imagePlat: ImageView = findViewById<ImageView>(R.id.image_plat)

        Picasso.
        get().load(image)
            .centerCrop()
            .fit()
            .into(imagePlat);
    }

}

