package fr.isen.benet.androiderestaurant



import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.squareup.picasso.Picasso

import fr.isen.benet.androiderestaurant.databinding.ActivityPlatBinding



class PlatActivity : AppCompatActivity() {

    var prix = 0.0
    var quantite=1

    lateinit var plat : RepasAffiche

    private lateinit var binding : ActivityPlatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.plat  =
            (intent.extras!!.getBinder("Plat") as ObjectWrapperForBinder?)!!.data as RepasAffiche

        this.title ="DroidRestaurant"

       //this.displayImage()

        var viewPager = findViewById<ViewPager>(R.id.viewpager)

        var mViewPagerAdapter = ViewPagerAdapter(this, this.plat.images)
        viewPager.adapter = mViewPagerAdapter


        binding.nomPlatDetail.text = this.plat.nom

        var listeIngredient = String()

        for(ingredient in this.plat.ingredient){

            listeIngredient += ingredient.name_fr + ", "
        }

        listeIngredient = listeIngredient.subSequence(0, listeIngredient.length-2) as String;

        this.prix = this.plat.prices

        binding.listeIngredients.text = listeIngredient

        binding.quantite.text = this.quantite.toString()

        binding.prix.text = this.prix.toString() + " €"

        binding.bouttonMoins.setOnClickListener(){
            if(this.quantite >1){
                this.quantite -= 1
                this.prix -= this.plat.prices
                binding.quantite.text = this.quantite.toString()
                binding.prix.text = this.prix.toString() + " €"
            }
        }

        binding.bouttonPlus.setOnClickListener(){

            this.quantite += 1
            this.prix += this.plat.prices
            binding.quantite.text = this.quantite.toString()
            binding.prix.text = this.prix.toString() + " €"

        }





    }

   /* fun displayImage(){

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
    }*/

}

