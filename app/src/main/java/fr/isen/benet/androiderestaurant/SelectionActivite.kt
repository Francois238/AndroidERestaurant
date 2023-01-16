package fr.isen.benet.androiderestaurant

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.isen.benet.androiderestaurant.databinding.ActivitySelectionActiviteBinding


class SelectionActivite : AppCompatActivity() {

    //private val array :Array<String> = resources.getStringArray(R.array.listeEntrees)
    var categoryName = " "
    private lateinit var binding : ActivitySelectionActiviteBinding

    lateinit var dishes: ArrayList<Dish>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       /* binding = ActivitySelectionActiviteBinding.inflate(layoutInflater)

        setContentView(binding.root)

        this.categoryName = intent.getStringExtra("categoryName").toString()

        this.title = categoryName

        this.initialiserList();*/

        val arrayEntrees : kotlin.Array<kotlin.String> = resources.getStringArray(fr.isen.benet.androiderestaurant.R.array.listeEntrees)

        for(value in arrayEntrees){
            println(value)
        }
        // Lookup the recyclerview in activity layout
        val listePlat = findViewById<View>(R.id.listePlat) as RecyclerView
        // Initialize contacts
       // dishes= Dish.addDish(arrayEntrees)
        // Create adapter passing in the sample user data
        //val adapter = DishesAdapter(dishes)
        // Attach the adapter to the recyclerview to populate items
       // listePlat.adapter = adapter
        // Set layout manager to position the items
       // listePlat.layoutManager = LinearLayoutManager(this)

    }
/*
    fun initialiserList(){

        if (this.categoryName.equals("Entrees")){
            val arrayEntrees : kotlin.Array<kotlin.String> = resources.getStringArray(fr.isen.benet.androiderestaurant.R.array.listeEntrees)
            for( value in arrayEntrees){
                kotlin.io.println(value)
            }

            // Lookup the recyclerview in activity layout
            val listePlat = findViewById<View>(R.id.listePlat) as RecyclerView
            // Initialize contacts
            var dishes: ArrayList<Dish> = Dish.addDish(arrayEntrees)
            // Create adapter passing in the sample user data
            val adapter = DishesAdapter(dishes)
            // Attach the adapter to the recyclerview to populate items
            listePlat.adapter = adapter
            // Set layout manager to position the items
            listePlat.layoutManager = LinearLayoutManager(this)


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
*/



}