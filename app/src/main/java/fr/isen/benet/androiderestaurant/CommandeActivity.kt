package fr.isen.benet.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.io.File
import java.io.FileInputStream

class CommandeActivity : AppCompatActivity() {

    var  tableauPlatEnregistre =ArrayList<PlatEnregistre>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commande)

        val file = File(filesDir, "plats_commandes.json")

        if (file.exists()) {

            println("file exist")
            val inputStream = FileInputStream(file)

            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)

            val jsonString = String(buffer)

            val gson = Gson()

            val tabEnregistrement =  gson.fromJson(jsonString.toString(),ListPlatEnregistre::class.java)

            this.tableauPlatEnregistre = tabEnregistrement.data
            for(value in tableauPlatEnregistre){
                println("Nom : " + value.nom + "prix : "+value.prix  + "quantite : " + value.quantite)

            }

            // Create adapter passing in the sample user data
            val adapter = CommandeAdapter(this.tableauPlatEnregistre)

            val listePlats =findViewById<View>(R.id.listeCommande) as RecyclerView

            // Attach the adapter to the recyclerview to populate items
            listePlats.adapter = adapter
            // Set layout manager to position the items
            listePlats.layoutManager = LinearLayoutManager(this)

        }
    }
}