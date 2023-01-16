package fr.isen.benet.androiderestaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(var dishes: ArrayList<String>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder.ViewHolder>() {

    class CategoryViewHolder(view : View){
        val cellName= view.findViewById<TextView>(R.id.cellName)
    }


    override fun onCreateViewHolder(parent : ViewGroup, viewType :Int) : CategoryViewHolder{
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_cell, parent, false)
        return CategoryViewHolder(view)

    }

    override fun onBindViewHolder(holder : CategoryViewHolder, position : Int){
        val dish = dishes[position]
        holder.cellName.text = dish
    }

    override fun getItemCount() : Int = dishes.size
}
