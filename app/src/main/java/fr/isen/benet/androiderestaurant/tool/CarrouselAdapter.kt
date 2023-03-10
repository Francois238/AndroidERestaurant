package fr.isen.benet.androiderestaurant.tool

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso
import fr.isen.benet.androiderestaurant.R

class CarrouselAdapter (private val mContext: Context, private  val itemList: ArrayList<String>) : PagerAdapter() {
    private var layoutInflater: LayoutInflater? = null

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(mContext)
        val view = layoutInflater!!.inflate(R.layout.item_caroussel, container, false)

        if(itemList[position] == ""){
            itemList[position]="a"
        }

        val imageview: ImageView = view.findViewById(R.id.imageCarrousel)
        Picasso.get().
        load(itemList[position])
            .error(R.drawable.image_accueil)
            .centerCrop()
            .fit()
            .into(imageview)

        container.addView(view, position)
        return view
    }

    override fun getCount(): Int {
        return itemList.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }
}