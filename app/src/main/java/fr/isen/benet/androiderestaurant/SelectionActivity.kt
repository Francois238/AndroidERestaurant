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
import fr.isen.benet.androiderestaurant.model.*
import fr.isen.benet.androiderestaurant.tool.ObjectWrapperForBinder
import fr.isen.benet.androiderestaurant.tool.RepasAdapter
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*
import kotlin.collections.ArrayList


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

        this.recupererCache() //On va regarder s'il y a du cache


    }

    private fun recupererCache(){

        val file = File(filesDir, "plats_cache")

        if (file.exists()) {

            val inputStream = FileInputStream(file)

            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)

            val jsonString = String(buffer)

            val gson = Gson()

            val platCache = gson.fromJson(jsonString, DataCache::class.java)

            //verifier si le timestamp est superieur à 5 minutes
            //On obtient le timestamp d'il y a 5 minutes

            val fiveMinutesAgo = Calendar.getInstance().apply { add(Calendar.MINUTE, -5) }.time

            val difference = platCache.timestamp - fiveMinutesAgo.time

            if (difference > 5 * 60 * 1000) { //Si la difference est superieure à 5 minutes

                this.recupererDataApi() //On va faire un appel sur l'api
            }

            else {
                this.creationListeDePlats(platCache.data.data) //Le cache est suffisament recent
            }
        }

        else { //Le fichier de cache n'existe pas
            this.recupererDataApi() //On va faire un appel sur l'api
        }

    }

    private fun recupererDataApi(){ //Fonction pour recuperer les donnees de l'api
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val `object` = JSONObject()
        try {

            `object`.put("id_shop", "1")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        var repas : RepasRecupere

        val url = "http://test.api.catering.bluecodegames.com/menu"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, `object`,
            { response ->

                val gson = Gson()

                repas =  gson.fromJson(response.toString(), RepasRecupere::class.java)
                println("repas : " + repas.data[0].name_fr)

                this.enregistrerCache(repas) //enregistrement en cache

                this.creationListeDePlats(repas.data)  //On va trier l'objet recu par l'api


            }
        ) { println("Error getting response") }
        requestQueue.add(jsonObjectRequest)



    }

    private fun enregistrerCache(repas : RepasRecupere){ //Fonction pour enregistrer les donnees de l'api en cache

        val file = File(filesDir, "plats_cache")

        if (!file.exists()) { // Si le fichier n'existe pas, on le cree

            file.createNewFile()
        }

        val gson = Gson()

        val jsonCache = gson.toJson(DataCache(repas, Date().time)) //On cree un objet qui contient les donnees de l'api et le timestamp

        val outputStream = FileOutputStream(file)
        outputStream.write(jsonCache.toByteArray()) //enregistrement des plats en cache

        outputStream.close()
    }

    private fun creationListeDePlats(repas : ArrayList<Repas>){

        for(item in repas){  //Creation d'un objet qui contient les donnees qui nous interesses
            for(value in item.items){
                this.tabDataApi.add(
                    RepasAffiche(value.name_fr, value.categ_name_fr,
                    value.images, value.ingredients, value.prices[0].price) )
            }
        }

        //Une fois notre objet cree, on va initialiser les differentes liste selon le type de plat
        this.initialiserList()

    }

    private fun initialiserList(){

        when (this.categoryName) {
            getString(R.string.entree) -> {

                val arrayDesserts = this.tabDataApi.filter { it.categorie == "Entrées"}


                this.displayList(arrayDesserts)

            }
            getString(R.string.plat) -> {

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

        // creation de l'adapter
        val adapter = RepasAdapter(dishes)

        val listePlats =findViewById<View>(R.id.listePlat) as RecyclerView

        // Faire le lien entre l'adapter et le recycler view
        listePlats.adapter = adapter
        // Affichage de la liste
        listePlats.layoutManager = LinearLayoutManager(this)

        adapter.setOnItemClickListener(object : RepasAdapter.OnItemClickListener {
            override fun onItemClick(itemView: View?, position: Int) {
                val plat = dishes[position]

                val bundle = Bundle()
                bundle.putBinder("Plat", ObjectWrapperForBinder(plat))

                startActivity(Intent(this@SelectionActivity, PlatActivity::class.java).putExtras(bundle)) //on passe l objet a la nouvelle activite
            }
        })


    }





}