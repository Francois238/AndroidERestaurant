package fr.isen.benet.androiderestaurant


import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.Window.FEATURE_ACTION_BAR
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.gson.Gson
import fr.isen.benet.androiderestaurant.databinding.ActivityPlatBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class PlatActivity : AppCompatActivity() {

    var prix = 0.0
    var quantite=1
    var  tableauPlatEnregistre =ListPlatEnregistre(ArrayList<PlatEnregistre>())
    private val SHARED_PREF_USER_INFO = "SHARED_PREF_USER_INFO"
    private val SHARED_PREF_USER_INFO_NAME = "QUANTITE"

    lateinit var plat : RepasAffiche

    private lateinit var binding : ActivityPlatBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityPlatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.plat  =
            (intent.extras!!.getBinder("Plat") as ObjectWrapperForBinder?)!!.data as RepasAffiche


       val toolbar = findViewById<View>(R.id.toolbar) as Toolbar

        // using toolbar as ActionBar
        setSupportActionBar(toolbar)

      //  this.title = "DroidRestaurant"

      // supportActionBar?.setDisplayShowHomeEnabled(true);
      //  supportActionBar?.setIcon(R.drawable.image_accueil)

        val file = File(filesDir, "plats_commandes.json")

        if (file.exists()) {

            println("file exist")
            val inputStream = FileInputStream(file)

            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)

            val jsonString = String(buffer)

            val gson = Gson()

            val tabEnregistrement =  gson.fromJson(jsonString.toString(),ListPlatEnregistre::class.java)

            this.tableauPlatEnregistre = tabEnregistrement
            for(value in tableauPlatEnregistre.data){
                println("Nom : " + value.nom + "prix : "+value.prix  + "quantite : " + value.quantite)

            }

        }

        var viewPager = findViewById<ViewPager>(R.id.viewpager)

        var mViewPagerAdapter = ViewPagerAdapter(this, this.plat.images)
        viewPager.adapter = mViewPagerAdapter


        binding.nomPlatDetail.text = this.plat.nom

        var listeIngredient = String()

        for(ingredient in this.plat.ingredient){

            listeIngredient += ingredient.name_fr + ", "
        }

        listeIngredient = listeIngredient.subSequence(0, listeIngredient.length-2) as String;

        this.prix = this.plat.prices

        binding.listeIngredients.text = listeIngredient

        binding.quantite.text = this.quantite.toString()

        binding.ajouterPanier.text = this.prix.toString() + " €"

        binding.bouttonMoins.setOnClickListener(){
            if(this.quantite >1){
                this.quantite -= 1
                this.prix -= this.plat.prices
                binding.quantite.text = this.quantite.toString()
                binding.ajouterPanier.text = this.prix.toString() + " €"
            }
        }

        binding.bouttonPlus.setOnClickListener(){

            this.quantite += 1
            this.prix += this.plat.prices
            binding.quantite.text = this.quantite.toString()
            binding.ajouterPanier.text = this.prix.toString() + " €"


        }

        binding.ajouterPanier.setOnClickListener(){

            val platEnregistre = PlatEnregistre(this.plat.nom, this.prix, this.quantite)

           val file = File(filesDir, "plats_commandes.json")

            if (!file.exists()) {

                file.createNewFile();

                this.tableauPlatEnregistre.data = ArrayList<PlatEnregistre>()
            }

            this.tableauPlatEnregistre.data.add(platEnregistre)


            val gson = Gson()
            val jsonPlatEnregistre = gson.toJson(this.tableauPlatEnregistre)

            println("Le json : " + jsonPlatEnregistre)

            val outputStream = FileOutputStream(file)
            outputStream.write(jsonPlatEnregistre.toByteArray());

            outputStream.close();


            getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE)
                .edit()
                .putString(SHARED_PREF_USER_INFO_NAME,7.toString() )
                .apply();

            this.displayAlert()


        }


    }

    fun displayAlert(){
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

