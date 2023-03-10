package fr.isen.benet.androiderestaurant.model

data class  RepasDetail(
    val id : Int,
    val name_fr : String,
    val name_en : String,
    val id_category : Int,
    val categ_name_fr : String,
    val categ_name_en : String,
    val images : ArrayList<String>,
    val ingredients : ArrayList<Ingredient>,
    val prices : ArrayList<Prix>
)