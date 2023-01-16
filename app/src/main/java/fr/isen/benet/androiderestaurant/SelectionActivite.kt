package fr.isen.benet.androiderestaurant

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import fr.isen.benet.androiderestaurant.databinding.ActivitySelectionActiviteBinding


class SelectionActivite : AppCompatActivity() {

    //private val array :Array<String> = resources.getStringArray(R.array.listeEntrees)
    var categoryName = " "
    private lateinit var binding : ActivitySelectionActiviteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySelectionActiviteBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.listePlat.adapter = CategoryAdapter(arrayListOf<String>())

        this.categoryName = intent.getStringExtra("categoryName").toString()

        this.title = categoryName

        this.initialiserList();
    }

    fun initialiserList(){

        if (this.categoryName.equals("Entrees")){
            val arrayEntrees : kotlin.Array<kotlin.String> = resources.getStringArray(fr.isen.benet.androiderestaurant.R.array.listeEntrees)
            for( value in arrayEntrees){
                kotlin.io.println(value)
            }

        }

        else if (this.categoryName.equals("Plats")){
            val arrayPlats : kotlin.Array<kotlin.String> = resources.getStringArray(fr.isen.benet.androiderestaurant.R.array.listePlats)
            for( value in arrayPlats){
                kotlin.io.println(value)
            }
        }
        else{
            val arrayDesserts : kotlin.Array<kotlin.String> = resources.getStringArray(fr.isen.benet.androiderestaurant.R.array.listeDesserts)
            for( value in arrayDesserts){
                kotlin.io.println(value)
            }
        }

    }



}