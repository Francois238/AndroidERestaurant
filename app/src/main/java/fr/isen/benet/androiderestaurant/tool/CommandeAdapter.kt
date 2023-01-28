package fr.isen.benet.androiderestaurant.tool

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.benet.androiderestaurant.R
import fr.isen.benet.androiderestaurant.model.PlatEnregistre

class CommandeAdapter (private val listCommade: List<PlatEnregistre>) : RecyclerView.Adapter<CommandeAdapter.ViewHolder>()
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
        val nomPlat: TextView = itemView.findViewById(R.id.nom_plat_commande)
        val imagePlat: ImageView = itemView.findViewById(R.id.icone_plat_commande)
        val prixPlat: TextView = itemView.findViewById(R.id.prix_plat_commande)
        val quantitePlat: TextView = itemView.findViewById(R.id.quantite_plat_commande)
        val imageSupprimer: ImageView = itemView.findViewById(R.id.icone_supprimer)


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
        val contactView = inflater.inflate(R.layout.item_commande, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get the data model based on position
        val commande: PlatEnregistre = listCommade[position]
        // Set item views based on your views and data model
        val nom = viewHolder.nomPlat
        nom.text = commande.nom

        val quantite = viewHolder.quantitePlat
        quantite.text = "x" + commande.quantite

        val prixView =viewHolder.prixPlat
        prixView.text = commande.prix.toString() + "€"

        val imageView = viewHolder.imagePlat

        var imagePlat = commande.image

        if (imagePlat =="") {
            imagePlat = "a"
        }

        Picasso.get().load(imagePlat)
            .error(R.drawable.image_accueil)
            .centerCrop()
            .fit()
            .into(imageView)



    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return listCommade.size
    }
}