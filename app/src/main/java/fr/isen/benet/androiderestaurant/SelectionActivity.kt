package fr.isen.benet.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.benet.androiderestaurant.databinding.ActivitySelectionBinding
import fr.isen.benet.androiderestaurant.model.Repas
import fr.isen.benet.androiderestaurant.model.RepasAffiche
import fr.isen.benet.androiderestaurant.model.RepasRecupere
import org.json.JSONException
import org.json.JSONObject


class SelectionActivity : AppCompatActivity() {

    private var categoryName = " "
    private lateinit var binding : ActivitySelectionBinding

    private val tabDataApi = ArrayList<RepasAffiche>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySelectionBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_selection)


        this.categoryName = intent.getStringExtra("categoryName").toString()

        this.title = categoryName

        this.recupererDataApi() //On va faire un appel sur l'api


    }

    private fun recupererDataApi(){ //Fonction pour recuperer les donnees de l'api
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val `object` = JSONObject()
        try {
            //input your API parameters
            `object`.put("id_shop", "1")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        var repas : RepasRecupere

        val url = "http://test.api.catering.bluecodegames.com/menu"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, `object`,
            { response ->

                //println("String Response : $response")
                val gson = Gson()

                repas =  gson.fromJson(response.toString(), RepasRecupere::class.java)
                println("repas : " + repas.data[0].name_fr)

                this.creationListeDePlats(repas.data)  //On va trier l'objet recu par l'api


            }
        ) { println("Error getting response") }
        requestQueue.add(jsonObjectRequest)



    }

    private fun creationListeDePlats(repas : ArrayList<Repas>){

        for(item in repas){  //Creation d'un objet qui contient les donnees qui nous interesses
            for(value in item.items){
                this.tabDataApi.add(
                    RepasAffiche(value.name_fr, value.categ_name_fr,
                    value.images, value.ingredients, value.prices[0].price) )
            }
        }

        for(value in this.tabDataApi){

            println("Nom " + value.nom + "categorie : " + value.categorie)
        }
        //Une fois notre objet cree, on va initialiser les differentes liste selon le type de plat
        this.initialiserList()


    }

    private fun initialiserList(){

        when (this.categoryName) {
            "Entrees" -> {

                val arrayDesserts = this.tabDataApi.filter { it.categorie == "Entrées"}


                this.displayList(arrayDesserts)

            }
            "Plats" -> {

                val arrayDesserts = this.tabDataApi.filter { it.categorie == "Plats"}


                this.displayList(arrayDesserts)
            }
            else -> {

                val arrayDesserts = this.tabDataApi.filter { it.categorie == "Desserts"}


                this.displayList(arrayDesserts)


            }
        }

    }

    private fun displayList(dishes : List<RepasAffiche>){ //Affichage de entrées, plats, desserts sur l'écran

        // Create adapter passing in the sample user data
        val adapter = RepasAdapter(dishes)

        val listePlats =findViewById<View>(R.id.listePlat) as RecyclerView

        // Attach the adapter to the recyclerview to populate items
        listePlats.adapter = adapter
        // Set layout manager to position the items
        listePlats.layoutManager = LinearLayoutManager(this)

        adapter.setOnItemClickListener(object : RepasAdapter.OnItemClickListener {
            override fun onItemClick(itemView: View?, position: Int) {
                val plat = dishes[position]

                val bundle = Bundle()
                bundle.putBinder("Plat", ObjectWrapperForBinder(plat))

                startActivity(Intent(this@SelectionActivity, PlatActivity::class.java).putExtras(bundle))
            }
        })


    }





}