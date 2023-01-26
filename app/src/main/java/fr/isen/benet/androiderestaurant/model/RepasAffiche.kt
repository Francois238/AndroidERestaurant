package fr.isen.benet.androiderestaurant.model

import fr.isen.benet.androiderestaurant.model.Ingredient

class RepasAffiche (
    val nom : String,
    val categorie : String,
    val images : ArrayList<String>,
    val ingredient : ArrayList<Ingredient>,
    val prices : Double
)