package fr.isen.benet.androiderestaurant.model

data class Repas (

    val name_fr : String,
    val name_en : String,
    val items : ArrayList<RepasDetail>
        )
