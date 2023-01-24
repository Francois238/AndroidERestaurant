package fr.isen.benet.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import fr.isen.benet.androiderestaurant.databinding.ActivityCommandeBinding
import java.io.File
import java.io.FileInputStream

class CommandeActivity : AppCompatActivity() {

    private var  tableauPlatEnregistre =ArrayList<PlatEnregistre>()

    private val SHARED_PREF_USER_INFO = "SHARED_PREF_USER_INFO"
    private val SHARED_PREF_USER_INFO_NAME = "QUANTITE"

    private lateinit var binding : ActivityCommandeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommandeBinding.inflate(layoutInflater)


        setContentView(binding.root)

        binding.commander.text = "Commander "


        val file = File(filesDir, "plats_commandes.json")

        if (file.exists()) {

            println("file exist")
            val inputStream = FileInputStream(file)

            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)

            val jsonString = String(buffer)

            val gson = Gson()

            val tabEnregistrement =  gson.fromJson(jsonString,ListPlatEnregistre::class.java)

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

            binding.commander.setOnClickListener(){

                val file = File(filesDir, "plats_commandes.json")

                if (file.exists()) {
                    val result = file.delete()
                    if (result) {
                        this.displayAlert(true)

                        getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE)
                            .edit()
                            .putString(SHARED_PREF_USER_INFO_NAME,0.toString() )
                            .apply()

                    } else {

                        this.displayAlert(false)
                    }
                }

            }

        }


    }

    private fun displayAlert(flag : Boolean){
        // Create the object of AlertDialog Builder class
        val builder = AlertDialog.Builder(this)


        val message = if(flag)   "Votre commande a été enregistrée" else "Vous n'avez aucun article dans votre panier"


        // Set the message show for the Alert time
        builder.setMessage(message)

        // Set Alert Title
        builder.setTitle("Confirmation")

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false)


        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("Ok") {
            // If user click no then dialog box is canceled.
                dialog, which -> dialog.cancel()
        }

        // Create the Alert dialog
        val alertDialog = builder.create()
        // Show the Alert Dialog box
        alertDialog.show()

    }
}