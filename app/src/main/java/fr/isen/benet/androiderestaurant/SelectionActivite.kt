package fr.isen.benet.androiderestaurant

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.isen.benet.androiderestaurant.databinding.ActivityHomeBinding
import fr.isen.benet.androiderestaurant.databinding.ActivitySelectionActiviteBinding


class SelectionActivite : AppCompatActivity() {

    var categoryName = " "
    private lateinit var binding : ActivitySelectionActiviteBinding

    var arrayPlats = ArrayList<Dish>();

    lateinit var dishes: ArrayList<Dish>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySelectionActiviteBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_selection_activite);

        this.categoryName = intent.getStringExtra("categoryName").toString()

        this.title = categoryName

        arrayPlats = this.initialiserList();




    }

    fun initialiserList() : ArrayList<Dish>{

        if (this.categoryName.equals("Entrees")){


            val arrayEntrees : kotlin.Array<kotlin.String> = resources.getStringArray(fr.isen.benet.androiderestaurant.R.array.listeEntrees)

            // Lookup the recyclerview in activity layout
            // Initialize contacts
            dishes= Dish.addDish(arrayEntrees)

            this.displayList(dishes)

            return dishes


        }

        else if (this.categoryName.equals("Plats")){


            val arrayPlats : kotlin.Array<kotlin.String> = resources.getStringArray(fr.isen.benet.androiderestaurant.R.array.listePlats)

            // Lookup the recyclerview in activity layout
            // Initialize contacts
            dishes= Dish.addDish(arrayPlats)

            this.displayList(dishes)
            return dishes
        }
        else{


            val arrayDesserts : kotlin.Array<kotlin.String> = resources.getStringArray(fr.isen.benet.androiderestaurant.R.array.listeDesserts)

            // Lookup the recyclerview in activity layout
            // Initialize contacts
            dishes= Dish.addDish(arrayDesserts)

            this.displayList(dishes)
            return dishes


        }

    }

    fun displayList(dishes : ArrayList<Dish>){

        // Create adapter passing in the sample user data
        val adapter = DishesAdapter(dishes)

        val listePlats =findViewById<View>(R.id.listePlat) as RecyclerView

        // Attach the adapter to the recyclerview to populate items
        listePlats.adapter = adapter
        // Set layout manager to position the items
        listePlats.layoutManager = LinearLayoutManager(this)

        adapter.setOnItemClickListener(object : DishesAdapter.OnItemClickListener {
            override fun onItemClick(itemView: View?, position: Int) {
                val name = dishes[position].name
                Toast.makeText(this@SelectionActivite, "$name was clicked!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@SelectionActivite, PlatActivity::class.java);

                intent.putExtra("Name", name)
                startActivity(intent);
            }
        })


    }





}