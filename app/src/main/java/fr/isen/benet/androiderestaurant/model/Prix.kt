package fr.isen.benet.androiderestaurant.model

data class Prix (
    val id : Int,
    val id_pizza : Int,
    val id_size : Int,
    val price : Double,
    val create_date : String,
    val update_date : String,
    val size : String
)