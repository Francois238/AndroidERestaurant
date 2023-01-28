package fr.isen.benet.androiderestaurant


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.gson.Gson
import fr.isen.benet.androiderestaurant.databinding.ActivityPlatBinding
import fr.isen.benet.androiderestaurant.model.ListPlatEnregistre
import fr.isen.benet.androiderestaurant.model.PlatEnregistre
import fr.isen.benet.androiderestaurant.model.RepasAffiche
import fr.isen.benet.androiderestaurant.tool.CarrouselAdapter
import fr.isen.benet.androiderestaurant.tool.ObjectWrapperForBinder
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class PlatActivity : AppCompatActivity() {

    private var prix = 0.0
    private var quantite=1
    private var nbEnregistre=0
    private var  tableauPlatEnregistre = ListPlatEnregistre(ArrayList())
    private val SHARED_PREF_USER_INFO = "SHARED_PREF_USER_INFO"
    private val SHARED_PREF_USER_INFO_NAME = "QUANTITE"

    private lateinit var plat : RepasAffiche

    private lateinit var binding : ActivityPlatBinding


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityPlatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.plat  =
            (intent.extras!!.getBinder("Plat") as ObjectWrapperForBinder?)!!.data as RepasAffiche


       val toolbar = findViewById<View>(R.id.toolbar) as Toolbar

        setSupportActionBar(toolbar) //affichage de la toolbar personnalisée

        val file = File(filesDir, "plats_commandes.json")

        if (file.exists()) {

            println("file exist")
            val inputStream = FileInputStream(file)

            val buffer = ByteArray(inputStream.available()) //lecture du fichier
            inputStream.read(buffer)

            val jsonString = String(buffer) //On a le json sous forme de string

            val gson = Gson()

            val tabEnregistrement =  gson.fromJson(jsonString, ListPlatEnregistre::class.java)

            this.tableauPlatEnregistre = tabEnregistrement

        }

        val nbEnregistrementsPreference = getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE).getString( //on recupere le nombre d'enregistrement dans les preferences
            SHARED_PREF_USER_INFO_NAME,
            null
        )


        if(nbEnregistrementsPreference == null){
            this.nbEnregistre=0
        }

        else{
            this.nbEnregistre = nbEnregistrementsPreference.toInt()
        }
        binding.nbEnregistrement.text=this.nbEnregistre.toString()



        val viewPager = findViewById<ViewPager>(R.id.viewpager)

        val mCarrouselAdapter = CarrouselAdapter(this, this.plat.images)
        viewPager.adapter = mCarrouselAdapter


        binding.nomPlatDetail.text = this.plat.nom

        var listeIngredient = String()

        for(ingredient in this.plat.ingredient){

            listeIngredient += ingredient.name_fr + ", "
        }

        listeIngredient = listeIngredient.subSequence(0, listeIngredient.length-2) as String

        this.prix = this.plat.prices

        binding.listeIngredients.text = listeIngredient

        binding.quantite.text = this.quantite.toString()

        binding.ajouterPanier.text = this.prix.toString() + " €"

        binding.bouttonMoins.setOnClickListener {
            if(this.quantite >1){
                this.quantite -= 1
                this.prix -= this.plat.prices
                binding.quantite.text = this.quantite.toString()
                binding.ajouterPanier.text = this.prix.toString() + " €"
            }
        }

        binding.bouttonPlus.setOnClickListener {

            this.quantite += 1
            this.prix += this.plat.prices
            binding.quantite.text = this.quantite.toString()
            binding.ajouterPanier.text = this.prix.toString() + " €"


        }

        binding.ajouterPanier.setOnClickListener {

            val platEnregistre = PlatEnregistre(this.plat.nom, this.prix,this.quantite, this.plat.images[0])

           val file = File(filesDir, "plats_commandes.json")

            if (!file.exists()) {

                file.createNewFile()

                this.tableauPlatEnregistre.data = ArrayList()
            }

            this.tableauPlatEnregistre.data.add(platEnregistre)

            this.nbEnregistre = this.tableauPlatEnregistre.data.size


            val gson = Gson()
            val jsonPlatEnregistre = gson.toJson(this.tableauPlatEnregistre)

            val outputStream = FileOutputStream(file) //enregistrement des commandes dans le fichier
            outputStream.write(jsonPlatEnregistre.toByteArray())

            outputStream.close()


            getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE)
                .edit()
                .putString(SHARED_PREF_USER_INFO_NAME,this.nbEnregistre.toString() )
                .apply()
            binding.nbEnregistrement.text =this.nbEnregistre.toString()

            this.displayAlert()


        }

        binding.panier.setOnClickListener {


            val intent = Intent(this@PlatActivity, CommandeActivity::class.java)

            startActivity(intent)

        }


    }

    private fun displayAlert(){
        // Create the object of AlertDialog Builder class
        val builder = AlertDialog.Builder(this)

        val message = "Le " + this.plat.nom + " a bien été ajouté à votre panier"

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

