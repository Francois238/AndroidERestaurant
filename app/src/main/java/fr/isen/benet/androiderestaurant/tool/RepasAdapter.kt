package fr.isen.benet.androiderestaurant.tool

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.benet.androiderestaurant.R
import fr.isen.benet.androiderestaurant.model.RepasAffiche

class RepasAdapter (private val listDish: List<RepasAffiche>) : RecyclerView.Adapter<RepasAdapter.ViewHolder>()
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
        val nomPlat: TextView = itemView.findViewById(R.id.nomPlat)
        val imagePlat: ImageView = itemView.findViewById(R.id.iconePlat)
        val prixPlat: TextView = itemView.findViewById(R.id.prixPlat)


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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.item_plat, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get the data model based on position
        val dish: RepasAffiche = listDish[position]
        // Set item views based on your views and data model
        val textView = viewHolder.nomPlat
        textView.text = dish.nom

        val prixView =viewHolder.prixPlat
        prixView.text = dish.prices.toString() + "€"

        var image = dish.images.toList().first()

        if (image =="") {
            image = "a"
        }

        val imageView = viewHolder.imagePlat

        Picasso.get().load(image)
            .error(R.drawable.image_accueil)
            .centerCrop()
            .fit()
            .into(imageView)
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return listDish.size
    }


}
