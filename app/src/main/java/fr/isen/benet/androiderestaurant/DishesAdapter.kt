package fr.isen.benet.androiderestaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class DishesAdapter (private val listDish: List<RepasAffiche>) : RecyclerView.Adapter<DishesAdapter.ViewHolder>()
{
    // Define the listener interface
    interface OnItemClickListener {
        fun onItemClick(itemView: View?, position: Int)
    }

    // Define listener member variable
    private lateinit var listener: OnItemClickListener

    // Define the method that allows the parent activity or fragment to define the listener
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomPlat: TextView = itemView.findViewById(R.id.nom_plat)
        val imagePlat: ImageView = itemView.findViewById(R.id.icone_plat)
        val prixPlat: TextView = itemView.findViewById(R.id.prix_plat)


        init {
            // Setup the click listener
            itemView.setOnClickListener {
                // Triggers click upwards to the adapter on click
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(itemView, position)
                }
            }
        }
    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishesAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.item_plat, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: DishesAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val dish: RepasAffiche = listDish[position]
        // Set item views based on your views and data model
        val textView = viewHolder.nomPlat
        textView.text = dish.nom

        val prixView =viewHolder.prixPlat
        prixView.text = dish.prices.toString() + "€"

        var image = dish.images.filterNotNull().first()

        if (image =="" || image==null) {
            image = R.drawable.image_accueil.toString()
        }

        val imageView = viewHolder.imagePlat

        Picasso.get().load(image)
            .centerCrop()
            .resize(150, 150)
            .into(imageView);
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return listDish.size
    }


}
