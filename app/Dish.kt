import java.util.*

class Dish(val name: String) {

    companion object {

        fun addDish(nom: String): ArrayList<Dish> {
            val dish = ArrayList<Dish>()
            dish.add(Dish(nom))
            return dish
        }
    }
}